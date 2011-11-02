/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import java.util.EnumSet;

import weka.core.Attribute;
import weka.core.FastVector;

/**
 * @author Scot Dalton
 *
 */
public class Utility {
	final static String IMAGE_PATH = 
		"/Users/dalton/Dropbox/MSIS/Systems Projects/google_earth/images";
	final static String TRAINING_IMAGE_PATH = 
		IMAGE_PATH+"/training";
	final static String ARABLE_TRAINING_IMAGE_PATH = 
		TRAINING_IMAGE_PATH+"/arable";
	final static String NON_ARABLE_TRAINING_IMAGE_PATH = 
		TRAINING_IMAGE_PATH+"/non_arable";
	final static String TESTING_IMAGE_PATH = 
		IMAGE_PATH+"/testing";
	final static String ARABLE_TESTING_IMAGE_PATH = 
		TESTING_IMAGE_PATH+"/arable";
	final static String NON_ARABLE_TESTING_IMAGE_PATH = 
		TESTING_IMAGE_PATH+"/non_arable";
	final static double CONFIDENCE_THRESHOLD = 0.60;
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
}