/**
 * 
 */
package edu.nyu.cs.lcs.gui;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.filefilter.DirectoryFileFilter;

import com.google.common.collect.Lists;

/**
 * @author Scot Dalton
 *
 */
public class GuiUtil {
	public static List<Date> getDates() {
		int numberOfDates = 2;
		int yearlyInterval = -5;
		Calendar cal = Calendar.getInstance();
		List<Date> dates = Lists.newArrayList();
		Date now = cal.getTime();
		dates.add(now);
		for(int i=1; i < numberOfDates; i++) {
			cal.add(Calendar.YEAR, yearlyInterval);
			dates.add(cal.getTime());
		}
		return dates;
	}
	
	public static List<String> getExistingRegions(File persistDirectory) {
		List<String> regions = Lists.newArrayList();
		regions.add("Select Region");
		for(String regionDirectory: persistDirectory.list(DirectoryFileFilter.INSTANCE))
			regions.add(regionDirectory);
		return regions;
	}
}
