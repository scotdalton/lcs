/**
 * 
 */
package edu.nyu.cs.lcs;

import java.awt.Point;
import java.util.Map;

import com.google.inject.Inject;

import edu.nyu.cs.lcs.classifications.Classification;
import edu.nyu.cs.lcs.model.TrainedModel;

/**
 * UnknownImage extends Image for candidate images.
 * 
 * @author Scot Dalton
 *
 */
public class UnknownImage extends Image {
	@Inject private TrainedModel trainedModel;
	private Double croplandPercentage;
	private Double developedPercentage;
	private Double desertPercentage;
	private Double forestPercentage;

	/**
	 * @param filename
	 * @throws Exception 
	 */
	@Inject
	public UnknownImage(String filename, TrainedModel trainedModel) {
		super(filename);
		this.trainedModel = trainedModel;
	}
	
	/**
	 * @param image
	 * @throws Exception 
	 */
	@Inject
	public UnknownImage(Image image, TrainedModel trainedModel) {
		super(image);
		this.trainedModel = trainedModel;
	}
	
	@Override
	public Map<Point, Classification> getClassificationMap() throws Exception {
		return trainedModel.classifyImage(this);
	}
	
	public double getCroplandPercentage() throws Exception {
		if(croplandPercentage == null) classify();
		return croplandPercentage;
	}
	
	public double getDevelopedPercentage() throws Exception {
		if(developedPercentage == null) classify();
		return developedPercentage;
	}
	
	public double getDesertPercentage() throws Exception {
		if(desertPercentage == null) classify();
		return desertPercentage;
	}
	
	public double getForestPercentage() throws Exception {
		if(forestPercentage == null) classify();
		return forestPercentage;
	}
	
	private void classify() throws Exception {
		int arableCount = 0;
		int developedCount = 0;
		int desertCount = 0;
		int forestCount = 0;
		for(Classification classification: getClassificationMap().values()) {
			if(classification.equals(Classification.CROPLAND)) {
				arableCount++;
			} else if (classification.equals(Classification.DEVELOPED)) {
				developedCount++;
			} else if (classification.equals(Classification.DESERT)) {
				desertCount++;
			} else if (classification.equals(Classification.FOREST)) {
				forestCount++;
			}
		}
		croplandPercentage = (double)100*arableCount;
		developedPercentage = (double)100*developedCount;
		desertPercentage = (double)100*desertCount;
		forestPercentage = (double)100*forestCount;
	}
}
