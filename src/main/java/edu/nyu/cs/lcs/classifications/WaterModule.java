/**
 * 
 */
package edu.nyu.cs.lcs.classifications;

/**
 * @author Scot Dalton
 *
 */
public class WaterModule extends ClassificationModule {
	private final static String PROPERTIES_FILE = 
		"src/main/resources/META-INF//water.properties";

	public WaterModule() {
		super(PROPERTIES_FILE);
	}
}