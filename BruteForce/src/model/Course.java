package model;

public class Course {
	private int ID;
	private String school; 
	private String major;
	private String number; 
	private float units; 
	private String name;
	private String description;
	private int semester;
	
	public Course(int ID, String school, String major, String number, float units, String name, String description, int semester) {
		this.ID = ID;
		this.school = school; 
		this.major = major; 
		this.number = number;
		this.units = units;
		this.name = name;
		this.description = description;
		this.semester = semester;
	}
	
	public int getID() {
		return ID;
	}

	public String getSchool() {
		return school;
	}

	public String getMajor() {
		return major;
	}

	public String getNumber() {
		return number;
	}

	public float getUnits() {
		return units;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getSemester() {
		return semester;
	}
}
