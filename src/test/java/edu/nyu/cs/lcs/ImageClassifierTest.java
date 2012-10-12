/**
 * 
 */
package edu.nyu.cs.lcs;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.junit.Ignore;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.nyu.cs.lcs.classifications.Classification;
import edu.nyu.cs.lcs.model.TrainedModel;
import edu.nyu.cs.lcs.model.TrainedModelModule;
import edu.nyu.cs.lcs.utility.FileUtil;


/**
 * @author Scot Dalton
 *
 */
public class ImageClassifierTest {
	private TrainedModel trainedModel;
	private final File dataDir = new File("../data/lcs");
	private final File wbBase = new File("/data/std5/West Bengal, India");
	private final File ghanaBase = new File("/data/std5/Hohoe and Kumawu, Ghana");
	
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
	public void persistClassificationImagesWB() throws Exception {
		File dir = new File("src/test/resources/WB");
		UnknownImage image1 = 
			new UnknownImage(dir + "/2006/22.573446407895652-88.45686452574266.png", getTrainedModel());
		printPercentages(image1);
		image1.getClassificationImage().persist(dir + "/2006-" + image1.getName() + ".classification.png");
		UnknownImage image2 = 
			new UnknownImage(dir + "/2010/22.573446407895652-88.45686452574266.png", getTrainedModel());
		printPercentages(image2);
		image2.getClassificationImage().persist(dir + "/2010-" + image2.getName() + ".classification.png");
		image2.getComparisonImage(image1).persist(dir + "/2010-" + image2.getName() + ".comparedto.2006-" + image1.getName() + ".png");
	}

	@Ignore
	@Test
	public void westBengal2000() throws Exception {
		File wb = new File(wbBase + "/2000-05-21");
		File wbCsv = new File(wbBase + "/2000-05-21-8b.csv");
		FileUtil.regionCSV(wb, wbCsv, getTrainedModel(), 4119, 381);
	}
	
	@Ignore
	@Test
	public void ghana2000() throws Exception {
		File wb = new File(ghanaBase + "/2000-09-17");
		File wbCsv = new File(ghanaBase + "/2000-09-17.csv");
		FileUtil.regionCSV(wb, wbCsv, getTrainedModel());
	}
	
	@Ignore
	@Test
	public void ghana2012() throws Exception {
		File wb = new File(ghanaBase + "/2012-09-17");
		File wbCsv = new File(ghanaBase + "/2012-09-17.csv");
		FileUtil.regionCSV(wb, wbCsv, getTrainedModel());
	}
	
	@Ignore
	@Test
	public void processCandidates() throws Exception {
		File cd = new File("/data/std5/candidates/");
		List<File> candidateDirs = 
			FileUtil.getDirectories(cd);
		assertEquals(314, candidateDirs.size());
		for(File candidateDir: candidateDirs) {
			if(cd.equals(candidateDir)) continue;
			System.out.println(candidateDir.getName());
			List<File> files = 
				FileUtil.getFiles(candidateDir);
			System.out.println(files.size());
			System.out.println(files.get(0).getName());
//			assertEquals(2, files.size());
			File file2000 = null; File file2012 = null;
			UnknownImage image2000 = null; UnknownImage image2012 = null;
			for(File file: files) {
				if(file.getName().equals("2000-05-21.png")) {
					file2000 = file;
				} else if(file.getName().equals("2012-05-21.png")) {
					file2012 = file;
				}
			}
			if(file2000 != null) {
				File image2000Classified = 
					new File(candidateDir + "/" +  FilenameUtils.getBaseName(file2000.getName()) + "_classified.png");
				if(!image2000Classified.exists()) {
					image2000 = new UnknownImage(file2000, getTrainedModel());
					image2000.getClassificationImage().persist(image2000Classified.getAbsolutePath());
				}
			}
			if(file2012 != null) {
				File image2012Classified = 
					new File(candidateDir + "/" +  FilenameUtils.getBaseName(file2012.getName()) + "_classified.png");
				if(!image2012Classified.exists()) {
					image2012 = new UnknownImage(file2012, getTrainedModel());
					image2012.getClassificationImage().persist(image2012Classified.getAbsolutePath());
				}
			}
			if(image2000 != null && image2012 != null) {
				image2012.getComparisonImage(image2000).persist(candidateDir + "/"  + "comparison.png");
			}
		}
	}
	
