package gov.vta.userlocation.controller;

import java.net.URLEncoder;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.ApiOperation;

import gov.vta.userlocation.dto.UserLocation;
import gov.vta.userlocation.service.UserLocationService;

@RestController
@RequestMapping
public class UserLocationController{

    //
    // Private instance variables
    //

    private final Logger LOGGER = Logger.getLogger(UserLocationController.class);
    private final  static String BAD_REQUEST_CLIENT_ERROR = "400 - Bad Request (Client Error) ";
    
    @Autowired
    private RestTemplate restTemplate;
    

    @Autowired
    private UserLocationService userLocationService;

    @CrossOrigin
    @RequestMapping(value="/user/{username}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)           
    @ApiOperation(
            value = "get user's current location"
          
    )
    public  ResponseEntity<UserLocation> getUserLocation(@PathVariable String username) {

  	UserLocation location = userLocationService.getUserLocation(username);
    	if( location == null) {
    		location = new UserLocation();
    		location.setName(username);
    	}
		return new ResponseEntity<UserLocation>(location,HttpStatus.OK);
    }
    @CrossOrigin
    @RequestMapping(value="/user",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)       
    public ResponseEntity<String> saveUserLocation(@RequestBody UserLocation userLocation) {
    	userLocationService.addUserLocation(userLocation);
    	return new ResponseEntity<String>("OK",HttpStatus.OK);
    }
    
    @CrossOrigin
    @RequestMapping(value="/address",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
            )       
    public  ResponseEntity<UserLocation> mapAddress(@RequestParam String lat, @RequestParam String lgn) {
    	
    	
    	String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+ lat + "," + lgn + "&key=AIzaSyB4eExFVgnAO-0lGpYLy-VZGYj2kda9OOI";
		UserLocation location = new UserLocation();
		location.setLattitude(lat);
		location.setLongtitude(lgn);
    	String s = restTemplate.getForObject(url, String.class)	;
    	 try {
			JSONObject jsonObject = new JSONObject(s);
			String status  =  (String) jsonObject.get("status");
			if (status.equals("OK")) {
				String formattedAddress = (String) jsonObject.getJSONArray("results").getJSONObject(0).get("formatted_address");
				location.setLocation(formattedAddress);
				return new ResponseEntity<UserLocation>(location,HttpStatus.OK);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return new ResponseEntity<UserLocation>(location,HttpStatus.BAD_REQUEST);

		
    }
    
    @CrossOrigin
    @RequestMapping(value="/location",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
            )       
    public  ResponseEntity<UserLocation> mapPosition(@RequestParam String address) {
    	
    	
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
				return new ResponseEntity<UserLocation>(userLocation,HttpStatus.OK);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return new ResponseEntity<UserLocation>(userLocation,HttpStatus.BAD_REQUEST);

		
    }
    
    


}