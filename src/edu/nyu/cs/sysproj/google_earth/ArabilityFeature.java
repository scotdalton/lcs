/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import edu.nyu.cs.sysproj.google_earth.features.DCTDownSample;
import edu.nyu.cs.sysproj.google_earth.features.Feature;
import edu.nyu.cs.sysproj.google_earth.features.MeanPixel;
import edu.nyu.cs.sysproj.google_earth.features.DCTMean;
import edu.nyu.cs.sysproj.google_earth.features.DCTDispersion;

/**
 * @author Scot Dalton
 *
 */
public enum ArabilityFeature {
	MEAN_PIXEL_BAND1 {
		Feature instantiate(Image image) {
			return new MeanPixel(image, 0);
		}
	},
	MEAN_PIXEL_BAND2 {
		Feature instantiate(Image image) {
			return new MeanPixel(image, 1);
		}
	},
	MEAN_PIXEL_BAND3 {
		Feature instantiate(Image image) {
			return new MeanPixel(image, 2);
		}
	},
	DCT_MEAN {
		Feature instantiate(Image image) {
			return new DCTMean(image);
		}
	},
	DCT_DISPERSION {
		Feature instantiate(Image image) {
			return new DCTDispersion(image);
		}
	},
	DCT_DOWN_SAMPLE_00 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 0, 0);
		}
	},
	DCT_DOWN_SAMPLE_10 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 1, 0);
		}
	},
	DCT_DOWN_SAMPLE_20 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 2, 0);
		}
	},
	DCT_DOWN_SAMPLE_30 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 3, 0);
		}
	},
	DCT_DOWN_SAMPLE_40 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 4, 0);
		}
	},
	DCT_DOWN_SAMPLE_50 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 5, 0);
		}
	},
	DCT_DOWN_SAMPLE_01 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 0, 1);
		}
	},
	DCT_DOWN_SAMPLE_11 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 1, 1);
		}
	},
	DCT_DOWN_SAMPLE_21 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 2, 1);
		}
	},
	DCT_DOWN_SAMPLE_31 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 3, 1);
		}
	},
	DCT_DOWN_SAMPLE_41 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 4, 1);
		}
	},
	DCT_DOWN_SAMPLE_51 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 5, 1);
		}
	},
	DCT_DOWN_SAMPLE_02 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 0, 2);
		}
	},
	DCT_DOWN_SAMPLE_12 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 1, 2);
		}
	},
	DCT_DOWN_SAMPLE_22 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 2, 2);
		}
	},
	DCT_DOWN_SAMPLE_32 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 3, 2);
		}
	},
	DCT_DOWN_SAMPLE_42 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 4, 2);
		}
	},
	DCT_DOWN_SAMPLE_52 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 5, 2);
		}
	},
	DCT_DOWN_SAMPLE_03 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 0, 3);
		}
	},
	DCT_DOWN_SAMPLE_13 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 1, 3);
		}
	},
	DCT_DOWN_SAMPLE_23 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 2, 3);
		}
	},
	DCT_DOWN_SAMPLE_33 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 3, 3);
		}
	},
	DCT_DOWN_SAMPLE_43 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 4, 3);
		}
	},
	DCT_DOWN_SAMPLE_53 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 5, 3);
		}
	},
	DCT_DOWN_SAMPLE_04 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 0, 4);
		}
	},
	DCT_DOWN_SAMPLE_14 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 1, 4);
		}
	},
	DCT_DOWN_SAMPLE_24 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 2, 4);
		}
	},
	DCT_DOWN_SAMPLE_34 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 3, 4);
		}
	},
	DCT_DOWN_SAMPLE_44 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 4, 4);
		}
	},
	DCT_DOWN_SAMPLE_54 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 5, 4);
		}
	},
	DCT_DOWN_SAMPLE_05 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 0, 5);
		}
	},
	DCT_DOWN_SAMPLE_15 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 1, 5);
		}
	},
	DCT_DOWN_SAMPLE_25 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 2, 5);
		}
	},
	DCT_DOWN_SAMPLE_35 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 3, 5);
		}
	},
	DCT_DOWN_SAMPLE_45 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 4, 5);
		}
	},
	DCT_DOWN_SAMPLE_55 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 5, 5);
		}
	}
	;

	abstract Feature instantiate(Image image);
}
