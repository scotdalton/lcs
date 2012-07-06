/**
 * 
 */
package edu.nyu.cs.lcs.model;

import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.Roi;

import java.awt.Point;
import java.io.File;
import java.util.List;
import java.util.Map;

import trainableSegmentation.WekaSegmentation;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;

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
	private Classifier classifier;
	private File serializationDirectory;
	private File classifierFile;
	/** flags of filters to be used */
	private static final boolean[] enabledFeatures = new boolean[] { 
		true, /* Gaussian_blur */
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
	
	/**
	 * Public constructor which uses the default WekaSegmentation classifier,
	 * RandomForest with 200 trees and 2 features used in random selection.
	 * @param serializationDirectory
	 * @throws Exception
	 */
	public TrainedModel(File serializationDirectory) throws Exception {
		this(serializationDirectory, null);
	}
	
	/**
	 * Public constructor that takes a classifier to use.
	 * @param serializationDirectory
	 * @param classifier
	 * @throws Exception
	 */
	public TrainedModel(File serializationDirectory, AbstractClassifier cls) throws Exception {
		this(serializationDirectory, cls, enabledFeatures);
	}
	
	
	/**
	 * Public constructor that takes a classifier to use.
	 * @param serializationDirectory
	 * @param classifier
	 * @throws Exception
	 */
	public TrainedModel(File serializationDirectory, AbstractClassifier cls, boolean[] enabledFeatures) throws Exception {
		this.serializationDirectory = serializationDirectory;
		wekaSegmentation = new WekaSegmentation(transparentImage.getImagePlus());
		wekaSegmentation.setEnabledFeatures(enabledFeatures);
		if (null != cls) 
			setClassifier(cls);
		classifierFile = 
			new File(getClassifierFileName());
		if (classifierFile.exists()) {
			classifier = deserializeClassifier(classifierFile);
		} else {
			trainClassifier();
			serializeClassifier(classifierFile, this.classifier);
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
		for (Classification classification : Classification.values()) {
			initWekaSegmentation(testWekaSegmentation, classification, classification.getTestingImages());
		}
		Evaluation eTest;
		eTest = new Evaluation(wekaSegmentation.getLoadedTrainingData());
		eTest.evaluateModel(classifier,
				testWekaSegmentation.getLoadedTrainingData());
		// Get the confusion matrix
		// confusionMatrix = eTest.confusionMatrix();
		// Print the result à la Weka explorer:
		return eTest.toSummaryString();
	}
	
	private void setClassifier(AbstractClassifier classifier) throws Exception {
		wekaSegmentation.setClassifier(classifier);
	}

	private String getClassifierFileName() {
		return serializationDirectory.getAbsolutePath() + "/" + 
			wekaSegmentation.getClassifier().getClass().getName() + ".model";
	}

	private void trainClassifier() {
		for (Classification classification : Classification.values()) {
			initWekaSegmentation(wekaSegmentation, classification, classification.getTrainingImages());
		}
		wekaSegmentation.trainClassifier();
		classifier = wekaSegmentation.getClassifier();
	}
	
	private void initWekaSegmentation(WekaSegmentation wekaSegmentation, Classification classification, List<Image> exampleImages) {
		if (classification.isTrainable()) {
			addClass(wekaSegmentation, classification.ordinal(), classification.name());
			for(Image exampleImage:exampleImages) {
				addExample(wekaSegmentation, classification.ordinal(), exampleImage);
			}
		}
	}
	
	private void addClass(WekaSegmentation wekaSegmentation, int classNum, String className) {
		wekaSegmentation.setClassLabel(classNum, className);
		if (classNum >= wekaSegmentation.getNumOfClasses())
			wekaSegmentation.addClass();
	}
	
	private void addExample(WekaSegmentation wekaSegmentation, int classNum, Image exampleImage) {
		ImagePlus imagePlus = exampleImage.getImagePlus();
		Roi roi = new Roi(0, 0, imagePlus.getWidth(),
				imagePlus.getHeight());
		roi.setImage(imagePlus);
		int n = imagePlus.getCurrentSlice();
		wekaSegmentation.addExample(classNum, roi, n);
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