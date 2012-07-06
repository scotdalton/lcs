/**
 * 
 */
package edu.nyu.cs.lcs.model;

import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.Roi;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import trainableSegmentation.FeatureStack;
import trainableSegmentation.WekaSegmentation;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instance;
import weka.core.Instances;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
	private static final Image transparentImage = new Image(
			"src/main/resources/META-INF/transparent.png");
	private static final String classificationFilename = "classification.png";
	private File serializationDirectory;
	private Classifier classifier;
	private File classifierFile;
	/** flags of filters to be used */
	private boolean[] enabledFeatures = new boolean[] { true, /* Gaussian_blur */
	true, /* Sobel_filter */
	true, /* Hessian */
	true, /* Difference_of_gaussians */
	true, /* Membrane_projections */
	false, /* Variance */
	false, /* Mean */
	false, /* Minimum */
	false, /* Maximum */
	false, /* Median */
	false, /* Anisotropic_diffusion */
	false, /* Bilateral */
	false, /* Lipschitz */
	false, /* Kuwahara */
	false, /* Gabor */
	false, /* Derivatives */
	false, /* Laplacian */
	false, /* Structure */
	false, /* Entropy */
	false /* Neighbors */
	};

	public TrainedModel(File serializationDirectory) throws Exception {
		this.serializationDirectory = serializationDirectory;
		wekaSegmentation = new WekaSegmentation(transparentImage.getImagePlus());
		wekaSegmentation.setEnabledFeatures(enabledFeatures);
		classifierFile = new File(serializationDirectory.getAbsolutePath()
				+ "/lcs.model");
		if (classifierFile.exists()) {
			classifier = deserializeClassifier(classifierFile);
		} else {
			classifier = trainClassifier();
			serializeClassifier(classifierFile, classifier);
		}
	}

	/**
	 * Returns the an Image with the classifications of the given Image.
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<Point, Classification> classifyImage(Image image) throws Exception {
		ImagePlus classifiedImagePlus = 
			wekaSegmentation.applyClassifier(image.getImagePlus(), 10, false);
		ImageStack classifiedStack = classifiedImagePlus.getStack();
		Map<Point, Classification> classificationMap = Maps.newHashMap();
		for (int x = 0; x < classifiedImagePlus.getWidth(); x++)
			for (int y = 0; y < classifiedImagePlus.getHeight(); y++)
				for (int z = 1; z <= classifiedStack.getSize(); z++)
					for(int pixel : classifiedStack.getProcessor(z).getPixel(x, y, null))
						classificationMap.put(new Point(x, y), Classification.valueOf(wekaSegmentation.getClassLabel(pixel/(255/(wekaSegmentation.getNumOfClasses() - 1)))));
		return classificationMap;
	}

	public String test() throws Exception {
		WekaSegmentation testWekaSegmentation = new WekaSegmentation(
				transparentImage.getImagePlus());
		Evaluation eTest;
		eTest = new Evaluation(wekaSegmentation.getLoadedTrainingData());
		eTest.evaluateModel(classifier,
				testWekaSegmentation.getLoadedTrainingData());
		// Get the confusion matrix
		// confusionMatrix = eTest.confusionMatrix();
		// Print the result à la Weka explorer:
		return eTest.toSummaryString();
	}

	private Classifier trainClassifier() {
		for (Classification classification : Classification.values()) {
			if (classification.isTrainable()) {
				wekaSegmentation.setClassLabel(classification.ordinal(),
						classification.name());
				if (classification.ordinal() >= wekaSegmentation
						.getNumOfClasses())
					wekaSegmentation.addClass();
				List<Image> trainingImages = classification.getTrainingImages();
				for (int i = 0; i < 10; i++) {
					Image trainingImage = trainingImages.get(i);
					// for(Image trainingImage:trainingImages) {
					ImagePlus imagePlus = trainingImage.getImagePlus();
					Roi roi = new Roi(0, 0, imagePlus.getWidth(),
							imagePlus.getHeight());
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

	private void serializeClassifier(File classifierFile, Classifier classifier)
			throws Exception {
		File serializationDirectory = classifierFile.getParentFile();
		if (!serializationDirectory.exists())
			serializationDirectory.mkdirs();
		String classifierFileName = classifierFile.getAbsolutePath();
		wekaSegmentation.saveClassifier(classifierFileName);
	}

	private Classifier deserializeClassifier(File classifierFile)
			throws Exception {
		String classifierFileName = classifierFile.getAbsolutePath();
		wekaSegmentation.loadClassifier(classifierFileName);
		return wekaSegmentation.getClassifier();
	}
}