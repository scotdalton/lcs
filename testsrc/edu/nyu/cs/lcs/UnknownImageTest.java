/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

import static edu.nyu.cs.sysproj.arability.TestUtility.getTestFileName1;
import static edu.nyu.cs.sysproj.arability.TestUtility.getTestFileName2;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Scot Dalton
 *
 */
public class UnknownImageTest {
	@Test
	public void unknownImageTest() throws Exception {
		UnknownImage unknownImage1 = new UnknownImage(getTestFileName1());
		System.out.println(unknownImage1.getArablePercentage());
		UnknownImage unknownImage2 = new UnknownImage(getTestFileName2());
		System.out.println(unknownImage2.getArablePercentage());
		assertTrue(
			unknownImage1.getArablePercentage() > 
				unknownImage2.getArablePercentage());
	}
}
