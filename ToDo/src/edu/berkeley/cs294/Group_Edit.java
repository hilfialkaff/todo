package edu.berkeley.cs294;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Group_Edit extends Activity implements OnClickListener {
	/** Called when the activity is first created. */

	//initialize a button and a counter
	Button addMember, cancel, save;
	static int counter = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// setup the layout
		setContentView(R.layout.group_edit);

		// add a click-listener on the button
		addMember = (Button) findViewById(R.id.AddMember);
		addMember.setOnClickListener(this);        

		cancel = (Button) findViewById(R.id.CancelButton);
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//Go back
			}
		});
		
		save = (Button) findViewById(R.id.SaveButton);
		save.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v){
				//Update database and go back
			}
		});

	}


	// run when the button is clicked
	@Override
	public void onClick(View view) {

		Intent addMember = new Intent(view.getContext(), AddMember.class);
		startActivityForResult(addMember,0);

		//Get results, if add new one, put a new row, if not, do nothing

		//		// get a reference for the TableLayout
		//		TableLayout table = (TableLayout) findViewById(R.id.Content_groupedit_editMembers);
		//		
		//		// create a new TableRow
		//		TableRow row = new TableRow(this);
		//
		//		// count the counter up by one
		//		counter++;
		//
		//		// create a button
		//		TextView memberName = new TextView(this);
		//		memberName.setText("person " + counter);
		//		memberName.setTextColor(Color.WHITE);
		//		memberName.setBackgroundColor(Color.BLACK);
		//
		//
		//		memberName.setOnClickListener(new View.OnClickListener() {
		//
		//			@Override
		//			public void onClick(View v) {
		//				// TODO Auto-generated method stub
		//				Intent intent = new Intent(v.getContext(), ToDo_Edit.class);
		//				startActivityForResult(intent, 0);
		//			}
		//		});
		//
		//		Button removeBtn = new Button(this);
		//		removeBtn.setText("-");
		//		
		//		row.addView(memberName);
		//		row.addView(removeBtn);
		//		
		//		// add the TableRow to the TableLayout
		//		table.addView(row,new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

	}
}