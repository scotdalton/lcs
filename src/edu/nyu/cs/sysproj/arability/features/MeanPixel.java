/**
 * 
 */
package edu.nyu.cs.sysproj.arability.features;

import edu.nyu.cs.sysproj.arability.Image;



/**
 * @author Scot Dalton
 *
 */
public class MeanPixel implements Feature {
	private float value;

	public MeanPixel(Image image, int band) {
		value = (float) image.getMeans()[band];
	}
	
	public float getValue() {
		return value;
	}
}