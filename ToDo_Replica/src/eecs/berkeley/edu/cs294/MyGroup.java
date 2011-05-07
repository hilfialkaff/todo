package eecs.berkeley.edu.cs294;

import java.io.Serializable;

import android.util.Log;

/*
 * Serializing the group's informations so that the code would be more readable
 * 
 * TODO: Error-checking on the parameters.
 */
public class MyGroup implements Serializable{

	private String name = "";
	private String timestamp = "";
	private String rails_id = "";
	private String description = "";
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	/*
	 * Debugging function
	 */
	public void printMembers() {
		Log.d("DbDEBUG", "MyGroup Object name: " + name + " timestamp: " + timestamp + 
				" rails_id: " + rails_id + " description: " + description);
	}
}