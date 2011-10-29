/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth.features;

import java.awt.image.RenderedImage;
import java.util.Arrays;

import math.transform.jwave.Transform;
import math.transform.jwave.handlers.DiscreteWaveletTransform;
import math.transform.jwave.handlers.wavelets.Haar02;
import edu.nyu.cs.sysproj.google_earth.Image;


/**
 * @author Scot Dalton
 *
 */
public class Texture implements FeatureInterface {
	private double[] waveletCoefficients;

	public Texture(Image image) {
		RenderedImage originalImage = image.getOriginalImage();
		Transform t = 
			new Transform(new DiscreteWaveletTransform(new Haar02(), 2));
		waveletCoefficients = 
			t.forward(originalImage.getData().getPixels(0, 0, 
				originalImage.getWidth(), originalImage.getHeight(),
					(double[])null)); 
	}
	
	public double[] getFeature() {
		return Arrays.copyOf(waveletCoefficients, waveletCoefficients.length);
	}	
}
