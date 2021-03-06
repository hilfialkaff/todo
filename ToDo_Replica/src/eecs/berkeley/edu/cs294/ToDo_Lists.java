package eecs.berkeley.edu.cs294;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class ToDo_Lists extends Activity {
	/** Called when the activity is first created. */
	TableLayout tl_todo_lists;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.todo_lists);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
		
		TextView tv_custom_title = (TextView) findViewById(R.id.tv_custom_title);
		tv_custom_title.setText("ToDo Lists");
		
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
		
		final ImageButton ib_custom_add_group = (ImageButton) findViewById(R.id.ib_custom_add_group);
		if (ib_custom_add_group != null) {
			ib_custom_add_group.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(v.getContext(), AddGroup.class);
					startActivityForResult(intent, 0);
				}
			});
		}

		tl_todo_lists = (TableLayout) findViewById(R.id.tl_todo_lists);
		populate();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		if (resultCode == Activity.RESULT_OK) {
			tl_todo_lists.removeAllViews();
			populate();
		}
	}

	private void populate() {
		List<String> titles = ToDo_Replica.dh.select_all_to_do("title");

		for (String title : titles) {
			TableRow row = new TableRow(this);		
			row.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			TextView tv_title = new TextView(this);
			tv_title.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));			
			tv_title.setText(title);
			tv_title.setTextSize(40);

			row.addView(tv_title);
			row.setContentDescription(tv_title.getText());
			row.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(v.getContext(), Preview.class);
					intent.putExtra("title_select", v.getContentDescription());
					startActivityForResult(intent, 3);
				}
			});
			registerForContextMenu(row);
			tl_todo_lists.addView(row);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.setHeaderTitle(v.getContentDescription());
		menu.add(0, Integer.parseInt(ToDo_Replica.dh.select_to_do("title", v.getContentDescription().toString()).
				get(DatabaseHelper.TD_ID_INDEX_T)), 0, "edit");
		menu.add(0, Integer.parseInt(ToDo_Replica.dh.select_to_do("title", v.getContentDescription().toString()).
				get(DatabaseHelper.TD_ID_INDEX_T)), 1, "delete");
	}

	@Override
	public boolean onContextItemSelected(MenuItem menuItem) {
		switch(menuItem.getOrder()) {
		case 0:
			Intent intent = new Intent(ToDo_Lists.this, Edit.class);
			intent.putExtra("pk_select", menuItem.getItemId());
			startActivityForResult(intent, 4);
			return true;
		case 1:
			List<String> todo_ids = ToDo_Replica.dh.select_all_to_do("td_id");
			
			/* Push changes to remote if applicable */
			List<String> oldEntry = ToDo_Replica.dh.select_to_do("td_id", 
					todo_ids.get(menuItem.getItemId() - 1));
			
			Log.d("DEBUG", oldEntry.get(DatabaseHelper.GROUP_ID_INDEX_T));
			
			if (oldEntry.get(DatabaseHelper.GROUP_ID_INDEX_T).equalsIgnoreCase("None") == false) {
				ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo netInfo = connManager.getActiveNetworkInfo();

				if(netInfo == null) {
					Log.d("DEBUG", "--------------- No internet connection --------- ");
				}

				if (netInfo.isConnected()) {
					ServerConnection.pushRemote(oldEntry, ServerConnection.TODO_SERVER_UPDATE,
							ServerConnection.DELETE_REQUEST);
				}
			}
			
			ToDo_Replica.dh.delete_to_do("td_id", todo_ids.get(menuItem.getItemId() - 1));
			tl_todo_lists.removeAllViews();
			populate();
			return true;
		}
		return false;
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.todo_lists_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.m_add_todo:
			startActivityForResult(new Intent(this, Add.class), 1);
			return true;
		}
		return false;
	}
}
