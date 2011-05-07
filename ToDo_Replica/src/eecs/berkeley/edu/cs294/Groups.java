package eecs.berkeley.edu.cs294;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class Groups extends Activity {
	/** Called when the activity is first created. */
	TableLayout group_list;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.groups);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
		
		final ImageButton ib_custom_add = (ImageButton) findViewById(R.id.ib_custom_add);
		if (ib_custom_add != null) {
			ib_custom_add.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(v.getContext(), Add.class);
					startActivityForResult(intent, 0);
				}
			});
		}
		
		Button addGroup = (Button) findViewById(R.id.addGroup);
		addGroup.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), AddGroup.class);
				startActivityForResult(intent, 1);
			}
		});
		
		group_list = (TableLayout) findViewById(R.id.tl_group_lists);
		ToDo_Replica.dh = new DatabaseHelper(this);
		
		List<String> titles = ToDo_Replica.dh.select_all_group_name();
		
		populate();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		if (resultCode == Activity.RESULT_OK) {
			group_list.removeAllViews();
			populate();
		}
	}

	private void populate() {
		List<String> groupnames = ToDo_Replica.dh.select_all_group_name();

		for (String groupname : groupnames) {
			TableRow row = new TableRow(this);		
			row.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			TextView tv_groupname= new TextView(this);
			tv_groupname.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));			
			tv_groupname.setText(groupname);

			row.addView(tv_groupname);
			row.setContentDescription(tv_groupname.getText());
			row.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(v.getContext(), PreviewGroup.class);
					intent.putExtra("group_select", v.getContentDescription());
					startActivityForResult(intent, 3);
				}
			});
			registerForContextMenu(row);
			group_list.addView(row);
		}
	}
}