/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import java.io.File;
import java.util.List;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import com.google.common.collect.Lists;

/**
 * @author Scot Dalton
 *
 */
public class TrainingSet {
	private final static String IMAGE_PATH = 
		"/Users/dalton/Dropbox/MSIS/Systems Projects/google_earth/images";
	private final static String TRAINING_IMAGE_PATH = 
		IMAGE_PATH+"/training";
	private final static String ARABLE_TRAINING_IMAGE_PATH = 
		TRAINING_IMAGE_PATH+"/arable";
	private final static String NON_ARABLE_TRAINING_IMAGE_PATH = 
		TRAINING_IMAGE_PATH+"/non_arable";
	private final static String TESTING_IMAGE_PATH = 
		IMAGE_PATH+"/testing";
	private final static String ARABLE_TESTING_IMAGE_PATH = 
		TESTING_IMAGE_PATH+"/arable";
	private final static String NON_ARABLE_TESTING_IMAGE_PATH = 
		TESTING_IMAGE_PATH+"/non_arable";
	private static TrainingSet trainingSet;
	private Instances instances;
	
	private TrainingSet(List<TrainingImage> trainingImages) {
		FastVector attributes = getAttributes();
		instances = 
			new Instances("Train", attributes, trainingImages.size());
		for(Image trainingImage: trainingImages) {
			List<Feature> features = trainingImage.getFeatures();
			Instance instance = new Instance(features.size());
			for(int i=0; i<features.size(); i++) {
				instance.setValue(
					(Attribute)attributes.elementAt(i), 
						features.get(i).getValue());      
			}
			// Class attribute is last
			instance.setValue(
				(Attribute)attributes.elementAt(attributes.size() - 1), 
					trainingImage.getClassification().toString());
			// add the instance
			instances.add(instance);
		}
	}
	
	public Instances getInstances() {
		return instances;
	}
	
	protected static TrainingSet getTrainingSet() {
		if (trainingSet == null) {
			trainingSet = getTrainingSet(getTrainingImages());
		}
		return trainingSet;
	}
	
	protected static TrainingSet getTestingSet() {
		if (trainingSet == null) {
			trainingSet = getTrainingSet(getTestingImages());
		}
		return trainingSet;
	}
	
	protected static TrainingSet getTrainingSet(List<TrainingImage> trainingImages) {
		trainingSet = new TrainingSet(trainingImages);
		return trainingSet;
	}
	
	private static List<TrainingImage> getTrainingImages() {
		List<TrainingImage> trainingImages = Lists.newArrayList();
		for(TrainingImage arableTrainingImage : 
			getTrainingImages(ARABLE_TRAINING_IMAGE_PATH, 
				ArabilityClassification.ARABLE))
					trainingImages.add(arableTrainingImage);
		for(TrainingImage nonArableTrainingImage : 
			getTrainingImages(NON_ARABLE_TRAINING_IMAGE_PATH, 
				ArabilityClassification.NON_ARABLE))
					trainingImages.add(nonArableTrainingImage);
		return trainingImages;
	}
	
	private static List<TrainingImage> getTestingImages() {
		List<TrainingImage> trainingImages = Lists.newArrayList();
		for(TrainingImage arableTrainingImage : 
			getTrainingImages(ARABLE_TESTING_IMAGE_PATH, 
				ArabilityClassification.ARABLE))
					trainingImages.add(arableTrainingImage);
		for(TrainingImage nonArableTrainingImage : 
			getTrainingImages(NON_ARABLE_TESTING_IMAGE_PATH, 
				ArabilityClassification.NON_ARABLE))
					trainingImages.add(nonArableTrainingImage);
		return trainingImages;
	}
	
	private static List<TrainingImage> getTrainingImages(
			String directoryName, ArabilityClassification classification) {
		List<TrainingImage> trainingImages = Lists.newArrayList();
		File directory = new File(directoryName);
		if (directory.isDirectory()) {
			String[] filenames = directory.list();
			for (String filename: filenames) {
				File file = 
					new File(directoryName + "/" + filename);
				if(file.isFile() && !file.isHidden()) {
					TrainingImage image = 
						new TrainingImage(file, classification);
					List<Image> choppedImages = image.getChoppedImages();
					for(Image choppedImage : choppedImages) {
						trainingImages.add((TrainingImage) choppedImage);
					}
				}
			}
		}
		return trainingImages;
	}

	private FastVector getAttributes() {
		// Declare image attributes (features)
		Attribute redMean = new Attribute("redMean");
		Attribute greenMean = new Attribute("greenMean");
		Attribute blueMean = new Attribute("blueMean");
//		Attribute texture = new Attribute("texture");
		// Declare the class attribute along with its values
		FastVector classValues = new FastVector(2);
		classValues.addElement(ArabilityClassification.ARABLE.toString());
		classValues.addElement(ArabilityClassification.NON_ARABLE.toString());
		Attribute classAttribute = new Attribute("theClass", classValues);
		// Declare the feature vector
		FastVector attributes = new FastVector(4);
		attributes.addElement(redMean);    
		attributes.addElement(greenMean);    
		attributes.addElement(blueMean);    
//		attributes.addElement(texture);    
		// Class attribute is last
		attributes.addElement(classAttribute);
		return attributes;
	}
}