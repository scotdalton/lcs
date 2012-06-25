/**
 * 
 */
package edu.nyu.cs.lcs.utility;

/**
 * @author Scot Dalton
 *
 */
public class RegionImageGrabber {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		for(String arg: args) {
			Geocoder geocoder = new Geocoder(arg);
			double longitudeStart = geocoder.getNortheastLongitude();
			double latitudeStart = geocoder.getNortheastLatitude();
			double longitudeEnd = geocoder.getSouthwestLongitude();
			double latitudeEnd = geocoder.getSouthwestLatitude();
			ImageGrabber.grabImages(longitudeStart, longitudeEnd, latitudeStart, 
					latitudeEnd, 1, -1, "tmp/"+arg);
		}
	}
}