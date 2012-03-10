/**
 * 
 */
package edu.nyu.cs.lcs.gui;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import edu.nyu.cs.lcs.TrainedModel;

/**
 * @author Scot Dalton
 *
 */
public class SaveRegions extends SaveImages {

	/**
	 * @param trainedModel
	 */
	public SaveRegions(TrainedModel trainedModel) {
		super(trainedModel);
	}

	private static final long serialVersionUID = 821354286645168569L;

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton saveButton = (JButton) event.getSource();
		setComponentsFromButton(saveButton);
		save("Save Region", regionAddress);
	}
}
