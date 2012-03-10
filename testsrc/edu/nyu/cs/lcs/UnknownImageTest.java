/**
 * 
 */
package edu.nyu.cs.lcs;

import static edu.nyu.cs.lcs.TestUtility.getTestFileName1;
import static edu.nyu.cs.lcs.TestUtility.getTestFileName2;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.nyu.cs.lcs.UnknownImage;

/**
 * @author Scot Dalton
 *
 */
public class UnknownImageTest {
	@Test
	public void unknownImageTest() throws Exception {
		Injector injector = 
			Guice.createInjector(new TrainedModelModule());
		TrainedModel trainedModel = 
			injector.getInstance(TrainedModel.class);
		UnknownImage unknownImage1 = new UnknownImage(getTestFileName1(), trainedModel);
		System.out.println(unknownImage1.getArablePercentage());
		UnknownImage unknownImage2 = new UnknownImage(getTestFileName2(), trainedModel);
		System.out.println(unknownImage2.getArablePercentage());
		assertTrue(
			unknownImage1.getArablePercentage() > 
				unknownImage2.getArablePercentage());
	}
}
