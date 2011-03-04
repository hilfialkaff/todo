/*
 * 
 * This app detects your current location. Does not work on simulator since it requires provider.
 * In simulator, needs to "telnet localhost <port>" where port could be found by "adb devices".
 * A simulation of geolocation changes could be emulated then by "geo fix <lat> <lang>"  
 * 
 */


package edu.berkeley.cs294.sample;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class ShowLocation extends MapActivity {

	private MapController mapController;
	private MapView mapView;
	private LocationManager locationManager;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.main);

		// create a map view
		RelativeLayout linearLayout = (RelativeLayout) findViewById(R.id.mainlayout);
		
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setStreetView(true);
		mapController = mapView.getController();
		mapController.setZoom(14); // Zoom 1 is world view

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, new GeoUpdateHandler());
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	
	public class GeoUpdateHandler implements LocationListener {

		// Called when the location has changed
		public void onLocationChanged(Location location) {
			int lat = (int) (location.getLatitude() * 1E6);
			int lng = (int) (location.getLongitude() * 1E6);
			GeoPoint point = new GeoPoint(lat, lng);
			mapController.animateTo(point); //	mapController.setCenter(point);
		}

		
		// Called when the provider is enabled by the user
		public void onProviderDisabled(String provider) {}


		// Called when the provider is disabled by the user
		public void onProviderEnabled(String provider) {}


		// This method is called when a provider is unable to fetch a location or if the provider 
		// has recently become available after a period of unavailability
		public void onStatusChanged(String provider, int status, Bundle extras) {}
	}
}
