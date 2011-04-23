package eecs.berkeley.edu.cs294;

import java.io.Serializable;

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
		return railsID;
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
}