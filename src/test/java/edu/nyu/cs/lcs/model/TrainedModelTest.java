/**
 * 
 */
package edu.nyu.cs.lcs.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import ij.ImagePlus;
import ij.gui.Roi;

import java.io.File;
import java.util.List;

import org.junit.Test;

import trainableSegmentation.WekaSegmentation;

import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.nyu.cs.lcs.Image;
import edu.nyu.cs.lcs.TestUtility;
import edu.nyu.cs.lcs.UnknownImage;
import edu.nyu.cs.lcs.classifications.Classification;
import edu.nyu.cs.lcs.model.TrainedModel;
import edu.nyu.cs.lcs.model.TrainedModelModule;

/**
 * @author Scot Dalton
 *
 */
public class TrainedModelTest {
//	@Test
	public void testNewTrainedModel() throws Exception {
		File serializationDirectory = new File("./.classifiers");
		TrainedModel trainedModel = 
			new TrainedModel(serializationDirectory);
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
	
//	@Test
	public void testFeatureStack() {
		Image image = new Image(TestUtility.IMAGE1);
		WekaSegmentation wekaSegmentation = 
			new WekaSegmentation(image.getImagePlus());
		for(Classification classification : Classification.values()) {
			wekaSegmentation.setClassLabel(classification.ordinal(), classification.name());
			if(classification.ordinal() >= wekaSegmentation.getNumOfClasses())
				wekaSegmentation.addClass();
			if(classification.isTrainable()) {
				List<Image> trainingImages = classification.getTrainingImages();
				for(int i = 0;  i < 10; i ++) {
					Image trainingImage = trainingImages.get(i);
//				for(Image trainingImage:trainingImages) {
					ImagePlus imagePlus = trainingImage.getImagePlus();
					Roi roi = new Roi(0, 0, imagePlus.getWidth(), imagePlus.getHeight());
					roi.setImage(imagePlus);
					int n = imagePlus.getCurrentSlice();
					int classNum = classification.ordinal();
					wekaSegmentation.addExample(classNum, roi, n);
				}
			}
		}
		wekaSegmentation.trainClassifier();
		wekaSegmentation.saveClassifier(".classifiers/lcs.model");
		wekaSegmentation.saveData("lcs.arff");
	}
}