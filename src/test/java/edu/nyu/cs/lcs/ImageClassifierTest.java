/**
 * 
 */
package edu.nyu.cs.lcs;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.nyu.cs.lcs.model.TrainedModel;
import edu.nyu.cs.lcs.model.TrainedModelModule;
import edu.nyu.cs.lcs.utility.FileUtil;


/**
 * @author Scot Dalton
 *
 */
public class ImageClassifierTest {
	private TrainedModel trainedModel;
	private final File testDir = new File("src/test/resources/META-INF");
	private final File wbBase = new File("../wb");
	
	@Ignore
	@Test
	public void persistClassificationImages() throws Exception {
		UnknownImage image1 = 
			new UnknownImage(testDir + "/MR3, Mbabane, Swaziland circa 2000.png", getTrainedModel());
		printPercentages(image1);
		image1.getClassificationImage().persist("src/test/resources/META-INF/MR3, Mbabane, Swaziland circa 2000.classification.png");
		UnknownImage image2 = 
			new UnknownImage(testDir + "/MR3, Mbabane, Swaziland circa 2012.png", getTrainedModel());
		printPercentages(image2);
		image2.getClassificationImage().persist("src/test/resources/META-INF/MR3, Mbabane, Swaziland circa 2012.classification.png");
	}
	
	@Test
	public void westBengal2000() throws Exception {
		File wb = new File(wbBase + "/2000");
		File wbCsv = new File(wbBase + "/2000.csv");
		FileUtil.regionCSV(wb, wbCsv, getTrainedModel());
	}
	
	private TrainedModel getTrainedModel() {
		if(trainedModel == null) {
			Injector injector = 
				Guice.createInjector(new TrainedModelModule());
			trainedModel = 
				injector.getInstance(TrainedModel.class);
		}
		return trainedModel;
	}

	private void printPercentages(UnknownImage image) throws Exception {
		System.out.println(image.getName() + " Cropland: " + image.getCroplandPercentage());
		System.out.println(image.getName() + " Developed: " + image.getDevelopedPercentage());
		System.out.println(image.getName() + " Desert: " + image.getDesertPercentage());
		System.out.println(image.getName() + " Forest: " + image.getForestPercentage());
	}
}
