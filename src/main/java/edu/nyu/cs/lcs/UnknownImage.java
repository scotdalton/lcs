/**
 * 
 */
package edu.nyu.cs.lcs;

import java.util.List;

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
	private List<Image> choppedImages;
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
		return trainedModel.classifyImage(this);
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
//		List<Image> choppedImages = getChoppedImages();
//		for(Image choppedImage : choppedImages) {
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
		arablePercentage = 100*arableCount/(double)choppedImages.size();
		developedPercentage = 100*developedCount/(double)choppedImages.size();
		desertPercentage = 100*desertCount/(double)choppedImages.size();
		forestPercentage = 100*forestCount/(double)choppedImages.size();
	}
}
