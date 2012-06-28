/**
 * 
 */
package edu.nyu.cs.lcs.gui;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.nyu.cs.lcs.Image;
import edu.nyu.cs.lcs.Region;
import edu.nyu.cs.lcs.TestUtility;
import edu.nyu.cs.lcs.UnknownImage;
import edu.nyu.cs.lcs.model.TrainedModel;
import edu.nyu.cs.lcs.model.TrainedModelModule;

/**
 * @author Scot Dalton
 *
 */
public class ImageGui {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Injector injector = 
			Guice.createInjector(new TrainedModelModule());
		TrainedModel trainedModel = 
			injector.getInstance(TrainedModel.class);
		Map<String, Image> images = Maps.newLinkedHashMap();
		for(String arg: args)
			images.put(arg, new UnknownImage(arg, trainedModel));
		Image image1 = new Image(TestUtility.IMAGE1);
		Image screenshot = new Image(TestUtility.IMAGE1);
		Image image2 = new Image(TestUtility.IMAGE2);
		images.put("Screenshot", screenshot);
		Image image3 = new Image(TestUtility.IMAGE3);
		images.put("Johannesburg, South Africa 2007", image3);
		Image image4 = new Image(TestUtility.IMAGE4);
		images.put("Johannesburg, South Africa 2009", image4);
		Image image5 = new Image(TestUtility.IMAGE5);
		images.put("Johannesburg, South Africa 2011", image5);
		List<Image> regionImages = Lists.newArrayList();
		regionImages.add(screenshot);
		regionImages.add(image1);
		regionImages.add(image2);
		regionImages.add(image3);
		regionImages.add(image4);
		regionImages.add(image5);
		Region region = new Region(regionImages, 3, 2);
		Image regionImage = region.getImage();
		images.put("Region", regionImage);
		images.put("Region Classification", region.getClassificationImage());
		final Map<String, Image> finalImages = 
			Collections.unmodifiableMap(images);
		// Set up training data.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI(finalImages);
			}
		});	
	}

	private static void createAndShowGUI(Map<String, Image> images) {
		//Create and set up the window.
		JFrame frame = new JFrame("Arability Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageTabbedPane imageTabbedPane = new ImageTabbedPane(images);
		frame.add(imageTabbedPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}
}
