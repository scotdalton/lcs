/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth.features;

import java.util.Arrays;

import edu.nyu.cs.sysproj.google_earth.Image;

import math.transform.jwave.Transform;
import math.transform.jwave.handlers.DiscreteWaveletTransform;
import math.transform.jwave.handlers.wavelets.Haar02;


/**
 * @author Scot Dalton
 *
 */
public class Texture implements FeatureInterface {
	private double[] waveletCoefficients;

	public Texture(Image image) {
		Transform t = 
			new Transform(new DiscreteWaveletTransform(new Haar02(), 2));
		waveletCoefficients = 
			t.forward(image.getOpenCvImage().getDoubleBuffer().array()); 
	}
	
	public double[] getFeature() {
		return Arrays.copyOf(waveletCoefficients, waveletCoefficients.length);
	}	
}
