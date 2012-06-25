/**
 * 
 */
package edu.nyu.cs.lcs;

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
	};
	
	public final static double CLOUDY_MEAN_THRESHOLD = 230.0;
	public final static double BLURRY_STANDARD_DEVIATION_THRESHOLD = 13.0;
	public final static double BRIGHT_MEAN_THRESHOLD = 200.0;
	abstract boolean isValid(Image image);
	
}
