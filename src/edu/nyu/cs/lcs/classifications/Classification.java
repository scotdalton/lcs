/**
 * 
 */
package edu.nyu.cs.lcs.classifications;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.HiddenFileFilter;

import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;

import edu.nyu.cs.lcs.Image;
import edu.nyu.cs.lcs.KnownImage;
import edu.nyu.cs.lcs.classifications.ClassificationModule.Alpha;
import edu.nyu.cs.lcs.classifications.ClassificationModule.Blue;
import edu.nyu.cs.lcs.classifications.ClassificationModule.Green;
import edu.nyu.cs.lcs.classifications.ClassificationModule.IsTrainable;
import edu.nyu.cs.lcs.classifications.ClassificationModule.Red;
import edu.nyu.cs.lcs.classifications.ClassificationModule.ShortName;
import edu.nyu.cs.lcs.classifications.ClassificationModule.TestingDirectory;
import edu.nyu.cs.lcs.classifications.ClassificationModule.TrainingDirectory;

/**
 * Classification defines the set of classifications we're 
 * working with.
 * 
 * @author Scot Dalton
 * 
 */
public enum Classification {
	CROPLAND(new CroplandModule()),
	DEVELOPED(new DevelopedModule()),
	DESERT(new DesertModule()),
	FOREST(new ForestModule()),
	UNKNOWN(new UnknownModule());
	
	@Inject
	private String shortName;
	@Inject
	private boolean isTrainable;
	@Inject
	private String trainingDirectory;
	@Inject
	private String testingDirectory;
	@Inject
	private int red;
	@Inject
	private int green;
	@Inject
	private int blue;
	@Inject
	private int alpha;
	
	private Classification(ClassificationModule classificationModule) {
		Injector injector = Guice.createInjector(classificationModule);
		shortName = 
			injector.getInstance(Key.get(String.class, ShortName.class));
		isTrainable = 
			injector.getInstance(
				Key.get(Boolean.class, IsTrainable.class));
		if(isTrainable) {
			trainingDirectory = 
				injector.getInstance(
					Key.get(String.class, TrainingDirectory.class));
			testingDirectory = 
				injector.getInstance(
					Key.get(String.class, TestingDirectory.class));
		}
		red = injector.getInstance(Key.get(Integer.class, Red.class));
		green = injector.getInstance(Key.get(Integer.class, Green.class));
		blue = injector.getInstance(Key.get(Integer.class, Blue.class));
		alpha = injector.getInstance(Key.get(Integer.class, Alpha.class));
	}
	
	public boolean isTrainable() {
		return isTrainable;
	}
	
	public int getRed() {
		return red;
	};

	public int getGreen() {
		return green;
	};

	public int getBlue() {
		return blue;
	};
	
	public int getAlpha() {
		return alpha;
	}
	
	public List<Image> getTrainingImages() {
		if(!isTrainable())
			throw new UnsupportedOperationException("Not trainable.");
		return getKnownImages(trainingDirectory);
	}
	
	public List<Image> getTestingImages() {
		if(!isTrainable())
			throw new UnsupportedOperationException("Not trainable.");
		return getKnownImages(testingDirectory);
	}

	@Override
	public String toString() {
		return shortName;
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