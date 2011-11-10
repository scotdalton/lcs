/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import static edu.nyu.cs.sysproj.google_earth.TestUtility.*;

import java.io.File;

import org.junit.Test;

/**
 * @author Scot Dalton
 *
 */
public class TrainedModelTest {
	@Test
	public void testTrainedModel() throws Exception {
		TrainedModel trainedModel;
			trainedModel = TrainedModel.getTrainedModel();
			System.out.println(trainedModel.test());
	}
	
	@Test
	public void testIncorrectMatches() throws Exception {
		TrainedModel trainedModel = TrainedModel.getTrainedModel();
		Runtime runtime = Runtime.getRuntime();
		String[] commands = {"open", "-a", "Preview", ""};
		int incorrectArableCount = 0;
		int incorrectNonArableCount = 0;
		for(File file : getCuratedArableTestingImageFiles()) {
			Image image = new Image(file);
			ArabilityClassification classification = 
				trainedModel.eval(image);
			if(!classification.equals(ArabilityClassification.ARABLE)) {
				incorrectArableCount ++;
				System.out.println(file.getName()+": "+classification);
				commands[3] = file.getAbsolutePath();
				runtime.exec(commands);
			}
		}
		for(File file : getCuratedNonArableTestingImageFiles()) {
			Image image = new Image(file);
			ArabilityClassification classification = 
				trainedModel.eval(image);
			if(!classification.equals(ArabilityClassification.NON_ARABLE)) {
				incorrectNonArableCount ++;
				System.out.println(file.getName()+": "+classification);
				commands[3] = file.getAbsolutePath();
				runtime.exec(commands);
			}
		}
		System.out.println(
			"Number of arable images incorrectly classified: " +
				incorrectArableCount);
		System.out.println(
			"Number of non arable images incorrectly classified: " +
				incorrectNonArableCount);
	}
}
