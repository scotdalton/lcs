/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.Histogram;
import javax.media.jai.JAI;

/**
 * @author Scot Dalton
 *
 */
public enum ValidityCharacteristic {
	CLOUDY {
		boolean isValid(Image image) {
			Histogram histogram = getHistogram(image);
			double[] means = histogram.getMean();
			for (double mean: means)
				if (mean > Utility.CLOUDY_MEAN_THRESHOLD) 
					return false;
			return true;
		}
	},
	BLURRY {
		boolean isValid(Image image) {
			Histogram histogram = getHistogram(image);
			double[] standardDeviations = histogram.getStandardDeviation();
			for (double standardDeviation: standardDeviations)
				if (standardDeviation < 
						Utility.BLURRY_STANDARD_DEVIATION_THRESHOLD) 
					return false;
			return true;
		}
	},
	BRIGHT {
		boolean isValid(Image image) {
			Histogram histogram = getHistogram(image);
			double[] means = histogram.getMean();
			for (double mean: means)
				if (mean > Utility.BRIGHT_MEAN_THRESHOLD) 
					return false;
			return true;
		}
	};
	
	abstract boolean isValid(Image image);
	
	Histogram getHistogram(Image image) {
		// Set up the parameters for the Histogram object.
		int[] bins = {256, 256, 256};             // The number of bins.
		double[] low = {0.0D, 0.0D, 0.0D};        // The low value.
		double[] high = {256.0D, 256.0D, 256.0D}; // The high value.
		// Construct the Histogram object.
		Histogram histogram = new Histogram(bins, low, high);
		// Create the parameter block.
		ParameterBlock histogramPB = (new ParameterBlock()).
			addSource(image.getOriginalImage()).add(null).add(1).add(1);
	     // Perform the histogram operation.
		RenderedImage histogramImage = 
			(RenderedImage)JAI.create("histogram", histogramPB, null);
	     // Retrieve the histogram data.
	     histogram = (Histogram) histogramImage.getProperty("histogram");
		return histogram;
	}
}
