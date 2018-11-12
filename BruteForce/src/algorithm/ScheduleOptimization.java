package algorithm;

import java.util.Vector;

import model.Course;
import model.LectureSection;
import model.Section;

public class ScheduleOptimization {
	
	/**
	 * Proposed Algorithm
	 * 
	 * 1) Choose a course
	 * 2) For that course, attempt to add a section of each type
	 * 3) If a section conflicts with the existing schedule, try and add different section of the same type. 
	 * 4) If no sections of a given type for a course can fit in the schedule, revert back to the previous section type and repeat (3) to find another working section
	 * 5) If all possible configurations of a course cannot be added, revert to the previous course and attempt (2) - (4) to find another working section combination
	 * 6) If there are no possible configurations for the given courses, schedule should be empty
	 * 7) If a valid schedule is found, it should be saved to the private schedule variable
	 */

	private Vector<Course> courses;
	private Vector<Section> schedule; //Section ID's
	
	public ScheduleOptimization(Vector<Course> courses) {
		this.courses = courses; 
		schedule = new Vector<Section>(); 
		
		addCourse(0); 
		
		//TODO If the schedule is not empty, the student has been enrolled, so write the new seat count to the database
	}
	
	/**
	 * TODO Helper method to attempt to add a course to the current schedule
	 */
	private void addCourse(int index) {
		
		//Base case if either a full schedule has been created or nothing has been created
		if (index < 0 || index >= courses.size()) {
			return; 
		}
		
		//Retrieve the current course
		Course course = courses.get(index); 
		
		//State machine implementation: lecture -> discussion -> lab -> quiz
		Vector<LectureSection> lectureSections = course.getLectureSections();
	}
	
	/**
	 * TODO Helper method to backtrack adding a course
	 */
	private void backtrackCourse(int courseIndex, int lectureIndex, int labIndex, int discussionIndex, int quizIndex) {
	
	}
	
	
	/**
	 * TODO Determines if a given section conflicts with the already-existing schedule
	 * 
	 * Returns false if there is a conflict
	 */
	private boolean noConflict(Section section) {
		
		boolean noConflict = false; 
		
		//Parse the days of the week
		//If true, the section meets on that day
		boolean[] days = parseDays(section.getDay()); 
		
		//Instantiate all of the days as not having a time slot for the given section
		for (boolean n : days) {
			n = false; 
		}
		
		//TODO Iterate through the current schedule. If days are the same, then compare the times
		for (Section s : schedule) {
			boolean[] sDays = parseDays(s.getDay()); 
			for (boolean d : days) {
				for (boolean sd : sDays) {
					if (d && sd) {
						//Compare the starting and ending times
						int[] start = parseTime(section.getStartTime()); 
						int[] end = parseTime(section.getEndTime()); 
						int[] sStart = parseTime(s.getStartTime()); 
						int[] sEnd = parseTime(s.getEndTime()); 
						
						//0) hour
						int startHour = start[0]; 
						//1) minute
						
						
					}
				}
			}
		}
		return noConflict; 
	}
	
	/*
	 * Helper method to parse the day String of a given section
	 */
	private boolean[] parseDays(String dayString) {
		boolean[] days = new boolean[7]; 
		
		//Case where section meets on MWF
		if (dayString.equals("MWF")) {
			days[1] = true; 
			days[3] = true; 
			days[5] = true; 
		} 
		
		//Case where section meets twice a week
		else if (dayString.indexOf(",") != -1) {
			
			//Sunday
			if (dayString.indexOf("Sun") != -1) {
				days[0] = true; 
			}
			
			//Monday
			if (dayString.indexOf("Mon") != -1) {
				days[1] = true; 
			}
			
			//Tuesday
			if (dayString.indexOf("Tues") != -1) {
				days[2] = true; 
			}
			
			//Wednesday
			if (dayString.indexOf("Wed") != -1) {
				days[3] = true; 
			}
			
			//Thursday
			if (dayString.indexOf("Thurs") != -1) {
				days[4] = true; 
			}
			
			//Friday
			if (dayString.indexOf("Fri") != -1) {
				days[5] = true; 
			}
			
			//Saturday
			if (dayString.indexOf("Sat") != -1) {
				days[6] = true; 
			}
		}
		
		//Case where section meets once a week
		else {
			
			//Sunday
			if (dayString.equals("Sunday")) {
				days[0] = true; 
			}
			
			//Monday
			if (dayString.equals("Monday")) {
				days[1] = true; 
			}
			
			//Tuesday
			if (dayString.equals("Tuesday")) {
				days[2] = true; 
			}
			
			//Wednesday
			if (dayString.equals("Wednesday")) {
				days[3] = true; 
			}
			
			//Thursday
			if (dayString.equals("Thursday")) {
				days[4] = true; 
			}
			
			//Friday
			if (dayString.equals("Friday")) {
				days[5] = true; 
			}
			
			//Saturday
			if (dayString.equals("Saturday")) {
				days[6] = true; 
			}
			
		}
		
		return days; 
	}
	
	/*
	 * Helper method to parse a time string into hours and minutes
	 */
	int[] parseTime(String t) {
		int[] time = new int[2]; 
		time[0] = Integer.parseInt(t.substring(0, t.indexOf(":"))); 
		time[1] = Integer.parseInt(t.substring(t.indexOf(":") + 1)); 
		
		return time; 
	}
	
	public Vector<Section> getSchedule() {
		return schedule; 
	}
}
