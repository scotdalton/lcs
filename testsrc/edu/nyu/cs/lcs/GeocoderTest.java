/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.nyu.cs.sysproj.arability.utility.Geocoder;


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

	@Test
	public void testGeocoder_latLng() throws Exception {
		Geocoder geocoder = new Geocoder((float)-25.461111, (float)30.928889);
		assertEquals("N4, South Africa", geocoder.getAddress());
	}

	@Test
	public void testGeocoder_corners() throws Exception {
		Geocoder geocoder = new Geocoder("Ghana");
		assertTrue((float)11.16666750 == geocoder.getNortheastLatitude());
		assertTrue((float)1.19956560 == geocoder.getNortheastLongitude());
		assertTrue((float)4.63390 == geocoder.getSouthwestLatitude());
		assertTrue((float)-3.24916690 == geocoder.getSouthwestLongitude());
	}
}
