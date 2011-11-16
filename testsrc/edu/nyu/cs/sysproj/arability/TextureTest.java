/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

import static edu.nyu.cs.sysproj.arability.TestUtility.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

import edu.nyu.cs.sysproj.arability.Image;
import edu.nyu.cs.sysproj.arability.features.DCTMean;

/**
 * @author Scot Dalton
 *
 */
public class TextureTest {
	@Test
	public void testGetFeature() {
		for(File file : getArableTrainingImageFiles()) {
			Image image = new Image(file);
			List<Image> choppedImages = image.getChoppedImages();
			for(Image choppedImage : choppedImages) {
				DCTMean dCTMean = 
					new DCTMean(choppedImage);
				System.out.println("File: " + file.getName());
				System.out.println("\tTexture: " + dCTMean.getValue());
			}
		}
	}
}