	@Ignore
	@Test
	public void createCompositeImages() {
		File cd = new File("/Users/dalton/Desktop/candidates");
		List<File> candidateDirs = 
			FileUtil.getDirectories(cd);
		for(File candidateDir: candidateDirs) {
			if(cd.equals(candidateDir)) continue;
			System.out.println(candidateDir.getName());
			List<File> files = 
				FileUtil.getFiles(candidateDir);
			System.out.println(files.size());
			System.out.println(files.get(0).getName());
//			assertEquals(4, files.size());
			Image image2000, image2012, image2000Classified, image2012Classified;
			image2000 = image2012 = image2000Classified = image2012Classified = null;
			for(File file: files) {
				if(file.getName().equals("2000-05-21.png")) {
					image2000 = new Image(file);
				} else if(file.getName().equals("2012-05-21.png")) {
					image2012 = new Image(file);
				} else if(file.getName().equals("2000-05-21_classified.png")) {
					image2000Classified = new Image(file);
				} else if(file.getName().equals("2012-05-21_classified.png")) {
					image2012Classified = new Image(file);
				}
			}
			if((image2000.getFile().length() <= 2621440) || 
				(image2012.getFile().length() <= 2621440) ||
				(image2000Classified.getFile().length() <= 2621440) ||
				(image2012Classified.getFile().length() <= 2621440)) continue;
			Image originalImage = 
				createAndSaveComposite(image2000, image2012, candidateDir + "/"  + "originalComposite.png", candidateDir.getName(), false);
			Image classifiedImage = 
				createAndSaveComposite(image2000Classified, image2012Classified, candidateDir + "/"  + "classifiedComposite.png", "Classifications", true);
			stackImages(originalImage, classifiedImage, candidateDir + "/"  + candidateDir.getName() + "-comp.png");
		}		
	}
	
	private void stackImages(Image image1, Image image2, String filename) {
		System.out.println(filename);
		assertEquals(image1.getWidth(), image2.getWidth());
		int width = image1.getWidth();
		int height = image1.getHeight()+image2.getHeight();
		BufferedImage img = new BufferedImage(width, height,	BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = img.createGraphics();
		graphics.drawImage(image1.getAsBufferedImage(), 0, 0, null);
		graphics.drawImage(image2.getAsBufferedImage(), 0, image1.getHeight(), null);
		(new Image(img)).persist(filename);
	}
	
	private Image createAndSaveComposite(Image image1, Image image2, String filename, String caption, boolean needsLegend) {
		int captionOffset = 60; 
		assertEquals(image1.getHeight(), image2.getHeight());
		int height = image1.getHeight() + captionOffset;
		int legendOffset = 160; 
		if(needsLegend) height += legendOffset;
		int borderHeight = image1.getHeight();
		int width = image1.getWidth()+image2.getWidth() + 2;
		BufferedImage img = new BufferedImage(width, height,	BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = img.createGraphics();
//		graphics.setBackground(Color.LIGHT_GRAY);
		graphics.drawImage(image1.getAsBufferedImage(), 0, 0, null);
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.drawRect(image1.getWidth(), 0, 2, borderHeight);
		graphics.drawImage(image2.getAsBufferedImage(), (image1.getWidth() + 2), 0, null);
		graphics.setFont(Font.decode("Arial-BOLD-30"));
	    FontMetrics fontMetrics = graphics.getFontMetrics();
	    int x = (width - fontMetrics.stringWidth(caption)) / 2;
	    int y = height - 15;
		if(needsLegend) y -= legendOffset;
		graphics.drawString(caption, x, y);
		if(needsLegend) setLegend(img, legendOffset, x);
		System.out.println(image1.getName());
	    if(image1.getName().equals("2000-05-21")) {
	    		String yearCaption = "Satellite image from 2000";
		    int xImage1 = (image1.getWidth() - fontMetrics.stringWidth(yearCaption)) / 2;
			graphics.drawString(yearCaption, xImage1, y);
	    } 
	    if(image2.getName().equals("2012-05-21")) {
    			String yearCaption = "Satellite image from 2012";
		    int xImage2 = image1.getWidth() + 2 + (image2.getWidth() - fontMetrics.stringWidth(yearCaption)) / 2;
			graphics.drawString(yearCaption, xImage2, y);
	    }
		(new Image(img)).persist(filename);
		return new Image(filename);
	}
	
	private void setLegend(BufferedImage img, int legendOffset, int x) {
		Graphics2D graphics = img.createGraphics();
		graphics.setFont(Font.decode("Arial-NORMAL-30"));
		int lineHeight = legendOffset/Classification.values().length;
		for(int i=0; i < Classification.values().length; i++) {
			Classification classification = 
				Classification.values()[i];
			if (classification.equals(Classification.UNKNOWN))
				continue;
			int red = classification.getRed();
			int green = classification.getGreen();
			int blue = classification.getBlue();
			int alpha = classification.getAlpha();
			graphics.setColor(new Color(red, green, blue, alpha));
		    int y = img.getHeight() - (i+1)*lineHeight;
			graphics.fillRect(x, y-lineHeight, lineHeight, lineHeight);
			graphics.setColor(Color.LIGHT_GRAY);
			graphics.drawString(classification.name(), x+lineHeight+5, y-5);
		}
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

	@Ignore
	@Test
	public void westBengal2012() throws Exception {
		File wb = new File(wbBase + "/2012-05-21");
		File wbCsv = new File(wbBase + "/2012-05-21.csv");
		FileUtil.regionCSV(wb, wbCsv, getTrainedModel(), 1950);
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
