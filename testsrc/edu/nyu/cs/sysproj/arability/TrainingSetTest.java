/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

/**
 * @author Scot Dalton
 *
 */
public class TrainingSetTest {
	@Test
	public void testTrainingSet() throws Exception {
		long startGet = Calendar.getInstance().getTimeInMillis();
		TrainingSet trainingSet = TrainingSet.getTrainingSet();
		long endGet = Calendar.getInstance().getTimeInMillis();
		System.out.println((endGet - startGet)/(double)1000);
		int startNumAttributes = 
			trainingSet.getInstances().numAttributes();
		int startNumClasses = 
			trainingSet.getInstances().numClasses();
		int startNumInstances = 
			trainingSet.getInstances().numInstances();
		long startRefresh = Calendar.getInstance().getTimeInMillis();
		trainingSet.refresh();
		long endRefresh = Calendar.getInstance().getTimeInMillis();
		System.out.println((endRefresh- startRefresh)/(double)1000);
		int refreshNumAttributes = 
			trainingSet.getInstances().numAttributes();
		int refreshNumClasses = 
			trainingSet.getInstances().numClasses();
		int refreshNumInstances = 
			trainingSet.getInstances().numInstances();
		assertEquals(startNumAttributes, refreshNumAttributes);
		assertEquals(startNumClasses, refreshNumClasses);
		assertEquals(startNumInstances, refreshNumInstances);
	}
}