/**
 * 
 */
package edu.nyu.cs.sysproj.arability.gui;

import static edu.nyu.cs.sysproj.arability.utility.Configuration.IMAGE_PATH;
import static edu.nyu.cs.sysproj.arability.utility.Configuration.persistImage;
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
import javax.swing.JSplitPane;
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
		JComponent inputContainer = 
			(JComponent)eventButton.getParent().getComponent(0);
		JSplitPane arabilitySplitPane = 
			(JSplitPane)eventButton.getParent().getParent().getParent();
		JPanel resultsContainer = (JPanel)arabilitySplitPane.getComponent(2);
		String address = null;
		float latitude = 0;
		float longitude = 0;
		if("latLng".equals(event.getActionCommand())) {
			latitude = 
				Float.valueOf(((JTextField) inputContainer.getComponent(1)).getText());
			longitude = 
				Float.valueOf(((JTextField) inputContainer.getComponent(3)).getText());
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
				((JTextField) inputContainer.getComponent(1)).getText();
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
			JOptionPane.showMessageDialog(inputContainer, "No address found.");
		} else {
			int numberOfDates = 2;
			int yearlyInterval = -2;
			Calendar cal = Calendar.getInstance();
			List<Date> dates = new ArrayList<Date>();
			Date now = cal.getTime();
			dates.add(now);
			for(int i=0; i < numberOfDates; i++) {
				cal.add(Calendar.YEAR, yearlyInterval);
				dates.add(cal.getTime());
			}
			Map<String, UnknownImage> imageMap = Maps.newHashMap();
			try {
				List<Image> images = 
					ImageFactory.getImagesForDates(dates, longitude, latitude, address, 5);
				int process = JOptionPane.showConfirmDialog(inputContainer, "Process images?");
				if (process == JOptionPane.YES_OPTION) {
					for (Image image: images) {
						SimpleDateFormat dateFormat = 
							new SimpleDateFormat("' in 'yyyy-MM-dd");
						String imageName = address + dateFormat.format(image.getDate());
						String fileName = IMAGE_PATH + "/captured/"+imageName+".png";
						persistImage(fileName, image.getRenderedImage());
						imageMap.put(imageName, new UnknownImage(fileName));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		    Iterator<Map.Entry<String, UnknownImage>> imageIterator = 
		    		imageMap.entrySet().iterator();
		    while (imageIterator.hasNext()) {
		        Map.Entry<String, UnknownImage> pairs = imageIterator.next();
		        JLabel resultsLabel = 
		        		new JLabel(pairs.getKey() + " percentage arable: " + 
		        			pairs.getValue().getArablePercentage());
				resultsLabel.setHorizontalAlignment(SwingConstants.CENTER);
				resultsLabel.setVerticalAlignment(SwingConstants.TOP);
				resultsLabel.setPreferredSize(new Dimension(100, 100));
		        resultsContainer.add(resultsLabel);
		    }
		    JOptionPane.showMessageDialog(resultsContainer, "Results ready.");
		}
	}
}