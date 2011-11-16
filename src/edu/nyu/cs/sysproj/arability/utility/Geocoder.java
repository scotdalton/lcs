/**
 * 
 */
package edu.nyu.cs.sysproj.arability.utility;

import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * @author Scot Dalton
 *
 */
public class Geocoder {
	private String GEOCODER = 
		"https://maps.googleapis.com/maps/api/geocode/json?sensor=false&";
	private String address;
	private float latitude;
	private float longitude;
	private JSONObject geocoding;
	
	public Geocoder(float longitude, float latitude) throws Exception {
		this.longitude = longitude;
		this.latitude = latitude;
		processGeocoding();
	}
	
	public Geocoder(String address) throws Exception {
		if (address == null) throw new NullPointerException();
		processGeocoding(address);
	}
	
	public String getAddress() {
		return address;
	}

	public float getLongitude() {
		return longitude;
	}

	public float getLatitude() {
		return latitude;
	}
	
	private JSONObject getGeocoding(String address) throws Exception {
		String geocoderURLString = GEOCODER;
		if(address != null) {
			geocoderURLString += 
				"address="+URLEncoder.encode(address, "UTF-8");
		} else {
			geocoderURLString += 
				"latlng="+longitude+","+latitude;
		}
		URL geocoderURL = new URL(geocoderURLString);
		JSONObject geocoding = 
			new JSONObject(new JSONTokener(geocoderURL.openStream()));
		return geocoding;
	}
	
	private void processGeocoding() throws Exception {
		processGeocoding(null);
	}

	private void processGeocoding(String address) throws Exception {
		geocoding = getGeocoding(address);
		JSONObject results = 
			geocoding.getJSONArray("results").getJSONObject(0);
		this.address = results.getString("formatted_address");
		JSONObject location = 
			results.getJSONObject("geometry").getJSONObject("location");
		this.longitude = ((Double) location.get("lat")).floatValue();
		this.longitude = ((Double) location.get("lng")).floatValue();
	}
}