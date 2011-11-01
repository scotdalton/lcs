/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import com.google.common.collect.Lists;

/**
 * @author Scot Dalton
 *
 */
public class Image {
	private final float CROPPED_WIDTH = 1000;
	private final float CROPPED_HEIGHT = 1000;
	private final int CHOPPED_COLUMNS =10;
	private final int CHOPPED_ROWS = 10;
	private RenderedImage originalImage;
	private int width;
	private int height;
	private int minX;
	private int minY;
	private ArabilityClassification classification;

	public Image(String imageFileName) {
		this(new File(imageFileName));
	}
	
	public Image(File imageFile) {
		this(JAI.create("fileload", imageFile.getAbsolutePath()));
	}
	
	public Image(RenderedImage renderedImage) {
		originalImage = renderedImage;
		width = originalImage.getWidth();
		height = originalImage.getHeight();
		minX = originalImage.getMinX();
		minY = originalImage.getMinY();
	}
	
	protected Image(File imageFile, ArabilityClassification classification) {
		this(imageFile);
		this.classification = classification;
	}
	
	private Image(RenderedImage renderedImage, ArabilityClassification classification) {
		this(renderedImage);
		this.classification = classification;
	}
	
	public List<Feature> getFeatures() {
		return null;
	}

	public ArabilityClassification getClassification() {
		return classification;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getMinX() {
		return minX;
	}
	
	public int getMinY() {
		return minY;
	}
	
	public List<Image> getChoppedImages() {
		return Collections.unmodifiableList(
			chop(getCroppedImage(), CHOPPED_COLUMNS, CHOPPED_ROWS));
	}
	
	public RenderedImage getOriginalImage() {
		return originalImage;
	}

	protected Image getCroppedImage() {
		return cropOriginal(CROPPED_WIDTH, CROPPED_HEIGHT);
	}

	private List<Image> chop(Image image, int columns, int rows) {
		List<Image> choppedImages = Lists.newArrayList();
		float columnWidth = image.getWidth()/columns;
		float rowHeight = image.getHeight()/rows;
		for(int colCounter = 0; colCounter < columns; colCounter++) {
			for(int rowCounter = 0; rowCounter < rows; rowCounter++) {
				float originX = 
					colCounter * columnWidth + image.getMinX();
				float originY = 
					rowCounter * rowHeight + image.getMinY();
				choppedImages.add(crop(
					image, originX, originY, columnWidth, rowHeight));
			}
		}
		return choppedImages;
	}
	
	private Image cropOriginal(float width, float height) {
		float originX = 
			originalImage.getWidth()/2 - width/2 + originalImage.getMinX();
		float originY = 
			originalImage.getHeight()/2 - height/2 + originalImage.getMinY();
		return crop(this, originX, originY, width, height);
	}

	private Image crop(Image image, float originX, 
			float originY, float width, float height) {
		if (image.getWidth() < width || 
				image.getHeight() < height)
			throw new IllegalArgumentException(
				"Crop dimensions are larger than the image.");
		ParameterBlock croppedImageParams = 
			new ParameterBlock().addSource(image.getOriginalImage()).
			add(originX).
				add(originY).add(width).add(height);
		return new Image(JAI.create("crop", croppedImageParams), classification);
	}
}