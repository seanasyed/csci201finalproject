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

public class Section {

	/*
	 * ---- Private members ----
	 */
	private String sectionID;
	private String session;
	private String type;
	private String startTime;
	private String endTime;
	private String day;
	private String instructor;
	private int numRegistered;
	private int classCapacity;
	private String buildingID;
	private String courseID;
	private String courseName;
	private String lectureSection_ID = ""; // used for web crawling

	/*
	 * ---- Class Constants ----
	 */
	private static final int DEFAULT_NUMREGISTERED = 0;
	private static final String DEFAULT_COURSENAME = "";

	/*
	 * ---- Constructors ----
	 */
	public Section(String sectionID, String session, String type, String startTime, String endTime, String day,
			String instructor, int numRegistered, int classCapacity, String buildingID, String courseID,
			String courseName) {

		this.sectionID = sectionID;
		this.session = session;
		this.type = type;
		this.startTime = startTime;
		this.endTime = endTime;
		this.day = day;
		this.instructor = instructor;
		this.numRegistered = numRegistered;
		this.classCapacity = classCapacity;
		this.buildingID = buildingID;
		this.courseID = courseID;
		this.courseName = courseName;
	}

	/**
	 * Constructor called by web crawling.
	 * 
	 * @param sectionID
	 * @param type
	 * @param start_time
	 * @param end_time
	 * @param day
	 * @param instructor
	 * @param building_ID
	 * @param classCapacity
	 * @param course_ID
	 */
	public Section(String sectionID, String type, String startTime, String endTime, String day, String instructor,
			String buildingID, int classCapacity, int courseID) {

		this(sectionID, type, type, startTime, endTime, day, instructor, DEFAULT_NUMREGISTERED, classCapacity,
				buildingID, Integer.toString(courseID), DEFAULT_COURSENAME);
	}

	/*
	 * ---- Getters and Setters ----
	 */
	public String getCourseName() {
		return courseName;
	}

	public String getSectionID() {
		return sectionID;
	}

	public String getSession() {
		return session;
	}

	public String getType() {
		return type;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getDay() {
		return day;
	}

	public String getInstructor() {
		return instructor;
	}

	public int getNumRegistered() {
		return numRegistered;
	}

	public int getClassCapacity() {
		return classCapacity;
	}

	public String getBuildingID() {
		return buildingID;
	}

	public String getCourseID() {
		return courseID;
	}

	public String getLectureSection_ID() {
		return lectureSection_ID;
	}

	public void setNumRegistered(int n) {
		numRegistered = n;
	}

	/**
	 * If {@code this} is no lecture, then set up a {@code lectureSeciond_ID}.
	 * 
	 * @param lectureSection_ID
	 */
	public void setLectureSection_ID(String lectureSection_ID) {
		if (!this.type.toLowerCase().equals("lecture"))
			this.lectureSection_ID = lectureSection_ID;
	}

	/*
	 * ---- DB string getters ----
	 */
	public String getSelectDBString() {
		return "SELECT * FROM " + type + "_Sections WHERE sectionID= " + "'" + sectionID
				+ (isLecture() ? "'" : "' AND Lecture_SectionID='" + lectureSection_ID + "'");
	}

	public String insertDBString() {

		String base = "INSERT INTO `scheduling`.`" + type + "_Sections` ("
				+ "`sectionID`, `type`, `start_time`, `end_time`, `day`, "
				+ "`instructor`, `numRegistered`, `classCapacity`, `Building_ID`, " + "`Course_ID`"
				+ (type.toLowerCase().equals("lecture") ? "" : ", `Lecture_SectionID`") + ")\n\tVALUES (";

		base += "\"" + sectionID + "\", ";
		base += "\"" + type + "\", ";
		base += "\"" + startTime + "\", ";
		base += "\"" + endTime + "\", ";
		base += "\"" + day + "\", ";
		base += "\"" + instructor + "\", ";
		base += numRegistered + ", ";
		base += classCapacity + ", ";
		base += "\"" + buildingID + "\", ";
		base += courseID;
		base += isLecture() ? ");" : ", \"" + lectureSection_ID + "\");";

		return base;
	}

	/*
	 * ---- Other methods ----
	 */
	public boolean isLecture() {
		return type.toLowerCase().equals("lecture");
	}

	@Override
	public String toString() {
		return "<font size=18px>Course: <b>" + courseName + "</b> (" + type + ")\t" + startTime + "-" + endTime + "\t"
				+ day + "\t" + instructor + "</font><br>";
	}
}
