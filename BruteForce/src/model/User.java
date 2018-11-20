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

public class User {
	private String username;
	private String fName; 
	private String lName;

	public User(String username, String fName, String lName) {
		this.username = username;
		this.fName = fName;
		this.lName = lName;
	}
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
