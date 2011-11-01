/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;

/**
 * @author Scot Dalton
 *
 */
public class ArabilityModel {
	private static ArabilityModel arabilityModel;
	private Classifier classifier;
	private TrainingSet trainingSet;
	private double[][] confusionMatrix;
	
	private ArabilityModel() {
		this(TrainingSet.getTrainingSet());
	}
	
	private ArabilityModel(TrainingSet trainingSet) {
		this.trainingSet = trainingSet;
		this.classifier = new NaiveBayes();
		try {
			this.classifier.buildClassifier(trainingSet.getInstances());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArabilityModel getArabilityModel() {
		if (arabilityModel == null)
			arabilityModel = new ArabilityModel();
		return arabilityModel;
	}
	
	public String test() {
		TrainingSet testingSet = TrainingSet.getTestingSet();
		Evaluation eTest;
		try {
			eTest = new Evaluation(trainingSet.getInstances());
			eTest.evaluateModel(classifier, testingSet.getInstances());
			// Get the confusion matrix
			confusionMatrix = eTest.confusionMatrix();
			// Print the result à la Weka explorer:
			return eTest.toSummaryString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Test failed.";
	}

	/**
	 * @return the confusionMatrix
	 */
	public double[][] getConfusionMatrix() {
		return confusionMatrix;
	}
}
