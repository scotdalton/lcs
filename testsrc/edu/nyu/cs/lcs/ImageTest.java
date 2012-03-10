package edu.nyu.cs.lcs;
/**
 * 
 */
import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

/**
 * @author Scot Dalton
 *
 */
public class ImageTest {
	@Test
	public void testNewImage_fromFileName() {
		Image image = new Image(TestUtility.getTestFileName1(), null);
		assertEquals(1500, image.getWidth());
		assertEquals(1000, image.getHeight());
	}
	
	@Test
	public void testNewImage_fromFile() {
		Image image = new Image(new File(TestUtility.getTestFileName1()), null);
		assertEquals(1500, image.getWidth());
		assertEquals(1000, image.getHeight());
	}
	
	@Test
	public void testNewImage_fromImage() {
		Image image = 
			new Image(new Image(new File(TestUtility.getTestFileName1()), null), null);
		assertEquals(1500, image.getWidth());
		assertEquals(1000, image.getHeight());
	}
	
	@Test
	public void testPersist() {
		Image image = new Image(TestUtility.getTestFileName1(), null);
		String filename = "./tmp/persistTest.png";
		image.persist(filename);
		File file = new File(filename);
		assertTrue(file.exists());
		assertTrue(file.isFile());
	}
	
	@Test
	public void testGetChoppedImages() {
		Image image = new Image(TestUtility.getTestFileName1(), null);
		List<Image> choppedImages = image.getChoppedImages();
		for (Image choppedImage:choppedImages) {
			assertEquals(100, choppedImage.getHeight());
			assertEquals(100, choppedImage.getWidth());
		}
	}
}