/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

import static edu.nyu.cs.sysproj.arability.utility.Configuration.CLASSIFIER;
import static edu.nyu.cs.sysproj.arability.utility.Configuration.SERIALIZATION_DIRECTORY;
import static edu.nyu.cs.sysproj.arability.utility.Configuration.CLASSIFIER_OPTIONS;
import static edu.nyu.cs.sysproj.arability.utility.Configuration.CONFIDENCE_THRESHOLD;

import java.io.File;
import java.util.List;
import java.util.Map;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import edu.nyu.cs.sysproj.arability.Features.FeatureSet;
import edu.nyu.cs.sysproj.arability.features.Feature;

/**
 * Singleton representing the trained model.
 * 
 * @author Scot Dalton
 *
 */
public class TrainedModel {
	private static Map<TrainedModelKey, TrainedModel> trainedModels;
	private static String classifierName = CLASSIFIER;
	private static String[] classifierOptions = CLASSIFIER_OPTIONS;
	private static List<FeatureSet> featureSets = Features.DEFAULT_FEATURE_SET;
	private static TrainedModelKey trainedModelKey = 
		new TrainedModelKey(TrainedModel.classifierName, TrainedModel.featureSets);
	private Classifier classifier;
	private TrainingSet trainingSet;
	private FastVector attributes;
	private File classifierFile;
//	private double[][] confusionMatrix;

	public static TrainedModel getTrainedModel() throws Exception {
		if (trainedModels == null)
			trainedModels = Maps.newHashMap();
		if (!trainedModels.containsKey(trainedModelKey))
			trainedModels.put(trainedModelKey, new TrainedModel());
		return trainedModels.get(trainedModelKey);
	}
	
	protected static void reset(String classifierName, 
			String[] classifierOptions, List<FeatureSet> featureSets) {
		TrainedModel.classifierName = classifierName;
		TrainedModel.classifierOptions = classifierOptions;
		TrainedModel.featureSets = featureSets;
		TrainedModel.trainedModelKey = 
			new TrainedModelKey(TrainedModel.classifierName, TrainedModel.featureSets);
	}
	
	private TrainedModel() throws Exception {
		this(SERIALIZATION_DIRECTORY);
	}
	
	private TrainedModel(String serializationDirectory) throws Exception {
		String featuresDirectory = "";
		for(FeatureSet featureSet: TrainedModel.featureSets)
			featuresDirectory += featureSet.toString();
		classifierFile = 
			new File(serializationDirectory + "/" + featuresDirectory +
				"/" + TrainedModel.classifierName + ".model");
		this.trainingSet = getTrainingSet(classifierFile.getParent());
		attributes = trainingSet.getAttributes();
		if(classifierFile.exists()) {
			classifier = deserializeClassifier(classifierFile);
		} else {
			refresh();
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
			if(distributions[i] > CONFIDENCE_THRESHOLD)
				return Classification.values()[i];
		return Classification.UNKNOWN;
	}
	
	/**
	 * For testing purposes.
	 * @throws Exception
	 */
	protected void refresh() throws Exception {
		classifier = 
			Classifier.forName(TrainedModel.classifierName, TrainedModel.classifierOptions);
		classifier.buildClassifier(trainingSet.getInstances());
		serializeClassifier(classifierFile, classifier);
	}

	public String test() throws Exception {
		TrainingSet testingSet = getTestingSet(classifierFile.getParent());
		Evaluation eTest;
		eTest = new Evaluation(trainingSet.getInstances());
		eTest.evaluateModel(classifier, testingSet.getInstances());
		// Get the confusion matrix
//		confusionMatrix = eTest.confusionMatrix();
		// Print the result à la Weka explorer:
		return eTest.toSummaryString();
	}
	
	private TrainingSet getTrainingSet(String serializationDirectory) throws Exception {
		return new TrainingSet("train", serializationDirectory);
	}
	
	private TrainingSet getTestingSet(String serializationDirectory) throws Exception {
		return new TestingSet("test", serializationDirectory);
	}
	
	private void serializeClassifier(File classifierFile, 
			Classifier classifier) throws Exception {
		File serializationDirectory = classifierFile.getParentFile();
		if(!serializationDirectory.exists()) serializationDirectory.mkdirs();
		String classifierFileName = classifierFile.getAbsolutePath();
		SerializationHelper.write(classifierFileName, classifier);
	}
	
	private Classifier deserializeClassifier(
			File classifierFile) throws Exception {
		String classifierFileName = classifierFile.getAbsolutePath();
		return (Classifier) SerializationHelper.read(classifierFileName);
	}
	
	private class TestingSet extends TrainingSet {
		private TestingSet(String name,
				String serializationDirectory) throws Exception {
			super(name, serializationDirectory);
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
					String serializationDirectory) throws Exception {
			this.name = name;
			// Set attributes
			List<Feature> features = Features.getFeatures(TrainedModel.featureSets);
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
						Features.getFeatureValuesForImage(trainingImage, TrainedModel.featureSets);
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
