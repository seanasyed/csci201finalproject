package model;

public class Section {
	
	private String sectionID; 
	private char type; 
	private int spotsLeft; 
	
	public Section(String sectionID, char type, int spotsLeft) {
		this.sectionID = sectionID; 
		this.type = type; 
		this.spotsLeft = spotsLeft; 
	}
	
	/**
	 * Setter and getter for sectionID
	 */
	
	public void setSectionID(String sectionID) {
		this.sectionID = sectionID; 
	}
	
	public String getSectionID() {
		return sectionID; 
	}
	
	/**
	 * Setter and getter for type
	 */
	
	public void setType(char type) {
		this.type = type; 
	}
	
	public char getType() {
		return type; 
	}
	
	/**
	 * Setter and getter for spotsLeft
	 */
	
	public void setSpotsLeft(int spotsLeft) {
		this.spotsLeft = spotsLeft; 
	}
	
	public int getSpotsLeft() {
		return spotsLeft; 
	}
}
