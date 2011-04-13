package eecs.berkeley.edu.cs294;

import java.io.Serializable;

public class MyTodo implements Serializable{

	public String name = "";
	public String title = "";
	public String content = "";

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
	public String getTodoContent() {
		return content;
	}
	public void setTodoContent(String content) {
		this.content = content;
	}
}