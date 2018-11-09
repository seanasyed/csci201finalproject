package server;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import database.DatabaseHandler;


public class BruteForceHandler {

	private DatabaseHandler dh;
	public BruteForceHandler() {
		dh = new DatabaseHandler();
	}
	public void handleRequest(String callType, BruteForceServlet servlet, HttpServletRequest request, HttpServletResponse response) {
		switch (callType) {
		case "login_user": {
			String email = request.getParameter("email"); 
			String password = request.getParameter("password"); 
			Map<String, String> data = new HashMap<String, String>();
			try {
				if (email.equals("") || password.equals("")) {
					data.put("result", "error");
					data.put("message", "One of the fields is empty.");
					String json = new Gson().toJson(data);
					response.setContentType("application/json");
					response.getWriter().write(json);
				} else if (!dh.authenticateUser(email, password)) {
					data.put("result", "error");
					data.put("message", "Email and password do not match.");
					String json = new Gson().toJson(data);
					response.setContentType("application/json");
					response.getWriter().write(json);
				} else {
					System.out.println("Logged In");
					request.setAttribute("email", "email");
					try {
						response.setContentType("text/html");
						RequestDispatcher dispatch = request.getRequestDispatcher("/index.html");
						dispatch.forward(request, response);
					} catch (IOException ie) {
						System.out.println(ie.getMessage());
					} catch (ServletException se) {
						System.out.println(se.getMessage());
					}
				}
				
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		}
		break;
		case "create_user": {
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
			}
		}
		break;
		case "suggestions": {
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
			}
		}
		break;
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