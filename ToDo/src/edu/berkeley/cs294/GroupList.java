package edu.berkeley.cs294;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GroupList extends Activity{

	Button addGroup, addContact;
	Button addToDo;
	int group_counter = 0;			//ToDo : Save counter as static?

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// setup the layout
		setContentView(R.layout.group_list);

		// add a click-listener on the button
		addGroup = (Button) findViewById(R.id.AddGroup);
		addGroup.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent addNewGroup = new Intent(view.getContext(), Group_Edit.class);
				startActivityForResult(addNewGroup, 0);
			}
		});
		
		addContact = (Button) findViewById(R.id.AddContact);
		addContact.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent addNewContact = new Intent(view.getContext(), AddContact.class);
				startActivityForResult(addNewContact,0);
			}
		});
		
	}
}
