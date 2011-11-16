/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

import static edu.nyu.cs.sysproj.arability.TestUtility.*;

import org.junit.Test;

import edu.nyu.cs.sysproj.arability.features.DCTDownSample;

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
