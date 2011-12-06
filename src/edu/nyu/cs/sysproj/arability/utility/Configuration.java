/**
 * 
 */
package edu.nyu.cs.sysproj.arability.utility;

/**
 * Utilility class for defaults, etc.
 * 
 * @author Scot Dalton
 *
 */
public class Configuration {
	public final static String BASE_PATH = 
//		"/home/std5/SystemsProjects/google_earth";
		"/Users/dalton/Dropbox/MSIS/SystemsProjects/google_earth";
//		"/Users/scotdalton/Dropbox/MSIS/SystemsProjects/google_earth";
	public final static String TMP_BASE_PATH = 
//		"/home/std5/tmp/";
		"/Users/dalton/tmp/";
//		"/Users/scotdalton/tmp";
	public final static String IMAGE_PATH = 
			BASE_PATH+"/images";
	public final static String TMP_IMAGE_PATH = 
			TMP_BASE_PATH+"/images";
	public final static String TRAINING_IMAGE_PATH = 
		IMAGE_PATH+"/training";
	public final static String ARABLE_TRAINING_IMAGE_PATH = 
		TRAINING_IMAGE_PATH+"/arable";
	public final static String DEVELOPED_TRAINING_IMAGE_PATH = 
		TRAINING_IMAGE_PATH+"/developed";
	public final static String DESERT_TRAINING_IMAGE_PATH = 
		TRAINING_IMAGE_PATH+"/desert";
	public final static String FOREST_TRAINING_IMAGE_PATH = 
		TRAINING_IMAGE_PATH+"/forest";
	public final static String CURATED_ARABLE_TRAINING_IMAGE_PATH = 
		ARABLE_TRAINING_IMAGE_PATH+"/curated";
	public final static String CURATED_DEVELOPED_TRAINING_IMAGE_PATH = 
		DEVELOPED_TRAINING_IMAGE_PATH+"/curated";
	public final static String CURATED_DESERT_TRAINING_IMAGE_PATH = 
		DESERT_TRAINING_IMAGE_PATH+"/curated";
	public final static String CURATED_FOREST_TRAINING_IMAGE_PATH = 
		FOREST_TRAINING_IMAGE_PATH+"/curated";
	public final static String TESTING_IMAGE_PATH = 
		IMAGE_PATH+"/testing";
	public final static String ARABLE_TESTING_IMAGE_PATH = 
		TESTING_IMAGE_PATH+"/arable";
	public final static String DEVELOPED_TESTING_IMAGE_PATH = 
		TESTING_IMAGE_PATH+"/developed";
	public final static String DESERT_TESTING_IMAGE_PATH = 
		TESTING_IMAGE_PATH+"/desert";
	public final static String FOREST_TESTING_IMAGE_PATH = 
		TESTING_IMAGE_PATH+"/forest";
	public final static String CURATED_ARABLE_TESTING_IMAGE_PATH = 
		ARABLE_TESTING_IMAGE_PATH+"/curated";
	public final static String CURATED_DEVELOPED_TESTING_IMAGE_PATH = 
		DEVELOPED_TESTING_IMAGE_PATH+"/curated";
	public final static String CURATED_DESERT_TESTING_IMAGE_PATH = 
		DESERT_TESTING_IMAGE_PATH+"/curated";
	public final static String CURATED_FOREST_TESTING_IMAGE_PATH = 
		FOREST_TESTING_IMAGE_PATH+"/curated";
	public final static String KML_DIRECTORY = 
		BASE_PATH+"/kml";
	public final static String TMP_KML_DIRECTORY = 
			TMP_BASE_PATH+"/kml";
	public final static String DATA_DIRECTORY = 
		BASE_PATH+"/data";
	public final static String TMP_DATA_DIRECTORY = 
		TMP_BASE_PATH+"/data";
	public final static double CONFIDENCE_THRESHOLD = 0.95;
	public final static double CLOUDY_MEAN_THRESHOLD = 230.0;
	public final static double BLURRY_STANDARD_DEVIATION_THRESHOLD = 13.0;
	public final static double BRIGHT_MEAN_THRESHOLD = 200.0;
	public final static float CROPPED_WIDTH = 1500;
	public final static float CROPPED_HEIGHT = 1000;
//	public final static int CHOPPED_COLUMNS = 10;
//	public final static int CHOPPED_ROWS = 10;
	public final static int CHOPPED_WIDTH = 100;
	public final static int CHOPPED_HEIGHT = 100;
//	public final static float DOWN_SAMPLE_SIZE = (float) 0.125;
	public final static int DOWN_SAMPLE_SQUARE_ROOT = 5;
	public final static String CLASSIFIER = 
		"weka.classifiers.bayes.NaiveBayes";
	public final static String[] CLASSIFIER_OPTIONS = {};
	public final static String CLASSIFIER_DIRECTORY = 
		TMP_BASE_PATH+"/arability/classifier";
	public final static String INSTANCES_DIRECTORY = 
		TMP_BASE_PATH+"/arability/instances";
}