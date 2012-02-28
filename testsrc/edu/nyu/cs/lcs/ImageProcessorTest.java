/**
 * 
 */
package edu.nyu.cs.sysproj.arability;


import java.io.File;

import org.junit.Test;

import com.googlecode.javacv.cpp.opencv_core.IplImage;


/**
 * @author Scot Dalton
 *
 */
public class ImageProcessorTest {
	private static String IMAGE_PATH = 
		"/Users/dalton/Dropbox/MSIS/Systems Projects/google_earth/images";
	private static String EDGE_IMAGE_PATH = 
		"/Users/dalton/Dropbox/MSIS/Systems Projects/google_earth/images/edges";

	@Test
	public void testCreateEdgeImage() {
		File directory = new File(IMAGE_PATH);
//		File file = new File(IMAGE_PATH + "/" + "Tamale_Ghana_1_20111004.png" );
//		ImageProcessor imageProcessor = new ImageProcessor(file);
//		IplImage cannyImage = imageProcessor.getCannyEdgeImage();
//		imageProcessor.toFile(EDGE_IMAGE_PATH + "/canny_" + file.getName(), 
//			cannyImage);
//		IplImage harrisImage = imageProcessor.getHarrisEdgeImage();
//		imageProcessor.toFile(EDGE_IMAGE_PATH + "/harris_" + file.getName(), 
//			harrisImage);
//		IplImage eigenImage = imageProcessor.getEigenEdgeImage();
//		imageProcessor.toFile(EDGE_IMAGE_PATH + "/eigen_" + file.getName(), 
//			eigenImage);
		if (directory.isDirectory()) {
			String[] filenames = directory.list();
			for (String filename: filenames) {
				File file = new File(IMAGE_PATH + "/" + filename);
				if(file.isFile() && !file.isHidden()) {
					ImageProcessor imageProcessor = new ImageProcessor(file);
					IplImage edgeImage = imageProcessor.getEdgeImage();
					imageProcessor.toFile(
						EDGE_IMAGE_PATH + "/" + filename, edgeImage);
				}
			}
		}
	}
}