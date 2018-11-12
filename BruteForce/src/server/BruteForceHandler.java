package server;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.gson.Gson;

import algorithm.ScheduleOptimization;
import database.DatabaseHandler;
import model.Course;
import model.LectureSection;
import model.Section;


public class BruteForceHandler {

	private DatabaseHandler dh;
	public BruteForceHandler() {
		dh = new DatabaseHandler();
	}
	public void handleRequest(String callType, BruteForceServlet servlet, HttpServletRequest request, HttpServletResponse response) {
		switch (callType) {
		case "login_user": {
			//DONE
			String email = request.getParameter("email"); 
			String password = request.getParameter("password"); 
			Map<String, String> data = new HashMap<String, String>();
			try {
				if (email.equals("") || password.equals("")) {
					data.put("result", "error");
					data.put("message", "One of the fields is empty.");
				} else if (!dh.authenticateUser(email, password)) {
					data.put("result", "error");
					data.put("message", "Email and password do not match.");
				} else {
					System.out.println("Logged In");
					data.put("result", "success");
					data.put("redirectURL", "http://localhost:8080/BruteForce/index.jsp");
				}
				String json = new Gson().toJson(data);
				response.setContentType("application/json");
				response.getWriter().write(json);
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		}
		break;
		case "create_user": {
			//DONE
			String email = request.getParameter("email"); 
			String password = request.getParameter("password"); 
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname"); 
			
			//Call DatabaseHandler to write user information
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
		case "suggestions": {
			//DONE
			String keyword = request.getParameter("keyword");
			
			//RETURNS MAJOR+NUMBER (e.g. CSCI-201) OF THE COURSES
			//THAT START WITH THE GIVEN KEYWORD (PREFIX)
			
			ArrayList<String> suggestions = dh.getCourseNames(keyword);
			String json = new Gson().toJson(suggestions);
			try {
				response.getWriter().write(json);
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		}
		break;
		case "check_schedule":
			//TODO: RUN THE ALGORITHM AND RETURN PROPER VALUES
			System.out.println("check_schedule");
			String startTime = request.getParameter("startTime"); 
			String endTime = request.getParameter("endTime");
			String courseListJSON = request.getParameter("courseList");
			
			//TODO: DISCUSS WITH FRANK
            System.out.println("submit_schedule");
            //get courses
            courseListJSON = request.getParameter("courseList");
            Vector<Course> vecCourses = new Vector<Course>();
            
            
            try {
                //CONVERT courseListJSON INTO AN LIST
                JSONArray courses = new JSONArray(courseListJSON);
                List<String> list = new ArrayList<String>();
                for(int i = 0; i < courses.length(); i++){
                    list.add(courses.optString(i));
                    
                    //SPLIT THE STRING BY "-"
                    //MAJOR: courseInfo[0]
                    //NUMBER: courseInfo[1]
                    String courseInfo[]= courses.optString(i).split("-");
                    System.out.println("major: " + courseInfo[0]);
                    System.out.println("number: " + courseInfo[1]);
                    Course course = dh.getCourse(courseInfo[0], courseInfo[1]);
                    if (course != null) vecCourses.add(course);
                }
            } catch (JSONException je) {
                System.out.println("je:" + je.getMessage());
            }
            startTime = request.getParameter("startTime"); 
            endTime = request.getParameter("endTime");
            System.out.println("vecCourses:" + vecCourses);
            for(int i = 0; i < vecCourses.size(); i++) {
            	System.out.println("Course: " + vecCourses.get(i));
            }
            ScheduleOptimization so = new ScheduleOptimization(vecCourses, startTime, endTime);
            Vector<Section> vecSections = so.getSchedule();
            Map<String, String> data = new HashMap<String, String>();
            response.setContentType("application/json");
			//response.getWriter().write(json);
            if (vecSections.size() >= 0) 
			break;
		case "submit_schedule":
			
//		case "get_schedule":
			
		default:
			break;
		}
	}
 }