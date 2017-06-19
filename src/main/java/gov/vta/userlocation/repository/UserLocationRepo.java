package gov.vta.userlocation.repository;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import gov.vta.userlocation.dto.UserLocation;

@Component
public class UserLocationRepo {
	
	private static ConcurrentHashMap<String,UserLocation> userLocations = new ConcurrentHashMap<String, UserLocation>();
	
	
	public void save(UserLocation user) {
		// save to cache.
		System.out.print("Saving..");
		userLocations.put(user.name.toLowerCase(),user);
		
	}
	
	public UserLocation findOne(String userName) {
		return userLocations.get(userName.toLowerCase());
	}
	

}
