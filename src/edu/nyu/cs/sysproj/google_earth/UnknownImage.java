/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import java.util.List;

/**
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
		int arableCount = 0;
		int nonArableCount = 0;
		List<Image> choppedImages = getChoppedImages();
		for(Image choppedImage : choppedImages) {
			if(choppedImage.getClassification().equals(ArabilityClassification.ARABLE))
				arableCount++;
			else if (choppedImage.getClassification().equals(ArabilityClassification.NON_ARABLE))
				nonArableCount++;
		}
		arablePercentage = arableCount/(double)choppedImages.size();
		nonArablePercentage = nonArableCount/(double)choppedImages.size();
	}

	public double getArablePercentage() {
		return arablePercentage;
	}
	
	public double getNonArablePercentage() {
		return nonArablePercentage;
	}
}
