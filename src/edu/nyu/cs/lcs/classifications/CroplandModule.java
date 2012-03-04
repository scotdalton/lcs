/**
 * 
 */
package edu.nyu.cs.lcs.classifications;

/**
 * @author Scot Dalton
 *
 */
public class CroplandModule extends ClassificationModule {
	private final static String PROPERTIES_FILE = 
		"./config/cropland.properties";

	public CroplandModule() {
		super(PROPERTIES_FILE);
	}
}