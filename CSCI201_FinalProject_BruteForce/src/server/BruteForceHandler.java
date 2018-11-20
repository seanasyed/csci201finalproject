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

package server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.gson.Gson;

import algorithm.ScheduleOptimization;
import database.DatabaseHandler;
import model.Course;
import model.ICS;
import model.Section;

//handler that connects back end and front end using different call types
public class BruteForceHandler {

	private DatabaseHandler dh;

	public BruteForceHandler() {
		dh = DatabaseHandler.getOneInstance();
	}

	public void start() {
		dh.connect();
	}

	public void close() {
		dh.close();
	}

	public void handleRequest(String callType, BruteForceServlet servlet, HttpServletRequest request,
			HttpServletResponse response) {

		start();

		switch (callType) {
		// user login
		case "login_user": {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			Map<String, String> data = new HashMap<String, String>();
			System.out.println("Trying to login...");
			try {
				String nextPage = "";
				if (email == null || email.equals("") || password == null || password.equals("")) {
					request.setAttribute("message", "One of the fields is empty.");
					System.out.println("dispatching w/ attribute: " + "empty");
					nextPage = "/login.jsp";
				} else if (!dh.isAuthenticated(email, password)) {
					request.setAttribute("message", "Email and password do not match.");
					System.out.println("dispatching w/ attribute: " + "dont match");
					nextPage = "/login.jsp";
				} else {
					request.setAttribute("email", email);
					nextPage = "/index.jsp";
				}

				RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
				dispatch.forward(request, response);
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			} catch (ServletException se) {
				System.out.println(se.getMessage());
			}
		}
			break;
			
		//create a user
		case "create_user": {
			// DONE
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");

			// Call DatabaseHandler to write user information
			Map<String, String> data = new HashMap<String, String>();
			try {
				if (dh.userExists(email)) {
					data.put("result", "error");
					data.put("message", "The email is already in use.");
				} else {
					dh.createUser(email, password, fname, lname);
					data.put("result", "success");
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}

			response.setContentType("application/json");
			String json = new Gson().toJson(data);
			System.out.println(json);
			try {
				response.getWriter().write(json);
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		}
			break;
			
		//suggest courses 
		case "suggestions": {
			String keyword = request.getParameter("keyword");

			// RETURNS MAJOR+NUMBER (e.g. CSCI-201) OF THE COURSES
			// THAT START WITH THE GIVEN KEYWORD (PREFIX)

			ArrayList<String> suggestions = dh.getCourseNames(keyword);
			String json = new Gson().toJson(suggestions);
			try {
				response.getWriter().write(json);
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		}
			break;
		
		//check the schdeule
		case "check_schedule": {
			//RUN THE ALGORITHM AND RETURN PROPER VALUES
			System.out.println("check_schedule");
			String username = request.getParameter("username");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String courseListJSON = request.getParameter("courseList");
			String distanceConstraint = request.getParameter("distanceConstraint");
			System.out.println("Distance: " + distanceConstraint);
			// get courses
			courseListJSON = request.getParameter("courseList");
			Vector<Course> vecCourses = new Vector<Course>();

			dh.unregister(username);
			try {
				// CONVERT courseListJSON INTO AN LIST
				JSONArray courses = new JSONArray(courseListJSON);
				List<String> list = new ArrayList<String>();
				for (int i = 0; i < courses.length(); i++) {
					list.add(courses.optString(i));

					// SPLIT THE STRING BY "-"
					// MAJOR: courseInfo[0]
					// NUMBER: courseInfo[1]
					String courseInfo[] = courses.optString(i).split("-");
					System.out.println("major: " + courseInfo[0]);
					System.out.println("number: " + courseInfo[1]);
					Course course = dh.getCourse(courseInfo[0], courseInfo[1]);
					if (course != null)
						vecCourses.add(course);
				}
			} catch (JSONException je) {
				System.out.println("je:" + je.getMessage());
			}
			System.out.println("vecCourses:" + vecCourses);
			for (int i = 0; i < vecCourses.size(); i++) {
				System.out.println("Course: " + vecCourses.get(i));
				System.out.println("lectureSection: " + vecCourses.get(i).getLectureSections());
				for (int j = 0; j < vecCourses.get(i).getLectureSections().size(); j++) {
					System.out.println("labs: " + vecCourses.get(i).getLectureSections().get(j).getLabs());
					System.out
							.println("discussions: " + vecCourses.get(i).getLectureSections().get(j).getDiscussions());
				}
			}

			if (startTime == null || startTime.equals(""))
				startTime = "00:01";
			if (endTime == null || endTime.equals(""))
				endTime = "23:59";
			if (distanceConstraint == null || distanceConstraint.equals(""))
				distanceConstraint = "9999";
			System.out.println("DistanceConstraint: " + Double.parseDouble(distanceConstraint));
			ScheduleOptimization so = new ScheduleOptimization(vecCourses, startTime, endTime,
					Double.parseDouble(distanceConstraint));
			Vector<Section> vecSections = so.getSchedule();
			for (int i = 0; i < vecSections.size(); i++) {
				System.out.println(vecSections.get(i).getCourseName() + " at " + vecSections.get(i).getBuildingID());
			}
			Map<String, String> data = new HashMap<String, String>();
			if (vecSections.size() <= 0) {
				data.put("valid", "false");
			} else {
				String vecSectionsJSON = new Gson().toJson(vecSections);
				data.put("courses", vecSectionsJSON);
			}
			System.out.println(vecSections);
			response.setContentType("application/json");

			String json = new Gson().toJson(data);
			try {
				response.getWriter().write(json);
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		}
			break;
		
		//when confirmed that the schedule is valid, submit
		case "submit_schedule": {
			System.out.println("subbmit_schedule");
			String username = request.getParameter("username");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String courseListJSON = request.getParameter("courseList");
			String distanceConstraint = request.getParameter("distanceConstraint");

			// get courses
			courseListJSON = request.getParameter("courseList");
			Vector<Course> vecCourses = new Vector<Course>();

			try {
				// CONVERT courseListJSON INTO AN LIST
				JSONArray courses = new JSONArray(courseListJSON);
				List<String> list = new ArrayList<String>();
				for (int i = 0; i < courses.length(); i++) {
					list.add(courses.optString(i));

					// SPLIT THE STRING BY "-"
					// MAJOR: courseInfo[0]
					// NUMBER: courseInfo[1]
					String courseInfo[] = courses.optString(i).split("-");
					System.out.println("major: " + courseInfo[0]);
					System.out.println("number: " + courseInfo[1]);
					Course course = dh.getCourse(courseInfo[0], courseInfo[1]);
					if (course != null)
						vecCourses.add(course);
				}
			} catch (JSONException je) {
				System.out.println("je:" + je.getMessage());
			}
			System.out.println("vecCourses:" + vecCourses);
			for (int i = 0; i < vecCourses.size(); i++) {
				System.out.println("Course: " + vecCourses.get(i));
				System.out.println("lectureSection: " + vecCourses.get(i).getLectureSections());
				for (int j = 0; j < vecCourses.get(i).getLectureSections().size(); j++) {
					System.out.println("labs: " + vecCourses.get(i).getLectureSections().get(j).getLabs());
					System.out
							.println("discussions: " + vecCourses.get(i).getLectureSections().get(j).getDiscussions());
				}
			}
			if (startTime == null || startTime.equals(""))
				startTime = "00:01";
			if (endTime == null || endTime.equals(""))
				endTime = "23:59";
			if (distanceConstraint == null || distanceConstraint.equals(""))
				distanceConstraint = "9999";
			System.out.println("DistanceConstraint: " + Double.parseDouble(distanceConstraint));
			ScheduleOptimization so = new ScheduleOptimization(vecCourses, startTime, endTime,
					Double.parseDouble(distanceConstraint));
			Vector<Section> vecSections = so.getSchedule();
			Map<String, String> data = new HashMap<String, String>();
			if (vecSections.size() <= 0) {
				data.put("valid", "false");
			} else {
				String vecSectionsJSON = new Gson().toJson(vecSections);
				data.put("courses", vecSectionsJSON);
			}
			System.out.println(vecSections);
			ArrayList<String> sectionIDs = new ArrayList<>();
			for (int i = 0; i < vecSections.size(); i++) {
				sectionIDs.add(vecSections.get(i).getSectionID());
			}
			if (!sectionIDs.isEmpty()) {
				String message = dh.registerSchedule(username, vecSections);
				if (message.equals("Successfully registered")) {
					data.put("result", "success");
					data.put("message", "submission completed.");
				} else {
					data.put("result", "error");
					data.put("message", message);
				}
			} else {
				data.put("result", "error");
				data.put("message", "submission failed.");
			}

			response.setContentType("application/json");

			String json = new Gson().toJson(data);
			try {
				response.getWriter().write(json);
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
			break;
		}

		//get the schedule returned by algorithm
		case "get_schedule": {
			String username = request.getParameter("username");
			try {
				Vector<Section> schedule = dh.getSchedule(username);
				sentEmail(request, schedule, username, response);
				String json = new Gson().toJson(schedule);
				try {
					response.getWriter().write(json);
				} catch (IOException ioe) {
					System.out.println(ioe.getMessage());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}

		//download the schedule in the form of ics
		case "get_download": {
			String username = request.getParameter("username");
			Vector<Section> schedule = new Vector<>();
			try {
				schedule = dh.getSchedule(username);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ICS ics = new ICS(username);

			try {
				response.getWriter().write(ics.print(schedule));
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}

			break;
		}

		default:
			break;
		}

	}

	//send the email with schedule
	private void sentEmail(HttpServletRequest request, Vector<Section> schedule, String to,
			HttpServletResponse response) {
		String host = "smtp.gmail.com";
		String from = "jeffreymillergivemeana@gmail.com"; // Sender email address
		String pass = "bruteforce1!"; // Sender email password

		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true"); // added this line
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props, null);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress toAddress = new InternetAddress(to);
			System.out.println(Message.RecipientType.TO + "\t" + to);
			message.addRecipient(Message.RecipientType.TO, toAddress);
			message.setSubject("Schedule Generated from Brute Force");
			message.setContent(getSchedule(schedule), "text/html");
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	//get the schedule, return vector of sections
	private String getSchedule(Vector<Section> schedule) {
		String str = "";
		for (Section course : schedule) {
			str += course.toString();
		}
		return str;
	}

}
