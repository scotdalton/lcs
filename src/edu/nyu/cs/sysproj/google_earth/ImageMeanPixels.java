/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.util.Arrays;

import javax.media.jai.JAI;

/**
 * @author Scot Dalton
 *
 */
public class ImageMeanPixels {
	private double[] meanPixels;

	public ImageMeanPixels(EarthImage earthImage) {
		// Set up the parameter block for the source image and
		// the three parameters.
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(earthImage.getJavaAiImage()); // The source image
		pb.add(null); // null ROI means whole image
		pb.add(1); // check every pixel horizontally
		pb.add(1); // check every pixel vertically
		// Perform the mean operation on the source image.
		RenderedImage meanImage = JAI.create("mean", pb, null);
		// Retrieve the mean pixel value.
		meanPixels = (double[])meanImage.getProperty("mean");
	}
	
	protected double[] getMeanPixels() {
		return Arrays.copyOf(meanPixels, meanPixels.length);
	}
}