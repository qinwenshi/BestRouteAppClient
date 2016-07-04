package ie.ul.myproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import ie.ul.myproject.activities.DestinationActivity;
import ie.ul.myproject.algorithm.DecisionTree;
import ie.ul.myproject.models.Route;

public class MainActivity extends Activity implements OnClickListener,
		ConnectionCallbacks, OnConnectionFailedListener {

	View restartButton, destinationButton, exitButton;

	List<Route> routes = new ArrayList<Route>();

	GoogleApiClient myClient;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		setContentView(R.layout.activity_main);

		buildGoogleApiClient();
		myClient.connect();


		destinationButton = (Button) findViewById(R.id.chooseDestinationButton);
		destinationButton.setEnabled(false);
		destinationButton.setOnClickListener(this);

		exitButton = (Button) findViewById(R.id.exitButton);
		exitButton.setOnClickListener(this);

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();

		if (id == R.id.chooseDestinationButton) {
			System.out.println("State your destination");
			startActivity(new Intent(this, DestinationActivity.class));

		} else if (id == R.id.exitButton) {
			System.out.println("Exit application");
			finish();
		}

	}

	protected synchronized void buildGoogleApiClient() {
		System.out.println("Building API");
		myClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API).build();
	}

	@Override
	public void onConnected(Bundle savedInstanceState) {
		System.out.println("On connected callback");

		try {
			DecisionTree.myLastLocation = LocationServices.FusedLocationApi
					.getLastLocation(myClient);
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		if (DecisionTree.myLastLocation != null) {
			// myLatitude.setText(String.valueOf(myLastLocation.getLatitude()));
			// myLongitude.setText(String.valueOf(myLastLocation.getLongitude()));
			System.out.println("Latitude: "
					+ DecisionTree.myLastLocation.getLatitude());
			System.out.println("Longitude: "
					+ DecisionTree.myLastLocation.getLongitude());
			destinationButton.setEnabled(true);
		}

	}

	@Override
	public void onConnectionSuspended(int arg0) {
		System.out.println("onConnectionSuspended");

	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		System.out.println("onConnectionFailed");

	}

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Main Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://ie.ul.myproject/http/host/path")
		);
		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Main Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://ie.ul.myproject/http/host/path")
		);
		AppIndex.AppIndexApi.end(client, viewAction);
		client.disconnect();
	}
}
