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
	
	public Course(int ID, String school, String major, String number, float units, String name, 
					String description, int semester) {
		this.ID = ID;
		this.school = school; 
		this.major = major; 
		this.number = number;
		this.units = units;
		this.name = name;
		this.description = description;
		this.semester = semester;
		this.lectureSections = new Vector<LectureSection>();
	}
	public void addLectureSection(LectureSection lectureSection) {
		lectureSections.add(lectureSection);
	}
	public boolean lectureSectionExists(String sectionID) {
		for (int i = 0; i < lectureSections.size(); i++) {
			if (lectureSections.get(i).getSectionID().equals(sectionID)) return true;
		}
		return false;
	}
	public LectureSection getLectureSection(String sectionID) {
		for (int i = 0; i < lectureSections.size(); i++) {
			if (lectureSections.get(i).getSectionID().equals(sectionID)) return lectureSections.get(i);
		}
		return null;
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