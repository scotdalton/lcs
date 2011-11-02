/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth.features;

import edu.nyu.cs.sysproj.google_earth.Image;

/**
 * @author Scot Dalton
 *
 */
public class MeanBlueColor extends MeanColor {

	public MeanBlueColor(Image image) {
		super(image);
	}

	@Override
	public float getValue() {
		double[] meanPixels = getMeanPixels();
		return (float)meanPixels[2];
	}
}