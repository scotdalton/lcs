/**
 * 
 */
package edu.nyu.cs.lcs;

//import static edu.nyu.cs.lcs.utility.Configuration.CLASSIFIER;
//import static edu.nyu.cs.lcs.utility.Configuration.CLASSIFIER_OPTIONS;
//import static edu.nyu.cs.lcs.utility.Configuration.CONFIDENCE_THRESHOLD;
//import static edu.nyu.cs.lcs.utility.Configuration.SERIALIZATION_DIRECTORY;

import java.io.File;
import java.util.List;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.nyu.cs.lcs.Features.FeatureSet;
import edu.nyu.cs.lcs.TrainedModelPropertiesModule.ClassifierName;
import edu.nyu.cs.lcs.TrainedModelPropertiesModule.ClassifierOptions;
import edu.nyu.cs.lcs.TrainedModelPropertiesModule.ConfidenceThreshold;
import edu.nyu.cs.lcs.TrainedModelPropertiesModule.SerializationDirectory;
import edu.nyu.cs.lcs.classifications.Classification;
import edu.nyu.cs.lcs.features.Feature;

/**
 * Singleton representing the trained model.
 * 
 * @author Scot Dalton
 *
 */
@Singleton
public class TrainedModel {
	private List<FeatureSet> featureSets; 
	private Classifier classifier;
	private TrainingSet trainingSet;
	private FastVector attributes;
	private double confidenceThreshold;
	private File classifierFile;
//	private double[][] confusionMatrix;

	@Inject
	public TrainedModel(@ClassifierName String classifierName,
			@ClassifierOptions List<String> classifierOptions,
			List<FeatureSet> featureSets, 
			@SerializationDirectory File serializationDirectory,
			@ConfidenceThreshold double confidenceThreshold) throws Exception {
		this.featureSets = featureSets;
		this.confidenceThreshold = confidenceThreshold;
		String featuresDirectory = "";
		for(FeatureSet featureSet: featureSets)
			featuresDirectory += featureSet.toString();
		classifierFile = 
			new File(serializationDirectory.getAbsolutePath() + 
				"/" + featuresDirectory + "/" + classifierName + ".model");
		this.trainingSet = getTrainingSet();
		attributes = trainingSet.getAttributes();
		System.out.println(classifierFile.toString());
		if(classifierFile.exists()) {
			classifier = deserializeClassifier(classifierFile);
		} else {
			classifier = 
				Classifier.forName(classifierName, classifierOptions.
					toArray(new String[0]));
			classifier.buildClassifier(trainingSet.getInstances());
			serializeClassifier(classifierFile, classifier);
		}
	}
	
	/**
	 * Returns the ArabilityClassification of the given Image.
	 * @return 
	 * @throws Exception 
	 */
	public Classification classifyImage(Image image) throws Exception {
		float[] values = Features.getFeatureValuesForImage(image, featureSets);
		Instance instance = new Instance(attributes.size());
		for(int i=0; i<values.length; i++){
			Attribute attribute = (Attribute)attributes.elementAt(i);
			instance.setValue(attribute, values[i]);      
		}
		// Specify that the instance belong to the training set 
		// in order to inherit from the set description
		instance.setDataset(trainingSet.getInstances());
		// Get the likelihood of each classes 
		// distribution[0] is the probability of being ARABLE
		// distribution[1] is the probability of being CITY 
		// distribution[2] is the probability of being DESERT 
		// distribution[3] is the probability of being FOREST 
		double[] distributions = 
			classifier.distributionForInstance(instance);
		for(int i=0; i<distributions.length; i++)
			if(distributions[i] > confidenceThreshold)
				return Classification.values()[i];
		return Classification.UNKNOWN;
	}
	
	public String test() throws Exception {
		TrainingSet testingSet = getTestingSet();
		Evaluation eTest;
		eTest = new Evaluation(trainingSet.getInstances());
		eTest.evaluateModel(classifier, testingSet.getInstances());
		// Get the confusion matrix
//		confusionMatrix = eTest.confusionMatrix();
		// Print the result à la Weka explorer:
		return eTest.toSummaryString();
	}
	
