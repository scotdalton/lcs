/**
 * 
 */
package edu.nyu.cs.sysproj.arability.utility;

import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.util.EnumSet;
import java.util.List;

import javax.media.jai.JAI;

import weka.core.Attribute;
import weka.core.FastVector;
import edu.nyu.cs.sysproj.arability.ArabilityClassification;
import edu.nyu.cs.sysproj.arability.ArabilityFeature;
import edu.nyu.cs.sysproj.arability.Image;
import edu.nyu.cs.sysproj.arability.features.Feature;

/**
 * Utilility class for defaults, etc.
 * 
 * @author Scot Dalton
 *
 */
public class Configuration {
	public final static String BASE_PATH = 
		"/Users/dalton/Dropbox/MSIS/SystemsProjects/google_earth";
	public final static String IMAGE_PATH = 
		BASE_PATH+"/images";
	public final static String TRAINING_IMAGE_PATH = 
		IMAGE_PATH+"/training";
	public final static String ARABLE_TRAINING_IMAGE_PATH = 
		TRAINING_IMAGE_PATH+"/arable";
	public final static String NON_ARABLE_TRAINING_IMAGE_PATH = 
		TRAINING_IMAGE_PATH+"/non_arable";
	public final static String CURATED_ARABLE_TRAINING_IMAGE_PATH = 
		ARABLE_TRAINING_IMAGE_PATH+"/curated";
	public final static String CURATED_NON_ARABLE_TRAINING_IMAGE_PATH = 
		NON_ARABLE_TRAINING_IMAGE_PATH+"/curated";
	public final static String TESTING_IMAGE_PATH = 
		IMAGE_PATH+"/testing";
	public final static String ARABLE_TESTING_IMAGE_PATH = 
		TESTING_IMAGE_PATH+"/arable";
	public final static String NON_ARABLE_TESTING_IMAGE_PATH = 
		TESTING_IMAGE_PATH+"/non_arable";
	public final static String CURATED_ARABLE_TESTING_IMAGE_PATH = 
		ARABLE_TESTING_IMAGE_PATH+"/curated";
	public final static String CURATED_NON_ARABLE_TESTING_IMAGE_PATH = 
		NON_ARABLE_TESTING_IMAGE_PATH+"/curated";
	public final static String KML_DIRECTORY = 
		BASE_PATH+"/kml";
	public final static double CONFIDENCE_THRESHOLD = 0.95;
	public final static double CLOUDY_MEAN_THRESHOLD = 230.0;
	public final static double BLURRY_STANDARD_DEVIATION_THRESHOLD = 13.0;
	public final static double BRIGHT_MEAN_THRESHOLD = 200.0;
	public final static float CROPPED_WIDTH = 1000;
	public final static float CROPPED_HEIGHT = 1000;
	public final static int CHOPPED_COLUMNS =10;
	public final static int CHOPPED_ROWS = 10;
//	public final static float DOWN_SAMPLE_SIZE = (float) 0.125;
	public final static int DOWN_SAMPLE_SQUARE_ROOT = 9;
	
	private static FastVector attributes;
	
	public static FastVector getAttributes() {
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
	
	public static float[] getAttributeValues(Image image) {
		List<Feature> features = image.getFeatures();
		float[] values = new float[features.size()];
		for(int i=0; i<features.size(); i++) 
			values[i] = features.get(i).getValue();
		return values;
	}

	public static void persistImage(String filename, RenderedImage source) {
		ParameterBlock fileStoreParams = (new ParameterBlock()).
			addSource(source).add(filename).add("PNG");
		JAI.create("filestore", fileStoreParams);
	}
}