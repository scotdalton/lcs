/**
 * 
 */
package edu.nyu.cs.sysproj.arability.gui;

import java.util.Collections;
import java.util.Map;

import javax.swing.JFrame;

import com.google.common.collect.Maps;

import edu.nyu.cs.sysproj.arability.Image;
import edu.nyu.cs.sysproj.arability.TestUtility;

/**
 * @author Scot Dalton
 *
 */
public class ImageGui {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Image> images = Maps.newLinkedHashMap();
		for(String arg: args)
			images.put(arg, new Image(arg));
		Image choppedImage = TestUtility.getTestImage2();
		images.put("Tamale_Ghana_1_20111004.png", choppedImage);
		images.put("DCT", choppedImage.getDiscreteCosineTransform());
		images.put("IDCT", choppedImage.getInverseDiscreteCosineTransform());
		images.put("GradientMagnitude (Sobel)", choppedImage.getGradientMagnitude());
		images.put("Add to GM", choppedImage.add(choppedImage.getGradientMagnitude()));
		images.put("Subtract GM", choppedImage.subtract(choppedImage.getGradientMagnitude()));
		images.put("Subtract GM DCT", choppedImage.subtract(choppedImage.getGradientMagnitude()).getDiscreteCosineTransform());
		images.put("Subtract GM IDCT", choppedImage.subtract(choppedImage.getGradientMagnitude()).getInverseDiscreteCosineTransform());
		images.put("Subtract from GM", choppedImage.getGradientMagnitude().subtract(choppedImage));
		images.put("Subtract from GM DCT", choppedImage.getGradientMagnitude().subtract(choppedImage).getDiscreteCosineTransform());
		images.put("Subtract from GM IDCT", choppedImage.getGradientMagnitude().subtract(choppedImage).getInverseDiscreteCosineTransform());
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
