/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import static edu.nyu.cs.sysproj.google_earth.TestUtility.getBlurryTestImage1;
import static edu.nyu.cs.sysproj.google_earth.TestUtility.getBrightTestImage1;
import static edu.nyu.cs.sysproj.google_earth.TestUtility.getCloudyTestImage1;
import static edu.nyu.cs.sysproj.google_earth.TestUtility.getTestImage1;

import static org.junit.Assert.*;
import org.junit.Test;


/**
 * @author Scot Dalton
 *
 */
public class ValidityCharacteristicTest {

	@Test
	public void testIsValid_regular() {
		assertTrue(getTestImage1().isValid());
	}

	@Test
	public void testIsValid_cloudy() {
		assertFalse(getCloudyTestImage1().isValid());
	}

	@Test
	public void testIsValid_blurry() {
		assertFalse(getBlurryTestImage1().isValid());
	}

	@Test
	public void testIsValid_bright() {
		assertFalse(getBrightTestImage1().isValid());
	}
}
