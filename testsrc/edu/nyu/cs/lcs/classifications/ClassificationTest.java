/**
 * 
 */
package edu.nyu.cs.lcs.classifications;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * @author Scot Dalton
 *
 */
public class ClassificationTest {
	
	@Test
	public void testCropland() {
		Classification cropland = Classification.CROPLAND;
		assertEquals("CL", cropland.toString());
		assertTrue(0 < cropland.getTrainingImages().size());
		assertTrue(0 < cropland.getTestingImages().size());
		assertTrue(cropland.isTrainable());
		assertEquals(0, cropland.getRed());
		assertEquals(255, cropland.getGreen());
		assertEquals(0, cropland.getBlue());
		assertEquals(100, cropland.getAlpha());
	}
	
	@Test
	public void testDeveloped() {
		Classification developed = Classification.DEVELOPED;
		assertEquals("DV", developed.toString());
		assertTrue(0 < developed.getTrainingImages().size());
		assertTrue(0 < developed.getTestingImages().size());
		assertTrue(developed.isTrainable());
		assertEquals(0, developed.getRed());
		assertEquals(128, developed.getGreen());
		assertEquals(128, developed.getBlue());
		assertEquals(100, developed.getAlpha());
	}
	
	@Test
	public void testForest() {
		Classification forest = Classification.FOREST;
		assertEquals("FR", forest.toString());
		assertTrue(0 < forest.getTrainingImages().size());
		assertTrue(0 < forest.getTestingImages().size());
		assertTrue(forest.isTrainable());
		assertEquals(0, forest.getRed());
		assertEquals(0, forest.getGreen());
		assertEquals(255, forest.getBlue());
		assertEquals(100, forest.getAlpha());
	}
	
	@Test
	public void testDesert() {
		Classification desert = Classification.DESERT;
		assertEquals("DS", desert.toString());
		assertTrue(0 < desert.getTrainingImages().size());
		assertTrue(0 < desert.getTestingImages().size());
		assertTrue(desert.isTrainable());
		assertEquals(255, desert.getRed());
		assertEquals(255, desert.getGreen());
		assertEquals(0, desert.getBlue());
		assertEquals(100, desert.getAlpha());
	}
	
	@Test
	public void testUnknown() {
		Classification unknown = Classification.UNKNOWN;
		assertEquals("UN", unknown.toString());
		assertTrue(!unknown.isTrainable());
		assertEquals(0, unknown.getRed());
		assertEquals(255, unknown.getGreen());
		assertEquals(255, unknown.getBlue());
		assertEquals(100, unknown.getAlpha());
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testUnknow_trainingImages() {
		Classification.UNKNOWN.getTrainingImages();
//		Shouldn't get here.
		fail();
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testUnknow_testingImages() {
		Classification.UNKNOWN.getTestingImages();
//		Shouldn't get here.
		fail();
	}
}