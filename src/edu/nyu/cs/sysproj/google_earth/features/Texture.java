/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth.features;

import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.util.Arrays;

import math.transform.jwave.Transform;
import math.transform.jwave.handlers.DiscreteWaveletTransform;
import math.transform.jwave.handlers.wavelets.Daub02;
import edu.nyu.cs.sysproj.google_earth.Image;


/**
 * @author Scot Dalton
 *
 */
public class Texture implements Feature{
//	private double[][] matrix;
//	private double[][] waveletCoefficients;
//	RenderedImage discreteCosineTransform;
	private float value;
	
//	public Texture(Image image, int x, int y) {
	public Texture(Image image) {
//		RenderedImage greyscaleImage = image.getGreyscaleImage();
//		setMatrix(greyscaleImage);
//		Transform t = 
//			new Transform(new DiscreteWaveletTransform(new Daub02(), 1));
//		waveletCoefficients = t.forward(matrix); 
//		value = image.getDiscreteCosineTransform().getData().
//			getSampleFloat(x, y, 0);
		value = 	(float) image.getDiscreteCosineTransform().getMeans()[0];
	}
	
	@Override
	public float getValue() {
		return value;
	}

//	public double[][] getWaveletCoefficients() {
//		return Arrays.copyOf(waveletCoefficients, waveletCoefficients.length);
//	}
//	
//	public RenderedImage getDiscreteCosineTransform() {
//		return discreteCosineTransform;
//	}
//	
//	public double[][] getMatrix() {
//		return matrix;
//	}
//	
//	private void setMatrix(RenderedImage singleBandImage) {
//		if(singleBandImage.getData().getNumBands() > 1)
//			throw new IllegalArgumentException();
//		int width = singleBandImage.getWidth();
//		int height = singleBandImage.getHeight();
//		Raster data = singleBandImage.getData();
//		matrix = new double[height][width];
//		for (int column = 0; column < width; column++)
//			for (int row = 0; row < height; row++)
//				matrix[row][column] = 
//					data.getSampleDouble(column, row, 0);
//	}
}