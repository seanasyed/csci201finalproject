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
	private Vector<String> schedule; //Section ID's
	
	public ScheduleOptimization(Vector<Course> courses) {
		this.courses = courses; 
		schedule = new Vector<String>(); 
		
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
		
		
		
		return false; 
	}
	
	public Vector<String> getSchedule() {
		return schedule; 
	}
}
