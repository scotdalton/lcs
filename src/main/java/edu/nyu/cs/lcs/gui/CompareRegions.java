/**
 * 
 */
package edu.nyu.cs.lcs.gui;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.google.common.collect.Maps;

import edu.nyu.cs.lcs.Image;
import edu.nyu.cs.lcs.UnknownImage;
import edu.nyu.cs.lcs.model.TrainedModel;

/**
 * @author Scot Dalton
 *
 */
public class CompareRegions extends CompareImages {

	/**
	 * @param trainedModel
	 */
	public CompareRegions(TrainedModel trainedModel) {
		super(trainedModel);
	}

	private static final long serialVersionUID = -5684742403384211626L;

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton compareButton = (JButton) event.getSource();
		setComponentsFromButton(compareButton);
		try {
			Map<String, String> imageFileNames = lcsFrame.getImageFileNames(); 
			Iterator<Map.Entry<String, String>> imageFileNameIterator = 
			imageFileNames.entrySet().iterator();
			Map<String, Image> unknownRegionImages = Maps.newLinkedHashMap();
		    // Grab first and iterate one step
			UnknownImage firstImage = null; 
			if(imageFileNameIterator.hasNext()) {
				Map.Entry<String, String> pair = imageFileNameIterator.next();
				String imageName = pair.getKey(); 
				firstImage = new UnknownImage(pair.getValue(), trainedModel);
				unknownRegionImages.put(imageName, firstImage);
				unknownRegionImages.put(imageName + " Classification Map", firstImage.getClassificationImage());
			}
		    // Get the rest of the images
			UnknownImage unknownImage = null; 
			while (imageFileNameIterator.hasNext()) {
				Map.Entry<String, String> pair = imageFileNameIterator.next();
				String imageName = pair.getKey(); 
				unknownImage = new UnknownImage(pair.getValue(), trainedModel);
				unknownRegionImages.put(imageName, unknownImage);
				unknownRegionImages.put(imageName + " Classification Map", unknownImage.getClassificationImage());
			}
		    // Newest image is the last one.
			UnknownImage lastImage = unknownImage;
			if(lastImage != null)
				unknownRegionImages.put(regionAddress + " Comparison", lastImage.getComparisonImage(firstImage));
			ImageTabbedPane imageTabbedPaneWithClassifications = 
			new ImageTabbedPane(unknownRegionImages);
			resultsPanel.removeAll();
			resultsPanel.add(imageTabbedPaneWithClassifications);
			resultsPanel.validate();
			compareButton.setEnabled(false);
			compareButton.repaint();
			JOptionPane.showMessageDialog(resultsPanel, "Results ready.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}