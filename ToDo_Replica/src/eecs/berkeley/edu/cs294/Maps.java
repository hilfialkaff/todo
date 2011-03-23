package eecs.berkeley.edu.cs294;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import junit.framework.Assert;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;



public class Maps extends MapActivity {
	
	String PLACE_1337="Soda Hall";
	
	MapView mapView;
	MapController mc;
	GeoPoint p;
	
	private static final boolean DEBUG = true;	
	private DatabaseHelper dh;
	
    class MapOverlay extends com.google.android.maps.Overlay
    {
    	GeoPoint location = null;
    	
    	/*
    	public MapOverlay(GeoPoint location)
        {
            super();
            this.location = location;
        }*/

    	@Override
        public boolean onTouchEvent(MotionEvent event, MapView mapView) 
        {   
            //---when user lifts his finger---
            if (event.getAction() == 1) {                
                GeoPoint p = mapView.getProjection().fromPixels(
                    (int) event.getX(),
                    (int) event.getY());
 
                Geocoder geoCoder = new Geocoder(
                    getBaseContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geoCoder.getFromLocation(
                        p.getLatitudeE6()  / 1E6, 
                        p.getLongitudeE6() / 1E6, 1);
 
                    String add = "";
                    if (addresses.size() > 0) 
                    {
                        for (int i=0; i<addresses.get(0).getMaxAddressLineIndex(); 
                             i++)
                           add += addresses.get(0).getAddressLine(i) + "\n";
                    }
 
                    Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
                }
                catch (IOException e) {                
                    e.printStackTrace();
                }   
                return true;
            }
            else                
                return false;
        }      

        @Override
        public boolean draw(Canvas canvas, MapView mapView, 
        boolean shadow, long when) 
        {
            super.draw(canvas, mapView, shadow);                   
            
            //Translate the GeoPoint to screen pixels
            Point screenPts = new Point();
 
            //Add a marker
        	List<String> places = dh.selectAll_to_do(DatabaseHelper.PLACE);
        	for(Iterator<String> it = places.iterator(); it.hasNext();) {
        	    placeTodoMark(it.next(), canvas, screenPts);
        	}
        	
            return true;
        }
        
        public void placeTodoMark(String currPlace, Canvas canvas, Point screenPts) 
        {
        	try {
    	    	Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
                List<Address> addresses = geoCoder.getFromLocationName(currPlace, 1);
                
                if(addresses.size() == 0) {
                	Toast.makeText(getBaseContext(), currPlace + " not found", Toast.LENGTH_SHORT).show();
                }
                	
                if (addresses.size() > 0) {
                    p = new GeoPoint(
                            (int) (addresses.get(0).getLatitude() * 1E6), 
                            (int) (addresses.get(0).getLongitude() * 1E6));

                    // Changing the map
                    mc.animateTo(p);
                    mc.setZoom(17);
                    mapView.invalidate();
                }    
            } catch(IOException err) {
                err.printStackTrace();
            }
            
            Bitmap bmp = BitmapFactory.decodeResource(
                    getResources(), R.drawable.pushpin);            
            canvas.drawBitmap(bmp, screenPts.x, screenPts.y, null);       	
        }
    }
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        // Setting mapView
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true);
        mapView.setStreetView(true);
 
        dh = new DatabaseHelper(this);
        
        // Putting the todo markers
        mc = mapView.getController();
        mc.setZoom(17);
        mapView.invalidate();
        MapOverlay mapOverlay = new MapOverlay();
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();        
        listOfOverlays.add(mapOverlay);        
 
        // Where to point the map to (langitude & latitude)    
        try {
        	Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geoCoder.getFromLocationName(
                PLACE_1337, 1);
            if (addresses.size() > 0) {
                p = new GeoPoint(
                        (int) (addresses.get(0).getLatitude() * 1E6), 
                        (int) (addresses.get(0).getLongitude() * 1E6));

                // Changing the map
                mc.animateTo(p);
            }    
        } catch(IOException err) {
            err.printStackTrace();
        }
        
        mapView.invalidate();
    }
    
    @Override
    protected boolean isRouteDisplayed() 
    {
        return false;
    }
}