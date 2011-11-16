/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

import java.util.List;

/**
 * UnknownImage extends Image for candidate images.
 * 
 * @author Scot Dalton
 *
 */
public class UnknownImage extends Image {
	private double arablePercentage;
	private double nonArablePercentage;

	/**
	 * @param imageFileName
	 * @throws Exception 
	 */
	public UnknownImage(String imageFileName) throws Exception {
		super(imageFileName);
		setPercentages();
	}
	
	/**
	 * @param renderedImage
	 * @throws Exception 
	 */
	public UnknownImage(Image image) throws Exception {
		super(image);
		setPercentages();
	}

	public double getArablePercentage() {
		return arablePercentage;
	}
	
	public double getNonArablePercentage() {
		return nonArablePercentage;
	}
	
	private void setPercentages() throws Exception {
		int arableCount = 0;
		int nonArableCount = 0;
		List<Image> choppedImages = getChoppedImages();
		for(Image choppedImage : choppedImages) {
			ArabilityClassification classification = 
				choppedImage.getClassification();
			if(classification.equals(ArabilityClassification.ARABLE))
				arableCount++;
			else if (classification.equals(ArabilityClassification.NON_ARABLE))
				nonArableCount++;
		}
		arablePercentage = arableCount/(double)choppedImages.size();
		nonArablePercentage = nonArableCount/(double)choppedImages.size();
	}
}
