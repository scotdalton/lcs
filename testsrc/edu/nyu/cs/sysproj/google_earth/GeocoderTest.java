/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.nyu.cs.sysproj.google_earth.geocoding.Geocoder;


/**
 * @author Scot Dalton
 *
 */
public class GeocoderTest {
	@Test
	public void testGeocoder_address() throws Exception {
		Geocoder geocoder = new Geocoder("274 Prospect Park West");
		assertEquals("274 Prospect Park West, Brooklyn, NY 11215, USA", 
			geocoder.getAddress());
	}
}
