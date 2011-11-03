/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import static edu.nyu.cs.sysproj.google_earth.TestUtility.getArableTrainingImageFiles;

import java.io.File;
import java.util.List;

import org.junit.Test;

import edu.nyu.cs.sysproj.google_earth.features.MeanPixel;



/**
 * @author Scot Dalton
 *
 */
public class ColorTest {

	@Test
	public void testMeanRed() {
		for(File file : getArableTrainingImageFiles()) {
			Image image = new Image(file);
			List<Image> choppedImages = image.getChoppedImages();
			for(Image choppedImage : choppedImages) {
				MeanPixel meanPixelBand1 = 
					new MeanPixel(choppedImage, 0);
				System.out.println("File: " + file.getName());
				System.out.println("\tMean pixel band 1: " + meanPixelBand1.getValue());
			}
		}
	}

	@Test
	public void testMeanGreen() {
		for(File file : getArableTrainingImageFiles()) {
			Image image = new Image(file);
			List<Image> choppedImages = image.getChoppedImages();
			for(Image choppedImage : choppedImages) {
				MeanPixel meanColorBand2 = 
					new MeanPixel(choppedImage, 1);
				System.out.println("File: " + file.getName());
				System.out.println("\tMean green pixel: " + meanColorBand2.getValue());
			}
		}
	}

	@Test
	public void testMeanBlue() {
		for(File file : getArableTrainingImageFiles()) {
			Image image = new Image(file);
			List<Image> choppedImages = image.getChoppedImages();
			for(Image choppedImage : choppedImages) {
				MeanPixel meanColorBand3 = 
					new MeanPixel(choppedImage, 2);
				System.out.println("File: " + file.getName());
				System.out.println("\tMean blue pixel: " + meanColorBand3.getValue());
			}
		}
	}
}