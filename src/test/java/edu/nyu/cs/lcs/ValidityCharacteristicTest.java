/**
 * 
 */
package edu.nyu.cs.lcs;

import static org.junit.Assert.*;
import org.junit.Test;


/**
 * @author Scot Dalton
 *
 */
public class ValidityCharacteristicTest {

	@Test
	public void testIsValid_regular() {
		assertTrue((new Image(TestUtility.IMAGE1)).isValid());
	}

	@Test
	public void testIsValid_cloudy() {
		assertFalse((new Image(TestUtility.CLOUDY)).isValid());
	}

	@Test
	public void testIsValid_blurry() {
		assertFalse((new Image(TestUtility.BLURRY)).isValid());
	}

	@Test
	public void testIsValid_bright() {
		assertFalse((new Image(TestUtility.BRIGHT)).isValid());
	}
}
