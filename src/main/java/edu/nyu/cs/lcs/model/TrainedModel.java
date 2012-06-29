/**
 * 
 */
package edu.nyu.cs.lcs.model;

import ij.ImagePlus;
import ij.gui.Roi;

import java.io.File;
import java.util.List;

import trainableSegmentation.WekaSegmentation;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;

import com.google.inject.Singleton;

import edu.nyu.cs.lcs.Image;
import edu.nyu.cs.lcs.classifications.Classification;

/**
 * Singleton representing the trained model.
 * 
 * @author Scot Dalton
 *
 */
@Singleton
public class TrainedModel {
	private WekaSegmentation wekaSegmentation;
	private static final Image transparentImage = 
		new Image("src/main/resources/META-INF/transparent.png");
	private Classifier classifier;
	private File classifierFile;

	public TrainedModel(File serializationDirectory) throws Exception {
		wekaSegmentation = 
			new WekaSegmentation(transparentImage.getImagePlus());
		classifierFile = 
			new File(serializationDirectory.getAbsolutePath() + "/lcs.model");
		if(classifierFile.exists()) {
			classifier = deserializeClassifier(classifierFile);
		} else {
			classifier = trainClassifier();
			serializeClassifier(classifierFile, classifier);
		}
	}
	
	/**
	 * Returns the an Image with the classifications of the given Image.
	 * @return 
	 * @throws Exception 
	 */
	public Image classifyImage(Image image) throws Exception {
		ImagePlus classifiedImagePlus = 
			wekaSegmentation.applyClassifier(image.getImagePlus(), 10, true);
		System.out.println(classifiedImagePlus.toString());
		return new Image(classifiedImagePlus.getBufferedImage());
//		// Get the likelihood of each classes 
//		// distribution[0] is the probability of being ARABLE
//		// distribution[1] is the probability of being CITY 
//		// distribution[2] is the probability of being DESERT 
//		// distribution[3] is the probability of being FOREST 
//		double[] distributions = 
//			classifier.distributionForInstance(instance);
//		for(int i=0; i<distributions.length; i++)
//			if(distributions[i] > confidenceThreshold)
//				return Classification.values()[i];
//		return Classification.UNKNOWN;
	}
	
	public String test() throws Exception {
		WekaSegmentation testWekaSegmentation = 
			new WekaSegmentation(transparentImage.getImagePlus());
		Evaluation eTest;
		eTest = new Evaluation(wekaSegmentation.getLoadedTrainingData());
		eTest.evaluateModel(classifier, testWekaSegmentation.getLoadedTrainingData());
		// Get the confusion matrix
//		confusionMatrix = eTest.confusionMatrix();
		// Print the result à la Weka explorer:
		return eTest.toSummaryString();
	}
	
	private Classifier trainClassifier() {
		for(Classification classification : Classification.values()) {
			wekaSegmentation.setClassLabel(classification.ordinal(), classification.name());
			if(classification.ordinal() >= wekaSegmentation.getNumOfClasses())
				wekaSegmentation.addClass();
			if(classification.isTrainable()) {
				List<Image> trainingImages = classification.getTrainingImages();
				for(int i = 0;  i < 10; i ++) {
					Image trainingImage = trainingImages.get(i);
//				for(Image trainingImage:trainingImages) {
					ImagePlus imagePlus = trainingImage.getImagePlus();
					Roi roi = new Roi(0, 0, imagePlus.getWidth(), imagePlus.getHeight());
					roi.setImage(imagePlus);
					int n = imagePlus.getCurrentSlice();
					int classNum = classification.ordinal();
					wekaSegmentation.addExample(classNum, roi, n);
				}
			}
		}
		wekaSegmentation.trainClassifier();
		return wekaSegmentation.getClassifier();
	}
	
	private void serializeClassifier(File classifierFile, 
			Classifier classifier) throws Exception {
		File serializationDirectory = classifierFile.getParentFile();
		if(!serializationDirectory.exists()) serializationDirectory.mkdirs();
		String classifierFileName = classifierFile.getAbsolutePath();
		wekaSegmentation.saveClassifier(classifierFileName);
	}
	
	private Classifier deserializeClassifier(
			File classifierFile) throws Exception {
		String classifierFileName = classifierFile.getAbsolutePath();
		wekaSegmentation.loadClassifier(classifierFileName);
		return wekaSegmentation.getClassifier();
	}
}