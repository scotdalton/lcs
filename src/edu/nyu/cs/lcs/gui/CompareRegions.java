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
import edu.nyu.cs.lcs.TrainedModel;
import edu.nyu.cs.lcs.UnknownImage;

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
			Image fromImage = null; 
			Image toImage = null; 
			if(imageFileNameIterator.hasNext()) {
				Map.Entry<String, String> pair = imageFileNameIterator.next();
				String imageName = pair.getKey(); 
				fromImage = new UnknownImage(pair.getValue(), trainedModel);
				unknownRegionImages.put(imageName, fromImage);
				unknownRegionImages.put(imageName + " Classification Map", fromImage.getClassificationHeatMap());
			}
			while (imageFileNameIterator.hasNext()) {
				Map.Entry<String, String> pair = imageFileNameIterator.next();
				String imageName = pair.getKey(); 
				toImage = new UnknownImage(pair.getValue(), trainedModel);
				unknownRegionImages.put(imageName, toImage);
				unknownRegionImages.put(imageName + " Classification Map", toImage.getClassificationHeatMap());
			}
			if(toImage != null)
				unknownRegionImages.put(regionAddress + " Comparison", toImage.getComparisonImage(fromImage));
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