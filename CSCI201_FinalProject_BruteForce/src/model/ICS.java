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
package model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class ICS {

	private PrintWriter out = null;

	private static final String FILENAME = "/term-20183.ics", FOLDERNAME = "downloads/";

	private static final String ROOT = "/Users/seansyed/Documents/Code/School/csci201finalproject/BruteForce/WebContent";

	private String username;

	public ICS(String username) {

		this.username = username.substring(0, username.indexOf("@"));
		System.out.println("username=" + this.username);

	}

	private void initialize() {

		System.out.println(ROOT + "/" + FOLDERNAME + username);
		while (!(new File(ROOT + "/" + FOLDERNAME + username).exists())) {
			try {
				// Create the folder
				(new File(ROOT + "/" + FOLDERNAME + username)).mkdirs();
				System.out.println("Created folder!");

			} catch (SecurityException se) {
				se.printStackTrace();
			}
		}
		try {
			// Update out
			out = new PrintWriter(ROOT + "/" + FOLDERNAME + username + FILENAME);
			System.out.println("Created out!");
		} catch (IOException e) {
			e.printStackTrace();
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

		return FOLDERNAME + username + FILENAME;
	}

	/**
	 * TODO Add your method description here.
	 * 
	 * @param section;
	 */
	private void writeEvent(Section section) {
		String currentTime = LocalDate.now(ZoneId.of("America/Los_Angeles")).format(DateTimeFormatter.BASIC_ISO_DATE)
				+ "T" + LocalTime.now(ZoneId.of("America/Los_Angeles")).format(DateTimeFormatter.ofPattern("HHmmss"));
		// Begin event
		out.println("BEGIN:VEVENT");
		// Create time
		out.println("CREATED:" + currentTime);
		// Update UID
		out.println("UID:20183-" + section.getSectionID() + "-" + section.getBuildingID() + username + "@my.usc.edu");
		// Add rule
		out.println("RRULE:FREQ=WEEKLY;INTERVAL=1;UNTIL=20181212T000000;BYDAY=" + processDays(section));
		out.println("EXDATE;TZID=America/Los_Angeles:20181123T" + processTime(section.getStartTime()) + "\n"
				+ "DTEND;TZID=America/Los_Angeles:20180824T" + processTime(section.getEndTime()) + "\n"
				+ "TRANSP:OPAQUE");
		// Add summary
		out.println("SUMMARY:" + section.getCourseName() + " (" + section.getType() + ")");
		// Add time rule
		out.println("DTSTART;TZID=America/Los_Angeles:20180824T" + processTime(section.getStartTime()) + "\n"
				+ "DTSTAMP;TZID=America/Los_Angeles:" + currentTime);
		// End event
		out.println("SEQUENCE:0\nEND:VEVENT");

	}

	/**
	 * TODO Add your method description here.
	 * 
	 * @param startTime
	 * @return
	 */
	private String processTime(String time) {
		int i = time.indexOf(":");

		return time.substring(0, i).length() > 1 ? time.substring(0, i) + time.substring(i + 1) + "00"
				: "0" + time.substring(0, i) + time.substring(i + 1) + "00";
	}

	/**
	 * TODO Add your method description here.
	 * 
	 * @param section
	 * @return
	 */
	private String processDays(Section section) {
		String base = "";
		String[] ref = { "M", "T", "W", "H", "F" };
		String[] result = { "MO", "TU", "WE", "TH", "FR" };
		String days = section.getDay();

		for (int i = 0; i < ref.length; i++) {
			if (days.contains(ref[i])) {
				base += base.length() > 0 ? "," + result[i] : result[i];
			}
		}

		return base;
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
		out.println("BEGIN:VCALENDAR\n" + "CALSCALE:GREGORIAN\n" + "VERSION:2.0\n" + "X-WR-CALNAME:Fall 2018 Classes\n"
				+ "METHOD:PUBLISH\n" + "BEGIN:VTIMEZONE\n" + "TZID:America/Los_Angeles\n" + "BEGIN:DAYLIGHT\n"
				+ "TZOFFSETFROM:-0800\n" + "RRULE:FREQ=YEARLY;BYMONTH=3;BYDAY=2SU\n" + "DTSTART:20180311T020000\n"
				+ "TZNAME:PDT\n" + "TZOFFSETTO:-0700\n" + "END:DAYLIGHT\n" + "BEGIN:STANDARD\n" + "TZOFFSETFROM:-0700\n"
				+ "RRULE:FREQ=YEARLY;BYMONTH=11;BYDAY=1SU\n" + "DTSTART:20181104T020000\n" + "TZNAME:PST\n"
				+ "TZOFFSETTO:-0800\n" + "END:STANDARD\n" + "END:VTIMEZONE");
	}

}
