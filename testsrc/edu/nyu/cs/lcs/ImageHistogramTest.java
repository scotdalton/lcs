/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

import static edu.nyu.cs.sysproj.arability.TestUtility.*;

import org.junit.Test;

import edu.nyu.cs.sysproj.arability.Image;

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