package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseHandler;
import model.BuildingCandidate;

public class CourseCrawling {

	private static final int TIMEOUT = 2000000;
	private List<String> courseLinks, names, schools;
	private String currentSchool = "";
	private DatabaseHandler dbh;

	public CourseCrawling() {
		courseLinks = new ArrayList<>();
		names = new ArrayList<>();
		schools = new ArrayList<>();
		dbh = DatabaseHandler.getOneInstance();
	}

	/**
	 * Output web crawling result.
	 * 
	 * @param url
	 */
	public void start(String url, String building) {

		// Connect to DB
		dbh.connect();

		// Update building table
		crawlBuildingJS(building);

		// Update course table
		crawlTermPage(url);
		SectionCrawler[] threads = new SectionCrawler[courseLinks.size()];
		int i = 0;
		while (!courseLinks.isEmpty()) {
			String link = courseLinks.remove(0); // Course page link
			String name = names.remove(0); // Major full name
			String school = schools.remove(0);
			threads[i] = new SectionCrawler(link, name, school, i);
			i++;
		}
		for (i = 0; i < threads.length; i++) {
			threads[i].start();
		}

		for (i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Close DB connection
		dbh.close();
	}

	private void crawlBuildingJS(String link) {
		try {
			BufferedReader reader = getReader(link);
			if (reader != null) {
				System.out.println("Adding buildings ...");
				String line = reader.readLine();

				// Process building info.
				String startLocation = "\"code\":";
				while (line.contains(startLocation)) {
					if (line.indexOf("\"code\":null") >= 0
							&& line.indexOf("\"code\":null") < line.indexOf(startLocation)) {
						line = line.substring(line.indexOf("\"code\":null") + (new String("\"code\":null")).length());
					} else {
						// Get a new string line and update the original line.
						line = line.substring(line.indexOf(startLocation) + (new String("\"code\":")).length());

						// Get [String ID, fullName, address; float longitude, latitude;]
						String id = line.substring(line.indexOf("\"") + 1, line.indexOf("\","));
						String fullName = line.substring(
								line.indexOf("\"name\":\"") + (new String("\"name\":\"")).length(),
								line.indexOf("\",\"short\":\""));
						String address = line.substring(
								line.indexOf("\"address\":\"") + (new String("\"address\":\"")).length(),
								line.indexOf("\",\"accessMap\":\""));
						// Check whether it is a building
						if (id.length() == 3) {
							float longitude = Float.parseFloat(line.substring(
									line.indexOf("\"longitude\":\"") + (new String("\"longitude\":\"")).length(),
									line.indexOf("\",\"photo\":")));
							float latitude = Float.parseFloat(line.substring(
									line.indexOf("\"latitude\":\"") + (new String("\"latitude\":\"")).length(),
									line.indexOf("\",\"longitude\":\"")));
							dbh.addBuilding(new BuildingCandidate(id, fullName, address, longitude, latitude));
						}
					}
				}
				// Add TBA
				dbh.addBuilding(new BuildingCandidate("TBA", "TBA", "TBA"));
				System.out.println("Finish adding buildings!");

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static BufferedReader getReader(String link) throws IOException {
		BufferedReader reader = null, tmp = null;
		try {
			URL url = new URL(link);
			// Initialize a HTTP connection.
			HttpURLConnection connection = null;
			while (connection == null || connection.getResponseCode() != 200) {
				connection = (HttpURLConnection) url.openConnection();
				// Initialize a GET request.
				connection.setRequestMethod("GET");
				// Set up time out values.
				connection.setConnectTimeout(TIMEOUT);
				connection.setReadTimeout(TIMEOUT);
			}
			// If successfully connected, then get the data from the web page.
			if (connection.getResponseCode() == 200) {
				System.out.println("\tSuccessfully connected! " + url);
				InputStream inputStream = connection.getInputStream();
				tmp = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			reader = tmp;
		}
		return reader;
	}

	/**
	 * 
	 * @param host
	 * @param linkMap
	 * 
	 * @return
	 */
	private void crawlTermPage(String link) {
		try {
			BufferedReader reader = getReader(link);
			if (reader != null) {
				String line = "";
				while ((line = reader.readLine()) != null) {
					while (isCourseOption(line)) {

						// Trim new lines to get abbreviation, name pairs
						int startIndex = line.indexOf("<option value=");
						int endIndex = line.indexOf("</option>") + (new String("</option>")).length();
						String newLine = line.substring(startIndex, endIndex);
						// Update line
						line = line.substring(endIndex);

						// Skip all department options, ge, nursing courses, and graduate studies
						// courses.
						if (!newLine.contains("disabled") && !newLine.contains("Graduate Studies")
								&& !newLine.contains("Nursing") && !newLine.contains("Category ")) {
							// Process abbreviation and course name
							startIndex = newLine.indexOf("value=\"") + (new String("value=\"")).length();
							endIndex = newLine.indexOf("\">- ");
							String abbreviation = newLine.substring(startIndex, endIndex);
							endIndex = newLine.indexOf(" |");
							startIndex = newLine.indexOf("\">- ") + 4;
							// String courseName = newLine.substring(startIndex, endIndex);
							// System.out.println(abbreviation + ": " + courseName);

							// Add links into course links map
							courseLinks.add(link + "/classes/" + abbreviation);
							names.add(abbreviation.toUpperCase());
							schools.add(currentSchool);
						} else if (newLine.contains("disabled")) {
							/*
							 * Update current school
							 */
							startIndex = newLine.indexOf("disabled=\"disabled\">")
									+ (new String("disabled=\"disabled\">")).length();
							endIndex = newLine.indexOf("</option>");
							currentSchool = newLine.substring(startIndex, endIndex);
						}

					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isCourseOption(String line) {
		if (line.contains("<option value=\"") && line.contains("\">- ") && line.contains("</option>"))
			return true;
		return false;
	}

}
