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

import edu.nyu.cs.sysproj.google_earth.features.Feature;
import static edu.nyu.cs.sysproj.google_earth.Utility.*;

/**
 * @author Scot Dalton
 *
 */
public class TrainingSet {
	private static TrainingSet trainingSet;
	private Instances instances;
	
	private TrainingSet(List<KnownImage> knownImages, String name) {
		FastVector attributes = Utility.getAttributes();
		instances = 
			new Instances(name, attributes, knownImages.size());
		for(Image trainingImage: knownImages) {
			List<Feature> features = trainingImage.getFeatures();
			Instance instance = new Instance(features.size());
			// Class attribute is first
			try {
				instance.setValue(
					(Attribute)attributes.elementAt(0), 
						trainingImage.getClassification().toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i=1; i<=features.size(); i++)
				instance.setValue(
					(Attribute)attributes.elementAt(i), 
						features.get(i).getValue());      
			// add the instance
			instances.add(instance);
		}
	}
	
	public Instances getInstances() {
		return instances;
	}
	
	protected static TrainingSet getTrainingSet() throws Exception {
		if (trainingSet == null) {
			trainingSet = getTrainingSet(getTrainingImages(), "Training");
		}
		return trainingSet;
	}
	
	protected static TrainingSet getTestingSet() throws Exception {
		if (trainingSet == null) {
			trainingSet = getTrainingSet(getTestingImages(), "Testing");
		}
		return trainingSet;
	}
	
	protected static TrainingSet getTrainingSet(
			List<KnownImage> knownImages, String name) {
		trainingSet = new TrainingSet(knownImages, name);
		return trainingSet;
	}
	
	private static List<KnownImage> getTrainingImages() {
		List<KnownImage> knownImages = Lists.newArrayList();
		for(KnownImage arableTrainingImage : 
			getTrainingImages(ARABLE_TRAINING_IMAGE_PATH, 
				ArabilityClassification.ARABLE))
					knownImages.add(arableTrainingImage);
		for(KnownImage nonArableTrainingImage : 
			getTrainingImages(NON_ARABLE_TRAINING_IMAGE_PATH, 
				ArabilityClassification.NON_ARABLE))
					knownImages.add(nonArableTrainingImage);
		return knownImages;
	}
	
	private static List<KnownImage> getTestingImages() {
		List<KnownImage> knownImages = Lists.newArrayList();
		for(KnownImage arableTrainingImage : 
			getTrainingImages(ARABLE_TESTING_IMAGE_PATH, 
				ArabilityClassification.ARABLE))
					knownImages.add(arableTrainingImage);
		for(KnownImage nonArableTrainingImage : 
			getTrainingImages(NON_ARABLE_TESTING_IMAGE_PATH, 
				ArabilityClassification.NON_ARABLE))
					knownImages.add(nonArableTrainingImage);
		return knownImages;
	}
	
	private static List<KnownImage> getTrainingImages(
			String directoryName, ArabilityClassification classification) {
		List<KnownImage> knownImages = Lists.newArrayList();
		File directory = new File(directoryName);
		if (directory.isDirectory()) {
			String[] filenames = directory.list();
			for (String filename: filenames) {
				File file = 
					new File(directoryName + "/" + filename);
				if(file.isFile() && !file.isHidden()) {
					KnownImage image = 
						new KnownImage(file, classification);
					List<Image> choppedImages = image.getChoppedImages();
					for(Image choppedImage : choppedImages) {
						choppedImage.setClassification(classification);
						knownImages.add((KnownImage) choppedImage);
					}
				}
			}
		}
		return knownImages;
	}
}