package gov.vta;

import java.util.List;

import gov.vta.userlocation.dto.UserLocation;

public class User {
	private String name;
	private List<UserLocation> locations;
	
	public User(String name) {
		this.name = name;
	}

	public List<UserLocation> getLocations() {
		return locations;
	}

	public void setLocations(List<UserLocation> locations) {
		this.locations = locations;
	}
	
	

}
