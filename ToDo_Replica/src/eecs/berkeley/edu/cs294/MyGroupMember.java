package eecs.berkeley.edu.cs294;

import java.io.Serializable;

import android.util.Log;

/*
 * Serializing the group members' informations so that the code would be more readable
 * 
 * TODO: Error-checking on the parameters.
 */
public class MyGroupMember implements Serializable{

	private String name = "";
	private String number = "";
	private String timestamp = "";
	private String rails_id = "";
	private String email = "";
	private String group_id = "";
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getRailsID() {
		return rails_id;
	}
	public void setRailsID(String rails_id) {
		this.rails_id = rails_id;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getGroupID() {
		return group_id;
	}
	public void setGroupID(String group_id) {
		this.group_id = group_id;
	}
	
	/*
	 * Debugging function
	 */
	public void printMembers() {
		Log.d("DbDEBUG", "My GroupMember Object name: " + name + " number: " + number +
				" timestamp: " + timestamp + " rails_id: " + rails_id + " email: " + email + 
				" group_id: " + group_id);
	}
}