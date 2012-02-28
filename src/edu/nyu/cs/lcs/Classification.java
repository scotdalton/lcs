/**
 * 
 */
package edu.nyu.cs.lcs;

import static edu.nyu.cs.lcs.utility.Configuration.CURATED_ARABLE_TESTING_IMAGE_PATH;
import static edu.nyu.cs.lcs.utility.Configuration.CURATED_ARABLE_TRAINING_IMAGE_PATH;
import static edu.nyu.cs.lcs.utility.Configuration.CURATED_DESERT_TESTING_IMAGE_PATH;
import static edu.nyu.cs.lcs.utility.Configuration.CURATED_DESERT_TRAINING_IMAGE_PATH;
import static edu.nyu.cs.lcs.utility.Configuration.CURATED_DEVELOPED_TESTING_IMAGE_PATH;
import static edu.nyu.cs.lcs.utility.Configuration.CURATED_DEVELOPED_TRAINING_IMAGE_PATH;
import static edu.nyu.cs.lcs.utility.Configuration.CURATED_FOREST_TESTING_IMAGE_PATH;
import static edu.nyu.cs.lcs.utility.Configuration.CURATED_FOREST_TRAINING_IMAGE_PATH;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.HiddenFileFilter;

import com.google.common.collect.Lists;

/**
 * Classification defines the set of classifications we're 
 * working with.
 * 
 * @author Scot Dalton
 * 
 */
public enum Classification {
	CROPLAND(true, CURATED_ARABLE_TRAINING_IMAGE_PATH, CURATED_ARABLE_TESTING_IMAGE_PATH) {
		@Override
		int getRed() {
			return 0;
		}

		@Override
		int getBlue() {
			return 0;
		}

		@Override
		int getGreen() {
			return 255;
		}
		
		@Override
		public String toString() {
			return "AR";
		}
	},
	DEVELOPED(true, CURATED_DEVELOPED_TRAINING_IMAGE_PATH, CURATED_DEVELOPED_TESTING_IMAGE_PATH) {
		@Override
		int getRed() {
			return 0;
		}

		@Override
		int getGreen() {
			return 128;
		}

		@Override
		int getBlue() {
			return 128;
		}
		
		@Override
		public String toString() {
			return "DV";
		}
	},
	DESERT(true, CURATED_DESERT_TRAINING_IMAGE_PATH, CURATED_DESERT_TESTING_IMAGE_PATH) {
		@Override
		int getRed() {
			return 255;
		}

		@Override
		int getGreen() {
			return 255;
		}

		@Override
		int getBlue() {
			return 0;
		}
		
		@Override
		public String toString() {
			return "DS";
		}
	},
	FOREST(true, CURATED_FOREST_TRAINING_IMAGE_PATH, CURATED_FOREST_TESTING_IMAGE_PATH) {
		@Override
		int getRed() {
			return 0;
		}

		@Override
		int getGreen() {
			return 0;
		}

		@Override
		int getBlue() {
			return 255;
		}
		
		@Override
		public String toString() {
			return "FR";
		}
	},
	UNKNOWN(false, null, null) {
		@Override
		int getRed() {
			return 0;
		}

		@Override
		int getGreen() {
			return 255;
		}

		@Override
		int getBlue() {
			return 255;
		}
		
		@Override
		public String toString() {
			return "UN";
		}
	};
	
	private boolean isTrainable;
	private String trainingDirectory;
	private String testingDirectory;
	
	private Classification(boolean isTrainable, 
			String trainingDirectory, String testingDirectory) {
		this.isTrainable = isTrainable;
		this.trainingDirectory = trainingDirectory;
		this.testingDirectory = testingDirectory;
	}
	
	public boolean isTrainable() {
		return isTrainable;
	}
	
	abstract int getRed();
	abstract int getGreen();
	abstract int getBlue();
	
	public int getAlpha() {
		return 100;
	}
	
	public List<Image> getTrainingImages() {
		return getKnownImages(trainingDirectory);
	}
	
	public List<Image> getTestingImages() {
		return getKnownImages(testingDirectory);
	}

	private List<Image> getKnownImages(
			String directoryName) {
		List<Image> knownImages = Lists.newArrayList();
		Collection<File> files = 
			FileUtils.listFiles(new File(directoryName), 
				new AndFileFilter(HiddenFileFilter.VISIBLE, 
					FileFileFilter.FILE), null);
		for (File file: files)
			knownImages.add(new KnownImage(file, this));
		return knownImages;
	}
}