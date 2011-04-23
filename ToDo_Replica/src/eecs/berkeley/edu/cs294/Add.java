package eecs.berkeley.edu.cs294;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AnalogClock;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TabHost.TabSpec;

public class Add extends Activity {
	/** Called when the activity is first created. */
	private String title, place, note, tag, group, status, priority;
	private String array_spinner_group[], array_spinner_status[], array_spinner_priority[];
	private Date date = new Date();
	ArrayAdapter<String> adapter;
	
	EditText et_title, et_place, et_note;
	AutoCompleteTextView actv_tag;
	Spinner s_group, s_status, s_priority;
	Button b_submit;
	TabHost th_add;
	TableLayout tl_todo_lists;

	private DatabaseHelper dh;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);

		th_add = (TabHost) findViewById(R.id.th_add);
		th_add.setup();

		TabSpec tab_one = th_add.newTabSpec("tab_one_btn_tab");
		tab_one.setContent(R.id.tl_add);
		tab_one.setIndicator("Basic");
		th_add.addTab(tab_one);
		
		TabSpec tab_two = th_add.newTabSpec("tab_two_btn_tab");
		tab_two.setContent(new TabHost.TabContentFactory() {  
			public View createTabContent(String tag) {  
				return(new AnalogClock(Add.this));  
			}  
		}); 
		tab_two.setIndicator("Advanced");
		th_add.addTab(tab_two);
		
		th_add.setCurrentTab(0);
		
		tl_todo_lists = (TableLayout) findViewById(R.id.tl_add);
		this.dh = new DatabaseHelper(this);

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

		s_priority = (Spinner) findViewById(R.id.s_priority);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array_spinner_priority);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s_priority.setAdapter(adapter);
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
				String dateStr = Long.toString(date.getTime());
				
				dh.insert_to_do(title, place, note, tag, group, status, priority, dateStr);

				/* Push changes to the remote if applicable */
				List<String> newEntry = dh.select_to_do(title);
				if(group != null) {
					ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
					NetworkInfo netInfo = connManager.getActiveNetworkInfo();

					if(netInfo == null) {
						Log.d("DEBUG", "--------------- No internet connection --------- ");
					}

					if (netInfo.isConnected()) {
						ServerConnection.pushRemote(newEntry, ServerConnection.POST_REQUEST);
					}
				}
				
				
				setResult(RESULT_OK);
				finish();
			}
		});
	}
}