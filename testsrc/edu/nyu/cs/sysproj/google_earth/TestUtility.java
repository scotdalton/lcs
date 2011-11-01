/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import java.io.File;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author Scot Dalton
 *
 */
public class TestUtility {
	public final static String IMAGE_PATH = 
		"/Users/dalton/Dropbox/MSIS/Systems Projects/google_earth/images";
	public final static String EDGE_IMAGE_PATH = IMAGE_PATH+"/edges";
	public final static String TRAINING_IMAGE_PATH = 
		IMAGE_PATH+"/training";
	public final static String ARABLE_TRAINING_IMAGE_PATH = 
		TRAINING_IMAGE_PATH+"/arable";
	public final static String NON_ARABLE_TRAINING_IMAGE_PATH = 
		TRAINING_IMAGE_PATH+"/non_arable";

	public static File getTestFile() {
		return new File(IMAGE_PATH + "/" + "Tamale_Ghana_1_20111004.png" );
	}

	public static List<File> getImageFiles() {
		return getFiles(IMAGE_PATH);
	}

	public static List<File> getArableTrainingImageFiles() {
		return getFiles(ARABLE_TRAINING_IMAGE_PATH);
	}
	
	public static List<File> getNonArableTrainingImageFiles() {
		return getFiles(NON_ARABLE_TRAINING_IMAGE_PATH);
	}
	
	private static List<File> getFiles(String directoryName) {
		List<File> fileList = Lists.newArrayList();
		File directory = new File(directoryName);
		if (directory.isDirectory()) {
			String[] filenames = directory.list();
			for (String filename: filenames) {
				File file = 
					new File(directoryName + "/" + filename);
				if(file.isFile() && !file.isHidden()) {
					fileList.add(file);
				}
			}
		}
		return fileList;
	}
}