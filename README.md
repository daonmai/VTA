# VTA

The backend component of user location application form.
To run the application:
 java -jar target/userlocation-0.0.1.jar

The location will be running on port 8080 with the following endpoints for REST API:

-To save user’s location
 /user
	
	-METHOD: POST
	-expected json: {
		name: <User name>
		location: <user’s actual address/location>
		lattitude:<user’s location lattitude>
		longitude:<user’s location longtitude>
	}

/user/{username}
	-METHOD: GET
  
