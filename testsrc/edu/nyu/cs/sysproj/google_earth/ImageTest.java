package edu.nyu.cs.sysproj.google_earth;
/**
 * 
 */
import static edu.nyu.cs.sysproj.google_earth.TestUtility.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.util.List;

import javax.media.jai.JAI;

import org.junit.Test;

/**
 * @author Scot Dalton
 *
 */
public class ImageTest {

	@Test
	public void testCreateImage() {
		Image image = new Image(getTestFile1());
		RenderedImage originalImage = image.getOriginalImage();
		assertTrue(image.getOriginalImage() instanceof RenderedImage);
		assertEquals(3, originalImage.getColorModel().
				getColorSpace().getNumComponents());
		assertEquals(1055, originalImage.getHeight(), 0);
		assertEquals(1544, originalImage.getWidth(), 0);
		assertTrue(image.getCroppedImage() instanceof Image);
		assertTrue(image.getChoppedImages() instanceof List<?>);
	}
	
	@Test
	public void testCroppedImage() {
		Image image = new Image(getTestFile1());
		Image croppedImage = image.getCroppedImage();
		RenderedImage originalImage = croppedImage.getOriginalImage();
		assertEquals(3, originalImage.getColorModel().
				getColorSpace().getNumComponents());
		assertEquals(1000, croppedImage.getHeight());
		assertEquals(1000, croppedImage.getWidth());
//		String outputFile = TestUtility.IMAGE_PATH + "/crop/testCrop.png";
//		persistImage(outputFile, croppedImage.getOriginalImage());
	}

	@Test
	public void testChoppedImages() {
		Image image = new Image(getTestFile1());
		List<Image> choppedImages = image.getChoppedImages();
		for (Image choppedImage:choppedImages) {
			assertEquals(100, choppedImage.getHeight());
			assertEquals(100, choppedImage.getWidth());
//			String outputFile = 
//				TestUtility.IMAGE_PATH + "/chop/testChop"+
//					choppedImage.getMinX()+"-"+
//						choppedImage.getMinY()+".png";
//			persistImage(outputFile, choppedImage.getOriginalImage());
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
				persistImage(outputFile, choppedImage.getOriginalImage());
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
				persistImage(outputFile, choppedImage.getOriginalImage());
			}
		}
	}
	
	private void persistImage(String filename, RenderedImage source) {
		ParameterBlock fileStoreParams = (new ParameterBlock()).
			addSource(source).add(filename).add("PNG");
		JAI.create("filestore", fileStoreParams);
	}
}