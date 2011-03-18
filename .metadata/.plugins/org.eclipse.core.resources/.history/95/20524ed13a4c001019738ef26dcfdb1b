package edu.berkeley.cs294;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ToDo extends Activity {
	/** Called when the activity is first created. */

	//initialize a button and a counter
	Button mapBtn, toDoListBtn, groupBtn;
	Button addToDo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// setup the layout
		setContentView(R.layout.main);

		mapBtn = (Button) findViewById(R.id.MapButton);
		mapBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent seeMap = new Intent(view.getContext(), ToDo_Edit.class);
				startActivityForResult(seeMap, 0);
			}
		});
		
		toDoListBtn = (Button) findViewById(R.id.ToDoListButton);
		toDoListBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent seeList = new Intent(view.getContext(), ToDoList.class);
				startActivityForResult(seeList, 0);
			}
		});
		
		groupBtn = (Button) findViewById(R.id.GroupButton);
		groupBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent seeGroups = new Intent(view.getContext(), GroupList.class);
				startActivityForResult(seeGroups, 0);
			}
		});
		
		addToDo = (Button) findViewById(R.id.AddToDoButton);
		addToDo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent addNewToDo = new Intent(view.getContext(), AddToDo.class);
				startActivityForResult(addNewToDo, 0);
			}
		});

	}
}