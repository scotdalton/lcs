/**
 * 
 */
package edu.nyu.cs.lcs;

import java.io.File;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import edu.nyu.cs.lcs.model.TrainedModel;

/**
 * @author Scot Dalton
 *
 */
public class RegionImage extends UnknownImage {

	/**
	 * @param filename
	 * @throws Exception 
	 */
	@Inject
	public RegionImage(String filename, TrainedModel trainedModel) {
		super(filename, trainedModel);
	}

	/**
	 * @param file
	 * @throws Exception 
	 */
	@Inject
	public RegionImage(File file, TrainedModel trainedModel) {
		super(file, trainedModel);
	}
	
	/**
	 * @param image
	 * @param trainedModel
	 */
	public RegionImage(Image image, TrainedModel trainedModel) {
		super(image, trainedModel);
	}

	public List<String> toCSV() throws Exception {
		return Lists.newArrayList(
			getLatitude(),
			getLongitude(),
			getDateString(),
			Double.toString(getCroplandPercentage()),
			Double.toString(getDevelopedPercentage()),
			Double.toString(getDesertPercentage()),
			Double.toString(getForestPercentage()),
			getName(),
			Long.toString(getFile().length()));
	}
	
	public String getLatitude() {
		return getName().split("-")[0];
	}
	
	public String getLongitude() {
		return getName().split("-")[1];
	}
	
	public String getDateString() {
		return getFile().getParent();
	}
}
