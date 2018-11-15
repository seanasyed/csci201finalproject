package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import algorithm.CourseCrawling;

/**
 * Servlet implementation class Admin
 */
@WebServlet("/Admin")
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String message;

	/**
	 * The term from which we want to crawl data.
	 */
	private static final String TERM = "20183";

	/**
	 * Building database url
	 */
	private static final String BUILDING_URL = "https://web-app.usc.edu/maps/all_map_data2.js.pagespeed.jm.HVbGqVyZmP.js";

	@Override
	public void init() throws ServletException {
		// Do required initialization
		message = "Updated at " + LocalDate.now(ZoneId.of("GMT-07:00")).toString();
		CourseCrawling cw = new CourseCrawling();
		cw.start("https://classes.usc.edu/term-" + TERM, BUILDING_URL);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Set response content type
		response.setContentType("text/html");

		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		out.println("<h1>" + message + "</h1>");
	}

	@Override
	public void destroy() {
		// do nothing.
	}

}
