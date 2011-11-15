/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import java.util.EnumSet;
import java.util.List;

import edu.nyu.cs.sysproj.google_earth.features.Feature;

import weka.core.Attribute;
import weka.core.FastVector;

/**
 * Utilility class for defaults, etc.
 * 
 * @author Scot Dalton
 *
 */
public class Utility {
	final static String BASE_PATH = 
		"/Users/dalton/Dropbox/MSIS/SystemsProjects/google_earth";
	final static String IMAGE_PATH = 
		BASE_PATH+"/images";
	final static String TRAINING_IMAGE_PATH = 
		IMAGE_PATH+"/training";
	final static String ARABLE_TRAINING_IMAGE_PATH = 
		TRAINING_IMAGE_PATH+"/arable";
	final static String NON_ARABLE_TRAINING_IMAGE_PATH = 
		TRAINING_IMAGE_PATH+"/non_arable";
	final static String CURATED_ARABLE_TRAINING_IMAGE_PATH = 
		ARABLE_TRAINING_IMAGE_PATH+"/curated";
	final static String CURATED_NON_ARABLE_TRAINING_IMAGE_PATH = 
		NON_ARABLE_TRAINING_IMAGE_PATH+"/curated";
	final static String TESTING_IMAGE_PATH = 
		IMAGE_PATH+"/testing";
	final static String ARABLE_TESTING_IMAGE_PATH = 
		TESTING_IMAGE_PATH+"/arable";
	final static String NON_ARABLE_TESTING_IMAGE_PATH = 
		TESTING_IMAGE_PATH+"/non_arable";
	final static String CURATED_ARABLE_TESTING_IMAGE_PATH = 
		ARABLE_TESTING_IMAGE_PATH+"/curated";
	final static String CURATED_NON_ARABLE_TESTING_IMAGE_PATH = 
		NON_ARABLE_TESTING_IMAGE_PATH+"/curated";
	final public static String KML_DIRECTORY = 
		BASE_PATH+"/kml";
	final static double CONFIDENCE_THRESHOLD = 0.95;
	final static double CLOUDY_MEAN_THRESHOLD = 230.0;
	final static double BLURRY_STANDARD_DEVIATION_THRESHOLD = 13.0;
	final static double BRIGHT_MEAN_THRESHOLD = 200.0;
	final static float CROPPED_WIDTH = 1000;
	final static float CROPPED_HEIGHT = 1000;
	final static int CHOPPED_COLUMNS =10;
	final static int CHOPPED_ROWS = 10;
	final static float DOWN_SAMPLE_SIZE = (float) 0.125;
	
	private static FastVector attributes;
	
	protected static FastVector getAttributes() {
		if(attributes == null) {
			attributes = 
				new FastVector((ArabilityFeature.values().length + 1));
			for(ArabilityFeature arabilityFeature : ArabilityFeature.values())
				attributes.addElement(new Attribute(arabilityFeature.toString()));
			FastVector classifications = 
				new FastVector(ArabilityClassification.values().length);
			EnumSet<ArabilityClassification> classificationValues = 
				EnumSet.range(ArabilityClassification.ARABLE,
					ArabilityClassification.NON_ARABLE);
			for(ArabilityClassification classification: classificationValues) {
				classifications.addElement(classification.toString());
			}
			// Class attribute is last
			attributes.addElement(new Attribute("classification", classifications));
		}
		return attributes;
	}
	
	protected static float[] getAttributeValues(Image image) {
		List<Feature> features = image.getFeatures();
		float[] values = new float[features.size()];
		for(int i=0; i<features.size(); i++) 
			values[i] = features.get(i).getValue();
		return values;
	}
}