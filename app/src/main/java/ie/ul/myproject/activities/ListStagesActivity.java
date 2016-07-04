package ie.ul.myproject.activities;

import ie.ul.myproject.R;
import ie.ul.myproject.algorithm.DecisionTree;
import ie.ul.myproject.models.Stage;

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

public class ListStagesActivity extends ListActivity {
	
	List<Stage> stages ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		stages = DecisionTree.selectedRoute.getStages();
		
		for(Stage stage: stages){
			System.out.println("Time in minutes: " + stage.getElapsedTimeMinutes());
		}
			
		this.setListAdapter(new ArrayAdapter<Stage>(this,
				R.layout.activity_list_stages, stages));
		
		ListView list = getListView();		
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("Stage position: " + position);

				if (position == 0 || position == 3){
					DecisionTree.selectedStage = stages.get(position);
					
					Intent showStageOnMapIntent = new Intent(ListStagesActivity.this,
							ShowStageOnMapActivity.class);

					startActivity(showStageOnMapIntent);
				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alternate_route, menu);
		return true;
	}

}
