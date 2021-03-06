package eecs.berkeley.edu.cs294;

import java.io.Serializable;

import android.util.Log;

/*
 * Serializing the sent invitation's information so that the code would be more readable
 * 
 * TODO: Error-checking on the parameters.
 */
public class MySentInvitation implements Serializable{

	private String group = "";
	private String timestamp = "";
	private String rails_id = "";
	private String recipient = "";
	private String user_id = "";
	private String description = "";
	private String status = "";
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
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
	
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	public String getUserID() {
		return user_id;
	}
	public void setUserID(String user_id) {
		this.user_id = user_id;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	/*
	 * Debugging function
	 */
	public void printMembers() {
		Log.d("DbDEBUG", "MySentInvitation Object group: " + group + " timestamp: " + timestamp + 
				" rails_id: " + rails_id + " recipient: " + recipient + 
				" user_id: " + user_id + " description: " + description + " status: " + status);
	}
}