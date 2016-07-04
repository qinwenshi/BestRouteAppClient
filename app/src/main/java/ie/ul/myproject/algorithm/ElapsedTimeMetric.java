package ie.ul.myproject.algorithm;

import ie.ul.myproject.models.Route;
import ie.ul.myproject.models.Stage;

public class ElapsedTimeMetric implements CostMetric {

	@Override
	public int getRouteCost(Route r) {
		int cost = 0;
		for(Stage s: r.getStages()){
			cost += s.getElapsedTimeMinutes();
		}
		return cost;
	}
}
