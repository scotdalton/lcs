/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

import static edu.nyu.cs.sysproj.arability.utility.Configuration.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import edu.nyu.cs.sysproj.arability.Image;
import edu.nyu.cs.sysproj.arability.utility.ImageFactory;
import edu.nyu.cs.sysproj.arability.utility.kml.Camera;
import edu.nyu.cs.sysproj.arability.utility.kml.Document;
import edu.nyu.cs.sysproj.arability.utility.kml.Kml;
import edu.nyu.cs.sysproj.arability.utility.kml.Placemark;

/**
 * @author Scot Dalton
 *
 */
public class ImageFactoryTest {
	@Test
	public void testGetImage() throws Exception {
		double latitude = -1.30785;
		double longitude = 36.84931;
		String name = "Nairobi Kenya";
		int numberOfDates = 1;
		int yearlyInterval = -2;
		Calendar cal = Calendar.getInstance();
		List<Date> dates = new ArrayList<Date>();
//		dates.add(cal.getTime());
		SimpleDateFormat dateFormat = 
			new SimpleDateFormat("' in 'yyyy-MM-dd");
		for(int i=0; i < numberOfDates; i++) {
			cal.add(Calendar.YEAR, yearlyInterval);
			dates.add(cal.getTime());
		}
		List<Placemark> placemarks = new ArrayList<Placemark>();
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
		Document document = 
			new Document.Builder().name("CSCI-GA 3033-011").
			placemarks(placemarks).build();
		List<Document> documents = new ArrayList<Document>();
		documents.add(document);
		Kml kml = new Kml.Builder().documents(documents).build();
		Image image = ImageFactory.getImage(kml, 5, 5000);
		String fileName = IMAGE_PATH + "/captured/image.png";
		image.persist(fileName);
	}
}
