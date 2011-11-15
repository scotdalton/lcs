/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import static edu.nyu.cs.sysproj.google_earth.TestUtility.persistImage;
import static edu.nyu.cs.sysproj.google_earth.Utility.IMAGE_PATH;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import edu.nyu.cs.sysproj.google_earth.image_capture.ImageFactory;
import edu.nyu.cs.sysproj.google_earth.kml.Camera;
import edu.nyu.cs.sysproj.google_earth.kml.Document;
import edu.nyu.cs.sysproj.google_earth.kml.Kml;
import edu.nyu.cs.sysproj.google_earth.kml.Placemark;

/**
 * @author Scot Dalton
 *
 */
public class TestTool {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// Train model at the start.		
		TrainedModel.getTrainedModel();
		double latitude = Double.parseDouble(args[0]);
		double longitude = Double.parseDouble(args[1]);
		String name = "Test Place";
		int numberOfDates = 2;
		int yearlyInterval = -2;
		Calendar cal = Calendar.getInstance();
		List<Date> dates = new ArrayList<Date>();
		dates.add(cal.getTime());
		SimpleDateFormat dateFormat = 
			new SimpleDateFormat("' in 'yyyy-MM-dd");
		for(int i=0; i < numberOfDates; i++) {
			cal.add(Calendar.YEAR, yearlyInterval);
			dates.add(cal.getTime());
		}
		Map<String, UnknownImage> imageMap = Maps.newHashMap();
		for (Date date : dates) {
			List<Placemark> placemarks = new ArrayList<Placemark>();
			// Create the camera
			Camera camera = new Camera.Builder().date(date).
				latitude(latitude).longitude(longitude).build();
			// Add the date to the name
			String imageName = name + dateFormat.format(date);
			// Create and add the placemark
			placemarks.add(new Placemark.Builder().name(imageName).
				camera(camera).build());
			Document document = 
				new Document.Builder().name(imageName).
				placemarks(placemarks).build();
			List<Document> documents = new ArrayList<Document>();
			documents.add(document);
			Kml kml = new Kml.Builder().documents(documents).build();
			String fileName = IMAGE_PATH + "/captured/"+imageName+".png";
			Image image = ImageFactory.getImage(kml, 5);
			persistImage(fileName, image.getRenderedImage());
			try {
				imageMap.put(imageName, new UnknownImage(fileName));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	    Iterator<Map.Entry<String, UnknownImage>> imageIterator = 
	    		imageMap.entrySet().iterator();
	    while (imageIterator.hasNext()) {
	        Map.Entry<String, UnknownImage> pairs = imageIterator.next();
	        System.out.println(pairs.getKey() + " percentage arable: " + pairs.getValue().getArablePercentage());
	    }	
	}
}