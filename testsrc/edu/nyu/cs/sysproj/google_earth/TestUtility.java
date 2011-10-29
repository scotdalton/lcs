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

	public static File getTestFile() {
		return new File(IMAGE_PATH + "/" + "Tamale_Ghana_1_20111004.png" );
	}

	public static List<File> getImageFiles() {
		List<File> fileList = Lists.newArrayList();
		File directory = new File(IMAGE_PATH);
		if (directory.isDirectory()) {
			String[] filenames = directory.list();
			for (String filename: filenames) {
				File file = new File(IMAGE_PATH + "/" + filename);
				if(file.isFile() && !file.isHidden()) {
					fileList.add(file);
				}
			}
		}
		return fileList;
	}

	public static List<File> getArableTrainingImageFiles() {
		List<File> fileList = Lists.newArrayList();
		File directory = new File(ARABLE_TRAINING_IMAGE_PATH);
		if (directory.isDirectory()) {
			String[] filenames = directory.list();
			for (String filename: filenames) {
				System.out.println(filename);
				File file = 
					new File(ARABLE_TRAINING_IMAGE_PATH + "/" + filename);
				if(file.isFile() && !file.isHidden()) {
					fileList.add(file);
				}
			}
		}
		return fileList;
	}
}