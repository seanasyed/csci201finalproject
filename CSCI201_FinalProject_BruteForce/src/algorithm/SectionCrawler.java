/**
 * Group 14
 * 
 * CP: Aya Shimizu (ashimizu@usc.edu)
 * Yiyang Hou (yiyangh@usc.edu)
 * Sean Syed (seansyed@usc.edu)
 * Eric Duguay (eduguay@usc.edu)
 * Xing Gao (gaoxing@usc.edu)
 * Sangjun Lee (sangjun@usc.edu)
 * 
 */

package algorithm;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseHandler;
import model.Course;
import model.Section;

public class SectionCrawler extends Thread {
	
	private DatabaseHandler dbh = DatabaseHandler.getOneInstance();
	private String line, school, major;
	private int threadNum;
	
	private static final int SLEEPTIME = 1000;
	
	public SectionCrawler(String line, String major, String school, int num) {
		this.line = line;
		this.school = school;
		this.major = major;
		threadNum = num;
	}
	
	public void processCourseLine(String line, String school, String major) {

		String startLocation = "<div class=\"course-info expandable\" id=\"";
		String endLocation = "</td></tr></table></div></div>";
		while (line.contains(startLocation)) {
			int startIndex = line.indexOf(startLocation) + startLocation.length();
			int endIndex = line.indexOf(endLocation) + endLocation.length();
			// Trim the given {@line} and get the new line
			String newLine = line.substring(startIndex, endIndex);		
			line = line.substring(endIndex);
					
			// Update each course under given {@code major}
			int courseID = addCourseToDB(newLine, school, major);
			
			try {
				Thread.sleep(SLEEPTIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Process section for each course 
			addSectionToDB(newLine, major, courseID);
			
		}	
	}
	
	private boolean isCourseDescription(String line) {
		if (line.contains("<div class=\"course-info expandable\""))
			return true;
		return false;
	}
	
	public void run() {
		System.out.println("\tThread " + threadNum + "\tStart processing: " + line + "\t" + major);		
		try {
			BufferedReader reader = CourseCrawling.getReader(line);
			if (reader != null) {
				String line = "";     
				while ((line = reader.readLine()) != null) {
					if (isCourseDescription(line)) {
						processCourseLine(line, school, major);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("\tThread " + threadNum + "\tend\n\n");		
		}
//		try {
//			Thread.sleep(SLEEPTIME);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
	}

	private void addSectionToDB(String line, String major, int courseID) {
		if (courseID < 0) return;
		System.out.println("\t\t\t\tRequesting data for course sections...");
		String startLocation = "<tr data-section-id=", endLocation = "</tr>";
		List<Section> sections = new ArrayList<>();
		List<Section> lectures = new ArrayList<>();
		// Assume all labs, discussions, and quizzes can be binded with all lectures.
		List<String> lectureSection_IDs = new ArrayList<>();
		while (line.contains(startLocation)) {
			String tmp = line.substring(line.indexOf(startLocation));
			line = tmp.substring(tmp.indexOf(endLocation));
			String sectionID = getInnerHTMLByClassName(tmp, "section");
			if (sectionID.length() > 0) {
				String type = getInnerHTMLByClassName(tmp, "type");
				if (type.contains("Lecture")) type = "Lecture";
				// Process time {[x]x:xx-[x]x:xx(a/p)m}
				String[] times = processTime(getInnerHTMLByClassName(tmp, "time"));	
				if (times != null) {
					// Process days
					String days = processDays(getInnerHTMLByClassName(tmp, "days").toUpperCase());
					if (days != null) {
						// Process instructor
						String instructor = processInstructor(getInnerHTMLByClassName(tmp, "instructor"));
						// Process class capacity
						String classCapacityString = getInnerHTMLByClassName(tmp, "registered");
						classCapacityString = classCapacityString.substring(classCapacityString.indexOf("of ") + 3);
						classCapacityString = classCapacityString.substring(0, classCapacityString.indexOf("<"));
						int classCapacity = classCapacityString.contains(":") || classCapacityString.contains(" ") ? 0 : Integer.parseInt(classCapacityString);
						if (classCapacity > 0) {
							String building_ID = tmp.contains("\"map\"") ? getInnerHTMLByClassName(tmp, "map").substring(0,3) : "TBA";
							
							Section section = new Section(sectionID, type, times[0], times[1], days,
									instructor, building_ID, classCapacity, courseID);
							// Add lecture id
							if (section.isLecture()) {
								lectures.add(section);
								lectureSection_IDs.add(sectionID);
							} else {
								// Add a new section object
								sections.add(section);
							}
						}
					}
				}
			}
		}
		
		// Write data into DB
		System.out.println("\t\t\t\tWriting data into DB for course sections...");
		for (int i = 0; i < lectures.size(); i++) {
			dbh.addSection(lectures.get(i));
		}
		for (int i = 0; i < sections.size() && lectures.size() > 0; i++) {
			Section section = sections.get(i);
			if (!section.isLecture()) {
				for (int j = 0; j < lectureSection_IDs.size(); j++)
					section.setLectureSection_ID(lectureSection_IDs.get(j));
			}
			dbh.addSection(section);
		}
		
	}
	
	private String processInstructor(String innerHTML) {
		if (innerHTML.contains("<a href=")) {
			// System.out.println(innerHTML);
			innerHTML = innerHTML.substring(innerHTML.indexOf("\">") + 2, innerHTML.indexOf("</"));
		}
		if (innerHTML.contains(",")) innerHTML = innerHTML.substring(0, innerHTML.indexOf(","));
		return innerHTML;
	}

	private String[] processTime(String time) {
		
		if (time.contains("TBA") || time.length() <= 0) return null;
		
		String start_time = time.substring(0, time.indexOf("-"));
		String end_time = time.substring(time.indexOf("-") + 1, time.length() - 2);
		if (time.substring(time.length() - 2).contains("p")) {
			int start_h = Integer.parseInt(start_time.substring(0, start_time.indexOf(":")));
			int end_h = Integer.parseInt(end_time.substring(0, end_time.indexOf(":")));
			if ( start_h <= end_h && end_h != 12 ) {
				start_time =  (start_h + 12) + start_time.substring(start_time.indexOf(":"));
			}
			if ( end_h != 12) {
				end_time =  (end_h + 12) + end_time.substring(end_time.indexOf(":"));
			}
		}
		return new String[] {start_time, end_time};
	}

	private String getInnerHTMLByClassName(String line, String className) {
		// System.out.print("Processing " + className + ":\t");
		String startLocation = "class=\"" + className + "\">";
		line = line.substring(line.indexOf(startLocation));
		line = line.substring(0, line.indexOf("</td"));
		// System.out.println(line.substring(line.indexOf(">") + 1));
		
		return line.substring(line.indexOf(">") + 1);
		
	}

	private String processDays(String days) {
		if (days.contains("TBA")) return null;
		String result = "";
		char[] symbols = {'M', 'T', 'W', 'H', 'F'};
		for (int i = 0; i < symbols.length; i++) {
			result += days.indexOf(symbols[i]) < 0 ? "" : symbols[i];
		}
		return result;
	}

	private int addCourseToDB(String line, String school, String major) {

		Course course = new Course(school, major);
		
		/*
		 * Update number, name, description, units
		 */
		
		// Get number
		int startIndex = line.indexOf("<strong>" + major + " ") 
				+ (new String("<strong>" + major + " ")).length();
		int endIndex = line.indexOf(":</strong>");
		String number = line.substring(startIndex, endIndex);
		course.setNumber(number);
		// Get name
		startIndex = line.indexOf("</strong>") + (new String("</strong>")).length();
		endIndex = line.indexOf("<span class=\"uni");
		String name = line.substring(startIndex, endIndex);
		line = line.substring(endIndex + 6);
		course.setName(name);
		// Get units
		float units = Float.parseFloat(line.substring(line.indexOf("(") + 1, line.indexOf("(") + 4));
		course.setUnits(units);
		// Get description
		startIndex = line.indexOf("<div class=\"catalogue\">") + (new String("<div class=\"catalogue\">")).length();
		line = line.substring(startIndex);
		endIndex = line.indexOf("</div>");
		String description = line.substring(0, endIndex);
		course.setDescription(description);
		
		// Insert into database
		dbh.addCourse(course);
		
		return dbh.getCourseId(course);
	}

}
