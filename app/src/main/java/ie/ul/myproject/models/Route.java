package ie.ul.myproject.models;

import ie.ul.myproject.algorithm.CostMetric;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Route implements Comparable<Route>, Serializable {

	List<Stage> stages = new ArrayList<Stage>();

	public Route() {
		super();
	}
	
	// visitor pattern
	public CostMetric metric;
	public void accept(CostMetric metric){
		this.metric = metric;
	}
	
	public int getRouteCost(){
		return metric.getRouteCost(this);
	}

	public List<Stage> getStages() {
		return stages;
	}

	public void addStage(Stage stage) {
		stages.add(stage);
	}

	public String toString(){
		int hours = getTimeMinutes()/60;
		int minutes = getTimeMinutes()%60;
				
		String s = stages.get(0).getTo().getGoogleName() + stages.get(3).getFrom().getGoogleName() + hours + ":" + minutes;
		return s;
	}

	public Route copyRoute() {
		Route r = new Route();
		for (Stage stage : stages) {
			r.addStage(stage);
		}
		return r;
	}

	public int getTimeMinutes() {

		int totalTime = 0;
		// so shortesRoute works
		if (stages.size() == 0) {
			return Integer.MAX_VALUE;
		}
		for (Stage stage : stages) {
			totalTime += stage.getElapsedTimeMinutes();
		}
		return totalTime;
	}

	@Override
	public int compareTo(Route r) {

		if (this.getRouteCost() < r.getRouteCost()) {
			return -1;
		} else if (this.getRouteCost() == r.getRouteCost()) {
			return 0;
		} else {
			return 1;
		}
	}
}
