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
		ImagePlus imagePlus = image1.getImagePlus();
		WekaSegmentation wekaSegmentation = new WekaSegmentation();
		for (Classification classification : Classification.values()) {
			wekaSegmentation.setClassLabel(classification.ordinal(), classification.name());
			if (classification.isTrainable()) {
				List<Image> trainingImages = classification.getTrainingImages();
				for (Image trainingImage : trainingImages) {
					wekaSegmentation.setTrainingImage(trainingImage.getImagePlus());
				}
			}
		}
		
		wekaSegmentation.saveData("tst/test.arff");
			
		for(String label: wekaSegmentation.getClassLabels()) 
			System.out.println(label);;
	}
}