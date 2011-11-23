/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

import static edu.nyu.cs.sysproj.arability.TestUtility.getTestFileName1;
import static edu.nyu.cs.sysproj.arability.TestUtility.getTestFileName2;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Jama.Matrix;


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
		Matrix arabilityMatrix1 = unknownImage1.getArabilityMatrix();
		System.out.println(arabilityMatrix1.get(0, 0));
		Matrix arabilityMatrix2 = unknownImage2.getArabilityMatrix();
		System.out.println(arabilityMatrix2.get(0, 0));
		System.out.println(arabilityMatrix1.times(arabilityMatrix2).get(0, 0));
		System.out.println(arabilityMatrix1.times(arabilityMatrix2).get(0, 1));
		System.out.println(arabilityMatrix1.times(arabilityMatrix2).get(0, 2));
		assertTrue(
			unknownImage1.getArablePercentage() > 
				unknownImage2.getArablePercentage());
	}
}
