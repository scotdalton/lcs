/**
 * 
 */
package edu.nyu.cs.lcs.utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
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

import au.com.bytecode.opencsv.CSVWriter;

import com.google.common.collect.Lists;

import edu.nyu.cs.lcs.Image;
import edu.nyu.cs.lcs.RegionImage;
import edu.nyu.cs.lcs.model.TrainedModel;

/**
 * @author Scot Dalton
 * Utility class for common file operations.
 */
public class FileUtil {
	public static final List<String> csvHeaders = 
		Lists.newArrayList("Latitude", "Longitude", "Date", "% Cropland", 
			"% Developed", "% Desert", "% Forest", "File Name", "File Size");
	
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
	
	public static void regionCSV(File regionDirectory, File csvFile, TrainedModel trainedModel) throws Exception {
		List<File> files =  getRegionSort(regionDirectory);
		CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile));
		csvWriter.writeNext(csvHeaders.toArray(new String[0]));
		for(File file:files) {
			RegionImage regionImage = 
				new RegionImage(file, trainedModel);
			csvWriter.writeNext(regionImage.toCSV().toArray(new String[0]));
		}
		csvWriter.close();
	}
}