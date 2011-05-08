package eecs.berkeley.edu.cs294;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

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
		
		setTitle("Preview Group");
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

		String name = extras.getString("group_select");
		List<String> row = ToDo_Replica.dh.select_group("name", name);

		if(!row.isEmpty()){
			tv_name_preview_group2.setText(row.get(1));
			tv_description_preview_group2.setText(row.get(2));
		}
		
		populateMembers(name);
	}
	
	private void populateMembers(String groupname) {
		List<String> members = ToDo_Replica.dh.select_group_member(groupname);

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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.group_preview_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.m_edit_group:
			Intent intent = new Intent(this, EditGroup.class);
			intent.putExtra("group_select", tv_name_preview_group2.getText().toString());
			startActivityForResult(intent, 1);
			return true;
		}
		return false;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		if (resultCode == Activity.RESULT_OK) {
			setResult(RESULT_OK);
			finish();
		}
	}
}