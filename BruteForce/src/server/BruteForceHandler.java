package server;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import database.DatabaseHandler;


public class BruteForceHandler {

	private DatabaseHandler dh;
	public BruteForceHandler() {
		dh = new DatabaseHandler();
	}
	public void handleRequest(String callType, HttpServletRequest request, HttpServletResponse response) {
		switch (callType) {
		case "create_user":
			System.out.println("Creating a user");
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
				break;
			}
			break;
		case "suggestions":
			String keyword = request.getParameter("keyword");
			System.out.print(keyword);
			ArrayList<String> suggestions = new ArrayList<>();
			
			//TODO: JUST ADD ALL THE COURSE CODES FROM THE DATABASE
			suggestions.add("CSCI-201");
			suggestions.add("PHYS-151");
			suggestions.add("CSCI-270");
			
			//Filtering the data with the keyword
			List<String> list = suggestions;
			list.removeIf(s -> !s.startsWith(keyword));
			
			json = new Gson().toJson(list);
			try {
				response.getWriter().write(json);
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
				break;
			}
		case "check_schedule":
			System.out.println("check_schedule");
			break;
		case "submit_schedule":
			System.out.println("submit_schedule");
			break;
			
		default:
			break;
		}
	}
 }