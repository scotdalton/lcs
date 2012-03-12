/**
 * 
 */
package edu.nyu.cs.lcs;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import edu.nyu.cs.lcs.classifications.Classification;

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
	
	/**
	 * Returns the classification of the image.
	 * @return
	 * @throws Exception
	 */
	@Override
	public Classification getClassification() {
		if(classification == null)
			try {
				classification = trainedModel.classifyImage(this);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return classification;
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
	
	/**
	 * Return an unmodifiable list of chopped images.
	 * @return
	 * @throws  
	 */
	@Override
	public List<Image> getChoppedImages() {
		if(choppedImages == null) {
			choppedImages = Lists.newArrayList();
			for(Image image: super.getChoppedImages())
				choppedImages.add(new UnknownImage(image, trainedModel));
		}
		return Collections.unmodifiableList(choppedImages);
	}
	
	private void classify() {
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
