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
		for (int band=0; band < image.getHistogram().getNumBands(); band++) {
			for (int bin=0; bin < image.getHistogram().getNumBins(band); bin++)
				System.out.print(image.getHistogram().getBinSize(band, bin) + ", ");
			System.out.println();
		}
	}
}