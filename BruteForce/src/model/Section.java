package model;

public class Section {
	private String sectionID; 
	private String session;
	private String type;
	private String time;
	private String day;
	private String instructor;
	private int numRegistered;
	private int classCapacity;
	private String buildingID;
	private String courseID;
	public Section(String sectionID, String session, String type, String time, String day, String instructor,
			int numRegistered, int classCapacity, String buildingID, String courseID) {
		super();
		this.sectionID = sectionID;
		this.session = session;
		this.type = type;
		this.time = time;
		this.day = day;
		this.instructor = instructor;
		this.numRegistered = numRegistered;
		this.classCapacity = classCapacity;
		this.buildingID = buildingID;
		this.courseID = courseID;
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
	public String getTime() {
		return time;
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
}
