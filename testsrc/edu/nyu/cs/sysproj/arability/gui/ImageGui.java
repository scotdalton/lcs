/**
 * 
 */
package edu.nyu.cs.sysproj.arability.gui;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import edu.nyu.cs.sysproj.arability.Image;
import edu.nyu.cs.sysproj.arability.Region;
import edu.nyu.cs.sysproj.arability.TestUtility;
import edu.nyu.cs.sysproj.arability.utility.Configuration;

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
		Map<String, Image> images = Maps.newLinkedHashMap();
		for(String arg: args)
			images.put(arg, new Image(arg));
		Image image1 = TestUtility.getTestImage1();
		Image screenshot = TestUtility.getTestImage1();
//		images.put("Johannesburg, South Africa 2003", image1);
//		images.put("Classification Map 2003", image1.getClassificationHeatMap());
		Image image2 = TestUtility.getTestImage2();
		images.put("Screenshot", screenshot);
//		images.put("Classification Map 2005", image2.getClassificationHeatMap());
		Image image3 = TestUtility.getTestImage3();
		images.put("Johannesburg, South Africa 2007", image3);
//		images.put("Classification Map 2007", image3.getClassificationHeatMap());
		Image image4 = TestUtility.getTestImage4();
		images.put("Johannesburg, South Africa 2009", image4);
//		images.put("Classification Map 2009", image4.getClassificationHeatMap());
		Image image5 = TestUtility.getTestImage5();
		images.put("Johannesburg, South Africa 2011", image5);
//		images.put("Classification Map 2011", image5.getClassificationHeatMap());
//		images.put("Comparison from 2003 to 2011", image5.getComparisonImage(image1));
		List<Image> regionImages = Lists.newArrayList();
		regionImages.add(screenshot);
		regionImages.add(image1);
		regionImages.add(image2);
		regionImages.add(image3);
		regionImages.add(image4);
		regionImages.add(image5);
		Region region = new Region(regionImages, 3, 2);
//		Image regionImage = region.getImage();
//		images.put("Region", regionImage);
//		images.put("Region Classification", region.getClassificationHeatMap());
//		regionImage.persist(Configuration.TMP_BASE_PATH+"/region.png");
		Image choppedImage = TestUtility.getTestImage1().getChoppedImages().get(0);
//		images.put("Tamale_Ghana_1_20041004.png", image1);
//		images.put("Classification", image.getChoppedImages().get(0).getClassificationOverlay());
//		images.put("Overlay", image.overlay(image.getChoppedImages().get(0).getClassificationOverlay()));
//		images.put("Tamale_Ghana_1_20111004.png", image2);
//		images.put("Classification Map 2011", image2.getClassificationHeatMap());
//		images.put("Arable", Image.getArableClassificationKey());
//		images.put("Developed", Image.getDevelopedClassificationKey());
//		images.put("Desert", Image.getDesertClassificationKey());
//		images.put("Forest", Image.getForestClassificationKey());
//		images.put("Unknown", Image.getUnknownClassificationKey());
//		for(Image choppedImage: image.getChoppedImages())
//			images.put(choppedImage.getClassification().toString()+ "(" +
//				choppedImage.getMinX()+", "+choppedImage.getMinY() + ")", 
//					choppedImage.getClassificationOverlay());
		images.put("Image", choppedImage);
		Image greyScale = choppedImage.getGreyscaleImage(); 
//		images.put(choppedImage.getClassification() + " (" + choppedImage.getMinX() + ", " + choppedImage.getMinY() + ")", choppedImage.getClassificationOverlay());
//		images.put("Greyscale", greyScale);
//		images.put("Greyscale", choppedImage.convolve(100, 100, choppedImage.getDiscreteCosineTransform().getPixels((float[]) null)));
		Image surfImage = choppedImage.getGreyscaleImage(); 
		for(int i=0; i<choppedImage.getSURF().size(); i++) {
			surfImage = choppedImage.convolve(8, 8, choppedImage.getSURF().get(i));
			images.put("SURF"+i, surfImage);
		}
//		images.put("DCT", choppedImage.getDiscreteCosineTransform());
		images.put("IDCT", choppedImage.getInverseDiscreteCosineTransform());
		images.put("GradientMagnitude (Sobel)", choppedImage.getGradientMagnitude());
//		images.put("Add to GM", choppedImage.add(choppedImage.getGradientMagnitude()));
//		images.put("Subtract GM", choppedImage.subtract(choppedImage.getGradientMagnitude()));
//		images.put("Subtract GM DCT", choppedImage.subtract(choppedImage.getGradientMagnitude()).getDiscreteCosineTransform());
//		images.put("Subtract GM IDCT", choppedImage.subtract(choppedImage.getGradientMagnitude()).getInverseDiscreteCosineTransform());
//		images.put("Subtract from GM", choppedImage.getGradientMagnitude().subtract(choppedImage));
//		images.put("Subtract from GM DCT", choppedImage.getGradientMagnitude().subtract(choppedImage).getDiscreteCosineTransform());
//		images.put("Subtract from GM IDCT", choppedImage.getGradientMagnitude().subtract(choppedImage).getInverseDiscreteCosineTransform());
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
