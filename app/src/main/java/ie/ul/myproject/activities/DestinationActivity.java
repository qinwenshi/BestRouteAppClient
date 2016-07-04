package ie.ul.myproject.activities;

import ie.ul.myproject.MainActivity;
import ie.ul.myproject.R;
import ie.ul.myproject.algorithm.DecisionTree;
import ie.ul.myproject.algorithm.ElapsedTimeMetric;
import ie.ul.myproject.algorithm.LessDrivingMetric;
import ie.ul.myproject.algorithm.LessSailingMetric;
import ie.ul.myproject.models.LandLocation;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class DestinationActivity extends Activity implements OnClickListener {
	
	RadioGroup radioMetric;
	RadioButton btnMetric;
	EditText destinationName;
	EditText destinationCountryCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_destination);
		
		View createButton = findViewById(R.id.stateDestination_button);
		createButton.setOnClickListener(this);

		View returnButton = findViewById(R.id.return_button);
		returnButton.setOnClickListener(this);
		
		
		destinationName = (EditText) findViewById(R.id.destination_text);
		destinationCountryCode = (EditText) findViewById(R.id.destination_countryCode);
		
		radioMetric = (RadioGroup) findViewById(R.id.metricChoice);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.destination, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.stateDestination_button:
			
			String destination = destinationName.getText().toString();		
			String googleName = destination.replace(", ", "+");
			googleName = googleName.replace(",", "+");
			googleName = googleName.replace(" ", "+");
			String countryCode = destinationCountryCode.getText().toString();
			
			
			if (radioMetric.getCheckedRadioButtonId() == R.id.time_Metric){
				DecisionTree.metric = new ElapsedTimeMetric();
			} else if (radioMetric.getCheckedRadioButtonId() == R.id.drive_Metric){
				DecisionTree.metric = new LessDrivingMetric();
			} else {
				DecisionTree.metric = new LessSailingMetric();
			}
					
			
			//System.out.println("Destination: " + destination);
			
			LandLocation location = new LandLocation(destination, googleName, countryCode);
			
			System.out.println("Destination stated");
			
			DecisionTree.destination = location;
			
			//System.out.println("Intended destination:" + DecisionTree.destination.getName());
			
			Intent listRoutesIntent = new Intent(this, ListRoutesActivity.class);
			startActivity(listRoutesIntent);
			
			System.out.println("Intended destination stated");
			break;
			
		case R.id.return_button:
			System.out.println("Return to menu");
			startActivity(new Intent(this, MainActivity.class));
		}
	}
}
