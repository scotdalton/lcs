/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

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
	final static double CONFIDENCE_THRESHOLD = 60.0;
	
	protected static FastVector getAttributes() {
		FastVector classifications = 
			new FastVector(ArabilityClassification.values().length);
		for(ArabilityClassification classification : ArabilityClassification.values())
			classifications.addElement(classification.toString());
		FastVector attributes = 
			new FastVector(ArabilityFeature.values().length + 1);
		// Class attribute is first
		attributes.addElement(new Attribute("classification", classifications));
		for(ArabilityFeature arabilityFeature : ArabilityFeature.values())
			attributes.addElement(new Attribute(arabilityFeature.toString()));
		return attributes;
	}
}