/**
 * 
 */
package edu.nyu.cs.lcs.utility;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.google.common.collect.Lists;

import edu.nyu.cs.lcs.Image;
import edu.nyu.cs.lcs.classifications.Classification;
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
public class ImageUtil {
	private static String kmlFilename;
	
	public static final Image TRANSPARENT_IMAGE = new Image(
	"src/main/resources/META-INF/transparent.png");

	public static final Image TOO_LARGE_IMAGE = new Image(
	"src/main/resources/META-INF/transparent.png");

	public static final Image INVALID_IMAGE = new Image(
	"src/main/resources/META-INF/transparent.png");

	
	public static Image takeScreenShot(int xCropFactor, int yCropFactor, Date date, int delay) throws AWTException {
		int width = (int) (getScreenWidth() - xCropFactor);
		int height = (int) (getScreenHeight() - yCropFactor);
		Rectangle rectangle = new Rectangle(xCropFactor/2, yCropFactor/2, 
			width, height);
		Robot robot = new Robot();
		robot.setAutoWaitForIdle(true);
		// Wait for 4 seconds.
		robot.delay(delay);
		return new Image(robot.createScreenCapture(rectangle), date);
	}
	
	public static double getScreenWidth() {
		return Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	}
	
	public static double getScreenHeight() {
		return Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	}
	
	public static Image getImageForColor(int red, int green, int blue, int alpha) {
		BufferedImage bufferedImage = 
			new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = bufferedImage.getGraphics();
		graphics.setColor(new Color(red, green, blue, alpha));
		graphics.fillRect(0, 0, 100, 100);
		graphics.dispose();
		return new Image(bufferedImage);
	}
	
	public static Image getImageKeyForClassification(Classification classification) {
		Image key = getImageForColor(classification.getRed(), classification.getGreen(), classification.getBlue(), classification.getAlpha());
		BufferedImage bufferedImage = key.getAsBufferedImage();
		Graphics graphics = bufferedImage.getGraphics();
		graphics.setColor(Color.black);
		graphics.setFont(new Font("Serif", Font.BOLD, 10));
		FontMetrics fontMetrics = graphics.getFontMetrics();
		int stringWidth = 
			fontMetrics.stringWidth(classification.toString());
		int x = key.getWidth() - stringWidth;
		int y = fontMetrics.getHeight();
		graphics.drawString(classification.toString(), x, y);
		graphics.dispose();
		//graphics.drawChars(classification.toString().toCharArray(), key.getMinX(), classification.toString().toCharArray().length, 0, 50);
		return new Image(bufferedImage);
	}
	
	public static Image getImageForRegion(File imageDirectory, int columns, int rows) {
		List<File> imageFiles = 
			FileUtil.getRegionSort(imageDirectory);
		if (imageFiles.size() > 20)
			return TOO_LARGE_IMAGE;
		// Assume all images are the same size and date
		Image firstImage = new Image(imageFiles.get(0));
		Date regionImageDate = firstImage.getDate();
		int regionImageWidth = firstImage.getWidth() * columns;
		int regionImageHeight = firstImage.getHeight() * rows;
		BufferedImage bufferedImage = 
			new BufferedImage(regionImageWidth, regionImageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = bufferedImage.getGraphics();
		int column = 0;
		int row = 0;
		// Start at top right (NE) and move down, then left
		// Last image is lower left (SW)
		for(int index = 0; index < imageFiles.size(); index++) {
			if(rows <= index && index % rows == 0) {
				// Increment column (left) and reset row
				column ++; 
				row = 0;
			}
			Image image = new Image(imageFiles.get(index));
			int x = firstImage.getWidth() * (columns - 1 - column);
			int y = image.getHeight() * row;
			graphics.drawImage(image.getAsBufferedImage(), x, y, null);
			row++;
		}
		return new Image(bufferedImage, regionImageDate);
	}

	public static Image getImage(Kml kml, int xCropFactor, int yCropFactor, 
			int waitForKml) throws Exception {
		return getImage(kml, xCropFactor, yCropFactor, waitForKml, null);
	}
	
	public static Image getImage(Kml kml, int xCropFactor, int yCropFactor, 
			int waitForKml, Date date) throws Exception {
		Image image = null;
		kml.persistToFile(getKmlFilename());
		GoogleEarth googleEarth = GoogleEarthFactory.getGoogleEarth();
		googleEarth.launch();
		googleEarth.openKml(getKmlFilename(), waitForKml);
		image = ImageUtil.takeScreenShot(xCropFactor, yCropFactor, date, 4000);
		googleEarth.destroy();
		return image;
	}
	
	public static String getKmlFilename() 
			throws FileNotFoundException, IOException {
		if (kmlFilename == null) {
			Properties kmlProperties = new Properties();
			kmlProperties.load(new FileReader("src/main/resources/META-INF//kml.properties"));
			kmlFilename = kmlProperties.getProperty("directory")+ 
			"/"+ kmlProperties.getProperty("file");
		}
		return kmlFilename;
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