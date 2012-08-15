/**
 * 
 */
package edu.nyu.cs.lcs.utility;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.comparator.CompositeFileComparator;
import org.apache.commons.io.comparator.LatitudeFileComparator;
import org.apache.commons.io.comparator.LongitudeFileComparator;
import org.apache.commons.io.comparator.RegionFileComparator;
import org.junit.Ignore;
import org.junit.Test;


/**
 * @author Scot Dalton
 *
 */
@Ignore
public class FileComparatorTest {
	@Test
	public void testLongitudeSort() {
		File dir = new File("/Users/dalton/Desktop/West Bengal, India/2012-05-21");
		List<File> files = FileUtil.getFiles(dir);
		Collections.sort(files, LongitudeFileComparator.LONGITUDE_COMPARATOR);
		System.out.println("LONG");
		for(File file: files)
			System.out.println(file.getName());
	}

	@Test
	public void testReverseLongitudeSort() {
		File dir = new File("/Users/dalton/Desktop/West Bengal, India/2012-05-21");
		List<File> files = FileUtil.getFiles(dir);
		Collections.sort(files, LongitudeFileComparator.REVERSE_LONGITUDE_COMPARATOR);
		System.out.println("REVERSE_LONG");
		for(File file: files)
			System.out.println(file.getName());
	}

	@Test
	public void testLatitudeSort() {
		File dir = new File("/Users/dalton/Desktop/West Bengal, India/2012-05-21");
		List<File> files = FileUtil.getFiles(dir);
		Collections.sort(files, LatitudeFileComparator.LATITUDE_COMPARATOR);
		System.out.println("LAT");
		for(File file: files)
			System.out.println(file.getName());
	}

	@Test
	public void testReverseLatitudeSort() {
		File dir = new File("/Users/dalton/Desktop/West Bengal, India/2012-05-21");
		List<File> files = FileUtil.getFiles(dir);
		Collections.sort(files, LatitudeFileComparator.REVERSE_LATITUDE_COMPARATOR);
		System.out.println("REVERSE_LAT");
		for(File file: files)
			System.out.println(file.getName());
	}

	@Test
	public void testCompositeSort() {
		File dir = new File("/Users/dalton/Desktop/West Bengal, India/2012-05-21");
		List<File> files = FileUtil.getFiles(dir);
		@SuppressWarnings("unchecked")
		CompositeFileComparator compositeComparator = 
			new CompositeFileComparator(
				LongitudeFileComparator.REVERSE_LONGITUDE_COMPARATOR, 
					LatitudeFileComparator.REVERSE_LATITUDE_COMPARATOR);
		Collections.sort(files, compositeComparator);
		System.out.println("COMPOSITE");
		for(File file: files)
			System.out.println(file.getName());
	}

	@Test
	public void RegionSort() {
		File dir = new File("/Users/dalton/Desktop/West Bengal, India/2012-05-21");
		List<File> files = FileUtil.getFiles(dir);
		Collections.sort(files, RegionFileComparator.REGION_COMPARATOR);
		System.out.println("REGION");
		for(File file: files)
			System.out.println(file.getName());
	}
}