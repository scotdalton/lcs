/**
 * 
 */
package edu.nyu.cs.lcs.classifications;

/**
 * @author Scot Dalton
 *
 */
public class DesertModule extends ClassificationModule {
	private final static String PROPERTIES_FILE = 
		"META-INF/desert.properties";

	public DesertModule() {
			super(PROPERTIES_FILE);
	}
}