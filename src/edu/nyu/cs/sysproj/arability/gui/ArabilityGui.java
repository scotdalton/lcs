/**
 * 
 */
package edu.nyu.cs.sysproj.arability.gui;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import edu.nyu.cs.sysproj.arability.TrainedModel;

/**
 * @author Scot Dalton
 *
 */
public class ArabilityGui {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Set up training data.
		try {
			TrainedModel.getTrainedModel();
		} catch (Exception e) {
			System.exit(1);
		}
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});	
	}

	private static void createAndShowGUI() {
		//Create and set up the window.
		JFrame frame = new JFrame("Arability Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set up latLng tab.
		// Compare action
		Action latLngCompare = new Compare();
		latLngCompare.putValue(Action.ACTION_COMMAND_KEY, "latLng");
		// Elements
		JLabel latitudeLabel = new JLabel("Latitude:");
		JLabel longitudeLabel = new JLabel("Longitude:");
		latitudeLabel.setPreferredSize(new Dimension(50, 50));
		longitudeLabel.setPreferredSize(new Dimension(50, 50));
		JTextField latitudeTextField = new JTextField(50);
		JTextField longitudeTextField = new JTextField(50);
		JButton latLngButton = new JButton(latLngCompare);
		// Input Panel
		JComponent latLngInputPanel = new JPanel(false);
		latLngInputPanel.setLayout(new GridLayout(2, 2));
		latLngInputPanel.add(latitudeLabel);
		latLngInputPanel.add(latitudeTextField);
		latLngInputPanel.add(longitudeLabel);
		latLngInputPanel.add(longitudeTextField);
		// Panel
		JComponent latLngPanel = new JPanel(false);
		latLngPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		latLngPanel.setLayout(new GridLayout(2, 1));
		latLngPanel.add(latLngInputPanel);
		latLngPanel.add(latLngButton);
		
		// Set up address tab.
		// Compare action
		Action addressCompare = new Compare();
		addressCompare.putValue(Action.ACTION_COMMAND_KEY, "address");
		// Elements
		JLabel addressLabel = new JLabel("Address:");
		addressLabel.setPreferredSize(new Dimension(100, 50));
		JTextField addressTextField = new JTextField(50);
		JButton addressButton = new JButton(addressCompare);
		// Input Panel
		JComponent addressInputPanel = new JPanel(false);
		addressInputPanel.setLayout(new GridLayout(1, 2));
		addressInputPanel.add(addressLabel);
		addressInputPanel.add(addressTextField);
		// Panel
		JComponent addressPanel = new JPanel(false);
		addressPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		LayoutManager addresslayoutManager = new GridLayout(2, 1);
		addressPanel.setLayout(addresslayoutManager);
		addressPanel.add(addressInputPanel);
		addressPanel.add(addressButton);
		
		// Set up settings tab.
		// Elements
		JLabel waitTimeLabel = new JLabel("Seconds to wait for Google Earth to render:");
		waitTimeLabel.setPreferredSize(new Dimension(50, 50));
		JSlider waitTimeSlider = new JSlider(JSlider.HORIZONTAL, 0, 60, 15);
		waitTimeSlider.setMajorTickSpacing(10);
		waitTimeSlider.setMinorTickSpacing(1);
		waitTimeSlider.setPaintTicks(true);
		waitTimeSlider.setPaintLabels(true);
		JLabel cropFactorLabel = new JLabel("Number of pixels to crop in the screenshot:");
		cropFactorLabel.setPreferredSize(new Dimension(50, 75));
		JSlider cropFactorSlider = new JSlider(JSlider.HORIZONTAL, 0, 200, 50);
		cropFactorSlider.setMajorTickSpacing(10);
		cropFactorSlider.setMinorTickSpacing(1);
		cropFactorSlider.setPaintTicks(true);
		cropFactorSlider.setPaintLabels(true);
		// Panel
		JComponent settingsPanel = new JPanel(false);
		settingsPanel.setName("Settings");
		LayoutManager settingslayoutManager = new GridLayout(2, 2);
		settingsPanel.setLayout(settingslayoutManager);
		settingsPanel.add(waitTimeLabel);
		settingsPanel.add(waitTimeSlider);
		settingsPanel.add(cropFactorLabel);
		settingsPanel.add(cropFactorSlider);
	
		
		// Add tabs to the tabbed pane.
		JTabbedPane arabilityTabbedPane = new JTabbedPane();
		arabilityTabbedPane.addTab("Compare by Lat/Lng", latLngPanel);
		arabilityTabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		arabilityTabbedPane.addTab("Compare by Address", addressPanel);
		arabilityTabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		arabilityTabbedPane.addTab("Settings", settingsPanel);
		arabilityTabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		
		// Add tabbed pane to the top of the split pane
		JSplitPane arabilitySplitPane = 
			new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		arabilitySplitPane.add(arabilityTabbedPane);
		
		// Add results pane to the bottom of the split pane
		JPanel resultsPanel = new JPanel(false);
		resultsPanel.setLayout(new GridLayout(3, 1));

		arabilitySplitPane.add(resultsPanel);

		// Add split pane to the frame. 
		frame.add(arabilitySplitPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}
}
