/**
 * 
 */
package edu.nyu.cs.lcs;

import com.google.inject.Inject;
import edu.nyu.cs.lcs.model.TrainedModel;

/**
 * UnknownImage extends Image for candidate images.
 * 
 * @author Scot Dalton
 *
 */
public class UnknownImage extends Image {
	@Inject private TrainedModel trainedModel;
	private Double arablePercentage;
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
	public Image getClassificationImage() throws Exception {
		return overlay(trainedModel.classifyImage(this));
	}
	
	public double getArablePercentage() {
		if(arablePercentage == null) classify();
		return arablePercentage;
	}
	
	public double getDevelopedPercentage() {
		if(developedPercentage == null) classify();
		return developedPercentage;
	}
	
	public double getDesertPercentage() {
		if(desertPercentage == null) classify();
		return desertPercentage;
	}
	
	public double getForestPercentage() {
		if(forestPercentage == null) classify();
		return forestPercentage;
	}
	
	private void classify() {
		int arableCount = 0;
		int developedCount = 0;
		int desertCount = 0;
		int forestCount = 0;
//		for(double pixel : getPixels(new double[0]));
//		for(Image choppedImage : ) {
//			Classification classification = 
//				choppedImage.getClassification();
//			if(classification.equals(Classification.CROPLAND)) {
//				arableCount++;
//			} else if (classification.equals(Classification.DEVELOPED)) {
//				developedCount++;
//			} else if (classification.equals(Classification.DESERT)) {
//				desertCount++;
//			} else if (classification.equals(Classification.FOREST)) {
//				forestCount++;
//			}
//		}
		arablePercentage = (double)100*arableCount;
		developedPercentage = (double)100*developedCount;
		desertPercentage = (double)100*desertCount;
		forestPercentage = (double)100*forestCount;
	}
}
