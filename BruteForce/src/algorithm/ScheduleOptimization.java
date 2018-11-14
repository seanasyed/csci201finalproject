package algorithm;

import java.util.Vector;

import database.DatabaseHandler;
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
	
	private DatabaseHandler dbh = DatabaseHandler.getOneInstance();
	
	private Vector<Section> schedule; //Section IDs
	private int startTimeConstraint = 0; 
	private int endTimeConstraint = 0; 
	private double distanceConstraint = 0; 
	public ScheduleOptimization(Vector<Course> courses, String startTimeConstraint, String endTimeConstraint, double distanceConstraint) {
		
		if (distanceConstraint != -1) {
			this.distanceConstraint = distanceConstraint;
		} else {
			this.distanceConstraint = Integer.MAX_VALUE; 
		}
		
		
		this.courses = courses; 
		for (int i = 0; i < courses.size(); i++) {
			//System.out.println(courses.get(i).getName());
			for (int j = 0; j < courses.get(i).getLectureSections().size(); j++) {
				//System.out.println(courses.get(i).getLectureSections().get(j).getSectionID());
			}
			//System.out.println();
		}
		
		if (!startTimeConstraint.equals("")) {
			this.startTimeConstraint = parseTime(startTimeConstraint)[0] * 100 + parseTime(startTimeConstraint)[1];
		}
		
		if (!endTimeConstraint.equals("")) {
			this.endTimeConstraint = parseTime(endTimeConstraint)[0] * 100 + parseTime(endTimeConstraint)[1];
		}
		
		if (this.startTimeConstraint == 0) {
			this.startTimeConstraint = 1; 
		}
		
		if (this.endTimeConstraint == 0) {
			this.endTimeConstraint = 2359;
		}
		
		schedule = new Vector<Section>(); 
		
		//System.out.println("startTimeConstraint: " + startTimeConstraint);
		//System.out.println("endTimeConstraint: " + endTimeConstraint + "\n"); 
		//System.out.println("Courses size is " + courses.size());
		
		//Initial recursive call 
		addCourse(0,0,0,0,0,0, false); 
		
		
		
	}
	
	/**
	 * Attempt to add a course into the schedule with a given state in terms of combination progression
	 */
	private void addCourse(int courseIndex, int lectureIndex, int discussionIndex, int labIndex, int quizIndex, int state, boolean backtrack) {
		
		//System.out.println("courseIndex: " + courseIndex); 
		//System.out.println("lectureIndex: " + lectureIndex); 
		//System.out.println("discussionIndex: " + discussionIndex);
		//System.out.println("labIndex: " + labIndex); 
		//System.out.println("quizIndex: " + quizIndex); 
		//System.out.println("state: " + state); 
		System.out.println("backtracking: " + backtrack + "\n");
		
		//Base case returns
		
		//If a complete schedule has been created, courseIndex should be out of bounds
		if (courseIndex >= courses.size()) {
			
			System.out.println("Finalized Schedule"); 
			for (int i = 0; i < schedule.size(); i++) {
				System.out.println(schedule.get(i).getSectionID());
			}
			return; 
		}
		
		//If no schedule has been created, courseIndex should be -1
		if (courseIndex == -1) {
			schedule.clear();
			return; 
		}
		
		Course course = courses.get(courseIndex); 
		
		
		if (lectureIndex >= course.getLectureSections().size()) {

			//Remove all of the course's sections from the schedule
			for (int i = 0; i < schedule.size(); i++) {
				if (schedule.get(i).getCourseID().equals(course.getLectureSections().get(lectureIndex - 1).getCourseID())) {
					schedule.remove(i); 
				}
			}
			addCourse(courseIndex - 1, 0, 0, 0, 0, 0, true); 
			return; 
		}
	
		Vector<LectureSection> lectures = course.getLectureSections(); //necessary for finding lectureIndex in the event of backtracking
		LectureSection lecture = lectures.get(lectureIndex); 
		Vector<Section> discussions = lecture.getDiscussions(); 
		Vector<Section> labs = lecture.getLabs(); 
		Vector<Section> quizzes = lecture.getQuizzes();
		
		//Check to see if it is a backtracking call
		
		/**
		 * Procedure to check if backtracking call
		 * 
		 * 1) Check to see if any of the course's sections are already in the schedule
		 * 2) If they are in the schedule, record the indexes of the sections in their respective Vectors
		 * 3) Remove all of the sections from that course 
		 * 4) Set the parameter values to wherever the course left off
		 * 5) Increment the farthest section type that is offered
		 * 6) Make state = 3
		 */
		
		//Iterate through each vector to check if any of the sections are already in the schedule
		int backtrackingLectureIndex = -1;
		int backtrackingDiscussionIndex = -1;
		int backtrackingLabIndex = -1; 
		int backtrackingQuizIndex = -1; 
		
		for (Section s : schedule) {
			
			//account for the different lecture dependencies
			for (int i = 0; i < lectures.size(); i++) {
				if (s.getSectionID().equals(lectures.get(i).getSectionID())) {
					backtrackingLectureIndex = i; 
					lecture = lectures.get(lectureIndex); 
					discussions = lecture.getDiscussions(); 
					labs = lecture.getLabs(); 
					quizzes = lecture.getQuizzes();
				}
			}
			for (int i = 0; i < discussions.size(); i++) {
				if (s.getSectionID().equals(discussions.get(i).getSectionID())) {
					backtrackingDiscussionIndex = i; 
				}
			}
			for (int i = 0; i < labs.size(); i++) {
				if (s.getSectionID().equals(labs.get(i).getSectionID())) {
					backtrackingLabIndex = i; 
				}
			}
			for (int i = 0; i < quizzes.size(); i++) {
				if (s.getSectionID().equals(quizzes.get(i).getSectionID())) {
					backtrackingQuizIndex = i; 
				}
			}
		}
		
		//System.out.println(); 
		//System.out.println("backtrackingLectureIndex: " + backtrackingLectureIndex);
		//System.out.println("backtrackingDiscussionIndex: " + backtrackingDiscussionIndex);
		//System.out.println("backtrackingLabIndex: " + backtrackingLabIndex);
		//System.out.println("backtrackingQuizIndex: " + backtrackingQuizIndex);
		
		//If any of the backtracking indexes != -1, handle all backtracking procedures
		if (backtrack) {
		
			System.out.println("Backtracking detected"); 
			//Replace the parameters with the backtracking indexes
			
			if (backtrackingLectureIndex != -1) {
				lectureIndex = backtrackingLectureIndex; 
				lecture = lectures.get(lectureIndex); 
				discussions = lecture.getDiscussions(); 
				labs = lecture.getLabs(); 
				quizzes = lecture.getQuizzes(); 
			}
			
			if (backtrackingDiscussionIndex != -1) {
				discussionIndex = backtrackingDiscussionIndex; 
			}
			
			if (backtrackingLabIndex != -1) {
				labIndex = backtrackingLabIndex; 
			}
			
			if (backtrackingQuizIndex != -1) {
				quizIndex = backtrackingQuizIndex;
			}
			
			
			//Increment the farthest section
			if (quizzes.size() > 0) {
				quizIndex++; 
			} else if (labs.size() > 0) {
				labIndex++; 
			} else if (discussions.size() > 0) {
				discussionIndex++; 
			} else {
				lectureIndex++; 
			}
			
			//Set the state to 3
			state = 0; 
			
			//Remove all of the course's sections from the schedule
			for (int i = 0; i < schedule.size(); i++) {
				if (schedule.get(i).getCourseID().equals(lecture.getCourseID())) {
					schedule.remove(i); 
				}
			}
			
		}
		
		if (startTimeConstraint != 0 && endTimeConstraint != 0) {
			//skip the sections that violate the time constraint
			int startTime, endTime; 
			//check lecture section
			startTime = parseTime(lecture.getStartTime())[0] * 100 + parseTime(lecture.getStartTime())[1]; 
			endTime = parseTime(lecture.getEndTime())[0] * 100 + parseTime(lecture.getEndTime())[1]; 
			
			//System.out.println("startTime: " + startTime);
			//System.out.println("startTimeConstraint: " + startTimeConstraint);
			//System.out.println("endTime: " + endTime);
			//System.out.println("endTimeConstraint: " + endTimeConstraint);
			if (startTime < startTimeConstraint || endTime > endTimeConstraint) {
				addCourse(courseIndex, lectureIndex + 1, discussionIndex, labIndex, quizIndex, state, false);
				return; 
			}
			//iterate through each of the Vectors to check all other sections
			for (Section s : discussions) {
				startTime = parseTime(s.getStartTime())[0] * 100 + parseTime(s.getStartTime())[1]; 
				endTime = parseTime(s.getEndTime())[0] * 100 + parseTime(s.getEndTime())[1]; 
				
				if (startTime < startTimeConstraint || endTime > endTimeConstraint) {
					addCourse(courseIndex, lectureIndex, discussionIndex + 1, labIndex, quizIndex, state, false);
					return; 
				}
			}
			
			for (Section s : labs) {
				startTime = parseTime(s.getStartTime())[0] * 100 + parseTime(s.getStartTime())[1]; 
				endTime = parseTime(s.getEndTime())[0] * 100 + parseTime(s.getEndTime())[1]; 
				
				if (startTime < startTimeConstraint || endTime > endTimeConstraint) {
					addCourse(courseIndex, lectureIndex, discussionIndex, labIndex + 1, quizIndex, state, false);
					return; 
				}
			}
			
			for (Section s : quizzes) {
				startTime = parseTime(s.getStartTime())[0] * 100 + parseTime(s.getStartTime())[1]; 
				endTime = parseTime(s.getEndTime())[0] * 100 + parseTime(s.getEndTime())[1]; 
				
				if (startTime < startTimeConstraint || endTime > endTimeConstraint) {
					addCourse(courseIndex, lectureIndex, discussionIndex, labIndex, quizIndex + 1, state, false);
					return; 
				}
			}
		}
		
		
		//If these sections don't exist, don't worry about them!
		if (discussions.size() == 0 && state == 1) {
			
			state++; 
			//System.out.println("143: State = " + state); 
		}
		
		if (labs.size() == 0 && state == 2) {
			 
			state ++; 
			//System.out.println("149: State = " + state);
		}
		
		if (quizzes.size() == 0 && state == 3) {
		
			state++; 
			//System.out.println("155: State = " + state);
		}
		
		//Check to see if the state's appropriate index is out of bounds
		
		//0 - lecture (already checked)
		
		//1 - discussion
		if (state == 1) {
			if (discussionIndex >= discussions.size()) {
				addCourse(courseIndex, lectureIndex + 1, 0, 0, 0, state - 1, false);
				//System.out.println("Line 163");
				return; 
			}
		//2 - lab
		} else if (state == 2) {
			if (labIndex >= labs.size()) {
				addCourse(courseIndex, lectureIndex, discussionIndex + 1, 0, 0, state - 1, false);
				//System.out.println("Line 170");
				return; 
			}
		//3 - quiz
		} else if (state == 3) {
			if (quizIndex >= quizzes.size()) {
				
				addCourse(courseIndex - 1, 0, 0, 0, 0, 0, true); 
				//System.out.println("Line 178");
				return; 
			}
		}

		//Handle each state
		//System.out.println("State = " + state);
		//0 - Lecture
		if (state == 0) {
			//System.out.println("In state 0");
			if (noConflict(lecture)) {
				
				schedule.add(lecture); 
				//System.out.println("Line 192, size = " + schedule.size());
				System.out.println("Added lecture section to course " + lecture.getCourseName());
				System.out.println("Increasing state to 1\n");
				addCourse(courseIndex, lectureIndex, discussionIndex, labIndex, quizIndex, 1, false); 
				
				return; 
			} else {
				
				//Remove all of the course's sections from the schedule
				
				System.out.println("Preparing for backtracking\n");
				for (int i = 0; i < schedule.size(); i++) {
					System.out.println("Scheduled section course: " + schedule.get(i).getCourseName()); 
					System.out.println("Current course: " + lecture.getCourseName());
					if (schedule.get(i).getCourseID().equals(lecture.getCourseID())) {
						System.out.println("Removing section from course " + lecture.getCourseName());
						schedule.remove(i); 
					}
				}
				System.out.println();
				System.out.println("Reverting to previous course with backtrack = true\n");
				addCourse(courseIndex - 1, 0, 0, 0, 0, 0, true); 
				//System.out.println("Line 197");
				return; 
			}
		}
		//1 - Discussion
		else if (state == 1) {
			if (noConflict(discussions.get(discussionIndex))) {
				schedule.add(discussions.get(discussionIndex));
				//System.out.println("Line 206, size = " + schedule.size());
				addCourse(courseIndex, lectureIndex, discussionIndex, labIndex, quizIndex, 2, false);
				
				return;
			} else {
				addCourse(courseIndex, lectureIndex, discussionIndex + 1, labIndex, quizIndex, 1, false); 
				//System.out.println("Line 210");
				return; 
			}
		}
		//2 - Lab
		else if (state == 2) {
			if (noConflict(labs.get(labIndex))) {
				schedule.add(labs.get(labIndex));
				//System.out.println("Line 219, size = " + schedule.size());
				addCourse(courseIndex, lectureIndex, discussionIndex, labIndex, quizIndex, 3, false); 
				
				return; 
			} else {
				addCourse(courseIndex, lectureIndex, discussionIndex, labIndex + 1, quizIndex, 2, false); 
				//System.out.println("Line 223");
				return; 
			}
		}
		//3 - Quiz
		else if (state == 3) {
			//System.out.println("State is 3");
			if (noConflict(quizzes.get(quizIndex))) {
				schedule.add(quizzes.get(quizIndex)); 
				//System.out.println("Line 233, size = " + schedule.size());
				addCourse(courseIndex, lectureIndex, discussionIndex, labIndex, quizIndex, 4, false); 
				
				return; 
			} else {
				addCourse(courseIndex, lectureIndex, discussionIndex, labIndex, quizIndex + 1, 3, false); 
				//System.out.println("Line 237");
				return;  
			}
		}
		//4 - Done
		else if (state == 4) {
			//System.out.println("Line 243");
			System.out.println("Completed adding course " + lecture.getCourseName());
			System.out.println("Moving onto next course\n");
			addCourse(courseIndex + 1, 0, 0, 0, 0, 0, false); 
			return; 
		}
	}
	
	
	
	
	/**
	 * Determines if a given section conflicts with the already-existing schedule
	 * 
	 * Returns false if there is a conflict
	 */
	private boolean noConflict(Section section) {
		
		boolean noConflict = true; 
		
		//Parse the days of the week
		//If true, the section meets on that day
		boolean[] days = parseDays(section.getDay()); 
		
		//Iterate through the current schedule. If days are the same, then compare the times
		for (Section s : schedule) {
			boolean[] sDays = parseDays(s.getDay()); 
			for (int i = 0; i < 7; i++) {
				if (days[i] && sDays[i]) {
					//Compare the starting and ending times
					int[] start = parseTime(section.getStartTime()); 
					int[] end = parseTime(section.getEndTime()); 
					int[] sStart = parseTime(s.getStartTime()); 
					int[] sEnd = parseTime(s.getEndTime()); 
					
					int startTime = start[0] * 100 + start[1]; 
					int endTime = end[0] * 100 + end[1]; 
					int sStartTime = sStart[0] * 100 + sStart[1];
					int sEndTime = sEnd[0] * 100 + sEnd[1]; 
					
					
					System.out.println("startTime: " + startTime);
					System.out.println("endTime: " + endTime);
					System.out.println("sStartTime: " + sStartTime);
					System.out.println("sEndTime: " + sEndTime);
					
					
					//Check all possible counterexamples
					
					//Partial overlap and vice-versa
					if ((startTime <= sStartTime && sStartTime <= endTime) && !section.getSectionID().equals(s.getSectionID())) {
						noConflict = false; 
						System.out.println("Conflict found between " + section.getCourseName() + " and " + s.getCourseName() + "\n");
						System.out.println("Section IDs " + section.getSectionID() + " and " + s.getSectionID());
						
					}
					
					if ((startTime <= sEndTime && sEndTime <= endTime) && !section.getSectionID().equals(s.getSectionID())) {
						noConflict = false; 
						System.out.println("Conflict found between " + section.getCourseName() + " and " + s.getCourseName() + "\n");
						System.out.println("Section IDs " + section.getSectionID() + " and " + s.getSectionID());
						
					}
					
					//One completely contains the other and vice-versa
					if ((sStartTime <= startTime && sEndTime >= endTime) && !section.getSectionID().equals(s.getSectionID())) {
						noConflict = false; 
						System.out.println("Conflict found between " + section.getCourseName() + " and " + s.getCourseName() + "\n");
						System.out.println("Section IDs " + section.getSectionID() + " and " + s.getSectionID());
						
					}
					
					if ((startTime <= sStartTime && endTime >= sEndTime) && !section.getSectionID().equals(s.getSectionID())) {
						noConflict = false; 
						System.out.println("Conflict found between " + section.getCourseName() + " and " + s.getCourseName() + "\n");
						System.out.println("Section IDs " + section.getSectionID() + " and " + s.getSectionID());
						
					}
					
					//They have the exact same time
					if ((sStartTime == startTime || sEndTime == endTime) && !section.getSectionID().equals(s.getSectionID())) {
						noConflict = false; 
						System.out.println("Conflict found between " + section.getCourseName() + " and " + s.getCourseName() + "\n");
						System.out.println("Section IDs " + section.getSectionID() + " and " + s.getSectionID());
					}
					
					
				}
			}
				
			
		}
		if (schedule.size() == 0) {
			noConflict = true; 
		}
		
		//logic to incorporate section capacity
		if (section.getNumRegistered() >= section.getClassCapacity()) {
			noConflict = false; 
		}
		
		return noConflict; 
	}
	
	/*
	 * Helper method to parse the day String of a given section
	 */
	private boolean[] parseDays(String dayString) {
		boolean[] days = new boolean[7]; 
		
		for (int i = 0; i < 7; i++) {
			days[i] = false; 
		}
		
//		//Case where section meets on MWF
//		if (dayString.equals("MWF")) {
//			days[1] = true; 
//			days[3] = true; 
//			days[5] = true; 
//		} 
//		
//		//Case where section meets twice a week
//		else if (dayString.indexOf(",") != -1) {
//			
//			//Sunday
//			if (dayString.indexOf("Sun") != -1) {
//				days[0] = true; 
//			}
//			
//			//Monday
//			if (dayString.indexOf("Mon") != -1) {
//				days[1] = true; 
//			}
//			
//			//Tuesday
//			if (dayString.indexOf("Tues") != -1) {
//				days[2] = true; 
//			}
//			
//			//Wednesday
//			if (dayString.indexOf("Wed") != -1) {
//				days[3] = true; 
//			}
//			
//			//Thursday
//			if (dayString.indexOf("Thurs") != -1) {
//				days[4] = true; 
//			}
//			
//			//Friday
//			if (dayString.indexOf("Fri") != -1) {
//				days[5] = true; 
//			}
//			
//			//Saturday
//			if (dayString.indexOf("Sat") != -1) {
//				days[6] = true; 
//			}
//		}
//		
//		//Case where section meets once a week
//		else {
//			
//			//Sunday
//			if (dayString.equals("Sunday")) {
//				days[0] = true; 
//			}
//			
//			//Monday
//			if (dayString.equals("Monday")) {
//				days[1] = true; 
//			}
//			
//			//Tuesday
//			if (dayString.equals("Tuesday")) {
//				days[2] = true; 
//			}
//			
//			//Wednesday
//			if (dayString.equals("Wednesday")) {
//				days[3] = true; 
//			}
//			
//			//Thursday
//			if (dayString.equals("Thursday")) {
//				days[4] = true; 
//			}
//			
//			//Friday
//			if (dayString.equals("Friday")) {
//				days[5] = true; 
//			}
//			
//			//Saturday
//			if (dayString.equals("Saturday")) {
//				days[6] = true; 
//			}
//			
//		}
		
		if (dayString.indexOf("M") != -1) {
			days[1] = true; 
		}
		
		if (dayString.indexOf("T") != -1) {
			days[2] = true; 
		}
		
		if (dayString.indexOf("W") != -1) {
			days[3] = true; 
		}
		
		if (dayString.indexOf("H") != -1) {
			days[4] = true; 
		}
		
		if (dayString.indexOf("F") != -1) {
			days[5] = true; 
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
	
	/*
	 * Helper method to determine the walking distance for a given schedule based on the coordinates of each Section
	 */
	private double getWalkingDistance() { 
		//convert miles to m 
		double distanceConstraintInMeters = distanceConstraint * 1609.344;
		double totalDistanceInMeters = 0;  
		double deltaPhi = 0; 
		double deltaLambda = 0; 
		double a = 0; 
		double c = 0; 
		double d = 0; 
		int R = 6371000; 
		
		for (int i = 0; i < schedule.size() - 1; i++) {
<<<<<<< HEAD
<<<<<<< HEAD
			double[] section1Coords = new DatabaseHandler().getOneInstance().getLatitudeAndLongitude(schedule.get(i).getBuildingID()); 
			double[] section2Coords = new DatabaseHandler().getOneInstance().getLatitudeAndLongitude(schedule.get(i).getBuildingID()); 
=======
			double[] section1Coords = dbh.getLatitudeAndLongitude(schedule.get(i).getBuildingID()); 
			double[] section2Coords = dbh.getLatitudeAndLongitude(schedule.get(i).getBuildingID()); 
>>>>>>> 5d05a3cf00184c198244b2cacb6175f04185fe87
=======
			double[] section1Coords = dbh.getLatitudeAndLongitude(schedule.get(i).getBuildingID()); 
			double[] section2Coords = dbh.getLatitudeAndLongitude(schedule.get(i).getBuildingID()); 
>>>>>>> 5d05a3cf00184c198244b2cacb6175f04185fe87
			
			a = 0; 
			c = 0; 
			d = 0; 
			
			deltaPhi = Math.abs(section1Coords[0] - section2Coords[0]); //Change in latitude
			deltaLambda = Math.abs(section1Coords[1] - section2Coords[1]); //Change in longitude
			
			//a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
			a = Math.pow(Math.sin(deltaPhi / 2), 2) + ((Math.cos(section1Coords[0]) * Math.cos(section2Coords[0]) * Math.pow(Math.sin(deltaLambda / 2), 2)));
			
			//c = 2 ⋅ atan2( √a, √(1−a) )
			c = 2 * Math.atan2(Math.pow(a, 0.5), Math.pow(1 - a, 0.5)); 
			
			//d = R ⋅ c
			d = R * c; 
			
			totalDistanceInMeters += d; 
		}
		
		
		return totalDistanceInMeters / 1609.344; 
	}
	
	public Vector<Section> getSchedule() {
		
		if (getWalkingDistance() > distanceConstraint) {
			schedule.clear(); 
		}
		return schedule; 
	}
}
