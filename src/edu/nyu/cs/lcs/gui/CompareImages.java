/**
 * 
 */
package edu.nyu.cs.sysproj.arability.gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.google.common.collect.Maps;

import edu.nyu.cs.sysproj.arability.Image;
import edu.nyu.cs.sysproj.arability.UnknownImage;


/**
 * @author Scot Dalton
 *
 */
public class CompareImages extends ArabilityAction {
	
	private static final long serialVersionUID = -5644593796043614642L;

	public CompareImages() {
		super("Classify/Compare");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_B);
		setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton compareButton = (JButton) event.getSource();
		setComponentsFromButton(compareButton);
		try {
			Map<String, String> imageFileNames = arabilityFrame.getImageFileNames(); 
		    Iterator<Map.Entry<String, String>> imageFileNameIterator = 
	    			imageFileNames.entrySet().iterator();
		    Map<String, Image> unknownImages = Maps.newLinkedHashMap();
		    Image fromImage = null; 
		    Image toImage = null; 
		    if(imageFileNameIterator.hasNext()) {
		        Map.Entry<String, String> pair = imageFileNameIterator.next();
		        String imageName = pair.getKey(); 
		        fromImage = new UnknownImage(pair.getValue());
		        unknownImages.put(imageName, fromImage);
		        unknownImages.put(imageName + " Classification Map", fromImage.getClassificationHeatMap());
		    }
		    while (imageFileNameIterator.hasNext()) {
		        Map.Entry<String, String> pair = imageFileNameIterator.next();
		        String imageName = pair.getKey(); 
		        toImage = new UnknownImage(pair.getValue());
		        unknownImages.put(imageName, toImage);
		        unknownImages.put(imageName + " Classification Map", toImage.getClassificationHeatMap());
		    }
		    unknownImages.put(address + " Comparison", toImage.getComparisonImage(fromImage));
			ImageTabbedPane imageTabbedPaneWithClassifications = 
				new ImageTabbedPane(unknownImages);
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