/**
 * 
 */
package edu.nyu.cs.lcs.utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.comparator.RegionFileComparator;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import au.com.bytecode.opencsv.CSVWriter;

import com.google.common.collect.Lists;

import edu.nyu.cs.lcs.Image;
import edu.nyu.cs.lcs.RegionImage;
import edu.nyu.cs.lcs.classifications.Classification;
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
	
	public static List<File> getDirectories(File directory) {
		System.out.println(directory);
		return Lists.newArrayList(FileUtils.listFilesAndDirs(directory, 
			new AndFileFilter(HiddenFileFilter.VISIBLE, 
				DirectoryFileFilter.DIRECTORY), TrueFileFilter.TRUE));
	}
	
	public static List<File> getRegionSort(File directory) {
		System.out.println(directory);
		List<File> files = getFiles(directory);
		Collections.sort(files, RegionFileComparator.REGION_COMPARATOR);
		return files;
	}
	
	public static List<String> getAttributesFromFile(File file) throws FileNotFoundException, IOException {
		List<String> attributeLines = Lists.newArrayList();
		Reader reader = new FileReader(file);
		for(String fileLine:IOUtils.readLines(reader)) {
			attributeLines.add(fileLine);
			if(fileLine.equals("@data")) break;
		}
		reader.close();
		return attributeLines;
	}
	
	public static List<String> getDataFromFile(File file, Classification classification) throws FileNotFoundException, IOException {
		List<String> dataLines = Lists.newArrayList();
		boolean processingData = false;
		for(String fileLine:IOUtils.readLines(new FileReader(file))) {
			// Parse out dummy classification lines if we have them.
			// There will be one duplicate instance.
			if(classification != null) {
				if(processingData)
					if(fileLine.contains(classification.name()))
						dataLines.add(fileLine);
			} else {
				if(processingData)
					dataLines.add(fileLine);
			}
			if(fileLine.equals("@data")) processingData = true;
		}
		return dataLines;
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
		regionCSV(regionDirectory, csvFile, trainedModel, 0);
	}
	
	public static void regionCSV(File regionDirectory, File csvFile, TrainedModel trainedModel, int startIndex) throws Exception {
		regionCSV(regionDirectory, csvFile, trainedModel, 0, 0);
	}
	
	public static void regionCSV(File regionDirectory, File csvFile, TrainedModel trainedModel, int startIndex, int filesToProcess) throws Exception {
		List<File> files =  getRegionSort(regionDirectory);
		int endIndex;
		endIndex = (filesToProcess == 0) ? 
			files.size() : (startIndex + filesToProcess);
		System.out.println(endIndex);
		CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile));
		csvWriter.writeNext(csvHeaders.toArray(new String[0]));
		for(int i = startIndex; i < endIndex; i++) {
			File file = files.get(i);
			System.out.println(file.getName());
			RegionImage regionImage = 
				new RegionImage(file, trainedModel);
			csvWriter.writeNext(regionImage.toCSV().toArray(new String[0]));
			csvWriter.flush();
			regionImage.getClassificationImage().persist(regionDirectory + "/" + regionImage.getName() + "_classified.png");
		}
		csvWriter.close();
	}
}