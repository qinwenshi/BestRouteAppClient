package ie.ul.myproject.models;

import ie.ul.myproject.algorithm.DecisionTree;

import java.io.Serializable;

import org.joda.time.LocalTime;

@SuppressWarnings("serial")
public class Crossing extends Stage implements Serializable {

	int id;
	int fromPortId;
	int toPortId;
	int ferryCompanyId;
	String departureTime;
	String arrivalTime;
	boolean mon, tue, wed, thu, fri, sat, sun;

	public Crossing() {

	}

	public Crossing(int id, int fromPortId, int toPortId, int ferryCompanyId,
			String departureTime, String arrivalTime, boolean mon, boolean tue,
			boolean wed, boolean thu, boolean fri, boolean sat, boolean sun) {
		super();
		this.id = id;
		this.fromPortId = fromPortId;
		this.toPortId = toPortId;
		this.ferryCompanyId = ferryCompanyId;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.mon = mon;
		this.tue = tue;
		this.wed = wed;
		this.thu = thu;
		this.fri = fri;
		this.sat = sat;
		this.sun = sun;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFromPortId() {
		return fromPortId;
	}

	public void setFromPortId(int fromPortId) {
		this.fromPortId = fromPortId;
	}

	public int getToPortId() {
		return toPortId;
	}

	public void setToPortId(int toPortId) {
		this.toPortId = toPortId;
	}

	public int getFerryCompanyId() {
		return ferryCompanyId;
	}

	public void setFerryCompanyId(int ferryCompanyId) {
		this.ferryCompanyId = ferryCompanyId;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
		startTime = LocalTime.parse(departureTime);
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
		endTime = LocalTime.parse(arrivalTime);
	}

	public boolean isMon() {
		return mon;
	}

	public void setMon(boolean mon) {
		this.mon = mon;
	}

	public boolean isTue() {
		return tue;
	}

	public void setTue(boolean tue) {
		this.tue = tue;
	}

	public boolean isWed() {
		return wed;
	}

	public void setWed(boolean wed) {
		this.wed = wed;
	}

	public boolean isThu() {
		return thu;
	}

	public void setThu(boolean thu) {
		this.thu = thu;
	}

	public boolean isFri() {
		return fri;
	}

	public void setFri(boolean fri) {
		this.fri = fri;
	}

	public boolean isSat() {
		return sat;
	}

	public void setSat(boolean sat) {
		this.sat = sat;
	}

	public boolean isSun() {
		return sun;
	}

	public void setSun(boolean sun) {
		this.sun = sun;
	}

	public String toString() {

		return startTime.toString("HH:mm") + ": Leave "
				+ DecisionTree.portMap.get(fromPortId).getGoogleName() + "\n"
				+ endTime.toString("HH:mm") + ": Arrive "
				+ DecisionTree.portMap.get(toPortId).getGoogleName() + "\n";
	}

	@Override
	public Place getTo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Place getFrom() {
		// TODO Auto-generated method stub
		return null;
	}

}
