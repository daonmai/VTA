package gov.vta.userlocation.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import gov.vta.userlocation.dto.UserLocation;
import gov.vta.userlocation.repository.UserLocationRepo;

@Service
public class UserLocationService {
	
	@Autowired
	UserLocationRepo rep;
	
	@Autowired
	RestTemplate restTemplate;
	
	public void addUserLocation(UserLocation userLocation) {
		
		if ( userLocation.getLattitude().isEmpty() || userLocation.getLongtitude().isEmpty()) 
			mapUserPosition(userLocation);
		rep.save( userLocation);
		
	}
	
	public UserLocation getUserLocation(String userName) {
		return rep.findOne(userName);
	//	UserLocation user = new UserLocation(userName, "123 SAn Jose, CA");
	//	return user;
		
		
	}
	
	public Set<UserLocation> getUserLocation() {
		//return (Set<UserLocation>) rep.findAll();
		return null;
	}
	
	
	public String getFormattedAddress(String lattitude, String longtitude)
	{
		String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=' + lattitude + ',' + longtitude + '&key=AIzaSyB4eExFVgnAO-0lGpYLy-VZGYj2kda9OOI'";
		return url;
		
		
		
	}
	
	public UserLocation mapUserPosition(UserLocation user) {
		
		String address= user.getLocation();
		String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(address) + "&key=AIzaSyB4eExFVgnAO-0lGpYLy-VZGYj2kda9OOI";
		System.out.println(url);
		UserLocation userLocation = new UserLocation();
		userLocation.setLocation(address);
	
		String s = restTemplate.getForObject(url, String.class)	;
		 try {
			JSONObject jsonObject = new JSONObject(s);
			String status  =  (String) jsonObject.get("status");
			if (status.equals("OK")) {
				JSONObject location = (JSONObject) jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
				
				userLocation.setLattitude(location.getString("lat"));
				userLocation.setLongtitude(location.getString("lng"));
				
			}
			
		} catch (Exception e) {
			
		}
		 return userLocation;
	}
	 
}
