/**
 * 
 */
package edu.nyu.cs.lcs;

import static edu.nyu.cs.lcs.utility.Configuration.*;

import java.io.File;
import java.util.List;

import com.google.common.collect.Lists;

import edu.nyu.cs.lcs.Image;

/**
 * @author Scot Dalton
 *
 */
public class TestUtility {

	public static String getTestRegionFileName1() {
//		return TMP_IMAGE_PATH + "/handselected/" + "Tamale_Ghana_1_20031004.png";
//		return TMP_IMAGE_PATH + "/handselected/" + "Harare_Zimbabwe_2_20031004.png";
		return TMP_BASE_PATH + "/Banjul, The Gambia/" + "Banjul, The Gambia circa 2006.png";
	}

	public static Image getTestRegion1() {
		return new Image(getTestRegionFileName1());
	}

	public static String getTestRegionClassificationFileName1() {
//		return TMP_IMAGE_PATH + "/handselected/" + "Tamale_Ghana_1_20031004.png";
//		return TMP_IMAGE_PATH + "/handselected/" + "Harare_Zimbabwe_2_20031004.png";
		return TMP_BASE_PATH + "regionClassification1.png";
	}

	public static Image getTestRegionClassificationMap1() {
		return new Image(getTestRegionClassificationFileName1());
	}

	public static String getTestRegionClassificationFileName2() {
//		return TMP_IMAGE_PATH + "/handselected/" + "Tamale_Ghana_1_20031004.png";
//		return TMP_IMAGE_PATH + "/handselected/" + "Harare_Zimbabwe_2_20031004.png";
		return TMP_BASE_PATH + "regionClassification2.png";
	}

	public static Image getTestRegionClassificationMap2() {
		return new Image(getTestRegionClassificationFileName2());
	}

	public static String getTestRegionFileName2() {
//		return TMP_IMAGE_PATH + "/handselected/" + "Tamale_Ghana_1_20031004.png";
//		return TMP_IMAGE_PATH + "/handselected/" + "Harare_Zimbabwe_2_20031004.png";
		return TMP_BASE_PATH + "/Banjul, The Gambia/" + "Banjul, The Gambia circa 2011.png";
	}

	public static Image getTestRegion2() {
		return new Image(getTestRegionFileName2());
	}

	public static String getTestFileName1() {
//		return TMP_IMAGE_PATH + "/handselected/" + "Tamale_Ghana_1_20031004.png";
//		return TMP_IMAGE_PATH + "/handselected/" + "Harare_Zimbabwe_2_20031004.png";
		return TMP_IMAGE_PATH + "/handselected/" + "Johannesburg_South_Africa_1_20031004.png";
	}

	public static Image getTestImage1() {
		return new Image(getTestFileName1());
	}

	public static String getTestFileName2() {
//		return TMP_IMAGE_PATH + "/handselected/" + "Tamale_Ghana_1_20051004.png";
//		return TMP_IMAGE_PATH + "/handselected/" + "Harare_Zimbabwe_2_20051004.png";
		return TMP_IMAGE_PATH + "/handselected/" + "Johannesburg_South_Africa_1_20051004.png";
	}

	public static Image getTestImage2() {
		return new Image(getTestFileName2());
	}

	public static String getTestFileName3() {
//		return TMP_IMAGE_PATH + "/handselected/" + "Tamale_Ghana_1_20071004.png";
//		return TMP_IMAGE_PATH + "/handselected/" + "Harare_Zimbabwe_2_20071004.png";
		return TMP_IMAGE_PATH + "/handselected/" + "Johannesburg_South_Africa_1_20071004.png";
	}

	public static Image getTestImage3() {
		return new Image(getTestFileName3());
	}

	public static String getTestFileName4() {
//		return TMP_IMAGE_PATH + "/handselected/" + "Tamale_Ghana_1_20091004.png";
//		return TMP_IMAGE_PATH + "/handselected/" + "Harare_Zimbabwe_2_20091004.png";
		return TMP_IMAGE_PATH + "/handselected/" + "Johannesburg_South_Africa_1_20091004.png";
	}

	public static Image getTestImage4() {
		return new Image(getTestFileName4());
	}

	public static String getTestFileName5() {
//		return TMP_IMAGE_PATH + "/handselected/" + "Tamale_Ghana_1_20111004.png";
//		return TMP_IMAGE_PATH + "/handselected/" + "Harare_Zimbabwe_2_20111004.png";
		return TMP_IMAGE_PATH + "/handselected/" + "Johannesburg_South_Africa_1_20111004.png";
	}

	public static Image getTestImage5() {
		return new Image(getTestFileName5());
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
	
	public static List<File> getDevelopedTrainingImageFiles() {
		return getFiles(DEVELOPED_TRAINING_IMAGE_PATH+"/selected");
	}
	
	public static List<File> getCuratedDevelopedTrainingImageFiles() {
		return getFiles(DEVELOPED_TRAINING_IMAGE_PATH+"/curated");
	}
	
	public static List<File> getArableTestingImageFiles() {
		return getFiles(ARABLE_TESTING_IMAGE_PATH+"/selected");
	}
	
	public static List<File> getCuratedArableTestingImageFiles() {
		return getFiles(ARABLE_TESTING_IMAGE_PATH+"/curated");
	}
	
	public static List<File> getDevelopedTestingImageFiles() {
		return getFiles(DEVELOPED_TESTING_IMAGE_PATH+"/selected");
	}
	
	public static List<File> getCuratedDevelopedTestingImageFiles() {
		return getFiles(DEVELOPED_TESTING_IMAGE_PATH+"/curated");
	}
	
	public static List<File> getDesertTestingImageFiles() {
		return getFiles(DESERT_TESTING_IMAGE_PATH+"/selected");
	}
	
	public static List<File> getCuratedDesertTestingImageFiles() {
		return getFiles(DESERT_TESTING_IMAGE_PATH+"/curated");
	}
	
	public static List<File> getForestTestingImageFiles() {
		return getFiles(FOREST_TESTING_IMAGE_PATH+"/selected");
	}
	
	public static List<File> getCuratedForestTestingImageFiles() {
		return getFiles(FOREST_TESTING_IMAGE_PATH+"/curated");
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