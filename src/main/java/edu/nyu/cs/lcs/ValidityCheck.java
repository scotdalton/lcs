/**
 * 
 */
package edu.nyu.cs.lcs;

import java.io.File;

/**
 * Some images are invalid, so we have some checks validity.
 * 
 * @author Scot Dalton
 *
 */
public enum ValidityCheck {
	CLOUDY {
		boolean isValid(Image image) {
			double[] means = image.getMeans();
			for (double mean: means)
				if (mean > CLOUDY_MEAN_THRESHOLD) 
					return false;
			return true;
		}
	},
	BLURRY {
		boolean isValid(Image image) {
			double[] standardDeviations = 
				image.getStandardDeviations();
			for (double standardDeviation: standardDeviations)
				if (standardDeviation < 
						BLURRY_STANDARD_DEVIATION_THRESHOLD) 
					return false;
			return true;
		}
	},
	BRIGHT {
		boolean isValid(Image image) {
			double[] means = image.getMeans();
			for (double mean: means)
				if (mean > BRIGHT_MEAN_THRESHOLD) 
					return false;
			return true;
		}
	},
	SMALL {
		boolean isValid(Image image) {
			File file = image.getFile();
			// if file is smaller than 2MB, don't process it.
			if(file.length() < 2097152) return false;
			return true;
		}
	};
	
	public final static double CLOUDY_MEAN_THRESHOLD = 230.0;
	public final static double BLURRY_STANDARD_DEVIATION_THRESHOLD = 13.0;
	public final static double BRIGHT_MEAN_THRESHOLD = 200.0;
	abstract boolean isValid(Image image);
	
}
