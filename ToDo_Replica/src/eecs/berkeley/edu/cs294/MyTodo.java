package eecs.berkeley.edu.cs294;

import java.io.Serializable;

import android.util.Log;

/*
 * Serializing the object todo so that the code would be more readable
 * 
 * TODO: Error-checking on the parameters.
 */
public class MyTodo implements Serializable{

	private String title = "";
	private String place = "";
	private String note = "";
	private String tag = "";
	private int group_id = 0;
	private String status = "";
	private String priority = "";
	private String timestamp = "";
	private String deadline = "";
	private String to_do_rails_id = "";
	
	public String getTodoTitle() {
		return title;
	}
	public void setTodoTitle(String title) {
		this.title = title;
	}
	public String getTodoPlace() {
		return place;
	}
	public void setTodoPlace(String place) {
		this.place = place;
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
	public String getTodoGroupId() {
		return Integer.toString(group_id);
	}
	public void setTodoGroupId(String group_id) {
		this.group_id = Integer.parseInt(group_id);
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
	public String getTodoDeadline() {
		return deadline;
	}
	public void setTodoDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getTodoRailsId() {
		return to_do_rails_id;
	}
	public void setTodoRailsID(String to_do_rails_id) {
		this.to_do_rails_id = to_do_rails_id;
	}
	
	/*
	 * Debugging function
	 */
	public void printMembers() {
		Log.d("DbDEBUG", "MyTodo Object title: " + title + " place: " + place + " note: " + note + 
				" tag: " + tag + " group_id: " + group_id + " status: " + status + 
				" priority: " + priority + " timestamp: " + timestamp + " deadline: " + deadline +  " to_do_rails_id: " + to_do_rails_id);
	}
}