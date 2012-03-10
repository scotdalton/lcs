/**
 * 
 */
package edu.nyu.cs.lcs;

import java.util.List;

import edu.nyu.cs.lcs.classifications.Classification;

/**
 * UnknownImage extends Image for candidate images.
 * 
 * @author Scot Dalton
 *
 */
public class UnknownImage extends Image {
	private double arablePercentage;
	private double developedPercentage;
	private double desertPercentage;
	private double forestPercentage;

	/**
	 * @param imageFileName
	 * @throws Exception 
	 */
	public UnknownImage(String imageFileName, TrainedModel trainedModel)
			throws Exception {
		super(imageFileName, trainedModel);
		processArability();
	}
	
	/**
	 * @param renderedImage
	 * @throws Exception 
	 */
	public UnknownImage(Image image) throws Exception {
		super(image);
		processArability();
	}

	public double getArablePercentage() {
		return arablePercentage;
	}
	
	public double getDevelopedPercentage() {
		return developedPercentage;
	}
	
	public double getDesertPercentage() {
		return desertPercentage;
	}
	
	public double getForestPercentage() {
		return forestPercentage;
	}
	
	private void processArability() throws Exception {
		int arableCount = 0;
		int developedCount = 0;
		int desertCount = 0;
		int forestCount = 0;
		List<Image> choppedImages = getChoppedImages();
		for(Image choppedImage : choppedImages) {
			Classification classification = 
				choppedImage.getClassification();
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
		arablePercentage = 100*arableCount/(double)choppedImages.size();
		developedPercentage = 100*developedCount/(double)choppedImages.size();
		desertPercentage = 100*desertCount/(double)choppedImages.size();
//		arabilityMatrix = new Matrix(arabilityValues);
	}
}
