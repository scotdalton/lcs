/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

/**
 * @author Scot Dalton
 *
 */
public enum ValidityCheck {
	CLOUDY {
		boolean isValid(Image image) {
			double[] means = image.getMeans();
			for (double mean: means)
				if (mean > Utility.CLOUDY_MEAN_THRESHOLD) 
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
						Utility.BLURRY_STANDARD_DEVIATION_THRESHOLD) 
					return false;
			return true;
		}
	},
	BRIGHT {
		boolean isValid(Image image) {
			double[] means = image.getMeans();
			for (double mean: means)
				if (mean > Utility.BRIGHT_MEAN_THRESHOLD) 
					return false;
			return true;
		}
	};
	
	abstract boolean isValid(Image image);
	
}
