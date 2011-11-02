/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth.features;

import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.util.Arrays;

import javax.media.jai.JAI;

import edu.nyu.cs.sysproj.google_earth.Image;

import weka.core.Attribute;



/**
 * @author Scot Dalton
 *
 */
public abstract class MeanColor implements Feature {
	private double[] meanPixels;

	public MeanColor(Image image) {
		// Set up the parameter block for the source image and
		// the three parameters.
		ParameterBlock meanColorPB = (new ParameterBlock()).
			addSource(image.getOriginalImage()).add(null).add(1).add(1);
		// Perform the mean operation on the source image.
		RenderedImage meanImage = JAI.create("mean", meanColorPB, null);
		// Retrieve the mean pixel value.
		meanPixels = (double[])meanImage.getProperty("mean");
	}
	
	public abstract float getValue();
	
	protected double[] getMeanPixels() {
		return meanPixels;
	}
}