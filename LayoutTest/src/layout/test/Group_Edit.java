package layout.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow.LayoutParams;

public class Group_Edit extends Activity implements OnClickListener {
	/** Called when the activity is first created. */

	//initialize a button and a counter
	Button btn;
	int counter = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// setup the layout
		setContentView(R.layout.main);

		// add a click-listener on the button
		btn = (Button) findViewById(R.id.Button01);
		btn.setOnClickListener(this);        

	}

	// run when the button is clicked
	public void onClick(View view) {

		// get a reference for the TableLayout
		TableLayout table = (TableLayout) findViewById(R.id.TableLayout03);

		// create a new TableRow
		//TableRow row = new TableRow(this);

		// count the counter up by one
		counter++;

		// create a button
		Button b = new Button(this);
		b.setText("person" + counter);
		b.setTextColor(Color.WHITE);
		b.setBackgroundColor(Color.BLACK);


		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(v.getContext(), ToDo_Edit.class);
				startActivityForResult(intent, 0);
			}
		});

		//        b.setOnClickListener(new View.OnClickListener() {
		//			public void onClick(View view) {
		//				Intent myIntent = new Intent(view.getContext(), ToDo_Edit.class);
		//                startActivityForResult(myIntent, 0);
		//			}
		//
		//		});
		// add the TextView and the CheckBox to the new TableRow
		//row.addView(b);

		// add the TableRow to the TableLayout
		table.addView(b,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

	}
}