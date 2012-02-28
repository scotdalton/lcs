/**
 * 
 */
package edu.nyu.cs.lcs;

import static edu.nyu.cs.lcs.utility.Configuration.KML_DIRECTORY;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.nyu.cs.lcs.utility.google_earth.GoogleEarth;
import edu.nyu.cs.lcs.utility.google_earth.GoogleEarthFactory;
import edu.nyu.cs.lcs.utility.google_earth.MacOsxGoogleEarth;

/**
 * @author Scot Dalton
 *
 */
public class GoogleEarthTest {
	
	@Test
	public void testGoogleEarth() throws Exception {
		GoogleEarth googleEarth = GoogleEarthFactory.getGoogleEarth();
		assertTrue(googleEarth instanceof MacOsxGoogleEarth);
		googleEarth.launch();
		String kmlFileName = KML_DIRECTORY+"/kml.kml";
		googleEarth.openKml(kmlFileName, 5000);
		googleEarth.destroy();
	}
}
