package ie.ul.myproject.algorithm;

import ie.ul.myproject.models.Route;
import ie.ul.myproject.models.Stage;

import java.util.List;

public class LessDrivingMetric implements CostMetric {

	@Override
	public int getRouteCost(Route r) {
		int cost = 0;
		List<Stage> stages = r.getStages();
		cost += stages.get(0).getElapsedTimeMinutes() * 2;
		cost += stages.get(1).getElapsedTimeMinutes();
		cost += stages.get(2).getElapsedTimeMinutes();
		cost += stages.get(3).getElapsedTimeMinutes() * 2;

		return cost;
	}
}
