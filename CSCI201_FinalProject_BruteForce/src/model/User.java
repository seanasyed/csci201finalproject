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

public class User {
	/*
	 * ---- Private members ----
	 */
	private String username;
	private String fName;
	private String lName;

	/*
	 * ---- Constructors ----
	 */
	public User(String username, String fName, String lName) {
		this.username = username;
		this.fName = fName;
		this.lName = lName;
	}

	/*
	 * ---- Getters and Setters ----
	 */
	public String getUsername() {
		return username;
	}

	public String getfName() {
		return fName;
	}

	public String getlName() {
		return lName;
	}

}
