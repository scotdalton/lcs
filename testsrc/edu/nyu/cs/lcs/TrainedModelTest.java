/**
 * 
 */
package edu.nyu.cs.lcs;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

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
		Image croplandTestImage = 
			new Image("./images/testing/arable/curated/Haryana_India_1.png_1064_427.png");
		assertEquals(Classification.CROPLAND, 
				trainedModel.classifyImage(croplandTestImage));
		Image desertTestImage = 
			new Image("./images/testing/desert/curated/26.625_29.75.png_100_500.png");
		assertEquals(Classification.DESERT, 
				trainedModel.classifyImage(desertTestImage));
	}

	@Test
	public void testInjectedTrainedModel() throws Exception {
		Injector injector = 
			Guice.createInjector(new TrainedModelPropertiesModule());
		TrainedModel trainedModel1 = 
			injector.getInstance(TrainedModel.class);
		TrainedModel trainedModel2 = 
			injector.getInstance(TrainedModel.class);
		assertSame(trainedModel1, trainedModel2);
	}
}