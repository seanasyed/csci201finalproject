package algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import model.Course;
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
	private Vector<Section> schedule; 
	
	public ScheduleOptimization(Vector<Course> courses) {
		this.courses = courses; 
		schedule = new Vector<Section>(); 
		
		addCourse(0); 
		
		//TODO If the schedule is not empty, the student has been enrolled, so write the new seat count to the database
	}
	
	/**
	 * Helper method to attempt to add a course to the current schedule
	 */
	private void addCourse(int index) {
		
		//Retrieve the current course
		Course course = courses.get(index); 
		
		//TODO Parse the sections into type vectors that are stored in a map based on the section type
		Map<String, Vector<Section>> sections = new HashMap<String, Vector<Section>>();
		
		
	}
	
	/**
	 * Helper method to attempt to add a section of a given type to the current schedule
	 * 
	 * Returns true if the section type is successfully added, false if not
	 */
	private boolean addSectionType() {
		
		//TODO Check for dependencies if the section is a lecture section
		//TODO Make sure that section capacities, etc are properly handled
		return false; 
	}
	
	public Vector<Section> getSchedule() {
		return schedule; 
	}
}
