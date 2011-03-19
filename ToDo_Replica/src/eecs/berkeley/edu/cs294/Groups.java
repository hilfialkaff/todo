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
	private DatabaseHelper dh;
	
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
		this.dh = new DatabaseHelper(this);
		
		List<String> titles = this.dh.selectAll_group_name();
		
		for (String title : titles) {
			TableRow row = new TableRow(this);		
			row.setLayoutParams(new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			
			LinearLayout ll_title = new LinearLayout(this);
			ll_title.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			
			TextView tv_title = new TextView(this);
			tv_title.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			tv_title.setText(title);
			
			View ruler = new View(this);
			ruler.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 1));
			
			ll_title.addView(tv_title);
			row.addView(ll_title);

			group_list.addView(row);
			group_list.addView(ruler);
		}
	}
}