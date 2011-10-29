/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import static edu.nyu.cs.sysproj.google_earth.TestUtility.getArableTrainingImageFiles;

import java.io.File;
import java.util.List;

import org.junit.Test;

import edu.nyu.cs.sysproj.google_earth.features.Color;

/**
 * @author Scot Dalton
 *
 */
public class ColorTest {

	@Test
	public void testGetFeature() {
		for(File file : getArableTrainingImageFiles()) {
			Image image = new Image(file);
			List<Image> choppedImages = image.getChoppedImages();
			for(Image choppedImage : choppedImages) {
				Color color = new Color(choppedImage);
				System.out.println("File: " + file.getName());
				for(double meanPixel: color.getFeature())
					System.out.println("\tMean pixel: " + meanPixel);
			}
		}
	}
}