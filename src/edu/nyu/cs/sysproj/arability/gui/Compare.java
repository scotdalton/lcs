/**
 * 
 */
package edu.nyu.cs.sysproj.arability.gui;

import static edu.nyu.cs.sysproj.arability.utility.Configuration.IMAGE_PATH;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.google.common.collect.Maps;

import edu.nyu.cs.sysproj.arability.Image;
import edu.nyu.cs.sysproj.arability.UnknownImage;
import edu.nyu.cs.sysproj.arability.utility.Geocoder;
import edu.nyu.cs.sysproj.arability.utility.ImageFactory;


/**
 * @author Scot Dalton
 *
 */
public class Compare extends AbstractAction {
	private static final long serialVersionUID = 1L;
	
	public Compare() {
		super("Compare");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton eventButton = (JButton) event.getSource();
		JComponent inputPanel = 
			(JComponent) eventButton.getParent().getComponent(0);
		JSplitPane arabilitySplitPane = 
			(JSplitPane) eventButton.getParent().getParent().getParent();
		JPanel resultsPanel = (JPanel) arabilitySplitPane.getComponent(2);
		JTabbedPane arabilityTabbedPane = 
			(JTabbedPane)arabilitySplitPane.getComponent(1);
		JPanel settingsPanel = 
			(JPanel) arabilityTabbedPane.getComponent(2);
		JSlider waitTimeSlider = (JSlider) settingsPanel.getComponent(1);
		JSlider cropFactorSlider = (JSlider) settingsPanel.getComponent(3);
		int waitTime = waitTimeSlider.getValue() * 1000;
		int cropFactor = cropFactorSlider.getValue();
		String address = null;
		float latitude = 0;
		float longitude = 0;
		if("latLng".equals(event.getActionCommand())) {
			String latitudeString = ((JTextField) inputPanel.getComponent(1)).getText();
			String longitudeString = ((JTextField) inputPanel.getComponent(3)).getText();
//			TODO: Handle empty strings.
			latitude = 
				Float.valueOf(latitudeString);
			longitude = 
				Float.valueOf(longitudeString);
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
			address = 
				((JTextField) inputPanel.getComponent(1)).getText();
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
			JOptionPane.showMessageDialog(inputPanel, "No address found.");
		} else {
			int numberOfDates = 3;
			int yearlyInterval = -2;
			Calendar cal = Calendar.getInstance();
			List<Date> dates = new ArrayList<Date>();
			Date now = cal.getTime();
			dates.add(now);
			for(int i=1; i < numberOfDates; i++) {
				cal.add(Calendar.YEAR, yearlyInterval);
				dates.add(cal.getTime());
			}
			try {
				List<Image> images = 
					ImageFactory.getImagesForDates(dates, longitude, latitude, address, cropFactor, waitTime);
				Map<String, String> imageFileMap = Maps.newLinkedHashMap();
				for (Image image: images) {
					SimpleDateFormat dateFormat = 
						new SimpleDateFormat("' in 'yyyy-MM-dd");
					String imageName = address + dateFormat.format(image.getDate());
					String fileName = IMAGE_PATH + "/captured/"+imageName+".png";
					image.persist(fileName);
					imageFileMap.put(imageName, fileName);
				}
				images = null;
				int process = JOptionPane.showConfirmDialog(inputPanel, "Process images?");
				if (process == JOptionPane.YES_OPTION) {
				    Iterator<Map.Entry<String, String>> imageFileIterator = 
				    		imageFileMap.entrySet().iterator();
				    while (imageFileIterator.hasNext()) {
				        Map.Entry<String, String> pair = imageFileIterator.next();
				        UnknownImage unknownImage = 
				        		new UnknownImage(pair.getValue());
				        JLabel arableLabel = 
			        			new JLabel(pair.getKey() + " percentage arable: " + 
			        				unknownImage.getArablePercentage());
				        JLabel developedLabel = 
			        			new JLabel(pair.getKey() + " percentage developed: " + 
			        				unknownImage.getDevelopedPercentage());
//				        JLabel desertLabel = 
//				        		new JLabel(pair.getKey() + " percentage desert: " + 
//				        			unknownImage.getDesertPercentage());
//				        JLabel forestLabel = 
//				        		new JLabel(pair.getKey() + " percentage forest: " + 
//				        			unknownImage.getForestPercentage());
				        arableLabel.setHorizontalAlignment(SwingConstants.CENTER);
				        arableLabel.setVerticalAlignment(SwingConstants.TOP);
				        arableLabel.setPreferredSize(new Dimension(100, 100));
				        developedLabel.setHorizontalAlignment(SwingConstants.CENTER);
				        developedLabel.setVerticalAlignment(SwingConstants.TOP);
				        developedLabel.setPreferredSize(new Dimension(100, 100));
//				        desertLabel.setHorizontalAlignment(SwingConstants.CENTER);
//				        desertLabel.setVerticalAlignment(SwingConstants.TOP);
//				        desertLabel.setPreferredSize(new Dimension(100, 100));
//				        forestLabel.setHorizontalAlignment(SwingConstants.CENTER);
//				        forestLabel.setVerticalAlignment(SwingConstants.TOP);
//				        forestLabel.setPreferredSize(new Dimension(100, 100));
				        resultsPanel.add(arableLabel);
				        resultsPanel.add(developedLabel);
//				        resultsPanel.add(desertLabel);
//				        resultsPanel.add(forestLabel);
				    }
				    JOptionPane.showMessageDialog(resultsPanel, "Results ready.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}