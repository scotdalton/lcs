/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

import edu.nyu.cs.sysproj.arability.utility.Configuration;

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
				if (mean > Configuration.CLOUDY_MEAN_THRESHOLD) 
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
						Configuration.BLURRY_STANDARD_DEVIATION_THRESHOLD) 
					return false;
			return true;
		}
	},
	BRIGHT {
		boolean isValid(Image image) {
			double[] means = image.getMeans();
			for (double mean: means)
				if (mean > Configuration.BRIGHT_MEAN_THRESHOLD) 
					return false;
			return true;
		}
	};
	
	abstract boolean isValid(Image image);
	
}
