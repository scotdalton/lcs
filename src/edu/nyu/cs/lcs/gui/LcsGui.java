/**
 * 
 */
package edu.nyu.cs.lcs.gui;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.nyu.cs.lcs.TrainedModel;
import edu.nyu.cs.lcs.TrainedModelModule;

/**
 * @author Scot Dalton
 *
 */
public class LcsGui {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					createAndShowGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
	}

	private static void createAndShowGUI() 
			throws FileNotFoundException, IOException {
		Injector injector = 
			Guice.createInjector(new TrainedModelModule());
		TrainedModel trainedModel = 
			injector.getInstance(TrainedModel.class);
		Properties properties = new Properties();
		properties.load(new FileReader("META-INF/gui.properties"));
		File persistDirectory = 
			new File(properties.getProperty("persistDirectory"));
		//Create and set up the window.
		LcsFrame lcsFrame = new LcsFrame();
		lcsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set up address tab.
		// Actions
		Action addressGetImage = new GetImage(persistDirectory);
		addressGetImage.putValue(Action.ACTION_COMMAND_KEY, "address");
		Action addressCompare = new CompareImages(trainedModel);
		Action addressSaveImages = new SaveImages();
		// Elements
		JLabel addressLabel = new JLabel("Address:");
		addressLabel.setPreferredSize(new Dimension(100, 50));
		JTextField addressTextField = new JTextField(50);
		JButton addressGetImagesButton = new JButton(addressGetImage);
		JButton addressCompareButton = new JButton(addressCompare);
		addressCompareButton.setEnabled(false);
		JButton addressSaveImagesButton = new JButton(addressSaveImages);
		addressSaveImagesButton.setEnabled(false);
		// Input Panel
		JComponent addressInputPanel = new JPanel(false);
		addressInputPanel.setLayout(new GridLayout(1, 2));
		addressInputPanel.add(addressLabel);
		addressInputPanel.add(addressTextField);
		// Button Panel
		JComponent addressButtonPanel = new JPanel(false);
		addressButtonPanel.setLayout(new GridLayout(1, 3));
		addressButtonPanel.add(addressGetImagesButton);
		addressButtonPanel.add(addressCompareButton);
		addressButtonPanel.add(addressSaveImagesButton);
		// Panel
		JComponent addressPanel = new JPanel(false);
		addressPanel.
			setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		LayoutManager addresslayoutManager = new GridLayout(2, 1);
		addressPanel.setLayout(addresslayoutManager);
		addressPanel.add(addressInputPanel);
		addressPanel.add(addressButtonPanel);
		
		// Set up region tab.
		// Actions
		Action regionGetRegion = new GetRegion(persistDirectory);
		Action regionCompare = new CompareRegions(trainedModel);
		Action regionSaveImages = new SaveRegions();
		// Elements
		JLabel newRegionLabel = new JLabel("New Region:");
		newRegionLabel.setPreferredSize(new Dimension(50, 50));
		JTextField newRegionTextField = new JTextField(50);
		JButton regionGetImagesButton = new JButton(regionGetRegion);
		JButton regionCompareButton = new JButton(regionCompare);
		regionCompareButton.setEnabled(false);
		JButton regionSaveImagesButton = new JButton(regionSaveImages);
		regionSaveImagesButton.setEnabled(false);
		// Input Panel
		JComponent regionInputPanel = new JPanel(false);
		regionInputPanel.setLayout(new GridLayout(1, 2));
		regionInputPanel.add(newRegionLabel);
		regionInputPanel.add(newRegionTextField);
		// Button Panel
		JComponent regionButtonPanel = new JPanel(false);
		regionButtonPanel.setLayout(new GridLayout(1, 3));
		regionButtonPanel.add(regionGetImagesButton);
		regionButtonPanel.add(regionCompareButton);
		regionButtonPanel.add(regionSaveImagesButton);
		// Panel
		JComponent regionPanel = new JPanel(false);
		regionPanel.
			setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		LayoutManager regionlayoutManager = new GridLayout(2, 1);
		regionPanel.setLayout(regionlayoutManager);
		regionPanel.add(regionInputPanel);
		regionPanel.add(regionButtonPanel);
		
		// Set up existingRegion tab.
		// Actions
		Action existingRegionGetRegion = 
			new GetExistingRegion(persistDirectory);
		Action existingRegionCompare = new CompareRegions(trainedModel);
		Action existingRegionSaveImages = new SaveRegions();
		// Elements
		JLabel existingRegionLabel = new JLabel("Region in Progress:");
		existingRegionLabel.setPreferredSize(new Dimension(50, 50));
		List<String> existingRegions = 
			GuiUtil.getExistingRegions(persistDirectory);
		JComboBox existingRegionComboBox = 
			new JComboBox(existingRegions.toArray());
		JButton existingRegionGetImagesButton = 
			new JButton(existingRegionGetRegion);
		JButton existingRegionCompareButton = 
			new JButton(existingRegionCompare);
		existingRegionCompareButton.setEnabled(false);
		JButton existingRegionSaveImagesButton = 
			new JButton(existingRegionSaveImages);
		existingRegionSaveImagesButton.setEnabled(false);
		// Input Panel
		JComponent existingRegionInputPanel = new JPanel(false);
		existingRegionInputPanel.setLayout(new GridLayout(1, 2));
		existingRegionInputPanel.add(existingRegionLabel);
		existingRegionInputPanel.add(existingRegionComboBox);
		// Button Panel
		JComponent existingRegionButtonPanel = new JPanel(false);
		existingRegionButtonPanel.setLayout(new GridLayout(1, 3));
		existingRegionButtonPanel.add(existingRegionGetImagesButton);
		existingRegionButtonPanel.add(existingRegionCompareButton);
		existingRegionButtonPanel.add(existingRegionSaveImagesButton);
		// Panel
		JComponent existingRegionPanel = new JPanel(false);
		existingRegionPanel.
			setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		LayoutManager existingRegionlayoutManager = new GridLayout(2, 1);
		existingRegionPanel.setLayout(existingRegionlayoutManager);
		existingRegionPanel.add(existingRegionInputPanel);
		existingRegionPanel.add(existingRegionButtonPanel);
		
		// Set up latLng tab.
		// Actions
		Action latLngGetImages = new GetImage(persistDirectory);
		latLngGetImages.putValue(Action.ACTION_COMMAND_KEY, "latLng");
		Action latLngCompare = new CompareImages(trainedModel);
		Action latLngSaveImages = new SaveImages();
		// Elements
		JLabel latitudeLabel = new JLabel("Latitude:");
		JLabel longitudeLabel = new JLabel("Longitude:");
		latitudeLabel.setPreferredSize(new Dimension(50, 50));
		longitudeLabel.setPreferredSize(new Dimension(50, 50));
		JTextField latitudeTextField = new JTextField(50);
		JTextField longitudeTextField = new JTextField(50);
		JButton latLngGetImagesButton = new JButton(latLngGetImages);
		JButton latLngCompareButton = new JButton(latLngCompare);
		latLngCompareButton.setEnabled(false);
		JButton latLngSaveImagesButton = new JButton(latLngSaveImages);
		latLngSaveImagesButton.setEnabled(false);
		// Input Panel
		JComponent latLngInputPanel = new JPanel(false);
		latLngInputPanel.setLayout(new GridLayout(2, 2));
		latLngInputPanel.add(latitudeLabel);
		latLngInputPanel.add(latitudeTextField);
		latLngInputPanel.add(longitudeLabel);
		latLngInputPanel.add(longitudeTextField);
		// Button Panel
		JComponent latLngButtonPanel = new JPanel(false);
		latLngButtonPanel.setLayout(new GridLayout(1, 3));
		latLngButtonPanel.add(latLngGetImagesButton);
		latLngButtonPanel.add(latLngCompareButton);
		latLngButtonPanel.add(latLngSaveImagesButton);
		// Panel
		JComponent latLngPanel = new JPanel(false);
		latLngPanel.
			setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		latLngPanel.setLayout(new GridLayout(2, 1));
		latLngPanel.add(latLngInputPanel);
		latLngPanel.add(latLngButtonPanel);
		
		// Set up settings tab.
		// Elements
		JLabel waitTimeLabel = 
			new JLabel("Seconds to wait for Google Earth to render:");
		waitTimeLabel.setPreferredSize(new Dimension(50, 50));
		JSlider waitTimeSlider = 
			new JSlider(JSlider.HORIZONTAL, 0, 60, 5);
		waitTimeSlider.setMajorTickSpacing(5);
		waitTimeSlider.setMinorTickSpacing(1);
		waitTimeSlider.setPaintTicks(true);
		waitTimeSlider.setPaintLabels(true);
		JLabel xCropFactorLabel = 
			new JLabel("Number of pixels to crop in the screenshot horizontally:");
		xCropFactorLabel.setPreferredSize(new Dimension(50, 75));
		JSlider xCropFactorSlider = 
			new JSlider(JSlider.HORIZONTAL, 0, 500, 50);
		xCropFactorSlider.setMajorTickSpacing(50);
		xCropFactorSlider.setMinorTickSpacing(10);
		xCropFactorSlider.setPaintTicks(true);
		xCropFactorSlider.setPaintLabels(true);
		JLabel yCropFactorLabel = 
			new JLabel("Number of pixels to crop in the screenshot vertically:");
		yCropFactorLabel.setPreferredSize(new Dimension(50, 75));
		JSlider yCropFactorSlider = 
			new JSlider(JSlider.HORIZONTAL, 0, 500, 250);
		yCropFactorSlider.setMajorTickSpacing(50);
		yCropFactorSlider.setMinorTickSpacing(10);
		yCropFactorSlider.setPaintTicks(true);
		yCropFactorSlider.setPaintLabels(true);
		// Panel
		JComponent settingsPanel = new JPanel(false);
		settingsPanel.setName("Settings");
		LayoutManager settingslayoutManager = new GridLayout(3, 2);
		settingsPanel.setLayout(settingslayoutManager);
		settingsPanel.add(waitTimeLabel);
		settingsPanel.add(waitTimeSlider);
		settingsPanel.add(xCropFactorLabel);
		settingsPanel.add(xCropFactorSlider);
		settingsPanel.add(yCropFactorLabel);
		settingsPanel.add(yCropFactorSlider);
	
		// Add tabs to the tabbed pane.
		JTabbedPane arabilityTabbedPane = new JTabbedPane();
		arabilityTabbedPane.addTab("Address", addressPanel);
		arabilityTabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		arabilityTabbedPane.addTab("Region", regionPanel);
		arabilityTabbedPane.setMnemonicAt(0, KeyEvent.VK_2);
		arabilityTabbedPane.addTab("Existing Region", existingRegionPanel);
		arabilityTabbedPane.setMnemonicAt(0, KeyEvent.VK_3);
		arabilityTabbedPane.addTab("Latitude/Longitude", latLngPanel);
		arabilityTabbedPane.setMnemonicAt(1, KeyEvent.VK_4);
		arabilityTabbedPane.addTab("Settings", settingsPanel);
		arabilityTabbedPane.setMnemonicAt(2, KeyEvent.VK_5);
		
		// Add tabbed pane to the top of the split pane
		JSplitPane arabilitySplitPane = 
			new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		arabilitySplitPane.add(arabilityTabbedPane);
		
		// Add results pane to the bottom of the split pane
		JPanel resultsPanel = new JPanel(false);
		resultsPanel.setLayout(new GridLayout(1, 1));

		arabilitySplitPane.add(resultsPanel);

		// Add split pane to the frame. 
		lcsFrame.add(arabilitySplitPane);

		// Display the window.
		lcsFrame.pack();
		lcsFrame.setVisible(true);
		lcsFrame.setExtendedState(lcsFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}
}
