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
	
	private TrainingSet(List<Image> knownImages, String name) throws Exception {
		FastVector attributes = Utility.getAttributes();
		instances = 
			new Instances(name, attributes, knownImages.size());
		for(Image trainingImage: knownImages) {
			List<Feature> features = trainingImage.getFeatures();
			Instance instance = new Instance(attributes.size());
			for(int i=0; i<features.size(); i++)
				instance.setValue((Attribute)attributes.elementAt(i), 
					features.get(i).getValue());      
			// Class attribute is last
			int classAttributeIndex = attributes.capacity() -1;
			instance.setValue(
				(Attribute)attributes.elementAt(classAttributeIndex), 
					trainingImage.getClassification().toString());
			instances.setClassIndex(classAttributeIndex);
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
	
	private static List<Image> getTrainingImages() {
		List<Image> knownImages = Lists.newArrayList();
		for(Image arableTrainingImage : 
			getKnownImages(ARABLE_TRAINING_IMAGE_PATH, 
				ArabilityClassification.ARABLE))
					knownImages.add(arableTrainingImage);
		for(Image nonArableTrainingImage : 
			getKnownImages(NON_ARABLE_TRAINING_IMAGE_PATH, 
				ArabilityClassification.NON_ARABLE))
					knownImages.add(nonArableTrainingImage);
		return knownImages;
	}
	
	private static List<Image> getTestingImages() {
		List<Image> knownImages = Lists.newArrayList();
		for(Image arableTrainingImage : 
			getKnownImages(ARABLE_TESTING_IMAGE_PATH, 
				ArabilityClassification.ARABLE))
					knownImages.add(arableTrainingImage);
		for(Image nonArableTrainingImage : 
			getKnownImages(NON_ARABLE_TESTING_IMAGE_PATH, 
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
			int i = 0;
			for (String filename: filenames) {
				File file = 
					new File(directoryName + "/" + filename);
				if(file.isFile() && !file.isHidden()) {
					i++;
					if(i > 6) break;
					KnownImage image = 
						new KnownImage(file, classification);
					List<Image> choppedImages = image.getChoppedImages();
					for(Image choppedImage : choppedImages)
						knownImages.add((Image) choppedImage);
				}
			}
		}
		return knownImages;
	}
}