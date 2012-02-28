/**
 * 
 */
package edu.nyu.cs.sysproj.arability.gui;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

/**
 * @author Scot Dalton
 *
 */
public class SaveRegions extends SaveImages {

	private static final long serialVersionUID = 821354286645168569L;

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton saveButton = (JButton) event.getSource();
		setComponentsFromButton(saveButton);
		save("Save Region", regionAddress);
	}
}
