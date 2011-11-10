/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import static edu.nyu.cs.sysproj.google_earth.TestUtility.*;

import org.junit.Test;

import edu.nyu.cs.sysproj.google_earth.features.DCTDownSample;

/**
 * @author Scot Dalton
 *
 */
public class DCTDownSampleTest {
	@Test
	public void testDCTDownSample() {
		DCTDownSample ds = 
			new DCTDownSample(getTestImage1(), 1, 0);
		System.out.println(ds.getValue());
	}
}
