/**
 * 
 */
package edu.nyu.cs.sysproj.arability.utility.google_earth;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author Scot Dalton
 *
 */
public class MacOsxGoogleEarth extends GoogleEarth {
	private final static List<String> MACOSX_LAUNCH_COMMAND = 
		Lists.newArrayList("open", "-a", "Google Earth");
//		Lists.newArrayList("/Applications/Google Earth.app/Contents/MacOS/Google Earth");
	private final static List<String> MACOSX_OPEN_KML_COMMAND = 
		MACOSX_LAUNCH_COMMAND;
//		Lists.newArrayList("open", "-a", "Google Earth");
	protected MacOsxGoogleEarth() {
		super(MACOSX_LAUNCH_COMMAND, MACOSX_OPEN_KML_COMMAND);
	}
	
	public void waitForLaunch() throws Exception {
		// Wait for 1 seconds to launch app.
		Thread.sleep(1000);
	}
	
	public void waitForKml(int waitForKml) throws Exception {
		// Wait for 5 seconds to load the KML.
		Thread.sleep(waitForKml);
	}
}
