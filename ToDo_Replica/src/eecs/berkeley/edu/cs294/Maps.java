package eecs.berkeley.edu.cs294;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class Maps extends MapActivity {

	//private static final boolean DEBUG = true;	
	private static final String MY_LOCATION ="Soda Hall";

	MapView mapView;
	MapController mc;
	GeoPoint p;
	LocationOverlay locationOverlay;

	private DatabaseHelper dh;

	class MapOverlay extends com.google.android.maps.Overlay
	{
		String place;

		public MapOverlay(String place)
		{
			super();
			this.place = place;
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, 
				boolean shadow) 
		{
			super.draw(canvas, mapView, shadow);
			Log.w("debug", "DRAW CALLED");
			Projection projection = mapView.getProjection();

			for (int i = 0; i < locationOverlay.size(); i++) {
				Log.w("debug", "locationOverlay: " + locationOverlay.createItem(i).getTitle());
				Point screenPts = new Point();
				projection.toPixels(locationOverlay.createItem(i).getPoint(), screenPts);

				Bitmap bmp = BitmapFactory.decodeResource(
						getResources(), R.drawable.pushpin);            
				canvas.drawBitmap(bmp, screenPts.x, screenPts.y-50, null); 
			}
		}

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		dh = new DatabaseHelper(this);

		// Setting the map
		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		mapView.setStreetView(true);
		mc = mapView.getController();
		mc.setZoom(17);

		// Putting the to-do markers

		List<String> places = dh.selectAll_to_do(DatabaseHelper.PLACE);

		Drawable drawable = this.getResources().getDrawable(R.drawable.pushpin);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
		locationOverlay = new LocationOverlay(mapView, drawable);

		for(Iterator<String> it = places.iterator(); it.hasNext();) {
			MapOverlay mapOverlay = new MapOverlay(it.next());
			try {
				Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
				List<Address> addresses = geoCoder.getFromLocationName(
						mapOverlay.place, 1);
				if (addresses.size() > 0) {
					GeoPoint p = new GeoPoint(
							(int) (addresses.get(0).getLatitude() * 1E6), 
							(int) (addresses.get(0).getLongitude() * 1E6));
					OverlayItem marker = new OverlayItem(p, mapOverlay.place, "");
					marker.setMarker(drawable);
					locationOverlay.addOverlayItem(marker);
				}    
			} catch(IOException err) {
				err.printStackTrace();
			}
		}
		 List<Overlay> listOfOverlays = mapView.getOverlays();
	     listOfOverlays.clear();
	     listOfOverlays.add(locationOverlay);
		mapView.invalidate();

		// Where to point the map to (longitude & latitude)    
		try {
			Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
			List<Address> addresses = geoCoder.getFromLocationName(
					MY_LOCATION, 1);
			if (addresses.size() > 0) {
				p = new GeoPoint(
						(int) (addresses.get(0).getLatitude() * 1E6), 
						(int) (addresses.get(0).getLongitude() * 1E6));

				// Changing the map
				mc.animateTo(p);
			}    
		}
		catch(IOException err) {
			err.printStackTrace();
		}
		mapView.invalidate();
	}
	public class LocationOverlay extends ItemizedOverlay<OverlayItem>  {
		private ArrayList<OverlayItem> overLayList = new ArrayList<OverlayItem>();    
		MapView mapView;

		public LocationOverlay(MapView mapView, Drawable defaultMarker) {
			super(boundCenterBottom(defaultMarker));
			populate();
			this.mapView = mapView; // need it for onTap
		}

		@Override
		protected OverlayItem createItem(int i) {
			return overLayList.get(i);
		}

		@Override
		public int size() {
			return overLayList.size();
		}

		public void addOverlayItem(OverlayItem overlayItem) {
			if(!overLayList.contains(overlayItem)){
				overLayList.add(overlayItem);
			}
			populate();
		}
		/*
		public void clear(){
			overLayList.clear();
		}  

		@Override
		public boolean onTap(int pIndex) {
			OverlayItem item = overLayList.get(pIndex);
			item.getTitle();
			mapView.getController().animateTo(item.getPoint());
			Toast.makeText(mapView.getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();      
			return false;
		}
		 */
	}

	@Override
	protected boolean isRouteDisplayed() 
	{
		return false;
	}
}