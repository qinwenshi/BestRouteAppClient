package ie.ul.myproject.activities;

import ie.ul.myproject.R;
import ie.ul.myproject.algorithm.DecisionTree;
import ie.ul.myproject.models.Place;
import ie.ul.myproject.parsing.ParseDirectionsJSON;
import ie.ul.myproject.services.http.HttpConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class ShowStageOnMapActivity extends FragmentActivity {

	Place start = DecisionTree.selectedStage.getFrom();
	Place end = DecisionTree.selectedStage.getTo();

	// get the other stage..


	static LatLng fromLocation, endLocation;
	GoogleMap googleMapO = null ;
	final String TAG = "PathGoogleMapActivity";
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_stage_on_map);
		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		System.out.println("HEREEEEEE" + fm);

		final ShowStageOnMapActivity that = this ;

		fm.getMapAsync(new OnMapReadyCallback() {
			@Override
			public void onMapReady(GoogleMap googleMap) {
				googleMapO = googleMap ;
				String url = getMapsApiDirectionsUrl();
				ReadTask readTask = new ReadTask();
				readTask.execute(url);

				fromLocation = new LatLng(start.getLatitude(), start.getLongitude());
				endLocation = new LatLng(end.getLatitude(), end.getLongitude());
				System.out.println(fromLocation);

				if (googleMap != null) {
					googleMap.addMarker(new MarkerOptions()
							.position(fromLocation)
							.title(start.getGoogleName())
							.snippet("From ..."));
					googleMap.addMarker(new MarkerOptions().position(endLocation)
							.title(end.getGoogleName()).snippet("to ..."));
				}

				// Move the camera instantly to house's location with a zoom of 8.
				googleMap.moveCamera(CameraUpdateFactory
						.newLatLngZoom(fromLocation, 8));

				// Zoom in, animating the camera.
				googleMap.animateCamera(CameraUpdateFactory.zoomTo(8), 2000, null);

				// ATTENTION: This was auto-generated to implement the App Indexing API.
				// See https://g.co/AppIndexing/AndroidStudio for more information.
				client = new GoogleApiClient.Builder(that).addApi(AppIndex.API).build();
				client.connect() ;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_route_on_map, menu);
		return true;
	}

	private String getMapsApiDirectionsUrl() {

		String sensor = "sensor=false";
		String origin;
		if (start.getGoogleName().equals("Here")) {
			origin = "origin=" + start.getLatitude() + "," + start.getLongitude();
		} else {
			origin = "origin=" + start.getGoogleName();
		}

		String params = origin + "&destination="
				+ end.getGoogleName() + "&" + sensor;
		String output = "json";
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + params;

		System.out.println("URL: " + url);
		return url;
	}

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
//		client.connect();
//		Action viewAction = Action.newAction(
//				Action.TYPE_VIEW, // TODO: choose an action type.
//				"ShowStageOnMap Page", // TODO: Define a title for the content shown.
//				// TODO: If you have web page content that matches this app activity's content,
//				// make sure this auto-generated web page URL is correct.
//				// Otherwise, set the URL to null.
//				Uri.parse("http://host/path"),
//				// TODO: Make sure this auto-generated app URL is correct.
//				Uri.parse("android-app://ie.ul.myproject.activities/http/host/path")
//		);
//		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
//		Action viewAction = Action.newAction(
//				Action.TYPE_VIEW, // TODO: choose an action type.
//				"ShowStageOnMap Page", // TODO: Define a title for the content shown.
//				// TODO: If you have web page content that matches this app activity's content,
//				// make sure this auto-generated web page URL is correct.
//				// Otherwise, set the URL to null.
//				Uri.parse("http://host/path"),
//				// TODO: Make sure this auto-generated app URL is correct.
//				Uri.parse("android-app://ie.ul.myproject.activities/http/host/path")
//		);
//		AppIndex.AppIndexApi.end(client, viewAction);
		client.disconnect();
	}

	private class ReadTask extends AsyncTask<String, String, String> {
		@Override
		protected String doInBackground(String... url) {
			String data = "";
			try {
				HttpConnection http = new HttpConnection();
				data = http.readUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			System.out.println("data: " + data);
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			new ParserTask().execute(result);
		}
	}

	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				ParseDirectionsJSON parser = new ParseDirectionsJSON();
				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
			ArrayList<LatLng> points = null;
			PolylineOptions polyLineOptions = null;
			@SuppressWarnings("unused")
			MarkerOptions markerOptions = new MarkerOptions();

			// traversing through routes
			for (int i = 0; i < routes.size(); i++) {
				points = new ArrayList<LatLng>();
				polyLineOptions = new PolylineOptions();

				// Fetching i-th route
				List<HashMap<String, String>> path = routes.get(i);

				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				polyLineOptions.addAll(points);
				polyLineOptions.width(6);
				polyLineOptions.color(Color.RED);
			}

			googleMapO.addPolyline(polyLineOptions);
		}
	}
}
