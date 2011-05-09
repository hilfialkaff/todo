package eecs.berkeley.edu.cs294;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class LastUpdates extends Activity {
	/** Called when the activity is first created. */
	TableLayout tl_updates;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.last_update);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);

		TextView tv_custom_title = (TextView) findViewById(R.id.tv_custom_title);
		tv_custom_title.setText("Updates");
		
		tl_updates = (TableLayout) findViewById(R.id.tl_last_update);
		ServerConnection.pullAllRemote();
		
		populate();
	}

	private void populate() {
		for (String update : DatabaseHelper.recent_updates) {
			TableRow row = new TableRow(this);		
			row.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			TextView tv_update= new TextView(this);
			tv_update.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));			
			tv_update.setText(update);
			tv_update.setTextSize(20);

			row.addView(tv_update);
			row.setContentDescription(tv_update.getText());
			registerForContextMenu(row);
			tl_updates.addView(row);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.last_update_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.m_clear_updates:
			DatabaseHelper.recent_updates.clear();
			tl_updates.removeAllViews();
			return true;
		}
		return false;
	}
}