/**
 * 
 */
package org.apache.commons.io.comparator;

import java.io.File;
import java.util.Comparator;

import org.apache.commons.io.FilenameUtils;

/**
 * @author Scot Dalton
 *
 */
public class LongitudeFileComparator extends AbstractFileComparator {

	public static final Comparator<File> LONGITUDE_COMPARATOR = 
		new LongitudeFileComparator();
	public static final Comparator<File> REVERSE_LONGITUDE_COMPARATOR = 
		new ReverseComparator(LONGITUDE_COMPARATOR);

	private static final long serialVersionUID = 4705881237442384317L;

	public int compare(File file1, File file2) {
		return longitude(file1).compareTo(longitude(file2));
    }
    
    private Double longitude(File file) {
    		return Double.valueOf(FilenameUtils.getBaseName(file.getName()).split("-")[1]);
    }
}
