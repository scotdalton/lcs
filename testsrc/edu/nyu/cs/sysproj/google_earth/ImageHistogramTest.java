/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import static edu.nyu.cs.sysproj.google_earth.TestUtility.getImageFiles;

import java.io.File;

import javax.media.jai.Histogram;

import org.junit.Test;

/**
 * @author Scot Dalton
 *
 */
public class ImageHistogramTest {

	@Test
	public void testCreateImageHistograms() {
		for(File file: getImageFiles()) {
			Image image = new Image(file);
//			ImageHistogram histogram = new ImageHistogram(image);
//			System.out.println("File: " + file.getName());
//			Histogram javaAiHistogram = histogram.getJavaAiHistogram();
//			for (int i=0; i < javaAiHistogram.getNumBands(); i++) {
//				System.out.println(javaAiHistogram.getBinSize(0, i));
//				System.out.println(javaAiHistogram.getBinSize(1, i));
//				System.out.println(javaAiHistogram.getBinSize(2, i));
//		     }

		}
	}

}
