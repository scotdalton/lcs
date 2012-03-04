/**
 * 
 */
package edu.nyu.cs.lcs.classifications;

/**
 * @author Scot Dalton
 *
 */
public class ForestModule extends ClassificationModule {
	private final static String PROPERTIES_FILE = 
		"./config/forest.properties";

	public ForestModule() {
			super(PROPERTIES_FILE);
	}
}