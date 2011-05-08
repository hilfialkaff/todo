package eecs.berkeley.edu.cs294;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class Preview extends Activity {
	TabHost th_preview;
	TextView tv_title_2, tv_place_2, tv_note_2, tv_tag_2, tv_group_2, tv_status_2, tv_priority_2, tv_deadline_2;
	Button b_ok;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preview);

		th_preview = (TabHost) findViewById(R.id.th_preview);
		th_preview.setup();

		TabSpec tab_one = th_preview.newTabSpec("tab_one_btn_tab");
		tab_one.setContent(R.id.tl_preview_21);
		tab_one.setIndicator("Basic");
		th_preview.addTab(tab_one);

		TabSpec tab_two = th_preview.newTabSpec("tab_two_btn_tab");
		tab_two.setContent(R.id.tl_preview_22);
		tab_two.setIndicator("More");
		th_preview.addTab(tab_two);
		
		th_preview.setCurrentTab(0);
		
		tv_title_2 = (TextView) findViewById(R.id.tv_title_2);
		tv_place_2 = (TextView) findViewById(R.id.tv_place_2);
		tv_note_2 = (TextView) findViewById(R.id.tv_note_2);
		tv_tag_2 = (TextView) findViewById(R.id.tv_tag_2);
		tv_group_2 = (TextView) findViewById(R.id.tv_group_2);
		tv_status_2 = (TextView) findViewById(R.id.tv_status_2);
		tv_priority_2 = (TextView) findViewById(R.id.tv_priority_2);
		tv_deadline_2 = (TextView) findViewById(R.id.tv_deadline_2);
		
		Bundle extras = getIntent().getExtras();

		String title = extras.getString("title_select");
		List<String> row = ToDo_Replica.dh.select_to_do("title", title);

		if(!row.isEmpty()){
			tv_title_2.setText(row.get(0));
			tv_place_2.setText(row.get(1));
			tv_note_2.setText(row.get(2));
			tv_tag_2.setText(row.get(3));
			tv_group_2.setText(row.get(4));
			tv_status_2.setText(row.get(5));
			tv_priority_2.setText(row.get(6));
			tv_deadline_2.setText(row.get(7));
		}
	}
}