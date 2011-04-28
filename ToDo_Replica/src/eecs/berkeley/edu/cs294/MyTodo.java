package eecs.berkeley.edu.cs294;

import java.io.Serializable;

import android.util.Log;

/*
 * Serializing the object todo so that the code would be more readable
 * 
 * TODO: Error-checking on the parameters.
 * TODO: Adjust with the actual server
 */
public class MyTodo implements Serializable{

	public String name = "";
	public String title = "";
	public String note = "";
	public String tag = "";
	public String group = "";
	public String status = "";
	public String priority = "";
	public String timestamp = "";
	public String railsID = "";
	
	public String getTodoName() {
		return name;
	}
	public void setTodoName(String name) {
		this.name = name;
	}
	public String getTodoPlace() {
		return title;
	}
	public void setTodoPlace(String title) {
		this.title = title;
	}
	public String getTodoNote() {
		return note;
	}
	public void setTodoNote(String note) {
		this.note = note;
	}
	public String getTodoTag() {
		return tag;
	}
	public void setTodoTag(String tag) {
		this.tag = tag;
	}
	public String getTodoGroup() {
		return note;
	}
	public void setTodoGroup(String group) {
		this.group = group;
	}
	public String getTodoStatus() {
		return status;
	}
	public void setTodoStatus(String status) {
		this.status = status;
	}
	public String getTodoPriority() {
		return priority;
	}
	public void setTodoPriority(String priority) {
		this.priority= priority;
	}
	public String getTodoTimestamp() {
		return timestamp;
	}
	public void setTodoTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getTodoRailsID() {
		return railsID;
	}
	public void setTodoRailsID(String railsID) {
		this.railsID = railsID;
	}
	
	/*
	 * Debugging function
	 */
	public void printMembers() {
		Log.d("ServerDEBUG", "MyTodo object name: " + name + " title: " + title + " note: " + note 
				+ " tag: " + tag + " group: " + group + " status: " + status + " priority: "
				+ " timestamp: " + timestamp + " railsID: " + railsID);
	}
}