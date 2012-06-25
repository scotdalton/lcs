/**
 * 
 */
package edu.nyu.cs.lcs.utility.google_earth;

/**
 * @author Scot Dalton
 *
 */
public class GoogleEarthFactory {
	static GoogleEarth googleEarth;

	public static GoogleEarth getGoogleEarth() {
		if(googleEarth == null) {
			if(System.getProperty("os.name").equals("Mac OS X")) {
				googleEarth = new MacOsxGoogleEarth();
			} else {
				throw new UnsupportedOperationException("Your operating system is not supported.");
			}
		}
		return googleEarth;
	}
}