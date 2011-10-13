/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.Histogram;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

//import com.googlecode.javacv.cpp.opencv_imgproc.CvHistogram;


/**
 * @author Scot Dalton
 *
 */
public class ImageHistogram {
	private EarthImage earthImage;
	private Histogram javaAiHistogram;
//	private CvHistogram openCvHistogram;
	
	public ImageHistogram(EarthImage earthImage) {
		this.earthImage = earthImage;
		setJavaAiHistogram();
	}
	
	protected Histogram getJavaAiHistogram() {
		return javaAiHistogram;
	}
	
	private void setJavaAiHistogram() {
		RenderedOp javaAiImage = earthImage.getJavaAiImage();
		// Set up the parameters for the Histogram object.
		int[] bins = {256, 256, 256};             // The number of bins.
		double[] low = {0.0D, 0.0D, 0.0D};        // The low value.
		double[] high = {256.0D, 256.0D, 256.0D}; // The high value.
		// Create the parameter block.
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(javaAiImage); // Specify the source image
		pb.add(null); // No ROI
		pb.add(1); // Sampling
		pb.add(1); // periods
        pb.add(bins);
        pb.add(low);
        pb.add(high);
        // Perform the histogram operation.
		RenderedOp op = JAI.create("histogram", pb, null);
		// Retrieve the histogram data.
		javaAiHistogram = (Histogram) op.getProperty("histogram");
	}
}
