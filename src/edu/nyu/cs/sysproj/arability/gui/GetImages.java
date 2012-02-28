/**
 * 
 */
package edu.nyu.cs.sysproj.arability.gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.google.common.collect.Maps;

import edu.nyu.cs.sysproj.arability.Image;
import edu.nyu.cs.sysproj.arability.utility.Configuration;
import edu.nyu.cs.sysproj.arability.utility.Geocoder;
import edu.nyu.cs.sysproj.arability.utility.ImageFactory;

/**
 * @author Scot Dalton
 *
 */
public class GetImages extends ArabilityAction {

	private static final long serialVersionUID = -5852278101756597007L;

	public GetImages() {
		super("Get Images");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton imagesButton = (JButton) event.getSource();
		setComponentsFromButton(imagesButton);
		if("latLng".equals(event.getActionCommand())) {
//			TODO: Handle empty strings.
			try {
				Geocoder geocoder = new Geocoder(latitude, longitude);
				address = geocoder.getAddress();
				if(address == null) {
					address = 
						"Location at latitude " + latitude + " and longitude " + longitude;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if ("address".equals(event.getActionCommand())) {
//			TODO: Handle empty string.
			try {
				Geocoder geocoder = new Geocoder(address);
				latitude = geocoder.getLatitude();
				longitude = geocoder.getLongitude();
				address = geocoder.getAddress();
			} catch (Exception e) {
				address = null;
				e.printStackTrace();
			}
		}
		if (address == null) {
			JOptionPane.showMessageDialog(arabilityTabbedPane, "No address found.");
		} else {
			addressTextField.setText(address);
			addressTextField.repaint();
			int numberOfDates = 2;
			int yearlyInterval = -5;
			Calendar cal = Calendar.getInstance();
			List<Date> dates = new ArrayList<Date>();
			Date now = cal.getTime();
			dates.add(now);
			for(int i=1; i < numberOfDates; i++) {
				cal.add(Calendar.YEAR, yearlyInterval);
				dates.add(cal.getTime());
			}
			List<Image> images;
			try {
				Collections.reverse(dates);
				images = ImageFactory.getImagesForDates(dates, longitude, latitude, address, xCropFactor, yCropFactor, waitTime);
				Map<String, String> imageFileNames = Maps.newLinkedHashMap();
				Map<String, Image> capturedImages = Maps.newLinkedHashMap();
				SimpleDateFormat dateFormat = new SimpleDateFormat("' circa 'yyyy");
				for(Image image: images) {
					String imageName = address + dateFormat.format(image.getDate());
					String fileName = Configuration.TMP_IMAGE_PATH + "/captured/"+imageName+".png";
					image.persist(fileName);
					imageFileNames.put(imageName, fileName);
					capturedImages.put(imageName, image);
				}
				arabilityFrame.setImageFileNames(imageFileNames);
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
}
