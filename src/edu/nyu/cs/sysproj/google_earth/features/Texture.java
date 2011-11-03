/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth.features;

import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.util.Arrays;

import javax.media.jai.ImageLayout;
import javax.media.jai.JAI;

import math.transform.jwave.Transform;
import math.transform.jwave.handlers.DiscreteWaveletTransform;
import math.transform.jwave.handlers.wavelets.Daub02;
import edu.nyu.cs.sysproj.google_earth.Image;


/**
 * @author Scot Dalton
 *
 */
public class Texture {
	private double[][] matrix;
	private double[][] waveletCoefficients;
	private double[] dctCoefficients;
	RenderedImage discreteCosineTransform;
	RenderedImage greyscaleImage;
	
	public Texture(Image image) {
		ColorModel cm = 
			new ComponentColorModel(
				ColorSpace.getInstance(
					ColorSpace.CS_GRAY), new int[] {8}, false, false, 
						Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
		ParameterBlock greyscalePB = 
			(new ParameterBlock()).
				addSource(image.getOriginalImage()).add(cm);
		ImageLayout greyscaleLayout = 
			(new ImageLayout()).setMinX(image.getMinX()).
				setMinY(image.getMinY()).setColorModel(cm);
		RenderingHints greyscaleRH = 
			new RenderingHints(JAI.KEY_IMAGE_LAYOUT, greyscaleLayout);
		greyscaleImage = 
			JAI.create("ColorConvert", greyscalePB, greyscaleRH);
		setMatrix(greyscaleImage);
		Transform t = 
			new Transform(new DiscreteWaveletTransform(new Daub02(), 1));
		waveletCoefficients = t.forward(matrix); 
		ParameterBlock dctPB = 
	    	 	(new ParameterBlock()).addSource(greyscaleImage);
	     discreteCosineTransform = JAI.create("dct", dctPB, null);
	     dctCoefficients =
	    	 	discreteCosineTransform.getData().
	    	 		getPixels(discreteCosineTransform.getMinX(), 
	    	 			discreteCosineTransform.getMinY(), 
	    	 				discreteCosineTransform.getWidth(), 
	    	 					discreteCosineTransform.getHeight(), 
	    	 						(double[])null);
	}
	
	public double[] getFeature() {
		return getDCTCoefficients();
	}
	
	public double[][] getWaveletCoefficients() {
		return Arrays.copyOf(waveletCoefficients, waveletCoefficients.length);
	}
	
	public double[] getDCTCoefficients() {
		return Arrays.copyOf(dctCoefficients, dctCoefficients.length);
	}
	
	public RenderedImage getDiscreteCosineTransform() {
		return discreteCosineTransform;
	}
	
	public RenderedImage getGreyscaleImage() {
		return greyscaleImage;
	}
	
	public double[][] getMatrix() {
		return matrix;
	}
	
	private void setMatrix(RenderedImage singleBandImage) {
		if(singleBandImage.getData().getNumBands() > 1)
			throw new IllegalArgumentException();
		int width = singleBandImage.getWidth();
		int height = singleBandImage.getHeight();
		Raster data = singleBandImage.getData();
		matrix = new double[height][width];
		for (int column = 0; column < width; column++)
			for (int row = 0; row < height; row++)
				matrix[row][column] = 
					data.getSampleDouble(column, row, 0);
	}
}
