/**
 * 
 */
package edu.nyu.cs.lcs;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.nyu.cs.lcs.utility.ImageUtil;
import edu.nyu.cs.lcs.utility.google_earth.GoogleEarth;
import edu.nyu.cs.lcs.utility.google_earth.GoogleEarthFactory;
import edu.nyu.cs.lcs.utility.google_earth.MacOsxGoogleEarth;

/**
 * @author Scot Dalton
 *
 */
public class GoogleEarthTest {
	
//	@Test
	public void testGoogleEarth() throws Exception {
		GoogleEarth googleEarth = GoogleEarthFactory.getGoogleEarth();
		assertTrue(googleEarth instanceof MacOsxGoogleEarth);
		googleEarth.launch();
		String kmlFileName = ImageUtil.getKmlFilename();
		googleEarth.openKml(kmlFileName, 5000);
		googleEarth.destroy();
	}
}
