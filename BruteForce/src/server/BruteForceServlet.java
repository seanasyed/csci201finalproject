package server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BruteForce")
public class BruteForceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BruteForceHandler bfh;
	//private BruteForceThread bft;
	//TODO Establish connection to database
	//TODO Schedule optimization
	
	public BruteForceServlet() {
		super();
		bfh = new BruteForceHandler();
		//bft = new BruteForceThread(6789);
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String callType = request.getParameter("callType");
		if (callType == null) return;
		bfh.handleRequest(callType, request, response);
	}
	
//	public static void main(String[] args) {
//		
//	}
}
