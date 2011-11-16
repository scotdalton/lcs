/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

//import edu.nyu.cs.sysproj.arability.features.DCTDispersion;
import edu.nyu.cs.sysproj.arability.features.DCTDownSample;
//import edu.nyu.cs.sysproj.arability.features.DCTMean;
import edu.nyu.cs.sysproj.arability.features.Feature;
import edu.nyu.cs.sysproj.arability.features.MeanPixel;

/**
 * ArabilityFeature defines the features that we're using to 
 * determine the classification.
 * (^\s*DCT_DOWN_SAMPLE_9_[0-9] \{\n)(.*\n)(.*\n)(.*\n)(.*\}\,)
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
//	DCT_MEAN {
//		Feature instantiate(Image image) {
//			return new DCTMean(image);
//		}
//	},
//	DCT_DISPERSION {
//		Feature instantiate(Image image) {
//			return new DCTDispersion(image);
//		}
//	},
	DCT_DOWN_SAMPLE_0_0 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 0, 0);
		}
	},
	DCT_DOWN_SAMPLE_1_0 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 1, 0);
		}
	},
	DCT_DOWN_SAMPLE_2_0 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 2, 0);
		}
	},
	DCT_DOWN_SAMPLE_3_0 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 3, 0);
		}
	},
	DCT_DOWN_SAMPLE_4_0 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 4, 0);
		}
	},
	DCT_DOWN_SAMPLE_5_0 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 5, 0);
		}
	},
	DCT_DOWN_SAMPLE_6_0 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 6, 0);
		}
	},
	DCT_DOWN_SAMPLE_7_0 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 7, 0);
		}
	},
	DCT_DOWN_SAMPLE_8_0 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 8, 0);
		}
	},
//	DCT_DOWN_SAMPLE_9_0 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 9, 0);
//		}
//	},
//	DCT_DOWN_SAMPLE_10_0 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 10, 0);
//		}
//	},
//	DCT_DOWN_SAMPLE_11_0 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 11, 0);
//		}
//	},
//	DCT_DOWN_SAMPLE_12_0 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 12, 0);
//		}
//	},
//	DCT_DOWN_SAMPLE_13_0 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 13, 0);
//		}
//	},
//	DCT_DOWN_SAMPLE_14_0 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 14, 0);
//		}
//	},
//	DCT_DOWN_SAMPLE_15_0 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 15, 0);
//		}
//	},
	DCT_DOWN_SAMPLE_0_1 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 0, 1);
		}
	},
	DCT_DOWN_SAMPLE_1_1 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 1, 1);
		}
	},
	DCT_DOWN_SAMPLE_2_1 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 2, 1);
		}
	},
	DCT_DOWN_SAMPLE_3_1 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 3, 1);
		}
	},
	DCT_DOWN_SAMPLE_4_1 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 4, 1);
		}
	},
	DCT_DOWN_SAMPLE_5_1 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 5, 1);
		}
	},
	DCT_DOWN_SAMPLE_6_1 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 6, 1);
		}
	},
	DCT_DOWN_SAMPLE_7_1 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 7, 1);
		}
	},
	DCT_DOWN_SAMPLE_8_1 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 8, 1);
		}
	},
//	DCT_DOWN_SAMPLE_9_1 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 9, 1);
//		}
//	},
//	DCT_DOWN_SAMPLE_10_1 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 10, 1);
//		}
//	},
//	DCT_DOWN_SAMPLE_11_1 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 11, 1);
//		}
//	},
//	DCT_DOWN_SAMPLE_12_1 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 12, 1);
//		}
//	},
//	DCT_DOWN_SAMPLE_13_1 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 13, 1);
//		}
//	},
//	DCT_DOWN_SAMPLE_14_1 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 14, 1);
//		}
//	},
//	DCT_DOWN_SAMPLE_15_1 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 15, 1);
//		}
//	},
	DCT_DOWN_SAMPLE_0_2 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 0, 2);
		}
	},
	DCT_DOWN_SAMPLE_1_2 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 1, 2);
		}
	},
	DCT_DOWN_SAMPLE_2_2 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 2, 2);
		}
	},
	DCT_DOWN_SAMPLE_3_2 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 3, 2);
		}
	},
	DCT_DOWN_SAMPLE_4_2 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 4, 2);
		}
	},
	DCT_DOWN_SAMPLE_5_2 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 5, 2);
		}
	},
	DCT_DOWN_SAMPLE_6_2 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 6, 2);
		}
	},
	DCT_DOWN_SAMPLE_7_2 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 7, 2);
		}
	},
	DCT_DOWN_SAMPLE_8_2 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 8, 2);
		}
	},
//	DCT_DOWN_SAMPLE_9_2 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 9, 2);
//		}
//	},
//	DCT_DOWN_SAMPLE_10_2 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 10, 2);
//		}
//	},
//	DCT_DOWN_SAMPLE_11_2 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 11, 2);
//		}
//	},
//	DCT_DOWN_SAMPLE_12_2 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 12, 2);
//		}
//	},
//	DCT_DOWN_SAMPLE_13_2 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 13, 2);
//		}
//	},
//	DCT_DOWN_SAMPLE_14_2 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 14, 2);
//		}
//	},
//	DCT_DOWN_SAMPLE_15_2 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 15, 2);
//		}
//	},
	DCT_DOWN_SAMPLE_0_3 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 0, 3);
		}
	},
	DCT_DOWN_SAMPLE_1_3 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 1, 3);
		}
	},
	DCT_DOWN_SAMPLE_2_3 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 2, 3);
		}
	},
	DCT_DOWN_SAMPLE_3_3 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 3, 3);
		}
	},
	DCT_DOWN_SAMPLE_4_3 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 4, 3);
		}
	},
	DCT_DOWN_SAMPLE_5_3 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 5, 3);
		}
	},
	DCT_DOWN_SAMPLE_6_3 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 6, 3);
		}
	},
	DCT_DOWN_SAMPLE_7_3 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 7, 3);
		}
	},
	DCT_DOWN_SAMPLE_8_3 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 8, 3);
		}
	},
//	DCT_DOWN_SAMPLE_9_3 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 9, 3);
//		}
//	},
//	DCT_DOWN_SAMPLE_10_3 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 10, 3);
//		}
//	},
//	DCT_DOWN_SAMPLE_11_3 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 11, 3);
//		}
//	},
//	DCT_DOWN_SAMPLE_12_3 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 12, 3);
//		}
//	},
//	DCT_DOWN_SAMPLE_13_3 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 13, 3);
//		}
//	},
//	DCT_DOWN_SAMPLE_14_3 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 14, 3);
//		}
//	},
//	DCT_DOWN_SAMPLE_15_3 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 15, 3);
//		}
//	},
	DCT_DOWN_SAMPLE_0_4 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 0, 4);
		}
	},
	DCT_DOWN_SAMPLE_1_4 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 1, 4);
		}
	},
	DCT_DOWN_SAMPLE_2_4 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 2, 4);
		}
	},
	DCT_DOWN_SAMPLE_3_4 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 3, 4);
		}
	},
	DCT_DOWN_SAMPLE_4_4 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 4, 4);
		}
	},
	DCT_DOWN_SAMPLE_5_4 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 5, 4);
		}
	},
	DCT_DOWN_SAMPLE_6_4 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 6, 4);
		}
	},
	DCT_DOWN_SAMPLE_7_4 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 7, 4);
		}
	},
	DCT_DOWN_SAMPLE_8_4 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 8, 4);
		}
	},
//	DCT_DOWN_SAMPLE_9_4 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 9, 4);
//		}
//	},
//	DCT_DOWN_SAMPLE_10_4 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 10, 4);
//		}
//	},
//	DCT_DOWN_SAMPLE_11_4 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 11, 4);
//		}
//	},
//	DCT_DOWN_SAMPLE_12_4 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 12, 4);
//		}
//	},
//	DCT_DOWN_SAMPLE_13_4 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 13, 4);
//		}
//	},
//	DCT_DOWN_SAMPLE_14_4 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 14, 4);
//		}
//	},
//	DCT_DOWN_SAMPLE_15_4 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 15, 4);
//		}
//	},
	DCT_DOWN_SAMPLE_0_5 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 0, 5);
		}
	},
	DCT_DOWN_SAMPLE_1_5 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 1, 5);
		}
	},
	DCT_DOWN_SAMPLE_2_5 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 2, 5);
		}
	},
	DCT_DOWN_SAMPLE_3_5 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 3, 5);
		}
	},
	DCT_DOWN_SAMPLE_4_5 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 4, 5);
		}
	},
	DCT_DOWN_SAMPLE_5_5 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 5, 5);
		}
	},
	DCT_DOWN_SAMPLE_6_5 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 6, 5);
		}
	},
	DCT_DOWN_SAMPLE_7_5 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 7, 5);
		}
	},
	DCT_DOWN_SAMPLE_8_5 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 8, 5);
		}
	},
//	DCT_DOWN_SAMPLE_9_5 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 9, 5);
//		}
//	},
//	DCT_DOWN_SAMPLE_10_5 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 10, 5);
//		}
//	},
//	DCT_DOWN_SAMPLE_11_5 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 11, 5);
//		}
//	},
//	DCT_DOWN_SAMPLE_12_5 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 12, 5);
//		}
//	},
//	DCT_DOWN_SAMPLE_13_5 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 13, 5);
//		}
//	},
//	DCT_DOWN_SAMPLE_14_5 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 14, 5);
//		}
//	},
//	DCT_DOWN_SAMPLE_15_5 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 15, 5);
//		}
//	},
	DCT_DOWN_SAMPLE_0_6 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 0, 6);
		}
	},
	DCT_DOWN_SAMPLE_1_6 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 1, 6);
		}
	},
	DCT_DOWN_SAMPLE_2_6 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 2, 6);
		}
	},
	DCT_DOWN_SAMPLE_3_6 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 3, 6);
		}
	},
	DCT_DOWN_SAMPLE_4_6 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 4, 6);
		}
	},
	DCT_DOWN_SAMPLE_5_6 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 5, 6);
		}
	},
	DCT_DOWN_SAMPLE_6_6 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 6, 6);
		}
	},
	DCT_DOWN_SAMPLE_7_6 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 7, 6);
		}
	},
	DCT_DOWN_SAMPLE_8_6 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 8, 6);
		}
	},
//	DCT_DOWN_SAMPLE_9_6 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 9, 6);
//		}
//	},
//	DCT_DOWN_SAMPLE_10_6 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 10, 6);
//		}
//	},
//	DCT_DOWN_SAMPLE_11_6 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 11, 6);
//		}
//	},
//	DCT_DOWN_SAMPLE_12_6 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 12, 6);
//		}
//	},
//	DCT_DOWN_SAMPLE_13_6 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 13, 6);
//		}
//	},
//	DCT_DOWN_SAMPLE_14_6 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 14, 6);
//		}
//	},
//	DCT_DOWN_SAMPLE_15_6 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 15, 6);
//		}
//	},
	DCT_DOWN_SAMPLE_0_7 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 0, 7);
		}
	},
	DCT_DOWN_SAMPLE_1_7 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 1, 7);
		}
	},
	DCT_DOWN_SAMPLE_2_7 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 2, 7);
		}
	},
	DCT_DOWN_SAMPLE_3_7 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 3, 7);
		}
	},
	DCT_DOWN_SAMPLE_4_7 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 4, 7);
		}
	},
	DCT_DOWN_SAMPLE_5_7 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 5, 7);
		}
	},
	DCT_DOWN_SAMPLE_6_7 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 6, 7);
		}
	},
	DCT_DOWN_SAMPLE_7_7 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 7, 7);
		}
	},
	DCT_DOWN_SAMPLE_8_7 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 8, 7);
		}
	},
//	DCT_DOWN_SAMPLE_9_7 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 9, 7);
//		}
//	},
//	DCT_DOWN_SAMPLE_10_7 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 10, 7);
//		}
//	},
//	DCT_DOWN_SAMPLE_11_7 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 11, 7);
//		}
//	},
//	DCT_DOWN_SAMPLE_12_7 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 12, 7);
//		}
//	},
//	DCT_DOWN_SAMPLE_13_7 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 13, 7);
//		}
//	},
//	DCT_DOWN_SAMPLE_14_7 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 14, 7);
//		}
//	},
//	DCT_DOWN_SAMPLE_15_7 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 15, 7);
//		}
//	},
	DCT_DOWN_SAMPLE_0_8 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 0, 8);
		}
	},
	DCT_DOWN_SAMPLE_1_8 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 1, 8);
		}
	},
	DCT_DOWN_SAMPLE_2_8 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 2, 8);
		}
	},
	DCT_DOWN_SAMPLE_3_8 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 3, 8);
		}
	},
	DCT_DOWN_SAMPLE_4_8 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 4, 8);
		}
	},
	DCT_DOWN_SAMPLE_5_8 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 5, 8);
		}
	},
	DCT_DOWN_SAMPLE_6_8 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 6, 8);
		}
	},
	DCT_DOWN_SAMPLE_7_8 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 7, 8);
		}
	},
	DCT_DOWN_SAMPLE_8_8 {
		Feature instantiate(Image image) {
			return new DCTDownSample(image, 8, 8);
		}
//	},
//	DCT_DOWN_SAMPLE_9_8 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 9, 8);
//		}
//	},
//	DCT_DOWN_SAMPLE_10_8 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 10, 8);
//		}
//	},
//	DCT_DOWN_SAMPLE_11_8 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 11, 8);
//		}
//	},
//	DCT_DOWN_SAMPLE_12_8 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 12, 8);
//		}
//	},
//	DCT_DOWN_SAMPLE_13_8 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 13, 8);
//		}
//	},
//	DCT_DOWN_SAMPLE_14_8 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 14, 8);
//		}
//	},
//	DCT_DOWN_SAMPLE_15_8 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 15, 8);
//		}
//	},
//	DCT_DOWN_SAMPLE_0_9 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 0, 9);
//		}
//	},
//	DCT_DOWN_SAMPLE_1_9 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 1, 9);
//		}
//	},
//	DCT_DOWN_SAMPLE_2_9 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 2, 9);
//		}
//	},
//	DCT_DOWN_SAMPLE_3_9 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 3, 9);
//		}
//	},
//	DCT_DOWN_SAMPLE_4_9 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 4, 9);
//		}
//	},
//	DCT_DOWN_SAMPLE_5_9 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 5, 9);
//		}
//	},
//	DCT_DOWN_SAMPLE_6_9 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 6, 9);
//		}
//	},
//	DCT_DOWN_SAMPLE_7_9 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 7, 9);
//		}
//	},
//	DCT_DOWN_SAMPLE_8_9 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 8, 9);
//		}
//	},
//	DCT_DOWN_SAMPLE_9_9 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 9, 9);
//		}
////	},
//	DCT_DOWN_SAMPLE_10_9 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 10, 9);
//		}
//	},
//	DCT_DOWN_SAMPLE_11_9 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 11, 9);
//		}
//	},
//	DCT_DOWN_SAMPLE_12_9 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 12, 9);
//		}
//	},
//	DCT_DOWN_SAMPLE_13_9 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 13, 9);
//		}
//	},
//	DCT_DOWN_SAMPLE_14_9 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 14, 9);
//		}
//	},
//	DCT_DOWN_SAMPLE_15_9 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 15, 9);
//		}
//	},
//	DCT_DOWN_SAMPLE_0_10 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 0, 10);
//		}
//	},
//	DCT_DOWN_SAMPLE_1_10 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 1, 10);
//		}
//	},
//	DCT_DOWN_SAMPLE_2_10 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 2, 10);
//		}
//	},
//	DCT_DOWN_SAMPLE_3_10 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 3, 10);
//		}
//	},
//	DCT_DOWN_SAMPLE_4_10 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 4, 10);
//		}
//	},
//	DCT_DOWN_SAMPLE_5_10 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 5, 10);
//		}
//	},
//	DCT_DOWN_SAMPLE_6_10 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 6, 10);
//		}
//	},
//	DCT_DOWN_SAMPLE_7_10 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 7, 10);
//		}
//	},
//	DCT_DOWN_SAMPLE_8_10 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 8, 10);
//		}
//	},
//	DCT_DOWN_SAMPLE_9_10 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 9, 10);
//		}
//	},
//	DCT_DOWN_SAMPLE_10_10 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 10, 10);
//		}
//	},
//	DCT_DOWN_SAMPLE_11_10 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 11, 10);
//		}
//	},
//	DCT_DOWN_SAMPLE_12_10 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 12, 10);
//		}
//	},
//	DCT_DOWN_SAMPLE_13_10 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 13, 10);
//		}
//	},
//	DCT_DOWN_SAMPLE_14_10 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 14, 10);
//		}
//	},
//	DCT_DOWN_SAMPLE_15_10 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 15, 10);
//		}
//	},
//	DCT_DOWN_SAMPLE_0_11 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 0, 11);
//		}
//	},
//	DCT_DOWN_SAMPLE_1_11 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 1, 11);
//		}
//	},
//	DCT_DOWN_SAMPLE_2_11 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 2, 11);
//		}
//	},
//	DCT_DOWN_SAMPLE_3_11 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 3, 11);
//		}
//	},
//	DCT_DOWN_SAMPLE_4_11 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 4, 11);
//		}
//	},
//	DCT_DOWN_SAMPLE_5_11 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 5, 11);
//		}
//	},
//	DCT_DOWN_SAMPLE_6_11 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 6, 11);
//		}
//	},
//	DCT_DOWN_SAMPLE_7_11 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 7, 11);
//		}
//	},
//	DCT_DOWN_SAMPLE_8_11 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 8, 11);
//		}
//	},
//	DCT_DOWN_SAMPLE_9_11 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 9, 11);
//		}
//	},
//	DCT_DOWN_SAMPLE_10_11 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 10, 11);
//		}
//	},
//	DCT_DOWN_SAMPLE_11_11 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 11, 11);
//		}
//	},
//	DCT_DOWN_SAMPLE_12_11 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 12, 11);
//		}
//	},
//	DCT_DOWN_SAMPLE_13_11 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 13, 11);
//		}
//	},
//	DCT_DOWN_SAMPLE_14_11 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 14, 11);
//		}
//	},
//	DCT_DOWN_SAMPLE_15_11 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 15, 11);
//		}
//	},
//	DCT_DOWN_SAMPLE_0_12 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 0, 12);
//		}
//	},
//	DCT_DOWN_SAMPLE_1_12 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 1, 12);
//		}
//	},
//	DCT_DOWN_SAMPLE_2_12 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 2, 12);
//		}
//	},
//	DCT_DOWN_SAMPLE_3_12 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 3, 12);
//		}
//	},
//	DCT_DOWN_SAMPLE_4_12 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 4, 12);
//		}
//	},
//	DCT_DOWN_SAMPLE_5_12 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 5, 12);
//		}
//	},
//	DCT_DOWN_SAMPLE_6_12 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 6, 12);
//		}
//	},
//	DCT_DOWN_SAMPLE_7_12 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 7, 12);
//		}
//	},
//	DCT_DOWN_SAMPLE_8_12 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 8, 12);
//		}
//	},
//	DCT_DOWN_SAMPLE_9_12 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 9, 12);
//		}
//	},
//	DCT_DOWN_SAMPLE_10_12 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 10, 12);
//		}
//	},
//	DCT_DOWN_SAMPLE_11_12 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 11, 12);
//		}
//	},
//	DCT_DOWN_SAMPLE_12_12 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 12, 12);
//		}
//	},
//	DCT_DOWN_SAMPLE_13_12 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 13, 12);
//		}
//	},
//	DCT_DOWN_SAMPLE_14_12 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 14, 12);
//		}
//	},
//	DCT_DOWN_SAMPLE_15_12 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 15, 12);
//		}
//	},
//	DCT_DOWN_SAMPLE_0_13 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 0, 13);
//		}
//	},
//	DCT_DOWN_SAMPLE_1_13 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 1, 13);
//		}
//	},
//	DCT_DOWN_SAMPLE_2_13 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 2, 13);
//		}
//	},
//	DCT_DOWN_SAMPLE_3_13 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 3, 13);
//		}
//	},
//	DCT_DOWN_SAMPLE_4_13 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 4, 13);
//		}
//	},
//	DCT_DOWN_SAMPLE_5_13 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 5, 13);
//		}
//	},
//	DCT_DOWN_SAMPLE_6_13 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 6, 13);
//		}
//	},
//	DCT_DOWN_SAMPLE_7_13 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 7, 13);
//		}
//	},
//	DCT_DOWN_SAMPLE_8_13 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 8, 13);
//		}
//	},
//	DCT_DOWN_SAMPLE_9_13 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 9, 13);
//		}
//	},
//	DCT_DOWN_SAMPLE_10_13 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 10, 13);
//		}
//	},
//	DCT_DOWN_SAMPLE_11_13 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 11, 13);
//		}
//	},
//	DCT_DOWN_SAMPLE_12_13 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 12, 13);
//		}
//	},
//	DCT_DOWN_SAMPLE_13_13 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 13, 13);
//		}
//	},
//	DCT_DOWN_SAMPLE_14_13 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 14, 13);
//		}
//	},
//	DCT_DOWN_SAMPLE_15_13 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 15, 13);
//		}
//	},
//	DCT_DOWN_SAMPLE_0_14 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 0, 14);
//		}
//	},
//	DCT_DOWN_SAMPLE_1_14 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 1, 14);
//		}
//	},
//	DCT_DOWN_SAMPLE_2_14 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 2, 14);
//		}
//	},
//	DCT_DOWN_SAMPLE_3_14 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 3, 14);
//		}
//	},
//	DCT_DOWN_SAMPLE_4_14 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 4, 14);
//		}
//	},
//	DCT_DOWN_SAMPLE_5_14 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 5, 14);
//		}
//	},
//	DCT_DOWN_SAMPLE_6_14 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 6, 14);
//		}
//	},
//	DCT_DOWN_SAMPLE_7_14 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 7, 14);
//		}
//	},
//	DCT_DOWN_SAMPLE_8_14 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 8, 14);
//		}
//	},
//	DCT_DOWN_SAMPLE_9_14 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 9, 14);
//		}
//	},
//	DCT_DOWN_SAMPLE_10_14 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 10, 14);
//		}
//	},
//	DCT_DOWN_SAMPLE_11_14 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 11, 14);
//		}
//	},
//	DCT_DOWN_SAMPLE_12_14 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 12, 14);
//		}
//	},
//	DCT_DOWN_SAMPLE_13_14 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 13, 14);
//		}
//	},
//	DCT_DOWN_SAMPLE_14_14 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 14, 14);
//		}
//	},
//	DCT_DOWN_SAMPLE_15_14 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 15, 14);
//		}
//	},
//	DCT_DOWN_SAMPLE_0_15 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 0, 15);
//		}
//	},
//	DCT_DOWN_SAMPLE_1_15 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 1, 15);
//		}
//	},
//	DCT_DOWN_SAMPLE_2_15 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 2, 15);
//		}
//	},
//	DCT_DOWN_SAMPLE_3_15 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 3, 15);
//		}
//	},
//	DCT_DOWN_SAMPLE_4_15 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 4, 15);
//		}
//	},
//	DCT_DOWN_SAMPLE_5_15 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 5, 15);
//		}
//	},
//	DCT_DOWN_SAMPLE_6_15 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 6, 15);
//		}
//	},
//	DCT_DOWN_SAMPLE_7_15 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 7, 15);
//		}
//	},
//	DCT_DOWN_SAMPLE_8_15 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 8, 15);
//		}
//	},
//	DCT_DOWN_SAMPLE_9_15 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 9, 15);
//		}
//	},
//	DCT_DOWN_SAMPLE_10_15 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 10, 15);
//		}
//	},
//	DCT_DOWN_SAMPLE_11_15 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 11, 15);
//		}
//	},
//	DCT_DOWN_SAMPLE_12_15 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 12, 15);
//		}
//	},
//	DCT_DOWN_SAMPLE_13_15 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 13, 15);
//		}
//	},
//	DCT_DOWN_SAMPLE_14_15 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 14, 15);
//		}
//	},
//	DCT_DOWN_SAMPLE_15_15 {
//		Feature instantiate(Image image) {
//			return new DCTDownSample(image, 15, 15);
//		}
	}
	;

	abstract Feature instantiate(Image image);
}
