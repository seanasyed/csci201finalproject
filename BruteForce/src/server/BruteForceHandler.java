package server;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import database.DatabaseHandler;


public class BruteForceHandler {

	private DatabaseHandler dh;
	
	public void handleRequest(String callType, HttpServletRequest request, HttpServletResponse response) {
		switch (callType) {
		case "create_user":
			String email = request.getParameter("email"); 
			String password = request.getParameter("password"); 
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname"); 

			
			//Call DatabaseHandler to write user information
			try {
				dh.createUser(email, password, fname, lname);
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
			
			response.setContentType("text");
			
			try {
				response.getWriter().write("true");
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
			
			String json = new Gson().toJson(list);
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