/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import edu.nyu.cs.sysproj.google_earth.features.Feature;
import edu.nyu.cs.sysproj.google_earth.features.MeanPixel;
import edu.nyu.cs.sysproj.google_earth.features.Texture;

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
	},
	TextureMean {
		Feature instantiate(Image image) {
			return new Texture(image);
		}
	};

	abstract Feature instantiate(Image image);
}
