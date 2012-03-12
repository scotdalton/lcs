/**
 * 
 */
package edu.nyu.cs.lcs.gui;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * @author Scot Dalton
 *
 */
public abstract class LcsAction extends AbstractAction {

	private static final long serialVersionUID = -7738053606176155146L;
	LcsFrame lcsFrame;
	JSplitPane arabilitySplitPane;
	JTabbedPane arabilityTabbedPane;
	JPanel addressPanel;
	JPanel addressInputPanel;
	JTextField addressTextField;
	String address;
	JPanel regionPanel;
	JPanel regionInputPanel;
	JTextField regionTextField;
	String regionAddress;
	JPanel latLngPanel;
	JPanel latLngInputPanel;
	JTextField latitudeTextField;
	JTextField longitudeTextField;
	float latitude;
	float longitude;
	JPanel settingsPanel;
	JSlider waitTimeSlider; 
	JSlider xCropFactorSlider;
	JSlider yCropFactorSlider;
	int waitTime;
	int xCropFactor;
	int yCropFactor;
	JPanel resultsPanel;
	Double croplandLoss;

	/**
	 * @param string
	 */
	public LcsAction(String string) {
		super(string);
	}
	
	void setComponentsFromButton(JButton button) {
		lcsFrame = (LcsFrame) SwingUtilities.getRoot(button);
		arabilitySplitPane = (JSplitPane) SwingUtilities.
			getAncestorOfClass(JSplitPane.class, button);
		arabilityTabbedPane = (JTabbedPane)arabilitySplitPane.getComponent(1);
		addressPanel = (JPanel) arabilityTabbedPane.getComponent(0);
		addressInputPanel = (JPanel) addressPanel.getComponent(0);
		addressTextField = (JTextField) addressInputPanel.getComponent(1);
		address = addressTextField.getText();
		regionPanel = (JPanel) arabilityTabbedPane.getComponent(1);
		regionInputPanel = (JPanel) regionPanel.getComponent(0);
		regionTextField = (JTextField) regionInputPanel.getComponent(1);
		regionAddress = regionTextField.getText();
		latLngPanel = (JPanel) arabilityTabbedPane.getComponent(2);
		latLngInputPanel = (JPanel) latLngPanel.getComponent(0);
		latitudeTextField = (JTextField) latLngInputPanel.getComponent(1);
		longitudeTextField = (JTextField) latLngInputPanel.getComponent(3);
		String latitudeString = latitudeTextField.getText();
		String longitudeString = longitudeTextField.getText();
		if(!(latitudeString.isEmpty() || longitudeString.isEmpty())) {
			latitude = Float.valueOf(latitudeString);
			longitude = Float.valueOf(longitudeString);
		}
		settingsPanel = (JPanel) arabilityTabbedPane.getComponent(3);
		waitTimeSlider = (JSlider) settingsPanel.getComponent(1);
		xCropFactorSlider = (JSlider) settingsPanel.getComponent(3);
		yCropFactorSlider = (JSlider) settingsPanel.getComponent(5);
		waitTime = waitTimeSlider.getValue() * 1000;
		xCropFactor = xCropFactorSlider.getValue();
		yCropFactor = yCropFactorSlider.getValue();
		resultsPanel = (JPanel) arabilitySplitPane.getComponent(2);
	}

	
}