	private TrainingSet getTrainingSet() throws Exception {
		return new TrainingSet("train", classifierFile.getParent(), featureSets);
	}
	
	private TrainingSet getTestingSet() throws Exception {
		return new TestingSet("test", classifierFile.getParent(), featureSets);
	}
	
	private void serializeClassifier(File classifierFile, 
			Classifier classifier) throws Exception {
		System.out.println("Serializing classifier.");
		File serializationDirectory = classifierFile.getParentFile();
		if(!serializationDirectory.exists()) serializationDirectory.mkdirs();
		String classifierFileName = classifierFile.getAbsolutePath();
		SerializationHelper.write(classifierFileName, classifier);
	}
	
	private Classifier deserializeClassifier(
			File classifierFile) throws Exception {
		System.out.println("Deserializing classifier.");
		String classifierFileName = classifierFile.getAbsolutePath();
		return (Classifier) SerializationHelper.read(classifierFileName);
	}
	
	private class TestingSet extends TrainingSet {
		private TestingSet(String name,
				String serializationDirectory, 
				List<FeatureSet> featureSets) throws Exception {
			super(name, serializationDirectory, featureSets);
		}
		
		@Override
		protected List<Image> getKnownImages() {
			List<Image> knownImages = Lists.newArrayList();
			for(Classification classification : Classification.values())
				if(classification.isTrainable()) 
					knownImages.addAll(classification.getTestingImages());
			return knownImages;
		}
	}
	
	private class TrainingSet {
		private Instances instances;
		private String name;
		private File instancesFile;
		private FastVector attributes;
		
		private TrainingSet(String name,
					String serializationDirectory,
					List<FeatureSet> featureSets) throws Exception {
			this.name = name;
			// Set attributes
			List<Feature> features = Features.getFeatures(featureSets);
			attributes = new FastVector(features.size() + 1);
			for(int i=0; i<features.size(); i++)
				attributes.addElement(new Attribute(features.get(i).toString(), i));
			FastVector classifications = 
				new FastVector(Classification.values().length-1);
			for(Classification classification: Classification.values())
				if(classification.isTrainable())
					classifications.addElement(classification.toString());
			// Class attribute is last
			attributes.addElement(new Attribute("classification", classifications, features.size()));
			instancesFile = new File(serializationDirectory + "/" + 
				this.name +".set");
			if(instancesFile.exists()) {
				instances = deserializeInstances(instancesFile);
			} else {
				List<Image> knownImages = getKnownImages();
				instances = 
					new Instances(name, attributes, knownImages.size());
				// Class attribute is last
				int classAttributeIndex = attributes.capacity() -1;
				instances.setClassIndex(classAttributeIndex);
				for(Image trainingImage: knownImages) {
					float[] values = 
						Features.getFeatureValuesForImage(trainingImage, featureSets);
					Instance instance = new Instance(attributes.size());
					for(int i=0; i<values.length; i++)
						instance.setValue((Attribute)attributes.elementAt(i), values[i]);
					instance.setValue(
						(Attribute)attributes.elementAt(classAttributeIndex), 
							trainingImage.getClassification().toString());
					instances.add(instance);
				}
				serializeInstances(instancesFile, instances);
			}
		}
		
		private Instances getInstances() {
			return instances;
		}
		
		private FastVector getAttributes() {
			return attributes;
		}
		
		protected List<Image> getKnownImages() {
			List<Image> knownImages = Lists.newArrayList();
			for(Classification classification : Classification.values())
				if(classification.isTrainable()) 
					knownImages.addAll(classification.getTrainingImages());
			return knownImages;
		}
		
		private void serializeInstances(File instancesFile, 
				Instances instances) throws Exception {
			File serializationDirectory = instancesFile.getParentFile();
			if(!serializationDirectory.exists()) serializationDirectory.mkdirs();
			String instancesFileName = instancesFile.getAbsolutePath();
			SerializationHelper.write(instancesFileName, instances);
		}
		
		private Instances deserializeInstances(
				File instancesFile) throws Exception {
			String instancesFileName = instancesFile.getAbsolutePath();
			return (Instances) SerializationHelper.read(instancesFileName);
		}
	}
}