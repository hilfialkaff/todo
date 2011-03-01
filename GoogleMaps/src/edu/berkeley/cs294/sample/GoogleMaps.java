package edu.berkeley.cs294.sample;

import com.google.android.maps.MapActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

//http://developer.android.com/guide/tutorials/views/hello-mapview.html

public class GoogleMaps extends MapActivity {
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, Menu.FIRST, Menu.FIRST, "Add To-Do");
		menu.add(0, Menu.FIRST + 1, Menu.FIRST + 1, "List To-Do");
		menu.add(0, Menu.FIRST + 2, Menu.FIRST + 2, "Group");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case Menu.FIRST:
			Toast.makeText(this, "Add To-Do Selected", Toast.LENGTH_LONG).show();
			return true;
		case Menu.FIRST + 1:
			Toast.makeText(this, "List To-Do Selected", Toast.LENGTH_LONG).show();
			return true;
		case Menu.FIRST + 2:
			Toast.makeText(this, "Group Selected", Toast.LENGTH_LONG).show();
		}
		return false;
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    protected boolean isRouteDisplayed() {
        return false;
    }

}