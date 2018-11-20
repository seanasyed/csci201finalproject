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

import java.util.Vector;

public class Course {
	/*
	 * ---- Private members ----
	 */
	private int ID;
	private String school;
	private String major;
	private String number;
	private float units;
	private String name;
	private String description;
	private int semester;
	private Vector<LectureSection> lectureSections;

	/*
	 * ---- Class Constants ----
	 */
	private static final int MAX_MAJOR = 45, MAX_NAME = 100;
	private static final int DEFAULT_SEMESTER = 1;

	/*
	 * ---- Constructors ----
	 */
	public Course(int ID, String school, String major, String number, float units, String name, String description,
			int semester) {
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

	/**
	 * Constructor for web crawling.
	 * 
	 * @param school
	 * @param major
	 */
	public Course(String school, String major) {
		this(0, school, major, null, 0, null, null, DEFAULT_SEMESTER);
		if (school.contains(" "))
			this.school = school.substring(0, school.indexOf(" "));
		if (major.length() > MAX_MAJOR)
			this.major = major.substring(0, MAX_MAJOR);

	}

	/*
	 * ---- Other methods ----
	 */
	public void addLectureSection(LectureSection lectureSection) {
		lectureSections.add(lectureSection);
	}

	public boolean lectureSectionExists(String sectionID) {
		for (int i = 0; i < lectureSections.size(); i++) {
			if (lectureSections.get(i).getSectionID().equals(sectionID))
				return true;
		}
		return false;
	}

	public String insertDBString() {

		if (number == null || name == null || description == null)
			return null;

		String base = "INSERT INTO `scheduling`.`Course` (" + "`school`, `major`, `number`, `units`, `name`, "
				+ "`description`, `semester`)\n\tVALUES (";
		school = school.replace('"', '\'');
		base += "\"" + school + "\", ";
		base += "\"" + major + "\", ";
		base += "\"" + number + "\", ";
		base += units + ", ";
		name = name.replace('"', '\'');
		base += "\"" + name + "\", ";
		description = description.replace("\"", "'");
		base += "\"" + description + "\", ";
		base += semester + ");";

		return base;
	}

	/*
	 * ---- Getters and Setters ----
	 */
	public LectureSection getLectureSection(String sectionID) {
		for (int i = 0; i < lectureSections.size(); i++) {
			if (lectureSections.get(i).getSectionID().equals(sectionID))
				return lectureSections.get(i);
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

	public void setNumber(String number) {
		this.number = number;
	}

	public void setName(String name) {
		// Remove spaces from the name and make sure the length of the name is no larger
		// than 100.
		while (name.charAt(0) == ' ')
			name = name.substring(1);
		while (name.charAt(name.length() - 1) == ' ')
			name = name.substring(0, name.length() - 1);
		if (name.length() > MAX_NAME)
			name = name.substring(0, MAX_NAME - 3) + "...";
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setUnits(float units) {
		this.units = units;
	}

	public Vector<LectureSection> getLectureSections() {
		return lectureSections;
	}

	/*
	 * ---- Override methods ----
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((major == null) ? 0 : major.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((school == null) ? 0 : school.hashCode());
		result = prime * result + semester;
		result = prime * result + Float.floatToIntBits(units);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (major == null) {
			if (other.major != null)
				return false;
		} else if (!major.equals(other.major))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (school == null) {
			if (other.school != null)
				return false;
		} else if (!school.equals(other.school))
			return false;
		if (semester != other.semester)
			return false;
		if (Float.floatToIntBits(units) != Float.floatToIntBits(other.units))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CourseCandidate [school=" + school + ", major=" + major + ", number=" + number + ", name=" + name
				+ ", description=" + description + ", units=" + units + ", semester=" + semester + "]";
	}

}