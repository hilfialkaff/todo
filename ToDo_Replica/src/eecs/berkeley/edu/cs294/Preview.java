package eecs.berkeley.edu.cs294;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Preview extends Activity {
	/** Called when the activity is first created. */
	TextView tv_title_preview2;
	TextView tv_place_preview2;
	TextView tv_note_preview2;
	TextView tv_tag_preview2;
	TextView tv_group_preview2;
	TextView tv_status_preview2;
	TextView tv_priority_preview2;
	
	private DatabaseHelper dh;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preview);
		this.dh = new DatabaseHelper(this);
		
		tv_title_preview2 = (TextView) findViewById(R.id.tv_title_preview2);
		tv_place_preview2 = (TextView) findViewById(R.id.tv_place_preview2);
		tv_note_preview2 = (TextView) findViewById(R.id.tv_note_preview2);
		tv_tag_preview2 = (TextView) findViewById(R.id.tv_tag_preview2);
		tv_group_preview2 = (TextView) findViewById(R.id.tv_group_preview2);
		tv_status_preview2 = (TextView) findViewById(R.id.tv_status_preview2);
		tv_priority_preview2 = (TextView) findViewById(R.id.tv_priority_preview2);
		
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			Log.w("debug", "extras null");
			return;
		}
		
		String title = extras.getString("title_select");
		List<String> row = this.dh.select_to_do(title);
		
		tv_title_preview2.setText(row.get(0));
		tv_place_preview2.setText(row.get(1));
		tv_note_preview2.setText(row.get(2));
		tv_tag_preview2.setText(row.get(3));
		tv_group_preview2.setText(row.get(4));
		tv_status_preview2.setText(row.get(5));
		tv_priority_preview2.setText(row.get(6));
	}
}