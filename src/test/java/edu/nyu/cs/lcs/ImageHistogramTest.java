/**
 * 
 */
package edu.nyu.cs.lcs;

import org.junit.Test;

import edu.nyu.cs.lcs.Image;

/**
 * @author Scot Dalton
 *
 */
public class ImageHistogramTest {

	@Test
	public void testCreateImageHistograms() {
		Image image = new Image(TestUtility.IMAGE1);
		for(double mean : image.getMeans())
			System.out.println(mean);
		for(double standardDeviation : image.getStandardDeviations())
			System.out.println(standardDeviation);
	}
}