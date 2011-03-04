/*
 * 
 * Demonstrate some functionalities of the google maps api (ie: Pointing your map to a certain
 * location, adding stuffs to the map and binding some keys to zooming in and out)
 * 
 */


package edu.berkeley.cs294.sample;

import java.util.List;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MapView.LayoutParams;
import com.google.android.maps.MapController;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.Overlay;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

public class GoogleMaps extends MapActivity {
	
	MapView mapView;
	MapController mc;
	GeoPoint p;

    class MapOverlay extends com.google.android.maps.Overlay
    {
        @Override
        public boolean draw(Canvas canvas, MapView mapView, 
        boolean shadow, long when) 
        {
            super.draw(canvas, mapView, shadow);                   
 
            //Translate the GeoPoint to screen pixels
            Point screenPts = new Point();
            mapView.getProjection().toPixels(p, screenPts);
 
            //Add a marker
            Bitmap bmp = BitmapFactory.decodeResource(
                getResources(), R.drawable.pushpin);            
            canvas.drawBitmap(bmp, screenPts.x, screenPts.y-50, null) ;
            return true;
        }
    }
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mapView = (MapView) findViewById(R.id.mapView);
        LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
        View zoomView = mapView.getZoomControls(); 
 
        zoomLayout.addView(zoomView, 
            new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 
        mapView.displayZoomControls(true);
        
        // Toggle between satellite & street view 
        // mapView.setSatellite(true);
        mapView.setStreetView(true);
        
        // Where to point the map to (langitude & latitude)
        mc = mapView.getController();
        String coordinates[] = {"1.352566007", "103.78921587"};
        double lat = Double.parseDouble(coordinates[0]);
        double lng = Double.parseDouble(coordinates[1]);

        p = new GeoPoint ((int) (lat * 1E6), (int) (lng * 1E6));
        
        // Changing the map
        mc.animateTo(p);
        mc.setZoom(17);
        
        // Add extra stuffs to the map
        MapOverlay mapOverlay = new MapOverlay();
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(mapOverlay);        
 
        mapView.invalidate();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
    /* Callback fn if key is pressed */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	MapController mc = mapView.getController();
    	
    	// Press Num 3 to zoom in & Num 1 to zoom out
    	switch(keyCode)
    	{
    	case KeyEvent.KEYCODE_3:
    		mc.zoomIn();
    		break;
    	case KeyEvent.KEYCODE_1:
    		mc.zoomOut();
    		break;
    	}
    		
    	return super.onKeyDown(keyCode, event);
    }
}