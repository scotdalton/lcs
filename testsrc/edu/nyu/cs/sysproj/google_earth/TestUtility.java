/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.util.List;

import javax.media.jai.JAI;

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

	public static String getTestFileName1() {
		return IMAGE_PATH + "/" + "Tamale_Ghana_1_20031004.png";
	}

	public static File getTestFile1() {
		return new File(getTestFileName1());
	}

	public static Image getTestImage1() {
		return new Image(getTestFile1());
	}

	public static String getTestFileName2() {
		return IMAGE_PATH + "/" + "Tamale_Ghana_1_20111004.png";
	}

	public static File getTestFile2() {
		return new File(getTestFileName2());
	}

	public static Image getTestImage2() {
		return new Image(getTestFile2());
	}

	public static String getTestChoppedFileName1() {
		return IMAGE_PATH + "/" + "chop/testChop272-27.png";
	}

	public static File getTestChoppedFile1() {
		return new File(getTestChoppedFileName1());
	}

	public static Image getTestChoppedImage1() {
		return new Image(getTestChoppedFile1());
	}

	public static String getCloudyTestFileName1() {
		return IMAGE_PATH + "/" + "Nanvura_Mozambique_1_20091004.png";
	}

	public static File getCloudyTestFile1() {
		return new File(getCloudyTestFileName1());
	}

	public static Image getCloudyTestImage1() {
		return new Image(getCloudyTestFile1());
	}

	public static String getBlurryTestFileName1() {
		return IMAGE_PATH + "/" + "Dar_es_Salaam_Tanzania_1_20031004.png";
	}

	public static File getBlurryTestFile1() {
		return new File(getBlurryTestFileName1());
	}

	public static Image getBlurryTestImage1() {
		return new Image(getBlurryTestFile1());
	}

	public static String getBrightTestFileName1() {
		return IMAGE_PATH + "/" + "Johannesburg_South_Africa_2_20111004.png";
	}

	public static File getBrightTestFile1() {
		return new File(getBrightTestFileName1());
	}

	public static Image getBrightTestImage1() {
		return new Image(getBrightTestFile1());
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
	
	public static void persistImage(String filename, RenderedImage source) {
		ParameterBlock fileStoreParams = (new ParameterBlock()).
			addSource(source).add(filename).add("PNG");
		JAI.create("filestore", fileStoreParams);
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