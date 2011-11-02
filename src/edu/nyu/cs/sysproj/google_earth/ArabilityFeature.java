/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import edu.nyu.cs.sysproj.google_earth.features.Feature;
import edu.nyu.cs.sysproj.google_earth.features.MeanBlueColor;
import edu.nyu.cs.sysproj.google_earth.features.MeanGreenColor;
import edu.nyu.cs.sysproj.google_earth.features.MeanRedColor;

/**
 * @author Scot Dalton
 *
 */
public enum ArabilityFeature {
	MeanRedColor {
		Feature getFeatureInstance(Image image) {
			return new MeanRedColor(image);
		}
	},
	MeanGreenColor {
		Feature getFeatureInstance(Image image) {
			return new MeanGreenColor(image);
		}
	},
	MeanBlueColor {
		Feature getFeatureInstance(Image image) {
			return new MeanBlueColor(image);
		}
	};

	abstract Feature getFeatureInstance(Image image);
}
