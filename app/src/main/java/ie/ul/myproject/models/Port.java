package ie.ul.myproject.models;

public class Port extends Place {
	
	int id ;
	String name ;
	String googleName;
	String countryCode;
	double latitude;
	double longitude;

	public Port() {
	}
	
	public Port(int id, String name, String googleName, String countryCode) {
		super();
		this.id = id;
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

	@Override
	public String toString() {
		return googleName ;
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
