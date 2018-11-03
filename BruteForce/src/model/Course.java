package model;

import java.util.Vector;

public class Course {
	
	private String school; 
	private String major;
	private float units; 
	private int number; 
	private Vector<Section> sections; 
	
	public Course(String school, String major, float units, int number, Vector<Section> sections) {
		this.school = school; 
		this.major = major; 
		this.units = units;
		this.number = number; 
		this.sections = sections; 
	}
	
	/**
	 * Setter and getter for school
	 */
	
	public void setSchool(String school) {
		this.school = school; 
	}
	
	public String getSchool() {
		return school; 
	}
	
	/*
	 * Setter and getter for major
	 */
	
	public void setMajor(String major) {
		this.major = major; 
	}
	
	public String getMajor() {
		return major; 
	}
	
	/*
	 * Setter and getter for units
	 */
	
	public void setUnits(float units) {
		this.units = units; 
	}
	
	public float getUnits() {
		return units; 
	}
	
	/*
	 * Setter and getter for number
	 */
	
	public void setNumber(int number) {
		this.number = number; 
	}
	
	public int getNumber() {
		return number; 
	}
	
	/*
	 * Setter and getter for sections
	 */
	
	public void setSections(Vector<Section> sections) {
		this.sections = sections; 
	}
	
	public Vector<Section> getSections() {
		return sections; 
	}
}
