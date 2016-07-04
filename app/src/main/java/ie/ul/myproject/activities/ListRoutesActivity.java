package ie.ul.myproject.activities;

import ie.ul.myproject.R;
import ie.ul.myproject.algorithm.DecisionTree;
import ie.ul.myproject.models.LandLocation;
import ie.ul.myproject.models.Route;
import ie.ul.myproject.models.Stage;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListRoutesActivity extends ListActivity {

	ArrayList<Stage> stages;
	
	//TextView myLatitude, myLongitude;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		double currentLatitude = DecisionTree.myLastLocation.getLatitude();
		double currentLongitude = DecisionTree.myLastLocation.getLongitude();
		
		LandLocation here = new LandLocation("here", currentLatitude, currentLongitude, "IE");
		here.setGoogleName(currentLatitude + "," + currentLongitude);
		
		DecisionTree tree = new DecisionTree(here, DecisionTree.destination);
		
		tree.buildTree();
		tree.accept();
		tree.sortRoutes();

		
		ListView list = getListView();
		final List<Route> routes = tree.getAllRoutes();

		this.setListAdapter(new ArrayAdapter<Route>(this,
				R.layout.activity_list_stages, routes));

		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("Route position: " + position);

				Route r = routes.get(position);
				DecisionTree.selectedRoute = r;

				Intent listStagesIntent = new Intent(ListRoutesActivity.this,
						ListStagesActivity.class);
				startActivity(listStagesIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_of_routes, menu);
		return true;
	}

}
