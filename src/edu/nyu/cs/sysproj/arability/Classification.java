/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

import static edu.nyu.cs.sysproj.arability.utility.Configuration.*;

import java.io.File;
import java.util.List;
import com.google.common.collect.Lists;
import java.util.Collections;

/**
 * ArabilityClassification defines the set of classifications we're 
 * working with.
 * 
 * @author Scot Dalton
 * 
 */
public enum Classification {
	ARABLE(true, CURATED_ARABLE_TRAINING_IMAGE_PATH, CURATED_ARABLE_TESTING_IMAGE_PATH),
	DEVELOPED(true, CURATED_DEVELOPED_TRAINING_IMAGE_PATH, CURATED_DEVELOPED_TESTING_IMAGE_PATH),
	DESERT(true, CURATED_DESERT_TRAINING_IMAGE_PATH, CURATED_DESERT_TESTING_IMAGE_PATH),
	FOREST(true, CURATED_FOREST_TRAINING_IMAGE_PATH, CURATED_FOREST_TESTING_IMAGE_PATH),
	UNKNOWN(false, null, null);
	
	private boolean isTrainable;
	private List<Image> trainingImages;
	private List<Image> testingImages;
	
	private Classification(boolean isTrainable, 
			String trainingDirectory, String testingDirectory) {
		this.isTrainable = isTrainable;
		trainingImages = Lists.newArrayList();
		testingImages = Lists.newArrayList();
		if(isTrainable) {
			trainingImages = getKnownImages(trainingDirectory);
			testingImages = getKnownImages(testingDirectory);
		}
	}
	
	public boolean isTrainable() {
		return isTrainable;
	}
	
	List<Image> getTrainingImages() {
		return Collections.unmodifiableList(trainingImages);
	}
	
	List<Image> getTestingImages() {
		return Collections.unmodifiableList(testingImages);
	}

	private List<Image> getKnownImages(
			String directoryName) {
		List<Image> knownImages = Lists.newArrayList();
		File directory = new File(directoryName);
		if (directory.isDirectory()) {
			String[] filenames = directory.list();
			for (String filename: filenames) {
				File file = 
					new File(directoryName + "/" + filename);
				if(file.isFile() && !file.isHidden()) {
					KnownImage image = new KnownImage(file, this);
					knownImages.add(image);
				}
			}
		}
		return knownImages;
	}
}