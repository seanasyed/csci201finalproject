package model;

import java.util.Vector;

public class Course {
	private int ID;
	private String school; 
	private String major;
	private String number; 
	private float units; 
	private String name;
	private String description;
	private int semester;
	private Vector<LectureSection> lectureSections; 
	
	public Course(int ID, String school, String major, String number, float units, String name, String description, int semester, Vector<LectureSection> lectureSections) {
		this.ID = ID;
		this.school = school; 
		this.major = major; 
		this.number = number;
		this.units = units;
		this.name = name;
		this.description = description;
		this.semester = semester;
		this.lectureSections = lectureSections;
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
	
	public Vector<LectureSection> getLectureSections() {
		return lectureSections; 
	}
}