/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth.features;

import edu.nyu.cs.sysproj.google_earth.Image;



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