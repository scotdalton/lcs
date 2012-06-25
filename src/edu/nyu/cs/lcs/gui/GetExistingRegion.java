/**
 * 
 */
package edu.nyu.cs.lcs.gui;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.filefilter.DirectoryFileFilter;

import com.google.common.collect.Lists;

/**
 * @author Scot Dalton
 *
 */
public class GetExistingRegion extends GetRegion {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4211447987764367672L;
	private Properties regionProperties;

	/**
	 * @param persistDirectory
	 */
	public GetExistingRegion(File persistDirectory) {
		super(persistDirectory);
		regionProperties = new Properties();
	}
	
	@Override
	void setRegionFields() throws Exception {
		regionAddress = 
			(String) existingRegionComboBox.getSelectedItem();
		regionProperties.
			load(new FileReader(getRegionPropertiesFile(regionAddress)));
		eastLongitude = 
			Double.valueOf(regionProperties.getProperty("eastLongitude"));
		westLongitude = 
			Double.valueOf(regionProperties.getProperty("westLongitude"));
		northLatitude = 
			Double.valueOf(regionProperties.getProperty("northLatitude"));
		southLatitude = 
			Double.valueOf(regionProperties.getProperty("southLatitude"));
	};
	
	@Override
	double getStartLongitude() {
		return 
			Double.valueOf(regionProperties.getProperty("longitude"));
	}

	@Override
	double getStartLatitude() {
		return 
			Double.valueOf(regionProperties.getProperty("latitude"));
	}
	
	@Override
	List<Date> getDates() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<Date> dates = Lists.newArrayList();
		for(String dateDirectory: getRegionDirectory(regionAddress).list(DirectoryFileFilter.DIRECTORY))
			dates.add(dateFormat.parse(dateDirectory));
		return dates;
	}


}
