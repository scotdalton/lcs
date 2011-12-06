/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

import static edu.nyu.cs.sysproj.arability.TestUtility.*;

import java.io.File;
import java.util.Calendar;

import org.junit.Test;

import edu.nyu.cs.sysproj.arability.Classification;
import edu.nyu.cs.sysproj.arability.Image;
import edu.nyu.cs.sysproj.arability.TrainedModel;

/**
 * @author Scot Dalton
 *
 */
public class TrainedModelTest {
	@Test
	public void testTrainedModel() throws Exception {
		String classifierName = "weka.classifiers.trees.J48";
		String[] classifierOptions = {};
		long startGet = Calendar.getInstance().getTimeInMillis();
		TrainedModel trainedModel = 
			TrainedModel.getTrainedModel(classifierName, classifierOptions);
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
			Classification classification = 
				trainedModel.classifyImage(image);
			if(!classification.equals(Classification.ARABLE)) {
				incorrectArableCount ++;
				System.out.println(file.getName()+": "+classification);
				commands[3] = file.getAbsolutePath();
//				runtime.exec(commands);
			}
		}
		System.out.println("Incorrect developed images:");
		for(File file : getCuratedDevelopedTestingImageFiles()) {
			Image image = new Image(file);
			Classification classification = 
				trainedModel.classifyImage(image);
			if(!classification.equals(Classification.DEVELOPED)) {
				incorrectDevelopedCount ++;
				System.out.println(file.getName()+": "+classification);
				commands[3] = file.getAbsolutePath();
//				runtime.exec(commands);
			}
		}
		System.out.println("Incorrect desert images:");
		for(File file : getCuratedDesertTestingImageFiles()) {
			Image image = new Image(file);
			Classification classification = 
				trainedModel.classifyImage(image);
			if(!classification.equals(Classification.DESERT)) {
				incorrectDesertCount ++;
				System.out.println(file.getName()+": "+classification);
				commands[3] = file.getAbsolutePath();
//				runtime.exec(commands);
			}
		}
		System.out.println("Incorrect forest images:");
		for(File file : getCuratedForestTestingImageFiles()) {
			Image image = new Image(file);
			Classification classification = 
				trainedModel.classifyImage(image);
			if(!classification.equals(Classification.FOREST)) {
				incorrectForestCount ++;
				System.out.println(file.getName()+": "+classification);
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
