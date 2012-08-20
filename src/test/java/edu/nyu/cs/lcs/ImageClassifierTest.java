/**
 * 
 */
package edu.nyu.cs.lcs;

import java.io.File;
import java.util.List;

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
@Ignore
public class ImageClassifierTest {
	private TrainedModel trainedModel;
	private final File dataDir = new File("../data/lcs");
	private final File wbBase = new File("/data/std5/West Bengal, India");
	
	@Ignore
	@Test
	public void persistClassificationImages() throws Exception {
		UnknownImage image1 = 
			new UnknownImage(dataDir + "/MR3, Mbabane, Swaziland circa 2000.png", getTrainedModel());
		printPercentages(image1);
		image1.getClassificationImage().persist(dataDir + "/" + image1.getName() + ".classification.png");
		UnknownImage image2 = 
			new UnknownImage(dataDir + "/MR3, Mbabane, Swaziland circa 2012.png", getTrainedModel());
		printPercentages(image2);
		image2.getClassificationImage().persist(dataDir + "/" + image2.getName() + ".classification.png");
		image2.getComparisonImage(image1).persist(dataDir + "/" + image2.getName() + ".comparedto." + image1.getName() + ".png");
	}
	
	@Ignore
	@Test
	public void westBengal2000() throws Exception {
		File wb = new File(wbBase + "/2000-05-21");
		File wbCsv = new File(wbBase + "/2000-05-21.csv");
		FileUtil.regionCSV(wb, wbCsv, getTrainedModel());
	}
	
	@Ignore
	@Test
	public void classifyWestBengal2000() throws Exception {
		File wb = new File(wbBase + "/2000-05-21");
		List<File> files = 
			FileUtil.getRegionSort(wb);
		String wbClassifications = wb + "/classifications/"; 
		int i = 0;
		for(File file: files) {
			if(file.length() < 2097152) continue;
			if(i >= 10) break;
			i++;
			UnknownImage image = 
				new UnknownImage(file, getTrainedModel());
			image.getClassificationImage().persist(wbClassifications + image.getName() + ".png");
		}
	}
	
	@Test
	public void westBengal2012() throws Exception {
		File wb = new File(wbBase + "/2012-05-21");
		File wbCsv = new File(wbBase + "/2012-05-21.csv");
		FileUtil.regionCSV(wb, wbCsv, getTrainedModel());
	}
	
	@Ignore
	@Test
	public void classifyWestBengal2012() throws Exception {
		File wb = new File(wbBase + "/2012-05-21");
		List<File> files = 
			FileUtil.getRegionSort(wb);
		String wbClassifications = wb + "/classifications/";
		int i = 0;
		for(File file: files) {
			if(file.length() < 2097152) continue;
			if(i >= 10) break;
			i++;
			UnknownImage image = 
				new UnknownImage(file, getTrainedModel());
			image.getClassificationImage().persist(wbClassifications + image.getName() + ".png");
		}
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
