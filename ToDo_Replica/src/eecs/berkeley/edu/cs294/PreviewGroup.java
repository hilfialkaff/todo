package eecs.berkeley.edu.cs294;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AnalogClock;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import android.widget.TableRow.LayoutParams;

public class PreviewGroup extends Activity {
	/** Called when the activity is first created. */
	TextView tv_name_preview_group2;
	TextView tv_description_preview_group2;
	TabHost th_preview_group;
	TableLayout member_list;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preview_group);

		th_preview_group = (TabHost) findViewById(R.id.th_preview_group);
		th_preview_group.setup();

		TabSpec tab_one = th_preview_group.newTabSpec("tab_one_btn_tab");
		tab_one.setContent(R.id.tl_preview_group_info);
		tab_one.setIndicator("Info");
		th_preview_group.addTab(tab_one);

		TabSpec tab_two = th_preview_group.newTabSpec("tab_two_btn_tab");
		tab_two.setContent(R.id.tl_preview_group_members);
		tab_two.setIndicator("Members");
		th_preview_group.addTab(tab_two);

		th_preview_group.setCurrentTab(0);

		member_list = (TableLayout) findViewById(R.id.tl_preview_group_members);
		
		ToDo_Replica.dh = new DatabaseHelper(this);
		
		tv_name_preview_group2 = (TextView) findViewById(R.id.tv_name_preview_group2);
		tv_description_preview_group2 = (TextView) findViewById(R.id.tv_description_preview_group2);

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			Log.w("debug", "extras null");
			return;
		}

		String title = extras.getString("title_select");
		List<String> row = ToDo_Replica.dh.select_to_do("title", title);

		if(!row.isEmpty()){
			tv_name_preview_group2.setText(row.get(0));
			tv_description_preview_group2.setText(row.get(1));
		}
		
		populateMembers();
	}
	
	private void populateMembers() {
		List<String> members = ToDo_Replica.dh.select_all_members("name");

		for (String member : members) {
			TableRow row = new TableRow(this);		
			row.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			TextView tv_member = new TextView(this);
			tv_member.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));			
			tv_member.setText(member);

			row.addView(tv_member);
			registerForContextMenu(row);
			member_list.addView(row);
		}
	}
}