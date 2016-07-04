package ie.ul.myproject.models;

import org.joda.time.LocalTime;
import org.joda.time.Minutes;

public abstract class Stage {
	
	static final long MINUTES_IN_DAY = 1440;
	
	LocalTime startTime;
	LocalTime endTime;
	
	public int getElapsedTimeMinutes() {
		int minutes = Minutes.minutesBetween(startTime, endTime).getMinutes();

		if (minutes < 0) {
			minutes += MINUTES_IN_DAY;
		}
		return minutes;
	}
	
	public abstract String toString() ;	
	
	public abstract Place getTo() ;
	public abstract Place getFrom() ;
	
	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
		
}