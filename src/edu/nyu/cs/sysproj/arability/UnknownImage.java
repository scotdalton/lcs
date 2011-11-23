/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

import java.util.List;

import Jama.Matrix;

/**
 * UnknownImage extends Image for candidate images.
 * 
 * @author Scot Dalton
 *
 */
public class UnknownImage extends Image {
	private Matrix arabilityMatrix;
	private double arablePercentage;
	private double nonArablePercentage;

	/**
	 * @param imageFileName
	 * @throws Exception 
	 */
	public UnknownImage(String imageFileName) throws Exception {
		super(imageFileName);
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
	
	public double getNonArablePercentage() {
		return nonArablePercentage;
	}
	
	public Matrix getArabilityMatrix() {
		return arabilityMatrix;
	}
	
	private void processArability() throws Exception {
		int arableCount = 0;
		int nonArableCount = 0;
		List<Image> choppedImages = getChoppedImages();
		double[][] arabilityValues = 
			new double[getChoppedRows()][getChoppedColumns()];
		int tileOffsetX = 
			(getRenderedImage().getTileWidth() - getWidth())/2;
		int tileOffsetY = 
			(getRenderedImage().getTileHeight() - getHeight())/2;
		for(Image choppedImage : choppedImages) {
			int row = 
				(int) ((choppedImage.getMinX()-tileOffsetX)/getChoppedWidth());
			int column = 
				(int) ((choppedImage.getMinY()-tileOffsetY)/getChoppedHeight());
			ArabilityClassification classification = 
				choppedImage.getClassification();
			if(classification.equals(ArabilityClassification.ARABLE)) {
				arabilityValues[row][column]=1;
				arableCount++;
			} else if (classification.equals(ArabilityClassification.NON_ARABLE)) {
				arabilityValues[row][column]=0;
				nonArableCount++;
			}
		}
		arablePercentage = arableCount/(double)choppedImages.size();
		nonArablePercentage = nonArableCount/(double)choppedImages.size();
		arabilityMatrix = new Matrix(arabilityValues);
	}
}
