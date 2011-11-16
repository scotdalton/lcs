/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

import static edu.nyu.cs.sysproj.arability.TestUtility.*;

import static org.junit.Assert.*;
import org.junit.Test;

import edu.nyu.cs.sysproj.arability.UnknownImage;


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
