/**
 * 
 */
package edu.nyu.cs.lcs.gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Map;

import javax.swing.Action;
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
public class CompareImages extends LcsAction {
	
	private static final long serialVersionUID = -5644593796043614642L;
	TrainedModel trainedModel;

	public CompareImages(TrainedModel trainedModel) {
		super("Classify/Compare");
		this.trainedModel = trainedModel;
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_B);
		setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton compareButton = (JButton) event.getSource();
		setComponentsFromButton(compareButton);
		try {
			Map<String, String> imageFileNames = lcsFrame.getImageFileNames(); 
		    Iterator<Map.Entry<String, String>> imageFileNameIterator = 
	    			imageFileNames.entrySet().iterator();
		    Map<String, Image> unknownImages = Maps.newLinkedHashMap();
		    // Grab oldest and iterate one step
		    UnknownImage oldestImage = null; 
		    if(imageFileNameIterator.hasNext()) {
		        Map.Entry<String, String> pair = imageFileNameIterator.next();
		        String imageName = pair.getKey(); 
		        oldestImage = new UnknownImage(pair.getValue(), trainedModel);
		        unknownImages.put(imageName, oldestImage);
		        unknownImages.put(imageName + " Classification Map", oldestImage.getClassificationImage());
		    }
		    // Get the rest of the images
		    UnknownImage unkownImage = null; 
		    while (imageFileNameIterator.hasNext()) {
		        Map.Entry<String, String> pair = imageFileNameIterator.next();
		        String imageName = pair.getKey(); 
		        unkownImage = new UnknownImage(pair.getValue(), trainedModel);
		        unknownImages.put(imageName, unkownImage);
		        unknownImages.put(imageName + " Classification Map", unkownImage.getClassificationImage());
		    }
		    // Newest image is the last one.
		    UnknownImage newestImage = unkownImage; 
		    unknownImages.put(address + " Comparison", newestImage.getComparisonImage(oldestImage));
			ImageTabbedPane imageTabbedPaneWithClassifications = 
				new ImageTabbedPane(unknownImages);
			resultsPanel.removeAll();
			resultsPanel.add(imageTabbedPaneWithClassifications);
			resultsPanel.validate();
			compareButton.setEnabled(false);
			compareButton.repaint();
			String message = "Results ready.\n";
			if(oldestImage.getCroplandPercentage() > newestImage.getCroplandPercentage())
				croplandLoss = oldestImage.getCroplandPercentage() - 
					newestImage.getCroplandPercentage();
			if (croplandLoss != null)
				message += (int)((double)croplandLoss) + "% cropland was lost.";
		    JOptionPane.showMessageDialog(resultsPanel, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}