package eecs.berkeley.edu.cs294;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class ToDo_Lists extends Activity {
	/** Called when the activity is first created. */
	TableLayout tl_todo_lists;
	private DatabaseHelper dh;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.todo_lists);
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
		
		tl_todo_lists = (TableLayout) findViewById(R.id.tl_todo_lists);
		this.dh = new DatabaseHelper(this);	
		
		List<String> titles = this.dh.selectAll_to_do_title();
		Log.w("debug", "titles size " + Integer.toString(titles.size()));

		for (String title : titles) {
			TableRow row = new TableRow(this);		
			row.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			
			TextView tv_title = new TextView(this);
			tv_title.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));			
			tv_title.setText(title);
			tv_title.setTextSize(40);
			
			row.addView(tv_title);
			row.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(v.getContext(), Add.class);
					startActivityForResult(intent, 2);
				}
			});
			
			tl_todo_lists.addView(row);
		}
		
	}
}