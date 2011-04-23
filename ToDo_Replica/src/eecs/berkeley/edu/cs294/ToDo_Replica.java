package eecs.berkeley.edu.cs294;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ToDo_Replica extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final boolean customTitle = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);
		if (customTitle)
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
		
		/* Timer code */
		/********************************************************************/
		Timer serverTimer = new Timer("serverTimer", true);
		TimerTask serverTimerTask = new TimerTask() {
			public void run() {
				ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo netInfo = connManager.getActiveNetworkInfo();
				
				if(netInfo == null) {
					Log.d("DEBUG", "---------- No internet connection ----------");
					// TODO: Warn the user
				}
					
				else if (netInfo.isConnected()) {
					Log.d("DEBUG", "---------- Connected to internet ----------");
					ServerConnection.pullRemote();
				}
			}
		};
		
		// TODO: Need to be un-hardcoded
		serverTimer.scheduleAtFixedRate(serverTimerTask, 50000, 50000); // Run every 50 seconds
		/********************************************************************/

		
		final TextView tv_custom_title = (TextView) findViewById(R.id.tv_custom_title);
		if (tv_custom_title != null)
			tv_custom_title.setText("ToDo");
		/*
		final ImageButton ib_custom_search = (ImageButton) findViewById(R.id.ib_custom_search);
		if (ib_custom_search != null)
		 */

		final ImageButton ib_custom_add = (ImageButton) findViewById(R.id.ib_custom_add);
		if (ib_custom_add != null) {
			ib_custom_add.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(v.getContext(), AddContact.class);
					startActivityForResult(intent, 0);
				}
			});
		}

		ImageButton b_maps = (ImageButton) findViewById(R.id.b_maps);
		b_maps.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), GoogleMaps.class);
				startActivityForResult(intent, 0);
			}
		});
		ImageButton b_todo_lists = (ImageButton) findViewById(R.id.b_todo_lists);
		b_todo_lists.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), ToDo_Lists.class);
				startActivityForResult(intent, 1);
			}
		});
		ImageButton b_groups = (ImageButton) findViewById(R.id.b_groups);
		b_groups.setBackgroundResource(R.drawable.ic_menu_allfriends);
		b_groups.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), Groups.class);
				startActivityForResult(intent, 2);
			}
		});

		ImageView iv_background = (ImageView) findViewById(R.id.iv_background);
		iv_background.setBackgroundResource(R.drawable.chocobo);
		
		/** Temp stuffs */
		/*
		Date date = new Date();
		Log.d("ServerDEBUG", Long.toString(date.getTime()));
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, Menu.FIRST, Menu.FIRST, "Menu 1");
		menu.add(0, Menu.FIRST + 1, Menu.FIRST + 1, "Menu 2");
		menu.add(0, Menu.FIRST + 2, Menu.FIRST + 2, "Menu 3");
		menu.add(0, Menu.FIRST + 3, Menu.FIRST + 3, "Menu 4");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case Menu.FIRST:
			return true;
		case Menu.FIRST + 1:
			return true;
		case Menu.FIRST + 2:
			return true;
		}
		return false;
	}
}
