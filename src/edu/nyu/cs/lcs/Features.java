/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import edu.nyu.cs.sysproj.arability.features.DCT;
import edu.nyu.cs.sysproj.arability.features.DCTDownSample;
import edu.nyu.cs.sysproj.arability.features.Feature;
import edu.nyu.cs.sysproj.arability.features.GradientMagnitude;
import edu.nyu.cs.sysproj.arability.features.MeanPixel;
import edu.nyu.cs.sysproj.arability.features.SURF;
import edu.nyu.cs.sysproj.arability.utility.Configuration;


/**
 * @author Scot Dalton
 *
 */
public class Features {
	private static Map<List<FeatureSet>, List<Feature>> features;
	private static int BANDS = 3;
	private static int DCT_HEIGHT = Configuration.CHOPPED_HEIGHT;
	private static int DCT_WIDTH = Configuration.CHOPPED_WIDTH;
	private static int DCT_DOWN_SAMPLE_HEIGHT = 
		Configuration.DOWN_SAMPLE_SQUARE_ROOT;
	private static int DCT_DOWN_SAMPLE_WIDTH = 
		Configuration.DOWN_SAMPLE_SQUARE_ROOT;
	private static int GRADIENT_MAGNITUDE_HEIGHT= 
		Configuration.CHOPPED_HEIGHT;
	private static int GRADIENT_MAGNITUDE_WIDTH = 
		Configuration.CHOPPED_WIDTH;
	private static int GRADIENT_MAGNITUDE_BANDS = 0;
	private static int NUM_SURFS = 1;
	private static int SURF_LENGTH = 64;
	public static List<FeatureSet> DEFAULT_FEATURE_SET = 
		Configuration.DEFAULT_FEATURE_SET;
	public enum FeatureSet{
		MEAN_PIXELS {
			@Override
			public List<Feature> getFeatures() {
				List<Feature> features = Lists.newArrayList();
				for(int i=0; i < BANDS; i++) {
					Feature feature = new MeanPixel();
					Map<String, Object> options = Maps.newHashMap();
					options.put("band", i);
					feature.setOptions(options);
					features.add(feature);
				}
				return features;
			}
		}, 
		DCT {
			@Override
			public List<Feature> getFeatures() {
				List<Feature> features = Lists.newArrayList();
				for(int x=0; x < DCT_WIDTH; x++) {
					for(int y=0; y < DCT_HEIGHT; y++) {
						Feature feature = new DCT();
						Map<String, Object> options = Maps.newHashMap();
						options.put("x", x);
						options.put("y", y);
						feature.setOptions(options);
						features.add(feature);
					}
				}
				return features;
			}
		}, 
		DCT_DOWN_SAMPLE {
			@Override
			public List<Feature> getFeatures() {
				List<Feature> features = Lists.newArrayList();
				for(int x=0; x < DCT_DOWN_SAMPLE_WIDTH; x++) {
					for(int y=0; y < DCT_DOWN_SAMPLE_HEIGHT; y++) {
						Feature feature = new DCTDownSample();
						Map<String, Object> options = Maps.newHashMap();
						options.put("x", x);
						options.put("y", y);
						feature.setOptions(options);
						features.add(feature);
					}
				}
				return features;
			}
		}, 
		GRADIENT_MAGNITUDE {
			@Override
			public List<Feature> getFeatures() {
				List<Feature> features = Lists.newArrayList();
				for(int x=0; x < GRADIENT_MAGNITUDE_WIDTH; x++) {
					for(int y=0; y < GRADIENT_MAGNITUDE_HEIGHT; y++) {
						for(int b=0; b < GRADIENT_MAGNITUDE_BANDS; b++) {
							Feature feature = new GradientMagnitude();
							Map<String, Object> options = Maps.newHashMap();
							options.put("x", x);
							options.put("y", y);
							options.put("b", b);
							feature.setOptions(options);
							features.add(feature);
						}
					}
				}
				return features;
			}
		}, 
		SURF {
			@Override
			public List<Feature> getFeatures() {
				List<Feature> features = Lists.newArrayList();
				for(int a=0; a < NUM_SURFS; a++) {
					for(int i=0; i < SURF_LENGTH; i++) {
						Feature feature = new SURF();
						Map<String, Object> options = Maps.newHashMap();
						options.put("a", a);
						options.put("i", i);
						feature.setOptions(options);
						features.add(feature);
					}
				}
				return features;
			}
		};
		
		public abstract List<Feature> getFeatures();
	};
	
	public static List<Feature> getFeatures(List<FeatureSet> featureSets) {
		if(features == null)
			features = Maps.newHashMap();
		if(!features.containsKey(featureSets)) {
			List<Feature> featuresInSets = Lists.newArrayList();
			for(FeatureSet featureSet: featureSets)
				featuresInSets.addAll(featureSet.getFeatures());
			features.put(featureSets, featuresInSets);
		}
		return features.get(featureSets);
	}
	
	public static float[] getFeatureValuesForImage(Image image, List<FeatureSet> featureSets) {
		List<Feature> features = Lists.newArrayList();
		for(Feature feature : getFeatures(featureSets)) {
			feature.setValue(image);
			features.add(feature);
		}
		float[] values = new float[features.size()];
		for(int i=0; i<features.size(); i++) 
			values[i] = features.get(i).getValue();
		return values;
	}
}
