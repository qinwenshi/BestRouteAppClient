package ie.ul.myproject.models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RoadStage extends Stage implements Serializable{
	
	//private static final long serialVersionUID = 1L;
	Place from ;
	Place to;
		
	int elapsedTimeMinutes;
	double distance;
	
	public RoadStage(){
		
	}

	public RoadStage(Place from, Place to, int time) {
		super();
		this.from = from;
		this.to = to;
		this.elapsedTimeMinutes = time;
	}
	
	public RoadStage copyRoadStage() {
		RoadStage r = new RoadStage();
		r.distance = distance ;
		r.elapsedTimeMinutes = elapsedTimeMinutes;
		r.to = to;
		r.from = from;
		return r;
	}

	public String toString(){
		
		return startTime.toString("HH:mm") + ": Leave " + from.getGoogleName() + 
				"\n" + endTime.toString("HH:mm") + ": Arrive " + to.getGoogleName() + "\n";
	}

	public Place getTo() {
		return to;
	}

	public Place getFrom() {
		return from ;
	}

	public void setFrom(Place from) {
		this.from = from;
	}

	public void setTo(Place to) {
		this.to = to;
	}

	public int getElapsedTimeMinutes() {
		return elapsedTimeMinutes;
	}

	public void setElapsedTimeMinutes(int elapsedTimeMinutes) {
		this.elapsedTimeMinutes = elapsedTimeMinutes;	
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

}
