/**
 * 
 */
package edu.nyu.cs.lcs.utility;

import static edu.nyu.cs.lcs.utility.Configuration.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import edu.nyu.cs.lcs.Image;
import edu.nyu.cs.lcs.utility.google_earth.GoogleEarth;
import edu.nyu.cs.lcs.utility.google_earth.GoogleEarthFactory;
import edu.nyu.cs.lcs.utility.kml.Camera;
import edu.nyu.cs.lcs.utility.kml.Document;
import edu.nyu.cs.lcs.utility.kml.Kml;
import edu.nyu.cs.lcs.utility.kml.Placemark;

/**
 * @author Scot Dalton
 *
 */
public class ImageFactory {
	public static Image getImage(Kml kml, int xCropFactor, int yCropFactor, 
			int waitForKml) throws Exception {
		return getImage(kml, xCropFactor, yCropFactor, waitForKml, null);
	}
	
	public static Image getImage(Kml kml, int xCropFactor, int yCropFactor, 
			int waitForKml, Date date) throws Exception {
		Image image = null;
		String kmlFileName = TMP_KML_DIRECTORY+"/kml.kml";
		kml.persistToFile(kmlFileName);
		GoogleEarth googleEarth = GoogleEarthFactory.getGoogleEarth();
		googleEarth.launch();
		googleEarth.openKml(kmlFileName, waitForKml);
		image = Image.takeScreenShot(xCropFactor, yCropFactor, date, 4000);
		googleEarth.destroy();
		return image;
	}
	
	public static List<Image> getImagesForDates(List<Date> dates, 
			float longitude, float latitude, String address, 
			int xCropFactor, int yCropFactor) throws Exception {
		return getImagesForDates(dates, longitude, latitude, address, xCropFactor, yCropFactor, waitForKml()); 
	}
	
	public static List<Image> getImagesForDates(List<Date> dates, 
			double longitude, double latitude, String address, 
			int xCropFactor, int yCropFactor, int waitTime) throws Exception {
		List<Image> images = Lists.newArrayList();
		for(Date date: dates)
			images.add(getImage(
				getKml(date, longitude, latitude, address), 
					xCropFactor, yCropFactor, waitTime, date));
		return images;
	}
	
	private static int waitForKml() {
		return 15000;
	}
	
	private static Kml getKml(Date date, double longitude, double latitude, String address) {
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
