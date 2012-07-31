/**
 * 
 */
package edu.nyu.cs.lcs.utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.RegionFileComparator;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.HiddenFileFilter;

import com.google.common.collect.Lists;

import edu.nyu.cs.lcs.Image;

/**
 * @author Scot Dalton
 * Utility class for common file operations.
 */
public class FileUtil {
	public static List<File> getFiles(File directory) {
		return Lists.newArrayList(FileUtils.listFiles(directory, 
			new AndFileFilter(HiddenFileFilter.VISIBLE, 
				FileFileFilter.FILE), null));
	}
	
	public static List<File> getRegionSort(File directory) {
		List<File> files = getFiles(directory);
		Collections.sort(files, RegionFileComparator.REGION_COMPARATOR);
		return files;
	}
	
	public static void zipImages(String zipFileName, Map<String, Image> images) throws Exception {
        byte data[] = new byte[2048];
		FileOutputStream zipFileOutputStream = 
			new FileOutputStream(zipFileName);
		ZipOutputStream zipOutputStream = 
			new ZipOutputStream(new BufferedOutputStream(zipFileOutputStream));
		for(String imageName: images.keySet()) {
			String imageFileName = imageName + ".png";
			BufferedInputStream imageInputStream = 
				new BufferedInputStream(
					images.get(imageName).getAsInputStream());
			zipOutputStream.putNextEntry(new ZipEntry(imageFileName));
			int count;
			while((count = imageInputStream.read(data, 0, 2048)) != -1)
				zipOutputStream.write(data, 0, count);
			imageInputStream.close();
		}
		zipOutputStream.close();
	}
}