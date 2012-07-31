/**
 * 
 */
package org.apache.commons.io.comparator;

import java.io.File;
import java.util.Comparator;

/**
 * @author Scot Dalton
 *
 */
public class RegionFileComparator extends CompositeFileComparator {
	public static final Comparator<File> REGION_COMPARATOR = 
		new RegionFileComparator();

	private static final long serialVersionUID = -8542208963897509779L;

	@SuppressWarnings("unchecked")
	public RegionFileComparator() {
		super(LongitudeFileComparator.REVERSE_LONGITUDE_COMPARATOR, 
			LatitudeFileComparator.REVERSE_LATITUDE_COMPARATOR);
	}
}