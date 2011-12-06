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
	private double developedPercentage;
	private double desertPercentage;
	private double forestPercentage;

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
	
	public double getDevelopedPercentage() {
		return developedPercentage;
	}
	
	public double getDesertPercentage() {
		return desertPercentage;
	}
	
	public double getForestPercentage() {
		return forestPercentage;
	}
	
	public Matrix getArabilityMatrix() {
		return arabilityMatrix;
	}
	
	private void processArability() throws Exception {
		int arableCount = 0;
		int developedCount = 0;
		int desertCount = 0;
		int forestCount = 0;
		List<Image> choppedImages = getChoppedImages();
//		double[][] arabilityValues = 
//			new double[getChoppedRows()][getChoppedColumns()];
//		int tileOffsetX = 
//			(getRenderedImage().getTileWidth() - getWidth())/2;
//		int tileOffsetY = 
//			(getRenderedImage().getTileHeight() - getHeight())/2;
		for(Image choppedImage : choppedImages) {
//			int row = 
//				(int) ((choppedImage.getMinX()-tileOffsetX)/getChoppedWidth());
//			int column = 
//				(int) ((choppedImage.getMinY()-tileOffsetY)/getChoppedHeight());
			Classification classification = 
				choppedImage.getClassification();
			if(classification.equals(Classification.ARABLE)) {
//				arabilityValues[row][column]=1;
				arableCount++;
			} else if (classification.equals(Classification.DEVELOPED)) {
//				arabilityValues[row][column]=0;
				developedCount++;
//			} else if (classification.equals(ArabilityClassification.DESERT)) {
////				arabilityValues[row][column]=0;
//				desertCount++;
//			} else if (classification.equals(ArabilityClassification.FOREST)) {
////				arabilityValues[row][column]=0;
//				forestCount++;
			}
		}
		arablePercentage = 100*arableCount/(double)choppedImages.size();
		developedPercentage = 100*developedCount/(double)choppedImages.size();
		desertPercentage = 100*desertCount/(double)choppedImages.size();
//		arabilityMatrix = new Matrix(arabilityValues);
	}
}
