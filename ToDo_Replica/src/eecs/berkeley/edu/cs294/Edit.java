package eecs.berkeley.edu.cs294;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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

public class Edit extends Activity {
	/** Called when the activity is first created. */
	private String title, place, note, tag, group, status, priority;
	private String array_spinner_group[], array_spinner_status[], array_spinner_priority[];
	ArrayAdapter<String> adapter;
	
	EditText et_title, et_place, et_note;
	AutoCompleteTextView actv_tag;
	Spinner s_group, s_status, s_priority;
	Button b_submit;
	TableLayout tl_todo_lists;
	
	private DatabaseHelper dh;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			Log.w("debug", "extras null");
			return;
		}
		
		final int pk = extras.getInt("pk_select");
		this.dh = new DatabaseHelper(this);	
		List<String> row = this.dh.select_to_do(pk);

		tl_todo_lists = (TableLayout) findViewById(R.id.tl_todo_lists);
		this.dh = new DatabaseHelper(this);
		
		//List<String> groups = dh.selectAll_group_name();
		//array_spinner_group = new String[groups.size()];
		array_spinner_group = new String[4];
		array_spinner_group[0] = "None";
		array_spinner_group[1] = "Group 1";
		array_spinner_group[2] = "Group 2";
		array_spinner_group[3] = "Group 3";
		
		
		array_spinner_status = new String[3];
		array_spinner_status[0] = "Incomplete";
		array_spinner_status[1] = "In Progress";
		array_spinner_status[2] = "Complete";
		
		array_spinner_priority = new String[3];
		array_spinner_priority[0] = "Low";
		array_spinner_priority[1] = "Medium";
		array_spinner_priority[2] = "High";
		
		et_title = (EditText) findViewById(R.id.et_title);
		et_title.setText(row.get(0));
		et_place = (EditText) findViewById(R.id.et_place);
		et_place.setText(row.get(1));
		et_note = (EditText) findViewById(R.id.et_note);
		et_note.setText(row.get(2));
		actv_tag = (AutoCompleteTextView) findViewById(R.id.actv_tag);
		actv_tag.setText(row.get(3));
		
		s_group = (Spinner) findViewById(R.id.s_group);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array_spinner_group);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s_group.setAdapter(adapter);
		//s_group.setSelection(groups.indexOf(row.get(4)));
		if (row.get(4).equalsIgnoreCase("None")) {		
			s_group.setSelection(0);
			group = "None";
		}
		else if (row.get(4).equalsIgnoreCase("Group 1")) {
			s_group.setSelection(1);
			group = "Group1";
		}
		else if (row.get(4).equalsIgnoreCase("Group 2")) {
			s_group.setSelection(2);
			group = "Group2";
		}
		else {
			s_group.setSelection(3);
			group = "Group3";
		}
		
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
		
		if (row.get(5).equalsIgnoreCase("Incomplete")) {		
			s_status.setSelection(0);
			status = "Incomplete";
		}
		else if (row.get(5).equalsIgnoreCase("In Progress")) {
			s_status.setSelection(1);
			status = "In Progress";
		}
		else {
			s_status.setSelection(2);
			status = "Complete";
		}
		
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
		
		s_priority = (Spinner) findViewById(R.id.s_priority);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array_spinner_priority);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s_priority.setAdapter(adapter);
		
		if (row.get(6).equalsIgnoreCase("Low")) {		
			s_priority.setSelection(0);
			priority = "Low";
		}
		else if (row.get(6).equalsIgnoreCase("Medium")) {
			s_priority.setSelection(1);
			priority = "Medium";
		}
		else {
			s_priority.setSelection(2);
			priority = "High";
		}
		
		s_priority.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				priority =  parent.getItemAtPosition(pos).toString();
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
				dh.update_to_do(pk, title, place, note, tag, group, status, priority);
				setResult(RESULT_OK);
				finish();
			}
		});
	}
}