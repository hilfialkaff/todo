package edu.berkeley.cs294;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ToDo_Edit extends Activity{
	EditText et_todo = (EditText) findViewById(R.id.et_todo);
	EditText et_tags = (EditText) findViewById(R.id.et_tags);
	EditText et_notes = (EditText) findViewById(R.id.et_notes);
	Button b_save = (Button) findViewById(R.id.b_save);
	String todo;
	String tags;
	String notes;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.todo_edit);
		
		b_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				todo = et_todo.getText().toString();
				tags = et_tags.getText().toString();
				notes = et_notes.getText().toString();
				
				
			}
		});
	}

	
	
	
}
