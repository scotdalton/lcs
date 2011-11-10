/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth.image_capture;

import static edu.nyu.cs.sysproj.google_earth.Utility.KML_DIRECTORY;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

import edu.nyu.cs.sysproj.google_earth.Image;
import edu.nyu.cs.sysproj.google_earth.kml.Kml;

/**
 * @author Scot Dalton
 *
 */
public class ImageFactory {
	public static Image getImage(Kml kml) throws Exception {
		Image image = null;
		String fileName = KML_DIRECTORY+"/kml.kml";
		kml.persistToFile(fileName);
		Runtime runtime = Runtime.getRuntime();
		String[] command = {"open", "-a", "Google Earth", fileName};
		runtime.exec(command);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		Rectangle rectangle = 
			new Rectangle(0, 0, screenSize.width, screenSize.height);
		Robot robot = new Robot();
		robot.setAutoWaitForIdle(true);
		// Wait for Google Earth to load.
		robot.delay(15000);
		image = new Image(robot.createScreenCapture(rectangle));
		return image;
	}
}
