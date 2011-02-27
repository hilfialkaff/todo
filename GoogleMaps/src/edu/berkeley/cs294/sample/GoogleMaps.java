package edu.berkeley.cs294.sample;

import com.google.android.maps.MapActivity;
import android.os.Bundle;

//http://developer.android.com/guide/tutorials/views/hello-mapview.html

public class GoogleMaps extends MapActivity {
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