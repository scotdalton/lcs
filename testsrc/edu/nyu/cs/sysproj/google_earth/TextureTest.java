/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import static edu.nyu.cs.sysproj.google_earth.TestUtility.*;

import java.awt.image.RenderedImage;

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
//		File file = getTestFile1();
//		for(File file : getArableTrainingImageFiles()) {
//			Image image = new Image(file);
//			List<Image> choppedImages = image.getChoppedImages();
//			for(Image choppedImage : choppedImages) {
			Image choppedImage = getTestChoppedImage1();
			System.out.println(
				"Chopped image dimensions: " + choppedImage.getWidth() + "x" + choppedImage.getHeight());
				Texture texture = new Texture(choppedImage);
//				int x = choppedImage.getMinX();
//				int y = choppedImage.getMinY();
//				String outputFile = 
//					TestUtility.IMAGE_PATH + "/chop/testGreyScaleChop"+x+"-"+y+".png";
//				persistImage(outputFile, texture.getGreyscaleImage());
//				RenderedImage dct = texture.getDiscreteCosineTransform();
//				int width = dct.getWidth();
//				System.out.println(width);
//				int height = dct.getHeight();
//				System.out.println(height);
//				int numBands = dct.getSampleModel().getNumBands();
//				System.out.println(numBands);
//				int dataType = dct.getSampleModel().getDataType();
//				System.out.println(dataType);
//				double[] dctData =
//					dct.getData().getPixels(dct.getMinX(), dct.getMinY(), width, height, (double[])null);
//				System.out.println(dctData.length);
//				double[] pixels = new double[dctData.length];
//				System.out.println("File: " + file.getName());
//				System.out.println("Min X: " + x);
//				System.out.println("Min Y: " + y);
//				double[][] matrix = texture.getMatrix();
				double[][] waveletCoefficients = 
					texture.getWaveletCoefficients();
				System.out.println(
					"Wavelet Coefficients: " + 
					waveletCoefficients.length + "x" + 
					waveletCoefficients[0].length);
				System.out.println();
				RenderedImage dct = 
					texture.getDiscreteCosineTransform();
				System.out.println(
					"DCT dimensions: " + 
					dct.getWidth() + "x" + 
					dct.getHeight());
				System.out.println(dct.getData().getSampleDouble(0, 0, 0));
//				if(texture.getWaveletCoefficients().equals(texture.getDCTCoefficients())) {
//					System.out.println("Equals");
//				}
//				for(double waveletCoefficients: texture.getFeature())
//					System.out.println(
//				"\tWavelet Coefficients: " + waveletCoefficients);
//			}
//		}
	}
}
