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
		"META-INF/cropland.properties";

	public CroplandModule() {
		super(PROPERTIES_FILE);
	}
}