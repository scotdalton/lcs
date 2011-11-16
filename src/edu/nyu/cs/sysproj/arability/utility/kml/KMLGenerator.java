/**
 * 
 */
package edu.nyu.cs.sysproj.arability.utility.kml;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @author Scot Dalton
 *
 */
public class KMLGenerator {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int numberOfDates = 5;
		int yearlyInterval = -2;
		Calendar cal = Calendar.getInstance();
		List<Date> dates = new ArrayList<Date>();
		dates.add(cal.getTime());
		SimpleDateFormat dateFormat = 
			new SimpleDateFormat("' in 'yyyy-MM-dd");
		for(int i=1; i < numberOfDates; i++) {
			cal.add(Calendar.YEAR, yearlyInterval);
			dates.add(cal.getTime());
		}
		List<Placemark> placemarks = new ArrayList<Placemark>();
		for (String arg:args) {
			String[] kmlInputs = arg.split(",");
			double latitude = Double.parseDouble(kmlInputs[0]);
			double longitude = Double.parseDouble(kmlInputs[1]);
			String name = kmlInputs[2];
			for (Date date : dates) {
				// Create the camera
				Camera camera = new Camera.Builder().date(date).
					latitude(latitude).longitude(longitude).build();
				// Add the date to the name
				String placemarkName = name + dateFormat.format(date);
				// Create and add the placemark
				placemarks.add(new Placemark.Builder().name(placemarkName).
					camera(camera).build());
			}
		}
//		Document document = 
//			new Document.Builder().name(name).
//				placemarks(placemarks).build();
		Document document = 
			new Document.Builder().name("CSCI-GA 3033-011").
			placemarks(placemarks).build();
//		Document document = 
//			new Document.Builder().name("Test Data").
//			placemarks(placemarks).build();
		List<Document> documents = new ArrayList<Document>();
		documents.add(document);
		Kml kml = new Kml.Builder().documents(documents).build();
		try {
//			kml.persistToFile(name.replace(" ", "_")+".kml");
			kml.persistToFile("csci-ga3033-011.kml");
//			kml.persistToFile("training_data.kml");
//			kml.persistToFile("test_data.kml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
