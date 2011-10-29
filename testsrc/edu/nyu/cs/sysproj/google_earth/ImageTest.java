package edu.nyu.cs.sysproj.google_earth;
/**
 * 
 */
import static edu.nyu.cs.sysproj.google_earth.TestUtility.getArableTrainingImageFiles;
import static edu.nyu.cs.sysproj.google_earth.TestUtility.getTestFile;
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
		Image image = new Image(getTestFile());
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
		Image image = new Image(getTestFile());
		Image croppedImage = image.getCroppedImage();
		RenderedImage originalImage = croppedImage.getOriginalImage();
		assertEquals(3, originalImage.getColorModel().
				getColorSpace().getNumComponents());
		assertEquals(1000, originalImage.getHeight());
		assertEquals(1000, originalImage.getWidth());
		String outputFile = TestUtility.IMAGE_PATH + "/crop/testCrop.png";
		ParameterBlock fileStoreParams = 
			new ParameterBlock().addSource(originalImage).
				add(outputFile).add("PNG");
		JAI.create("filestore", fileStoreParams);
	}

	@Test
	public void testChoppedImages() {
		Image image = new Image(getTestFile());
		List<Image> choppedImages = image.getChoppedImages();
		for (Image choppedImage:choppedImages) {
			RenderedImage originalImage = choppedImage.getOriginalImage();
			assertEquals(100, originalImage.getHeight());
			assertEquals(100, originalImage.getWidth());
			int x = originalImage.getMinX();
			int y = originalImage.getMinY();
			String outputFile = 
				TestUtility.IMAGE_PATH + "/chop/testChop"+x+"-"+y+".png";
			ParameterBlock fileStoreParams = new ParameterBlock();
			fileStoreParams.addSource(originalImage);
			fileStoreParams.add(outputFile);
			fileStoreParams.add("PNG");
			JAI.create("filestore", fileStoreParams);
		}
	}
	
	@Test
	public void testArableTrainingData() {
		for(File file: getArableTrainingImageFiles()) {
			Image image = new Image(file);
			List<Image> choppedImages = image.getChoppedImages();
			for (Image choppedImage:choppedImages) {
				RenderedImage originalImage = 
					choppedImage.getOriginalImage();
				int x = originalImage.getMinX();
				int y = originalImage.getMinY();
				String outputFile = 
					TestUtility.ARABLE_TRAINING_IMAGE_PATH + 
						"/chop/"+ file.getName() +
							"_Chop_"+x+"_"+y+".png";
				ParameterBlock fileStoreParams = new ParameterBlock();
				fileStoreParams.addSource(choppedImage);
				fileStoreParams.add(outputFile);
				fileStoreParams.add("PNG");
				System.out.println(outputFile);
				JAI.create("filestore", fileStoreParams);
			}
		}

	}
}
