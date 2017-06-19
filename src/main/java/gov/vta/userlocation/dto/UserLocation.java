package gov.vta.userlocation.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Entity
//@Table(name="UserLocation")
@Component
public class UserLocation implements Serializable {
	
	// @Id
	public  String name;
	 
	
	private String location;
	

	private String lattitude;
	private String longtitude;
	private Timestamp addedTimestamp;
	
	public UserLocation() {
		
	}
	public UserLocation(String name, String location) {
		this.setName(name);
		this.location = location;
	}

	
	public String getLattitude() {
		return lattitude;
	}
	public void setLattitude(String lattidue) {
		this.lattitude = lattidue;
	}
	public Timestamp getAddedTimestamp() {
		return addedTimestamp;
	}
	public void setAddedTimestamp(Timestamp addedTimestamp) {
		this.addedTimestamp = addedTimestamp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}
