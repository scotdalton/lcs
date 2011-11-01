/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import static edu.nyu.cs.sysproj.google_earth.TestUtility.getArableTrainingImageFiles;

import java.io.File;
import java.util.List;

import org.junit.Test;



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
				MeanRedColor meanRedColor = new MeanRedColor(choppedImage);
				System.out.println("File: " + file.getName());
				System.out.println("\tMean red pixel: " + meanRedColor.getValue());
			}
		}
	}

	@Test
	public void testMeanGreen() {
		for(File file : getArableTrainingImageFiles()) {
			Image image = new Image(file);
			List<Image> choppedImages = image.getChoppedImages();
			for(Image choppedImage : choppedImages) {
				MeanGreenColor meanGreenColor = new MeanGreenColor(choppedImage);
				System.out.println("File: " + file.getName());
				System.out.println("\tMean green pixel: " + meanGreenColor.getValue());
			}
		}
	}

	@Test
	public void testMeanBlue() {
		for(File file : getArableTrainingImageFiles()) {
			Image image = new Image(file);
			List<Image> choppedImages = image.getChoppedImages();
			for(Image choppedImage : choppedImages) {
				MeanBlueColor meanBlueColor = new MeanBlueColor(choppedImage);
				System.out.println("File: " + file.getName());
				System.out.println("\tMean blue pixel: " + meanBlueColor.getValue());
			}
		}
	}
}