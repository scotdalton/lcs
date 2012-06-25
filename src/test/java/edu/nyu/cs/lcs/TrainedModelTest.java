/**
 * 
 */
package edu.nyu.cs.lcs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import ij.ImagePlus;

import java.io.File;
import java.util.List;

import org.junit.Test;

import trainableSegmentation.FeatureStack;
import trainableSegmentation.FeatureStackArray;
import trainableSegmentation.WekaSegmentation;

import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.nyu.cs.lcs.Features.FeatureSet;
import edu.nyu.cs.lcs.classifications.Classification;

/**
 * @author Scot Dalton
 *
 */
public class TrainedModelTest {
	/** expected membrane thickness */
	private int membraneThickness = 1;
	/** size of the patch to use to enhance the membranes */
	private int membranePatchSize = 19;

	/** minimum sigma to use on the filters */
	private float minimumSigma = 1f;
	/** maximum sigma to use on the filters */
	private float maximumSigma = 16f;

	/** use neighborhood flag */
	private boolean useNeighbors = false;
	/** flags of filters to be used */
	private boolean[] enabledFeatures = new boolean[]{
			true, 	/* Gaussian_blur */
			true, 	/* Sobel_filter */
			true, 	/* Hessian */
			true, 	/* Difference_of_gaussians */
			true, 	/* Membrane_projections */
			false, 	/* Variance */
			false, 	/* Mean */
			false, 	/* Minimum */
			false, 	/* Maximum */
			false, 	/* Median */
			false,	/* Anisotropic_diffusion */
			false, 	/* Bilateral */
			false, 	/* Lipschitz */
			false, 	/* Kuwahara */
			false,	/* Gabor */
			false, 	/* Derivatives */
			false, 	/* Laplacian */
			false,	/* Structure */
			false,	/* Entropy */
			false	/* Neighbors */
	};

	@Test
	public void testNewTrainedModel() throws Exception {
		double confidenceThreshold = 0.95;
		String classifierName = "weka.classifiers.lazy.IBk";
		List<String> classifierOptions = Lists.newArrayList();
		List<FeatureSet> featureSets = 
			Lists.newArrayList(FeatureSet.MEAN_PIXELS);
		File serializationDirectory = new File("./.classifiers");
		TrainedModel trainedModel = 
			new TrainedModel(classifierName, 
				classifierOptions, featureSets, 
					serializationDirectory, confidenceThreshold);
		System.out.println(trainedModel.test());
		Image croplandTestImage = 
			new UnknownImage(TestUtility.CROPLAND, trainedModel);
		assertEquals(Classification.CROPLAND, 
				trainedModel.classifyImage(croplandTestImage));
		Image desertTestImage = 
			new UnknownImage(TestUtility.DESERT, trainedModel);
		assertEquals(Classification.DESERT, 
				trainedModel.classifyImage(desertTestImage));
	}

	@Test
	public void testInjectedTrainedModel() throws Exception {
		Injector injector = 
			Guice.createInjector(new TrainedModelModule());
		TrainedModel trainedModel1 = 
			injector.getInstance(TrainedModel.class);
		TrainedModel trainedModel2 = 
			injector.getInstance(TrainedModel.class);
		assertSame(trainedModel1, trainedModel2);
	}
	
	@Test
	public void testFeatureStack() {
		Image image1 = new Image(TestUtility.IMAGE1);
//		ImagePlus imagePlus = image1.getImagePlus();
//		FeatureStack featureStack = new FeatureStack(imagePlus);
		
		FeatureStackArray featureStackArrary = 
			new FeatureStackArray(membranePatchSize, minimumSigma, 
				maximumSigma, useNeighbors, membraneThickness, 
					membranePatchSize, enabledFeatures);
		WekaSegmentation wekaSegmentation = new WekaSegmentation();
		System.out.println(wekaSegmentation.getNumOfClasses());
		for(Classification classification : Classification.values()) {
			if(classification.ordinal() >= wekaSegmentation.getNumOfClasses())
				wekaSegmentation.addClass();
			wekaSegmentation.setClassLabel(classification.ordinal(), classification.name());
			if(classification.isTrainable()) {
				List<Image> trainingImages = classification.getTrainingImages();
				for(int i = 0;  i < 4; i ++) {
					Image trainingImage = trainingImages.get(i);
					ImagePlus imagePlus = trainingImage.getImagePlus();
					wekaSegmentation.setTrainingImage(imagePlus);
					FeatureStack featureStack = 
						new FeatureStack(imagePlus);
					wekaSegmentation.addBinaryData(
						trainingImage.getImagePlus(), featureStack, 
							classification.name());
				}
			}
		}
		System.out.println(wekaSegmentation.getNumOfClasses());
		wekaSegmentation.saveData("tst/test.arff");
			
		for(String label: wekaSegmentation.getClassLabels()) 
			System.out.println(label);;
	}
}