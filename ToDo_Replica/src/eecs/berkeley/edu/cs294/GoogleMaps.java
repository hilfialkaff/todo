//skeleton: http://developer.android.com/resources/tutorials/views/hello-mapview.html
//my_location: http://www.vogella.de/articles/AndroidLocationAPI/article.html
//gps: http://csie-tw.blogspot.com/2009/06/android-driving-direction-route-path.html

package eecs.berkeley.edu.cs294;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class GoogleMaps extends MapActivity implements LocationListener, OnTouchListener {
	private MapView mapView;
	private MapController mc;
	private Drawable drawable;
	private CustomItemizedOverlay itemizedOverlay;
	private List<Overlay> mapOverlays;
	private LocationManager locationManager;
	private String provider;
	private Location location;
	private GeoPoint my_location;
	private int overlay_status = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.google_maps);

		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setStreetView(true);

		mc = mapView.getController();
		mc.setZoom(17);

		ToDo_Replica.dh = new DatabaseHelper(this);

		drawable = this.getResources().getDrawable(R.drawable.pushpin);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
		itemizedOverlay = new CustomItemizedOverlay(drawable, mapView);

		mapOverlays = mapView.getOverlays();

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		location = locationManager.getLastKnownLocation(provider);

		if(location != null) {
			Log.w("debug", "Wireless Mode");
			my_location = new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));
			mc.animateTo(my_location);
		}
		else {
			Log.w("debug", "GPS Mode");
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new GeoUpdateHandler());
		}

		populate();

		if(itemizedOverlay.size() != 0)
			mapOverlays.add(itemizedOverlay);
	}

	private void populate() {
		List<String[]> todos = ToDo_Replica.dh.select_to_do_title_place();
		if(!todos.isEmpty()) {
			for(Iterator<String[]> it = todos.iterator(); it.hasNext();) {
				String todo[] = it.next();
				try {
					Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
					List<Address> addresses = geoCoder.getFromLocationName(todo[1], 1);
					if (addresses.size() > 0) {
						GeoPoint p = new GeoPoint((int) (addresses.get(0).getLatitude() * 1E6), (int) (addresses.get(0).getLongitude() * 1E6));
						OverlayItem marker = new OverlayItem(p, todo[1], todo[0]);
						marker.setMarker(drawable);
						itemizedOverlay.addOverlay(marker);
					}    
				} catch(IOException err) {
					err.printStackTrace();
				}
			}
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public class CustomItemizedOverlay extends ItemizedOverlay<OverlayItem>  {
		private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
		private MapView mapView;

		public CustomItemizedOverlay(Drawable defaultMarker) {
			super(boundCenterBottom(defaultMarker));	
		}

		public CustomItemizedOverlay(Drawable defaultMarker, MapView mapView) {
			super(boundCenterBottom(defaultMarker));
			this.mapView = mapView;
		}

		public void addOverlay(OverlayItem overlay) {
			mOverlays.add(overlay);
			populate();
		}

		@Override
		protected OverlayItem createItem(int i) {
			return mOverlays.get(i);
		}

		@Override
		public int size() {
			return mOverlays.size();
		}

		public void clear() {
			mOverlays.clear();
		}  

		@Override
		public boolean onTap(int index) {
			DemoPopupWindow dw = new DemoPopupWindow(mapView);
			dw.showLikeQuickAction(0, 30);

			OverlayItem item = mOverlays.get(index);
			AlertDialog.Builder dialog = new AlertDialog.Builder(mapView.getContext());
			dialog.setTitle(item.getTitle());
			dialog.setMessage(item.getSnippet());
			//dialog.show();

			GeoPoint srcGeoPoint = new GeoPoint((int) my_location.getLatitudeE6(), (int) my_location.getLongitudeE6());
			GeoPoint destGeoPoint = new GeoPoint((int) item.getPoint().getLatitudeE6(), (int) item.getPoint().getLongitudeE6());

			if(overlay_status == -1)
				overlay_status = mapView.getOverlays().size();
			else {
				int i = mapView.getOverlays().size() - 1, j = overlay_status;
				while(i >= j) {
					System.err.println("overlay status: " + overlay_status);
					mapView.getOverlays().remove(i);
					i--;
				}
			}
			mapView.invalidate();
			DrawPath(srcGeoPoint, destGeoPoint, Color.GREEN, mapView);
			mc.animateTo(destGeoPoint);

			return true;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		return false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		my_location = new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));
		mc.animateTo(my_location);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		switch(status) {
		case LocationProvider.AVAILABLE:
			Toast.makeText(this, "Status Available: " + provider, Toast.LENGTH_SHORT).show();
			break;
		case LocationProvider.OUT_OF_SERVICE:
			Toast.makeText(this, "Status Out-Of-Service: " + provider, Toast.LENGTH_SHORT).show();
			break;
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
			Toast.makeText(this, "Status Temporarily Unavailable: " + provider, Toast.LENGTH_SHORT).show();
			break;
		}
	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Provider Enabled: " + provider, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Provider Disabled: " + provider, Toast.LENGTH_SHORT).show();
	}

	public class GeoUpdateHandler implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			my_location = new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));
			mc.animateTo(my_location);
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}


	private void DrawPath(GeoPoint source, GeoPoint destination, int color, MapView mv) {
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.google.com/maps?f=d&hl=en");
		urlString.append("&saddr=");
		urlString.append( Double.toString((double)source.getLatitudeE6()/1.0E6));
		urlString.append(",");
		urlString.append( Double.toString((double)source.getLongitudeE6()/1.0E6));
		urlString.append("&daddr=");
		urlString.append( Double.toString((double)destination.getLatitudeE6()/1.0E6));
		urlString.append(",");
		urlString.append( Double.toString((double)destination.getLongitudeE6()/1.0E6));
		urlString.append("&ie=UTF8&0&om=0&output=kml");

		Document document = null;
		HttpURLConnection huc = null;
		URL url = null;

		try {
			url = new URL(urlString.toString());
			huc = (HttpURLConnection)url.openConnection();
			huc.setRequestMethod("GET");
			huc.setDoOutput(true);
			huc.setDoInput(true);
			huc.connect();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			document = db.parse(huc.getInputStream());

			if(document.getElementsByTagName("GeometryCollection").getLength() > 0) {
				String path = document.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getFirstChild().getNodeValue();

				String [] pairs = path.split(" ");
				String[] lngLat = pairs[0].split(",");
				GeoPoint startGP = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6), (int)(Double.parseDouble(lngLat[0])*1E6));
				mv.getOverlays().add(new MyOverlay(startGP, startGP, 1));
				GeoPoint gp1;
				GeoPoint gp2 = startGP;
				for(int i=1; i<pairs.length; i++) {
					lngLat = pairs[i].split(",");
					gp1 = gp2;
					gp2 = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6), (int)(Double.parseDouble(lngLat[0])*1E6));
					mv.getOverlays().add(new MyOverlay(gp1, gp2, 2, color));
				}
				mv.getOverlays().add(new MyOverlay(destination, destination, 3));
			}
		}

		catch (MalformedURLException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		catch (SAXException e) {
			e.printStackTrace();
		}
	}

	private static class DemoPopupWindow extends BetterPopupWindow implements OnClickListener {
		public DemoPopupWindow(View anchor) {
			super(anchor);
		}

		@Override
		protected void onCreate() {
			LayoutInflater inflater = (LayoutInflater) this.anchor.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			ViewGroup root = (ViewGroup) inflater.inflate(R.layout.popup_grid_layout, null);

			for(int i = 0, icount = root.getChildCount() ; i < icount ; i++) {
				View v = root.getChildAt(i);

				if(v instanceof TableRow) {
					TableRow row = (TableRow) v;
					for(int j = 0, jcount = row.getChildCount() ; j < jcount ; j++) {
						View item = row.getChildAt(j);
						if(item instanceof Button) {
							Button b = (Button) item;
							b.setOnClickListener(this);
						}
					}
				}
			}
			this.setContentView(root);
		}

		@Override
		public void onClick(View v) {
			Button b = (Button) v;
			Toast.makeText(this.anchor.getContext(), b.getText(), Toast.LENGTH_SHORT).show();
			this.dismiss();
		}
	}
}