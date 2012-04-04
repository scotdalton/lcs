/**
 * 
 */
package edu.nyu.cs.lcs.gui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import edu.nyu.cs.lcs.Image;
import edu.nyu.cs.lcs.Region;
import edu.nyu.cs.lcs.utility.Geocoder;
import edu.nyu.cs.lcs.utility.ImageUtil;
import edu.nyu.cs.lcs.utility.google_earth.GoogleEarth;

/**
 * @author Scot Dalton
 *
 */
public class GetRegion extends GetImage {

	private static final long serialVersionUID = -4098880070895817401L;
	private double northLatitude = 0;
	private double eastLongitude = 0;
	private double southLatitude = 0;
	private double westLongitude = 0;

	public GetRegion(File persistDirectory) {
		super(persistDirectory);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton imagesButton = (JButton) event.getSource();
		setComponentsFromButton(imagesButton);
		try {
			Geocoder geocoder = new Geocoder(regionAddress);
			northLatitude = 
				Float.valueOf(geocoder.getNortheastLatitude());
			eastLongitude = 
				Float.valueOf(geocoder.getNortheastLongitude());
			southLatitude = 
				Float.valueOf(geocoder.getSouthwestLatitude());
			westLongitude = 
				Float.valueOf(geocoder.getSouthwestLongitude());
			regionAddress = geocoder.getAddress();
		} catch (Exception e) {
			regionAddress = null;
			e.printStackTrace();
		}
		if (regionAddress == null) {
			JOptionPane.showMessageDialog(arabilityTabbedPane, "No address found.");
		} else {
			try {
				regionTextField.setText(regionAddress);
				regionTextField.repaint();
				List<Date> dates = GuiUtil.getDates();
				SimpleDateFormat dateFormat = new SimpleDateFormat("' circa 'yyyy");
				Map<String, String> imageFileNames = Maps.newLinkedHashMap();
				Map<String, Image> capturedImages = Maps.newLinkedHashMap();
				double longitudeStepFactor = getLongitudeStepFactor();
				double latitudeStepFactor = getLatitudeStepFactor();
				List<List<Image>> regionImagesByDate = Lists.newArrayList();
				for(int dateIndex=0; dateIndex<dates.size(); dateIndex++) {
					List<Image> regionImages = Lists.newArrayList();
					regionImagesByDate.add(dateIndex, regionImages);
				}
				int columns = 
					(int) Math.ceil((eastLongitude - westLongitude)/longitudeStepFactor);
				int rows = 
					(int) Math.ceil((northLatitude - southLatitude)/latitudeStepFactor);
				for(double longitude = eastLongitude; 
						longitude > (westLongitude); 
							longitude = longitude - longitudeStepFactor) {
					for(double latitude = northLatitude; 
							latitude > (southLatitude); 
								latitude = latitude - latitudeStepFactor) {
						List<Image> dateImages = 
							ImageUtil.getImagesForDates(dates, 
								longitude, latitude, regionAddress, 
									xCropFactor, yCropFactor, waitTime);
//						for(int dateIndex = 0; dateIndex < dates.size(); dateIndex++) {
//							regionImagesByDate.get(dateIndex).add(dateImages.get(dateIndex));
//						}
//						Persist Images
						for (Image dateImage: dateImages) {
							File regionDateDirectory = 
								getRegionDateDirectory(dateImage.getDate());
							if(!regionDateDirectory.exists())
								regionDateDirectory.mkdirs();
//							if(!regionPropertiesFile.exists()) regionPropertiesFile.createNewFile();
							dateImage.persist(regionDateDirectory.getAbsolutePath() + "/" + latitude + "-" + longitude + ".png");
						}
						// Set/Update Region Properties.
						File regionPropertiesFile = 
							new File(getRegionDirectory().getAbsolutePath() + 
								"/.region.properties");
						FileWriter fileWriter = new FileWriter(regionPropertiesFile);
						fileWriter.write("eastLongitude: "+ eastLongitude);
						fileWriter.write("westLongitude: "+ westLongitude);
						fileWriter.write("northLatitude: "+ northLatitude);
						fileWriter.write("southLatitude: "+ southLatitude);
						fileWriter.write("regionAddress: "+ regionAddress);
						fileWriter.write("xCropFactor: "+ xCropFactor);
						fileWriter.write("yCropFactor: "+ yCropFactor);
						fileWriter.write("waitTime: "+ waitTime);
						fileWriter.write("longitude: "+ longitude);
						fileWriter.write("latitude: "+ latitude);
						fileWriter.flush();
					}
				}
//				for(List<Image> regionImages: regionImagesByDate) {
//					Region region = new Region(regionImages, columns, rows);
//					Image regionImage = region.getImage();
//					String regionName = regionAddress + dateFormat.format(regionImage.getDate());
//					capturedImages.put(regionName, regionImage);
//					String fileName = Configuration.TMP_IMAGE_PATH + "/captured/"+regionName+".png";
//					regionImage.persist(fileName);
//					imageFileNames.put(regionName, fileName);
//				}
				for(Date date: dates) {
					Region region = new Region(getRegionDateDirectory(date), columns, rows);
					Image regionImage = region.getImage();
					String regionName = regionAddress + dateFormat.format(date);
					capturedImages.put(regionName, regionImage);
					String fileName = persistDirectory.getAbsolutePath() + 
						"/"+regionName+".png";
					regionImage.persist(fileName);
					imageFileNames.put(regionName, fileName);
				}
				lcsFrame.setImageFileNames(imageFileNames);
				ImageTabbedPane imageTabbedPane = new ImageTabbedPane(capturedImages);
				resultsPanel.removeAll();
				resultsPanel.add(imageTabbedPane);
				resultsPanel.validate();
				// Enable the other buttons.
				JButton compareButton = 
					(JButton) imagesButton.getParent().getComponent(1);
				if(! compareButton.isEnabled()) {
					compareButton.setEnabled(true);
					compareButton.repaint();
				}
				JButton saveImagesButton = 
					(JButton) imagesButton.getParent().getComponent(2);
				if(! saveImagesButton.isEnabled()) {
					saveImagesButton.setEnabled(true);
					saveImagesButton.repaint();
				}
				JOptionPane.showMessageDialog(arabilityTabbedPane, "Images Grabbed");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private File getRegionDateDirectory(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return new File(getRegionDirectory() + "/" + 
			dateFormat.format(date));
	}
	
	private File getRegionDirectory() {
		return new File(persistDirectory.getAbsoluteFile() + "/" + 
			regionAddress);
	}

	private double getLongitudeStepFactor() {
//		return Configuration.LONGITUDE_STEP_FACTOR;
		LatLng northwestLatLng = new LatLng(northLatitude, westLongitude);
		LatLng northeastLatLng = new LatLng(northLatitude, eastLongitude);
		double regionXDistance = 
			LatLngTool.distance(northwestLatLng, northeastLatLng, 
				LengthUnit.KILOMETER );
		double imageWidth = ImageUtil.getScreenWidth() - xCropFactor;
		double imageXDistance = GoogleEarth.SCALE * imageWidth;
		double xRatio = imageXDistance/regionXDistance;
		return Math.abs((eastLongitude - westLongitude)*xRatio);
	}

	private double getLatitudeStepFactor() {
//		return Configuration.LATITUDE_STEP_FACTOR;
		LatLng northwestLatLng = new LatLng(northLatitude, westLongitude);
		LatLng southwestLatLng = new LatLng(southLatitude, westLongitude);
		double regionYDistance = 
			LatLngTool.distance(northwestLatLng, southwestLatLng, 
				LengthUnit.KILOMETER );
		double imageHeight = ImageUtil.getScreenHeight() - yCropFactor;
		double imageYDistance = GoogleEarth.SCALE * imageHeight;
		double yRatio = imageYDistance/regionYDistance;
		return Math.abs((northLatitude - southLatitude)*yRatio);
	}
}