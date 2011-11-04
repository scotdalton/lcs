/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

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
}
