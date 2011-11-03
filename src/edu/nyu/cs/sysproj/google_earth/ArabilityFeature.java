/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import edu.nyu.cs.sysproj.google_earth.features.Feature;
import edu.nyu.cs.sysproj.google_earth.features.MeanPixel;

/**
 * @author Scot Dalton
 *
 */
public enum ArabilityFeature {
	MeanPixelBand1 {
		Feature instantiate(Image image) {
			return new MeanPixel(image, 0);
		}
	},
	MeanPixelBand2 {
		Feature instantiate(Image image) {
			return new MeanPixel(image, 1);
		}
	},
	MeanPixelBand3 {
		Feature instantiate(Image image) {
			return new MeanPixel(image, 2);
		}
	};

	abstract Feature instantiate(Image image);
}
