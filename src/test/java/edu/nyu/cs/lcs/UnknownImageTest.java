/**
 * 
 */
package edu.nyu.cs.lcs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.nyu.cs.lcs.model.TrainedModel;
import edu.nyu.cs.lcs.model.TrainedModelModule;

/**
 * @author Scot Dalton
 *
 */
public class UnknownImageTest {
	private TrainedModel trainedModel;
	private File persistFile;

	@Before
	public void setup() {
		Injector injector = 
			Guice.createInjector(new TrainedModelModule());
		trainedModel = 
			injector.getInstance(TrainedModel.class);
		persistFile = new File("src/test/resources/META-INF/persistTest.png");
		if(persistFile.exists()) persistFile.delete();
	}
	
	@After
	public void teardown() {
		if(persistFile.exists()) persistFile.delete();
	}

	@Test
	public void testNewImage_fromFileName() {
		Image image = new UnknownImage(TestUtility.IMAGE1, trainedModel);
		assertEquals(1544, image.getWidth());
		assertEquals(1055, image.getHeight());
	}
	
	@Test
	public void testPersist() {
		Image image = new UnknownImage(TestUtility.IMAGE1, trainedModel);
		image.persist(persistFile.getAbsolutePath());
		assertTrue(persistFile.exists());
		assertTrue(persistFile.isFile());
	}
	
	@Test
	public void testChoppedUnknownImage() {
		Image image = new UnknownImage(TestUtility.CHOPPED, null);
		assertEquals(100, image.getHeight());
		assertEquals(100, image.getWidth());
	}
	
	@Test
	public void unknownImageTest() throws Exception {
		UnknownImage unknownImage1 = new UnknownImage(TestUtility.IMAGE1, 
			trainedModel);
		UnknownImage unknownImage2 = new UnknownImage(TestUtility.IMAGE2, 
			trainedModel);
		assertTrue(
			unknownImage1.getArablePercentage() > 
				unknownImage2.getArablePercentage());
	}
}