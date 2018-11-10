package model;

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
