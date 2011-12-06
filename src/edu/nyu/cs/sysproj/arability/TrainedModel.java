/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

import static edu.nyu.cs.sysproj.arability.utility.Configuration.CLASSIFIER;
import static edu.nyu.cs.sysproj.arability.utility.Configuration.CLASSIFIER_DIRECTORY;
import static edu.nyu.cs.sysproj.arability.utility.Configuration.CLASSIFIER_OPTIONS;
import static edu.nyu.cs.sysproj.arability.utility.Configuration.CONFIDENCE_THRESHOLD;

import java.io.File;
import java.util.Map;

import com.google.common.collect.Maps;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.SerializationHelper;

/**
 * Singleton representing the trained model.
 * 
 * @author Scot Dalton
 *
 */
public class TrainedModel {
	private static Map<String, TrainedModel> trainedModels;
	private Classifier classifier;
	private TrainingSet trainingSet;
	private FastVector attributes;
	private String classifierName;
	private String[] classifierOptions;
	private File classifierFile;
//	private double[][] confusionMatrix;

	/**
	 * Returns the ArabilityClassification of the given Image.
	 * @return 
	 * @throws Exception 
	 */
	public Classification classifyImage(Image image) throws Exception {
		float[] values = Features.getFeatureValuesForImage(image);
		Instance instance = new Instance(attributes.size());
		for(int i=0; i<values.length; i++){
			Attribute attribute = (Attribute)attributes.elementAt(i);
			instance.setValue(attribute, values[i]);      
		}
		// Specify that the instance belong to the training set 
		// in order to inherit from the set description
		instance.setDataset(TrainingSet.getTrainingSet().getInstances());
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
	
	private TrainedModel(String classifierName, 
			String[] classifierOptions) throws Exception {
		this(classifierName, classifierOptions, CLASSIFIER_DIRECTORY);
	}
	
	private TrainedModel(String classifierName, String[] classifierOptions, 
			String serializationDirectory) throws Exception {
		this.classifierName = classifierName;
		this.classifierOptions = classifierOptions;
		this.trainingSet = TrainingSet.getTrainingSet();
		attributes = trainingSet.getAttributes();
		classifierFile = 
			new File(serializationDirectory + "/" + 
				classifierName + ".model");
		if(classifierFile.exists()) {
			classifier = deserializeClassifier(classifierFile);
		} else {
			refresh();
		}
	}
	
	protected void refresh() throws Exception {
		classifier = 
			Classifier.forName(classifierName, classifierOptions);
		classifier.buildClassifier(trainingSet.getInstances());
		serializeClassifier(classifierFile, classifier);
	}

	public static TrainedModel getTrainedModel() throws Exception {
		return getTrainedModel(CLASSIFIER, CLASSIFIER_OPTIONS);
	}
	
	public static TrainedModel getTrainedModel(String classifierName, 
			String[] classifierOptions) throws Exception {
		if (trainedModels == null)
			trainedModels = Maps.newHashMap();
		if (!trainedModels.containsKey(classifierName))
			trainedModels.put(classifierName, new TrainedModel(classifierName, classifierOptions));
		return trainedModels.get(classifierName);
	}
	
	public String test() throws Exception {
		TrainingSet testingSet = TrainingSet.getTestingSet();
		Evaluation eTest;
		eTest = new Evaluation(trainingSet.getInstances());
		eTest.evaluateModel(classifier, testingSet.getInstances());
		// Get the confusion matrix
//		confusionMatrix = eTest.confusionMatrix();
		// Print the result à la Weka explorer:
		return eTest.toSummaryString();
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
}