package server;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
	}
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) {
		String callType = request.getParameter("callType");
		if (callType == null) return;
		bfh.handleRequest(callType, this, request, response);
	}

}
