package ie.ul.myproject.models;

import org.joda.time.LocalTime;

public class WaitStage extends Stage {

	public WaitStage(LocalTime startTime, LocalTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}


	public String toString() {
		return "Waiting from " + startTime.toString("HH:mm") + " to "
				+ endTime.toString("HH:mm");
	}

	@Override
	public Place getTo() {
		return null;
	}

	@Override
	public Place getFrom() {
		return null;
	}

}
