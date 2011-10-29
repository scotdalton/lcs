/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import static edu.nyu.cs.sysproj.google_earth.TestUtility.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

import edu.nyu.cs.sysproj.google_earth.features.Texture;

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
				Texture texture = new Texture(choppedImage);
				System.out.println("File: " + file.getName());
				System.out.println("Min X: " + 
					choppedImage.getOriginalImage().getMinX());
				System.out.println("Min Y: " + 
					choppedImage.getOriginalImage().getMinY());
				for(double waveletCoefficients: texture.getFeature())
					System.out.println(
						"\tWavelet Coefficients: " + waveletCoefficients);
			}
		}
	}
}
