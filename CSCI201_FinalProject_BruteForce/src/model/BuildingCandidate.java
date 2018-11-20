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

public class BuildingCandidate {
	
	String ID, fullName, address;
	float longitude, latitude;
	
	private static final int MAX_ADDRESS = 45, MAX_FULLNAME = 45;
	
	public BuildingCandidate(String iD, String fullName, String address) {
		ID = iD;
		if (fullName.length() > MAX_FULLNAME) {
			if (fullName.contains(" (")) {
				fullName = fullName.substring(0, fullName.indexOf(" ("));
			} else {
				fullName = fullName.substring(0, MAX_FULLNAME - 3) + "...";
			}
		}
		this.fullName = fullName;
		if (address.length() > MAX_ADDRESS) {
			if (address.contains("\\r\\n")) {
				address = address.substring(0, address.indexOf("\\r\\n"));
			} else {
				address = address.substring(0, MAX_ADDRESS - 3) + "...";
			}
		}
		this.address = address;
		longitude = 1; // Use positive value to indicate the 
						// longitude and attitude are unknown 
		latitude = 0;
	}

	public BuildingCandidate(String iD, String fullName, String address, float longitude, float latitude) {
		this(iD, fullName, address);
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public String getID() {
		return ID;
	}

	public String insertDBString() {
		
		String base = "INSERT INTO `scheduling`.`Building` ("
				+ "`ID`, `fullName`, `address`, `longitude`, `latitude`"
				+ ")\n\tVALUES (";
		base += "\"" + ID + "\", ";
		base += "\"" + fullName + "\", ";
		base += "\"" + address + "\", ";
		base += longitude + ", ";
		base += latitude + ");";
		
		return base;
	}
	
}
