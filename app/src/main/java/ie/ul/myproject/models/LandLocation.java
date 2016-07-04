package ie.ul.myproject.models;

import java.util.ArrayList;
import java.util.List;

public class LandLocation extends Place {
	
	protected int id ;
	protected String name ;
	protected String googleName;
	protected String countryCode;
	public double latitude;
	public double longitude;
	
	
	List<RoadStage> stages = new ArrayList<RoadStage>();
	
	LandLocation() {
		super();
	}
	
	public LandLocation(String name, double latitude, double longitude, String countryCode){
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.countryCode = countryCode;
	}

	public LandLocation(String name, String googleName, String countryCode) {
		super();
		this.name = name;
		this.googleName = googleName;
		this.countryCode = countryCode;
	}
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getGoogleName() {
		return googleName;
	}
	public void setGoogleName(String googleName) {
		this.googleName = googleName;
	}

	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public List<RoadStage> getStages() {
		return stages;
	}
		
	public void setStages(List<RoadStage> stages) {
		this.stages = stages;
	}
	
	public void addStage(RoadStage stage){
		stages.add(stage);
		
	}

	@Override
	public String toString() {
		return googleName;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
