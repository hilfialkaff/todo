package eecs.berkeley.edu.cs294;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;

public class Add extends Activity {
	/** Called when the activity is first created. */
	private String title, place, note, tag, group, status;
	private String array_spinner_group[], array_spinner_status[];
	ArrayAdapter<String> adapter;
	
	EditText et_title, et_place, et_note;
	AutoCompleteTextView actv_tag;
	Spinner s_group, s_status;
	Button b_submit;
	TableLayout tl_todo_lists;
	
	private DatabaseHelper dh;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		
		tl_todo_lists = (TableLayout) findViewById(R.id.tl_todo_lists);
		this.dh = new DatabaseHelper(this);
		
		array_spinner_group = new String[4];
		array_spinner_group[0] = "None";
		array_spinner_group[1] = "Group 1";
		array_spinner_group[2] = "Group 2";
		array_spinner_group[3] = "Group 3";
		
		array_spinner_status = new String[3];
		array_spinner_status[0] = "Incomplete";
		array_spinner_status[1] = "On Progress";
		array_spinner_status[2] = "Complete";
		
		et_title = (EditText) findViewById(R.id.et_title);
		et_place = (EditText) findViewById(R.id.et_place);
		et_note = (EditText) findViewById(R.id.et_note);
		
		actv_tag = (AutoCompleteTextView) findViewById(R.id.actv_tag);
		
		s_group = (Spinner) findViewById(R.id.s_group);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array_spinner_group);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s_group.setAdapter(adapter);
		s_group.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				group =  parent.getItemAtPosition(pos).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// do nothing
			}
		});
		
		s_status = (Spinner) findViewById(R.id.s_status);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array_spinner_status);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s_status.setAdapter(adapter);
		s_status.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				status =  parent.getItemAtPosition(pos).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// do nothing
			}
		});
		
		b_submit = (Button) findViewById(R.id.b_submit);
		
		b_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				title = et_title.getText().toString();
				place = et_place.getText().toString();
				note = et_note.getText().toString();
				tag = actv_tag.getText().toString();
				dh.insert_to_do(title, place, note, tag, group, status);
				setResult(RESULT_OK);
				finish();
			}
		});
	}
}