/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayesSimple;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;

/**
 * @author Scot Dalton
 *
 */
public class TrainedModel {
	private static TrainedModel trainedModel;
	private Classifier classifier;
	private TrainingSet trainingSet;
//	private double[][] confusionMatrix;

	/**
	 * Returns the ArabilityClassification of the given Image.
	 * @return 
	 * @throws Exception 
	 */
	public ArabilityClassification eval(Image image) throws Exception {
		FastVector attributes = Utility.getAttributes();
		float[] values = Utility.getAttributeValues(image);
		Instance instance = new Instance(attributes.size());
		for(int i=0; i<values.length; i++)
			instance.setValue((Attribute)attributes.elementAt(i), values[i]);      
		// Specify that the instance belong to the training set 
		// in order to inherit from the set description
		instance.setDataset(TrainingSet.getTrainingSet().getInstances());
		 // Get the likelihood of each classes 
		 // distribution[0] is the probability of being ARABLE
		 // distribution[1] is the probability of being NON_ARABLE 
		 double[] distribution = 
			 classifier.distributionForInstance(instance);
		 if(distribution[0] > Utility.CONFIDENCE_THRESHOLD) {
			 return ArabilityClassification.ARABLE;
		 } else if(distribution[1] > Utility.CONFIDENCE_THRESHOLD) {
			 return ArabilityClassification.NON_ARABLE;
		 } else {
			 return ArabilityClassification.UNKNOWN;
		 }
	}
	
	private TrainedModel() throws Exception {
		this(TrainingSet.getTrainingSet());
	}
	
	private TrainedModel(TrainingSet trainingSet) throws Exception {
		this.trainingSet = trainingSet;
		this.classifier = new NaiveBayesSimple();
		this.classifier.buildClassifier(trainingSet.getInstances());
	}
	
	public static TrainedModel getTrainedModel() throws Exception {
		if (trainedModel == null)
			trainedModel = new TrainedModel();
		return trainedModel;
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
}