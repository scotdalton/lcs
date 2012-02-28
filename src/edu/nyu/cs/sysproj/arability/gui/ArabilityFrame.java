/**
 * 
 */
package edu.nyu.cs.sysproj.arability.gui;

import java.util.Collections;
import java.util.Map;

import javax.swing.JFrame;

/**
 * @author Scot Dalton
 *
 */
public class ArabilityFrame extends JFrame {

	private static final long serialVersionUID = 4712853905673136405L;
	private Map<String, String> imageFileNames;
	
	public ArabilityFrame() {
		super("Classify Arability");
	}

	/**
	 * @param imageFileNames the imageFileNames to set
	 */
	public void setImageFileNames(Map<String, String> imageFileNames) {
		this.imageFileNames = 
			Collections.unmodifiableMap(imageFileNames);
	}

	/**
	 * @return the imageFileNames
	 */
	public Map<String, String> getImageFileNames() {
		return Collections.unmodifiableMap(imageFileNames);
	}
}