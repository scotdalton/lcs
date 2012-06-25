/**
 * 
 */
package edu.nyu.cs.lcs.utility;

import java.io.InputStreamReader;
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
	private float northeastLongitude;
	private float northeastLatitude;
	private float southwestLongitude;
	private float southwestLatitude;
	private JSONObject geocoding;
	
	public Geocoder(float latitude, float longitude) throws Exception {
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
	
	public float getNortheastLongitude() {
		return northeastLongitude;
	}
	
	public float getNortheastLatitude() {
		return northeastLatitude;
	}
	
	public float getSouthwestLongitude() {
		return southwestLongitude;
	}
	
	public float getSouthwestLatitude() {
		return southwestLatitude;
	}
	
	private JSONObject getGeocoding(String address) throws Exception {
		String geocoderURLString = GEOCODER;
		if(address != null) {
			geocoderURLString += 
				"address="+URLEncoder.encode(address, "UTF-8");
		} else {
			geocoderURLString += 
				"latlng="+latitude+","+longitude;
		}
		System.out.println(geocoderURLString);
		URL geocoderURL = new URL(geocoderURLString);
		JSONObject geocoding = new JSONObject(new JSONTokener(
			new InputStreamReader(geocoderURL.openStream())));
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
		longitude = ((Double) location.get("lng")).floatValue();
		latitude = ((Double) location.get("lat")).floatValue();
		JSONObject bounds = 
			results.getJSONObject("geometry").getJSONObject("bounds");
		JSONObject northeast = bounds.getJSONObject("northeast");
		northeastLongitude = ((Double) northeast.get("lng")).floatValue();
		northeastLatitude = ((Double) northeast.get("lat")).floatValue();
		JSONObject southwest = bounds.getJSONObject("southwest");
		southwestLongitude = ((Double) southwest.get("lng")).floatValue();
		southwestLatitude = ((Double) southwest.get("lat")).floatValue();
	}
}