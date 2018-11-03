package model;

import java.util.Vector;

public class User {
	
	private String fName; 
	private String lName; 
	private String username; 
	private int studentID; 
	private String passwordHash; 
	//private Vector<Course> courses; 
	private Vector<Section> schedule; 
	
	public User(String fName, String lName, String username, int studentID) {
		
		this.fName = fName; 
		this.lName = lName; 
		this.username = username; 
		this.studentID = studentID;
		
		//courses = new Vector<Course>(); 
		schedule = new Vector<Section>(); 
	}
	
	/**
	 * Setter and getter for fName
	 */
	
	public void setFName(String fName) {
		this.fName = fName; 
	}
	
	public String getFName() {
		return fName; 
	}
	
	/**
	 * Setter and getter for lName
	 */
	
	public void setLName(String lName) {
		this.lName = lName; 
	}
	
	public String getLName() {
		return lName; 
	}
	
	/**
	 * Setter and getter for username
	 */
	
	public void setUsername(String username) {
		this.username = username; 
	}
	
	public String getUsername() {
		return username; 
	}
	
	/**
	 * Setter and getter for studentID
	 */
	
	public void setStudentID(int studentID) {
		this.studentID = studentID; 
	}
	
	public int getStudentID() {
		return studentID;
	}
	
	/*
	 * Setter and getter for passwordHash
	 */
	
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public String getPasswordHash() {
		return passwordHash; 
	}
	
	/**
	 * Setter and getter for courses
	 */
	
//	public void setCourses(Vector<Course> courses) {
//		this.courses = courses; 
//	}
//	
//	public Vector<Course> getCourses() {
//		return courses; 
//	}
	
	/**
	 * Setter and getter for schedule
	 */
	
	public void setSchedule(Vector<Section> schedule) {
		this.schedule = schedule; 
	}
	
	public Vector<Section> getSchedule() {
		return schedule; 
	}
	
}
