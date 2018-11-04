package server;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
public class BruteForceHandler {
	public BruteForceHandler() { }
	public void handleRequest(String callType, HttpServletRequest request, HttpServletResponse response) {
		switch (callType) {
		case "add_user":
			System.out.println("add_user");
			break;
		case "check_class_list":
			System.out.print("check_class_list");
			break;
		case "create_user":
			System.out.println(request.getParameter("username"));
			System.out.println(request.getParameter("email"));
			System.out.println(request.getParameter("password"));
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
		default:
			break;
		}
	}
 }