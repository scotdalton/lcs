/**
 * 
 */
package edu.nyu.cs.lcs.gui;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
}
