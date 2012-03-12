/**
 * 
 */
package edu.nyu.cs.lcs;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;

import edu.nyu.cs.lcs.FeaturesPropertiesModule.DCTDownSampleXs;
import edu.nyu.cs.lcs.FeaturesPropertiesModule.DCTDownSampleYs;
import edu.nyu.cs.lcs.FeaturesPropertiesModule.DCTHeight;
import edu.nyu.cs.lcs.FeaturesPropertiesModule.DCTWidth;
import edu.nyu.cs.lcs.FeaturesPropertiesModule.GradientMagnitudeBands;
import edu.nyu.cs.lcs.FeaturesPropertiesModule.GradientMagnitudeDownSampleXs;
import edu.nyu.cs.lcs.FeaturesPropertiesModule.GradientMagnitudeDownSampleYs;
import edu.nyu.cs.lcs.FeaturesPropertiesModule.GradientMagnitudeHeight;
import edu.nyu.cs.lcs.FeaturesPropertiesModule.GradientMagnitudeWidth;
import edu.nyu.cs.lcs.FeaturesPropertiesModule.MeanPixelBands;
import edu.nyu.cs.lcs.FeaturesPropertiesModule.NumSurfs;
import edu.nyu.cs.lcs.FeaturesPropertiesModule.SurfLength;
import edu.nyu.cs.lcs.features.DCT;
import edu.nyu.cs.lcs.features.DCTDownSample;
import edu.nyu.cs.lcs.features.Feature;
import edu.nyu.cs.lcs.features.GradientMagnitude;
import edu.nyu.cs.lcs.features.MeanPixel;
import edu.nyu.cs.lcs.features.SURF;


/**
 * @author Scot Dalton
 *
 */
public class Features {
	private static Map<List<FeatureSet>, List<Feature>> features;
	private static Injector injector = 
		Guice.createInjector(new FeaturesPropertiesModule());
	public enum FeatureSet{
		MEAN_PIXELS {
			@Override
			public List<Feature> getFeatures() {
				List<Feature> features = Lists.newArrayList();
				int bands = injector.getInstance(Key.
					get(Integer.class, MeanPixelBands.class));
				for(int i=0; i < bands; i++) {
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
				int width = injector.getInstance(Key.
					get(Integer.class, DCTWidth.class));
				int height = injector.getInstance(Key.
					get(Integer.class, DCTHeight.class));
				for(int x=0; x < width; x++) {
					for(int y=0; y < height; y++) {
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
				int width = injector.getInstance(Key.
					get(Integer.class, DCTDownSampleXs.class));
				int height = injector.getInstance(Key.
					get(Integer.class, DCTDownSampleYs.class));
				for(int x=0; x < width; x++) {
					for(int y=0; y < height; y++) {
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
				int width = injector.getInstance(Key.
					get(Integer.class, GradientMagnitudeWidth.class));
				int height = injector.getInstance(Key.
					get(Integer.class, GradientMagnitudeHeight.class));
				int bands = injector.getInstance(Key.
					get(Integer.class, GradientMagnitudeBands.class));
				for(int x=0; x < width; x++) {
					for(int y=0; y < height; y++) {
						for(int b=0; b < bands; b++) {
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
		GRADIENT_MAGNITUDE_DOWNSAMPLE {
			@Override
			public List<Feature> getFeatures() {
				List<Feature> features = Lists.newArrayList();
				int width = injector.getInstance(Key.
					get(Integer.class, GradientMagnitudeDownSampleXs.class));
				int height = injector.getInstance(Key.
					get(Integer.class, GradientMagnitudeDownSampleYs.class));
				int bands = injector.getInstance(Key.
					get(Integer.class, GradientMagnitudeBands.class));
				for(int x=0; x < width; x++) {
					for(int y=0; y < height; y++) {
						for(int b=0; b < bands; b++) {
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
				int num = injector.getInstance(Key.
					get(Integer.class, NumSurfs.class));
				int length = injector.getInstance(Key.
					get(Integer.class, SurfLength.class));
				for(int a=0; a < num; a++) {
					for(int i=0; i < length; i++) {
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