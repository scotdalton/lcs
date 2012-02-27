package edu.nyu.cs.sysproj.arability;
/**
 * 
 */
import static edu.nyu.cs.sysproj.arability.TestUtility.getArableTestingImageFiles;
import static edu.nyu.cs.sysproj.arability.TestUtility.getArableTrainingImageFiles;
import static edu.nyu.cs.sysproj.arability.TestUtility.getDevelopedTestingImageFiles;
import static edu.nyu.cs.sysproj.arability.TestUtility.getDevelopedTrainingImageFiles;
import static edu.nyu.cs.sysproj.arability.TestUtility.getTestImage1;
import static edu.nyu.cs.sysproj.arability.utility.Configuration.ARABLE_TESTING_IMAGE_PATH;
import static edu.nyu.cs.sysproj.arability.utility.Configuration.ARABLE_TRAINING_IMAGE_PATH;
import static edu.nyu.cs.sysproj.arability.utility.Configuration.DEVELOPED_TESTING_IMAGE_PATH;
import static edu.nyu.cs.sysproj.arability.utility.Configuration.DEVELOPED_TRAINING_IMAGE_PATH;
import static edu.nyu.cs.sysproj.arability.utility.Configuration.IMAGE_PATH;
import static edu.nyu.cs.sysproj.arability.utility.Configuration.TMP_IMAGE_PATH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

import edu.nyu.cs.sysproj.arability.utility.Configuration;

/**
 * @author Scot Dalton
 *
 */
public class ImageTest {
//	@Test
	public void testRegion1() throws Exception {
		Image region = TestUtility.getTestRegion1();
		Image classificationMap = region.getClassificationHeatMap();
		classificationMap.persist(Configuration.TMP_BASE_PATH+ "regionClassification1.png");
	}
	
//	@Test
	public void testRegion2() throws Exception {
		Image region = TestUtility.getTestRegion2();
		Image classificationMap = region.getClassificationHeatMap();
		classificationMap.persist(Configuration.TMP_BASE_PATH+ "regionClassification2.png");
	}
	
	@Test
	public void testRegionComparison() throws Exception {
		Image toImage = TestUtility.getTestRegion2();
		Image fromImage = TestUtility.getTestRegion1();
		Image comparison = toImage.getComparisonImage(fromImage);
		comparison.persist(Configuration.TMP_BASE_PATH+ "regionComparison.png");
	}
	
//	@Test 
	public void testIDCT() {
		Image image = getTestImage1();
		List<Image> choppedImages = image.getChoppedImages();
		Image choppedImage = choppedImages.get(0);
		Image idct = choppedImage.getInverseDiscreteCosineTransform();
		idct.persist(TMP_IMAGE_PATH+"/idct.jpg");
	}
	
//	@Test 
	public void testDCTAdd() {
		Image image = getTestImage1();
		List<Image> choppedImages = image.getChoppedImages();
		Image choppedImage = choppedImages.get(0);
		Image dctAdd = 
			choppedImage.add(choppedImage.getDiscreteCosineTransform());
		dctAdd.persist(TMP_IMAGE_PATH+"/dctAdd.png");
		
	}
	
//	@Test
	public void testGradientMagnitude() {
		Image image = getTestImage1();
		Image gradientMagnitude = image.getGradientMagnitude();
		gradientMagnitude.persist(TMP_IMAGE_PATH+"/gradientMagnitude.png");
	}
	
//	@Test
	public void testCreateImage() {
		Image image = getTestImage1();
		assertEquals(1000, image.getHeight());
		assertEquals(1000, image.getWidth());
		assertTrue(image.getChoppedImages() instanceof List<?>);
		String outputFile = IMAGE_PATH + "/crop/testCrop.png";
		image.persist(outputFile);
	}
	
//	@Test
	public void testChoppedImages() {
		Image image = getTestImage1();
		List<Image> choppedImages = image.getChoppedImages();
		for (Image choppedImage:choppedImages) {
			assertEquals(100, choppedImage.getHeight());
			assertEquals(100, choppedImage.getWidth());
			String outputFile = 
				IMAGE_PATH + "/chop/testChop"+
					choppedImage.getMinX()+"-"+
						choppedImage.getMinY()+".png";
			choppedImage.persist(outputFile);
		}
	}
	
//	@Test
	public void testTrainingData() {
		for(File file: getArableTrainingImageFiles()) {
			Image image = new Image(file);
			List<Image> choppedImages = image.getChoppedImages();
			for (Image choppedImage: choppedImages) {
				int x = choppedImage.getMinX();
				int y = choppedImage.getMinY();
				String outputFile = 
					ARABLE_TRAINING_IMAGE_PATH + "/" + file.getName() + 
						"_"+x+"_"+y+".png";
				choppedImage.persist(outputFile);
			}
		}
		for(File file: getDevelopedTrainingImageFiles()) {
			Image image = new Image(file);
			List<Image> choppedImages = image.getChoppedImages();
			for (Image choppedImage: choppedImages) {
				int x = choppedImage.getMinX();
				int y = choppedImage.getMinY();
				String outputFile = 
					DEVELOPED_TRAINING_IMAGE_PATH + "/" + file.getName() + 
						"_"+x+"_"+y+".png";
				choppedImage.persist(outputFile);
			}
		}
	}

//	@Test
	public void testTestingData() {
		for(File file: getArableTestingImageFiles()) {
			Image image = new Image(file);
			List<Image> choppedImages = image.getChoppedImages();
			for (Image choppedImage: choppedImages) {
				int x = choppedImage.getMinX();
				int y = choppedImage.getMinY();
				String outputFile = 
					ARABLE_TESTING_IMAGE_PATH + "/" + file.getName() + 
						"_"+x+"_"+y+".png";
				choppedImage.persist(outputFile);
			}
		}
		for(File file: getDevelopedTestingImageFiles()) {
			Image image = new Image(file);
			List<Image> choppedImages = image.getChoppedImages();
			for (Image choppedImage: choppedImages) {
				int x = choppedImage.getMinX();
				int y = choppedImage.getMinY();
				String outputFile = 
					DEVELOPED_TESTING_IMAGE_PATH + "/" + file.getName() + 
						"_"+x+"_"+y+".png";
				choppedImage.persist(outputFile);
			}
		}
		
		
	}
}