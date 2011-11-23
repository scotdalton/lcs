/**
 * 
 */
package edu.nyu.cs.sysproj.arability.utility;

import static edu.nyu.cs.sysproj.arability.utility.Configuration.*;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import edu.nyu.cs.sysproj.arability.Image;
import edu.nyu.cs.sysproj.arability.utility.google_earth.GoogleEarth;
import edu.nyu.cs.sysproj.arability.utility.google_earth.GoogleEarthFactory;
import edu.nyu.cs.sysproj.arability.utility.kml.Camera;
import edu.nyu.cs.sysproj.arability.utility.kml.Document;
import edu.nyu.cs.sysproj.arability.utility.kml.Kml;
import edu.nyu.cs.sysproj.arability.utility.kml.Placemark;

/**
 * @author Scot Dalton
 *
 */
public class ImageFactory {
	public static Image getImage(Kml kml, int cropFactor, 
			int waitForKml) throws Exception {
		return getImage(kml, cropFactor, waitForKml, null);
	}
	
	public static Image getImage(Kml kml, int cropFactor, 
			int waitForKml, Date date) throws Exception {
		Image image = null;
		String kmlFileName = TMP_KML_DIRECTORY+"/kml.kml";
		kml.persistToFile(kmlFileName);
		GoogleEarth googleEarth = GoogleEarthFactory.getGoogleEarth();
		googleEarth.launch();
		googleEarth.openKml(kmlFileName, waitForKml);
		image = getScreenShot(cropFactor, date);
		googleEarth.destroy();
		return image;
	}
	
	public static List<Image> getImagesForDates(List<Date> dates, 
			float longitude, float latitude, String address, 
			int cropFactor) throws Exception {
		List<Image> images = Lists.newArrayList();
		for(int index=0; index<dates.size(); index++) {
			Date date = dates.get(index);
			images.add(getImage(
				getKml(date, longitude, latitude, address), 
					cropFactor, waitForKml(index), date));
		}
		return images;
	}
	
	private static int waitForKml(int index) {
		return (index == 0) ? 10000:10000;
	}
	
	private static Image getScreenShot(int cropFactor, Date date) throws AWTException {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		Rectangle rectangle = new Rectangle(cropFactor, cropFactor, 
			screenSize.width-cropFactor, screenSize.height-cropFactor);
		Robot robot = new Robot();
		robot.setAutoWaitForIdle(true);
		// Wait for 4 seconds.
		robot.delay(4000);
		return new Image(robot.createScreenCapture(rectangle), date);
	}
	
	private static Kml getKml(Date date, float longitude, float latitude, String address) {
		if (address == null)
			address = longitude + ", " + latitude;
		SimpleDateFormat dateFormat = 
			new SimpleDateFormat("' in 'yyyy-MM-dd");
		List<Placemark> placemarks = new ArrayList<Placemark>();
		// Create the camera
		Camera camera = new Camera.Builder().date(date).
			latitude(latitude).longitude(longitude).build();
		// Add the date to the name
		String imageName = address + dateFormat.format(date);
		// Create and add the placemark
		placemarks.add(new Placemark.Builder().name(imageName).
			camera(camera).build());
		Document document = 
			new Document.Builder().name(imageName).
			placemarks(placemarks).build();
		List<Document> documents = new ArrayList<Document>();
		documents.add(document);
		return new Kml.Builder().documents(documents).build();
	}
}
