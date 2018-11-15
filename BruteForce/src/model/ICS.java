/**
 * Name: Xing Gao
 * USCID: 1504356084
 * Email: gaoxing@usc.edu
 * Course: CSCI 201L
 * Term: Fall 2018
 * Assignment: TODO Add your number here.
 * 
 * Description: 
 * 			TODO Add your description here.
 * 
 * File Created at: Nov 14, 2018, 9:08:22 PM
 *
 */
package model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * @author XXX
 *
 */
public class ICS {

	private PrintWriter out = null;

	private static final String FILENAME = "/term-20191.ics", FOLDERNAME = "downloads/";

	private String username;

	public ICS(String username) {
		this.username = username;

	}

	private void initialize() {

		if (!(new File(FOLDERNAME + username).exists())) {
			try {
				// Create the folder
				(new File(FOLDERNAME + username)).mkdir();

			} catch (SecurityException se) {
				se.printStackTrace();
			}
			try {
				// Update out
				out = new PrintWriter(FOLDERNAME + username + FILENAME);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void close() {
		out.close();
	}

	public String print(Vector<Section> courses) {
		initialize();

		// Write data into the file
		writeHeader();
		for (int i = 0; i < courses.size(); i++) {
			writeEvent(courses.get(i));
		}
		writeFooter();

		close();

		return null;
	}

	/**
	 * TODO Add your method description here.
	 * 
	 * @param section;
	 */
	private void writeEvent(Section section) {
		// Begin event
		out.println("BEGIN:VEVENT");
		// Create time
		// out.println("CREATED:" +
		// LocalDate.now(ZoneId.of("GMT-07:00")).format(DateTimeFormatter.BASIC_ISO_DATE)
		// + "T"
		// + LocalDate.now(ZoneId.of("GMT-07:00")).format(DateTimeFormatter.));
		// End event

	}

	/**
	 * TODO Add your method description here.
	 */
	private void writeFooter() {
		out.println("END:VCALENDAR");

	}

	/**
	 * TODO Add your method description here.
	 */
	private void writeHeader() {
		out.println("BEGIN:VCALENDAR\n" + "CALSCALE:GREGORIAN\n" + "VERSION:2.0\n"
				+ "X-WR-CALNAME:Spring 2019 Classes\n" + "METHOD:PUBLISH\n" + "BEGIN:VTIMEZONE\n"
				+ "TZID:America/Los_Angeles\n" + "BEGIN:DAYLIGHT\n" + "TZOFFSETFROM:-0800\n"
				+ "RRULE:FREQ=YEARLY;BYMONTH=3;BYDAY=2SU\n" + "DTSTART:20190310T020000\n" + "TZNAME:PDT\n"
				+ "TZOFFSETTO:-0700\n" + "END:DAYLIGHT\n" + "BEGIN:STANDARD\n" + "TZOFFSETFROM:-0700\n"
				+ "RRULE:FREQ=YEARLY;BYMONTH=11;BYDAY=1SU\n" + "DTSTART:20191103T020000\n" + "TZNAME:PST\n"
				+ "TZOFFSETTO:-0800\n" + "END:STANDARD\n" + "END:VTIMEZONE");
	}

}
