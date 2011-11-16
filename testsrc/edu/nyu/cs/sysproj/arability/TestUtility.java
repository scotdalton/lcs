/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

import static edu.nyu.cs.sysproj.arability.utility.Configuration.*;

import java.io.File;
import java.util.List;

import com.google.common.collect.Lists;

import edu.nyu.cs.sysproj.arability.Image;

/**
 * @author Scot Dalton
 *
 */
public class TestUtility {

	public static String getTestFileName1() {
		return IMAGE_PATH + "/" + "Tamale_Ghana_1_20031004.png";
	}

	public static Image getTestImage1() {
		return new Image(getTestFileName1());
	}

	public static String getTestFileName2() {
		return IMAGE_PATH + "/" + "Tamale_Ghana_1_20111004.png";
	}

	public static Image getTestImage2() {
		return new Image(getTestFileName2());
	}

	public static String getTestChoppedFileName1() {
		return IMAGE_PATH + "/" + "chop/testChop272-27.png";
	}

	public static Image getTestChoppedImage1() {
		return new Image(getTestChoppedFileName1());
	}

	public static String getCloudyTestFileName1() {
		return IMAGE_PATH + "/" + "Nanvura_Mozambique_1_20091004.png";
	}

	public static Image getCloudyTestImage1() {
		return new Image(getCloudyTestFileName1());
	}

	public static String getBlurryTestFileName1() {
		return IMAGE_PATH + "/" + "Dar_es_Salaam_Tanzania_1_20031004.png";
	}

	public static Image getBlurryTestImage1() {
		return new Image(getBlurryTestFileName1());
	}

	public static String getBrightTestFileName1() {
		return IMAGE_PATH + "/" + "Johannesburg_South_Africa_2_20111004.png";
	}

	public static Image getBrightTestImage1() {
		return new Image(getBrightTestFileName1());
	}

	public static List<File> getImageFiles() {
		return getFiles(IMAGE_PATH);
	}

	public static List<File> getArableTrainingImageFiles() {
		return getFiles(ARABLE_TRAINING_IMAGE_PATH+"/selected");
	}
	
	public static List<File> getCuratedArableTrainingImageFiles() {
		return getFiles(ARABLE_TRAINING_IMAGE_PATH+"/curated");
	}
	
	public static List<File> getNonArableTrainingImageFiles() {
		return getFiles(NON_ARABLE_TRAINING_IMAGE_PATH+"/selected");
	}
	
	public static List<File> getCuratedNonArableTrainingImageFiles() {
		return getFiles(NON_ARABLE_TRAINING_IMAGE_PATH+"/curated");
	}
	
	public static List<File> getArableTestingImageFiles() {
		return getFiles(ARABLE_TESTING_IMAGE_PATH+"/selected");
	}
	
	public static List<File> getCuratedArableTestingImageFiles() {
		return getFiles(ARABLE_TESTING_IMAGE_PATH+"/curated");
	}
	
	public static List<File> getNonArableTestingImageFiles() {
		return getFiles(NON_ARABLE_TESTING_IMAGE_PATH+"/selected");
	}
	
	public static List<File> getCuratedNonArableTestingImageFiles() {
		return getFiles(NON_ARABLE_TESTING_IMAGE_PATH+"/curated");
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