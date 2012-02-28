/**
 * 
 */
package edu.nyu.cs.lcs;

import static edu.nyu.cs.lcs.TestUtility.getCuratedArableTestingImageFiles;
import static edu.nyu.cs.lcs.TestUtility.getCuratedDesertTestingImageFiles;
import static edu.nyu.cs.lcs.TestUtility.getCuratedDevelopedTestingImageFiles;
import static edu.nyu.cs.lcs.TestUtility.getCuratedForestTestingImageFiles;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import edu.nyu.cs.lcs.Image;
import edu.nyu.cs.lcs.LandClassification;
import edu.nyu.cs.lcs.TrainedModel;
import edu.nyu.cs.lcs.Features.FeatureSet;

/**
 * @author Scot Dalton
 *
 */
public class TrainedModelTest {
	@Test
	public void testTrainedModel() throws Exception {
		String classifierName = "weka.classifiers.trees.J48";
		String[] classifierOptions = {};
//		FeatureSet[] featureSets = Features.DEFAULT_FEATURE_SET;
		List<FeatureSet> featureSets = Lists.newArrayList(FeatureSet.MEAN_PIXELS, FeatureSet.SURF);
		TrainedModel.reset(classifierName, classifierOptions, featureSets);
		long startGet = Calendar.getInstance().getTimeInMillis();
		TrainedModel trainedModel = TrainedModel.getTrainedModel();
		long endGet = Calendar.getInstance().getTimeInMillis();
		System.out.println((endGet - startGet)/(double)1000);
//		long startRefresh = Calendar.getInstance().getTimeInMillis();
//		trainedModel.refresh();
//		long endRefresh = Calendar.getInstance().getTimeInMillis();
//		System.out.println((endRefresh- startRefresh)/(double)1000);
		System.out.println(trainedModel.test());
	}
	
//	@Test
	public void testIncorrectMatches() throws Exception {
		TrainedModel trainedModel = TrainedModel.getTrainedModel();
//		Runtime runtime = Runtime.getRuntime();
		String[] commands = {"open", "-a", "Preview", ""};
		int incorrectArableCount = 0;
		int incorrectDevelopedCount = 0;
		int incorrectDesertCount = 0;
		int incorrectForestCount = 0;
		System.out.println("Incorrect arable images:");
		for(File file : getCuratedArableTestingImageFiles()) {
			Image image = new Image(file);
			LandClassification landClassification = 
				trainedModel.classifyImage(image);
			if(!landClassification.equals(LandClassification.ARABLE)) {
				incorrectArableCount ++;
				System.out.println(file.getName()+": "+landClassification);
				commands[3] = file.getAbsolutePath();
//				runtime.exec(commands);
			}
		}
		System.out.println("Incorrect developed images:");
		for(File file : getCuratedDevelopedTestingImageFiles()) {
			Image image = new Image(file);
			LandClassification landClassification = 
				trainedModel.classifyImage(image);
			if(!landClassification.equals(LandClassification.DEVELOPED)) {
				incorrectDevelopedCount ++;
				System.out.println(file.getName()+": "+landClassification);
				commands[3] = file.getAbsolutePath();
//				runtime.exec(commands);
			}
		}
		System.out.println("Incorrect desert images:");
		for(File file : getCuratedDesertTestingImageFiles()) {
			Image image = new Image(file);
			LandClassification landClassification = 
				trainedModel.classifyImage(image);
			if(!landClassification.equals(LandClassification.DESERT)) {
				incorrectDesertCount ++;
				System.out.println(file.getName()+": "+landClassification);
				commands[3] = file.getAbsolutePath();
//				runtime.exec(commands);
			}
		}
		System.out.println("Incorrect forest images:");
		for(File file : getCuratedForestTestingImageFiles()) {
			Image image = new Image(file);
			LandClassification landClassification = 
				trainedModel.classifyImage(image);
			if(!landClassification.equals(LandClassification.FOREST)) {
				incorrectForestCount ++;
				System.out.println(file.getName()+": "+landClassification);
				commands[3] = file.getAbsolutePath();
//				runtime.exec(commands);
			}
		}
		System.out.println(
			"Number of arable images incorrectly classified: " +
				incorrectArableCount);
		System.out.println(
			"Number of develope images incorrectly classified: " +
				incorrectDevelopedCount);
		System.out.println(
			"Number of desert images incorrectly classified: " +
				incorrectDesertCount);
		System.out.println(
			"Number of forest images incorrectly classified: " +
				incorrectForestCount);
	}
}
