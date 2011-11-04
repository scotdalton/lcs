package edu.nyu.cs.sysproj.google_earth;
/**
 * 
 */
import static edu.nyu.cs.sysproj.google_earth.TestUtility.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.image.RenderedImage;
import java.io.File;
import java.util.List;

import org.junit.Test;

/**
 * @author Scot Dalton
 *
 */
public class ImageTest {

//	@Test
	public void testCreateImage() {
		Image image = getTestImage1();
		RenderedImage originalImage = image.getRenderedImage();
		assertTrue(image.getRenderedImage() instanceof RenderedImage);
		assertEquals(3, originalImage.getColorModel().
				getColorSpace().getNumComponents());
		assertEquals(1000, image.getHeight());
		assertEquals(1000, image.getWidth());
		assertTrue(image.getChoppedImages() instanceof List<?>);
		String outputFile = TestUtility.IMAGE_PATH + "/crop/testCrop.png";
		persistImage(outputFile, image.getRenderedImage());
	}
	
//	@Test
	public void testChoppedImages() {
		Image image = getTestImage1();
		List<Image> choppedImages = image.getChoppedImages();
		for (Image choppedImage:choppedImages) {
			assertEquals(100, choppedImage.getHeight());
			assertEquals(100, choppedImage.getWidth());
			String outputFile = 
				TestUtility.IMAGE_PATH + "/chop/testChop"+
					choppedImage.getMinX()+"-"+
						choppedImage.getMinY()+".png";
			persistImage(outputFile, choppedImage.getRenderedImage());
		}
	}
	
	@Test
	public void testTrainingData() {
		for(File file: getArableTrainingImageFiles()) {
			Image image = new Image(file);
			List<Image> choppedImages = image.getChoppedImages();
			for (Image choppedImage: choppedImages) {
				int x = choppedImage.getMinX();
				int y = choppedImage.getMinY();
				String outputFile = 
					TestUtility.ARABLE_TRAINING_IMAGE_PATH + 
						"/chop/"+ file.getName() + 
							"_Chop_"+x+"_"+y+".png";
				persistImage(outputFile, choppedImage.getRenderedImage());
			}
		}
		for(File file: getNonArableTrainingImageFiles()) {
			Image image = new Image(file);
			List<Image> choppedImages = image.getChoppedImages();
			for (Image choppedImage: choppedImages) {
				int x = choppedImage.getMinX();
				int y = choppedImage.getMinY();
				String outputFile = 
					TestUtility.NON_ARABLE_TRAINING_IMAGE_PATH + 
						"/chop/"+ file.getName() + 
							"_Chop_"+x+"_"+y+".png";
				persistImage(outputFile, choppedImage.getRenderedImage());
			}
		}
	}
}