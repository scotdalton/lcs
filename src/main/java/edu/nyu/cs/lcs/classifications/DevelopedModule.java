/**
 * 
 */
package edu.nyu.cs.lcs.classifications;

/**
 * @author Scot Dalton
 *
 */
public class DevelopedModule extends ClassificationModule {
	private final static String PROPERTIES_FILE = 
		"src/main/resources/META-INF//developed.properties";

	public DevelopedModule() {
			super(PROPERTIES_FILE);
	}
}