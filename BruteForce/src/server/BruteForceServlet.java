package server;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BruteForce")
public class BruteForceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BruteForceHandler bfh;
	// private BruteForceThread bft;

	public BruteForceServlet() {
		super();
		bfh = new BruteForceHandler();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) {
		String callType = request.getParameter("callType");
		System.out.println(callType + " called...");
		if (callType == null)
			return;
		bfh.handleRequest(callType, this, request, response);
	}

}
