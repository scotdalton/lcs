/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import static edu.nyu.cs.sysproj.google_earth.TestUtility.getTestFile1;

import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.util.List;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.SnapshotImage;

import org.junit.Test;

import edu.nyu.cs.sysproj.google_earth.features.Texture;



/**
 * @author Scot Dalton
 *
 */
public class TextureTest {
	@Test
	public void testGetFeature() {
		File file = getTestFile1();
//		for(File file : getArableTrainingImageFiles()) {
			Image image = new Image(file);
			List<Image> choppedImages = image.getChoppedImages();
			for(Image choppedImage : choppedImages) {
				Texture texture = new Texture(choppedImage);
				int x = choppedImage.getMinX();
				int y = choppedImage.getMinY();
				String outputFile = 
					TestUtility.IMAGE_PATH + "/chop/testDCTChop"+x+"-"+y+".png";
				ParameterBlock fileStoreParams = new ParameterBlock();
				RenderedImage dct = texture.getDiscreteCosineTransform();
				int width = dct.getWidth();
				System.out.println(width);
				int height = dct.getHeight();
				System.out.println(height);
				int numBands = dct.getSampleModel().getNumBands();
				System.out.println(numBands);
				int dataType = dct.getSampleModel().getDataType();
				System.out.println(dataType);
				double[] dctData =
					dct.getData().getPixels(dct.getMinX(), dct.getMinY(), width, height, (double[])null);
				System.out.println(dctData.length);
				double[] pixels = new double[dctData.length];
//				RenderedImage dctImage = dct.
//				fileStoreParams.addSource(dctImage);
//				fileStoreParams.add(outputFile);
//				fileStoreParams.add("PNG");
//				JAI.create("filestore", fileStoreParams);
				System.out.println("File: " + file.getName());
				System.out.println("Min X: " + x);
				System.out.println("Min Y: " + y);
				System.out.println(
					"Wavelet Coefficients: " + 
						texture.getWaveletCoefficients().length);
				System.out.println(
					"DCT Coefficients: " + 
						texture.getDCTCoefficients().length);
				if(texture.getWaveletCoefficients().equals(texture.getDCTCoefficients())) {
					System.out.println("Equals");
				}
//				for(double waveletCoefficients: texture.getFeature())
//					System.out.println(
//				"\tWavelet Coefficients: " + waveletCoefficients);
			}
//		}
	}
}
