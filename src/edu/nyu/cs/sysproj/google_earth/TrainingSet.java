/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import static edu.nyu.cs.sysproj.google_earth.Utility.*;

import java.io.File;
import java.util.List;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import com.google.common.collect.Lists;

/**
 * Singleton representing the training set.
 * 
 * @author Scot Dalton
 *
 */
public class TrainingSet {
	private static TrainingSet trainingSet;
	private Instances instances;
	
	private TrainingSet(List<Image> knownImages, String name) throws Exception {
		FastVector attributes = Utility.getAttributes();
		instances = 
			new Instances(name, attributes, knownImages.size());
		// Class attribute is last
		int classAttributeIndex = attributes.capacity() -1;
		instances.setClassIndex(classAttributeIndex);
		for(Image trainingImage: knownImages) {
			float[] values = Utility.getAttributeValues(trainingImage);
			Instance instance = new Instance(attributes.size());
			for(int i=0; i<values.length; i++)
				instance.setValue((Attribute)attributes.elementAt(i), values[i]);
			instance.setValue(
				(Attribute)attributes.elementAt(classAttributeIndex), 
					trainingImage.getClassification().toString());
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
			List<Image> knownImages, String name) throws Exception {
		trainingSet = new TrainingSet(knownImages, name);
		return trainingSet;
	}
	
	/**
	 * Protected for testing purposes.
	 * @return
	 */
	protected static List<Image> getTrainingImages() {
		List<Image> knownImages = Lists.newArrayList();
		for(Image arableTrainingImage : getArableTrainingImages())
			knownImages.add(arableTrainingImage);
		for(Image nonArableTrainingImage : getNonArableTrainingImages())
			knownImages.add(nonArableTrainingImage);
		return knownImages;
	}
	
	protected static List<Image> getArableTrainingImages() {
		return getKnownImages(CURATED_ARABLE_TRAINING_IMAGE_PATH, 
			ArabilityClassification.ARABLE);
	}
	
	protected static List<Image> getNonArableTrainingImages() {
		return getKnownImages(CURATED_NON_ARABLE_TRAINING_IMAGE_PATH, 
			ArabilityClassification.NON_ARABLE);
	}
	
	private static List<Image> getTestingImages() {
		List<Image> knownImages = Lists.newArrayList();
		for(Image arableTrainingImage : 
			getKnownImages(CURATED_ARABLE_TESTING_IMAGE_PATH, 
				ArabilityClassification.ARABLE))
					knownImages.add(arableTrainingImage);
		for(Image nonArableTrainingImage : 
			getKnownImages(CURATED_NON_ARABLE_TESTING_IMAGE_PATH, 
				ArabilityClassification.NON_ARABLE))
					knownImages.add(nonArableTrainingImage);
		return knownImages;
	}
	
	private static List<Image> getKnownImages(
			String directoryName, ArabilityClassification classification) {
		List<Image> knownImages = Lists.newArrayList();
		File directory = new File(directoryName);
		if (directory.isDirectory()) {
			String[] filenames = directory.list();
			for (String filename: filenames) {
				File file = 
					new File(directoryName + "/" + filename);
				if(file.isFile() && !file.isHidden()) {
					KnownImage image = 
						new KnownImage(file, classification);
					knownImages.add(image);
				}
			}
		}
		return knownImages;
	}
}