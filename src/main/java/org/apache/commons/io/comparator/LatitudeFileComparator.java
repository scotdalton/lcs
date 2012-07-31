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
public class LatitudeFileComparator extends AbstractFileComparator {
	
	public static final Comparator<File> LATITUDE_COMPARATOR = 
		new LatitudeFileComparator();
	public static final Comparator<File> REVERSE_LATITUDE_COMPARATOR = 
		new ReverseComparator(LATITUDE_COMPARATOR);

	private static final long serialVersionUID = 1532712853581878877L;
	
	public int compare(File file1, File file2) {
		return latitude(file1).compareTo(latitude(file2));
    }
    
    private Double latitude(File file) {
		return Double.valueOf(FilenameUtils.getBaseName(file.getName()).split("-")[0]);
    }
}
