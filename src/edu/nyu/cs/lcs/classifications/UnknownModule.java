/**
 * 
 */
package edu.nyu.cs.lcs.classifications;

/**
 * @author Scot Dalton
 *
 */
public class UnknownModule extends ClassificationModule {
	private final static String PROPERTIES_FILE = 
		"./config/unknown.properties";

	public UnknownModule() {
			super(PROPERTIES_FILE);
	}
}