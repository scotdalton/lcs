/**
 * 
 */
package edu.nyu.cs.sysproj.arability.utility;

import static edu.nyu.cs.sysproj.arability.utility.Configuration.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import edu.nyu.cs.sysproj.arability.Image;

/**
 * @author Scot Dalton
 *
 */
public class ImageGrabber {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		double longitudeStart = -5.0;
		double longitudeEnd = 36.0;
		double latitudeStart = 35.0;
		double latitudeEnd = 6.0;
		int numberOfDates = 1;
		int yearlyInterval = -1;
		for(String arg: args) {
			if (arg.startsWith("longitudeStart="))
				longitudeStart=Double.valueOf(arg.split("=")[1]);
			if (arg.startsWith("longitudeEnd="))
				longitudeEnd=Double.valueOf(arg.split("=")[1]);
			if (arg.startsWith("latitudeStart="))
				latitudeStart=Double.valueOf(arg.split("=")[1]);
			if (arg.startsWith("latitudeEnd="))
				latitudeEnd=Double.valueOf(arg.split("=")[1]);
			if (arg.startsWith("numberOfDates="))
				numberOfDates=Integer.valueOf(arg.split("=")[1]);
			if (arg.startsWith("yearlyInterval="))
				yearlyInterval=Integer.valueOf(arg.split("=")[1]);
		}
		Calendar cal = Calendar.getInstance();
		List<Date> dates = Lists.newArrayList();
		Date now = cal.getTime();
		dates.add(now);
		for(int i=1; i < numberOfDates; i++) {
			cal.add(Calendar.YEAR, yearlyInterval);
			dates.add(cal.getTime());
		}
		SimpleDateFormat dateFormat = 
			new SimpleDateFormat("yyyy-MM-dd");
		for(double longitude = longitudeStart; 
				longitude < longitudeEnd; 
					longitude = longitude + .125) {
			for(double latitude = latitudeStart; 
					latitude > latitudeEnd; 
						latitude = latitude - .125) {
				List<Image> images = 
					ImageFactory.getImagesForDates(dates, (float)longitude, (float)latitude, null, 5);
				for(Image image: images) {
					if (image.isValid()) {
						String imageName = longitude + "_" + latitude;
						File grabbedDir = 
							new File(TMP_IMAGE_PATH + "/grabbed/" +dateFormat.format(image.getDate()));
						if(!grabbedDir.exists()) grabbedDir.mkdirs();
						String fileName = 
							grabbedDir.getAbsolutePath() + "/" + imageName+".png";
						System.out.println(fileName);
						persistImage(fileName, image.getRenderedImage());
					}
				}
			}
		}
	}
}