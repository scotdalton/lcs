/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import static edu.nyu.cs.sysproj.google_earth.TestUtility.*;

import org.junit.Test;

/**
 * @author Scot Dalton
 *
 */
public class ImageHistogramTest {

	@Test
	public void testCreateImageHistograms() {
		Image image = getTestImage1();
		for(double mean : image.getMeans())
			System.out.println(mean);
		for(double standardDeviation : image.getStandardDeviations())
			System.out.println(standardDeviation);
	}
}