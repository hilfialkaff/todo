package eecs.berkeley.edu.cs294;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class Preview extends Activity {
	/** Called when the activity is first created. */
	TextView tv_title_preview2;
	TextView tv_place_preview2;
	TextView tv_note_preview2;
	TextView tv_tag_preview2;
	TextView tv_group_preview2;
	TextView tv_status_preview2;
	TextView tv_priority_preview2;
	TabHost th_preview;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preview);
		
		th_preview = (TabHost) findViewById(R.id.th_preview);
		th_preview.setup();

		TabSpec tab_one = th_preview.newTabSpec("tab_one_btn_tab");
		tab_one.setContent(R.id.tl_preview);
		tab_one.setIndicator("Basic");
		th_preview.addTab(tab_one);
		
		TabSpec tab_two = th_preview.newTabSpec("tab_two_btn_tab");
		tab_two.setContent(new TabHost.TabContentFactory() {  
			public View createTabContent(String tag) {  
				return(new AnalogClock(Preview.this));  
			}  
		}); 
		tab_two.setIndicator("Advanced");
		th_preview.addTab(tab_two);
		
		th_preview.setCurrentTab(0);
		
		ToDo_Replica.dh = new DatabaseHelper(this);
		
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
		List<String> row = ToDo_Replica.dh.select_to_do_title(title);
		
		tv_title_preview2.setText(row.get(0));
		tv_place_preview2.setText(row.get(1));
		tv_note_preview2.setText(row.get(2));
		tv_tag_preview2.setText(row.get(3));
		tv_group_preview2.setText(row.get(4));
		tv_status_preview2.setText(row.get(5));
		tv_priority_preview2.setText(row.get(6));
	}
}