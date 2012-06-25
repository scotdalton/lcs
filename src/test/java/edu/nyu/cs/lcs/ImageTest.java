package edu.nyu.cs.lcs;
/**
 * 
 */
import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Scot Dalton
 *
 */
public class ImageTest {
	private File persistFile;

	@Before
	public void setup() {
		persistFile = new File("./tmp/persistTest.png");
		if(persistFile.exists()) persistFile.delete();
	}
	
	@After
	public void teardown() {
		if(persistFile.exists()) persistFile.delete();
	}

	@Test
	public void testNewImage_fromFileName() {
		Image image = new Image(TestUtility.IMAGE1);
		assertEquals(1500, image.getWidth());
		assertEquals(1000, image.getHeight());
	}
	
	@Test
	public void testNewImage_fromFile() {
		Image image = new Image(new File(TestUtility.IMAGE1));
		assertEquals(1500, image.getWidth());
		assertEquals(1000, image.getHeight());
	}
	
	@Test
	public void testNewImage_fromImage() {
		Image image = 
			new Image(new Image(new File(TestUtility.IMAGE1)));
		assertEquals(1500, image.getWidth());
		assertEquals(1000, image.getHeight());
	}
	
	@Test
	public void testPersist() {
		Image image = new Image(TestUtility.IMAGE1);
		image.persist(persistFile.getAbsolutePath());
		assertTrue(persistFile.exists());
		assertTrue(persistFile.isFile());
	}
	
	@Test
	public void testGetChoppedImages() {
		Image image = new Image(TestUtility.IMAGE1);
		List<Image> choppedImages = image.getChoppedImages();
		for (Image choppedImage:choppedImages) {
			assertEquals(100, choppedImage.getHeight());
			assertEquals(100, choppedImage.getWidth());
		}
	}
	
	@Test
	public void testChoppedImage() {
		Image image = new Image(TestUtility.CHOPPED);
		assertEquals(100, image.getHeight());
		assertEquals(100, image.getWidth());
	}
}