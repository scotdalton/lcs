/**
 * 
 */
package edu.nyu.cs.lcs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import edu.nyu.cs.lcs.utility.Geocoder;


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

	@Ignore
	@Test
	public void testGeocoder_corners() throws Exception {
		Geocoder geocoder = new Geocoder("Ghana");
		assertTrue((float)11.16666750 == geocoder.getNortheastLatitude());
		assertTrue((float)1.1995540 == geocoder.getNortheastLongitude());
		assertTrue((float)4.738873799999999 == geocoder.getSouthwestLatitude());
		assertTrue((float)-3.24916690 == geocoder.getSouthwestLongitude());
	}
}
