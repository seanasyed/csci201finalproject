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

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BruteForce")
public class BruteForceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BruteForceHandler bfh;

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
