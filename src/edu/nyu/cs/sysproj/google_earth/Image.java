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

import com.google.common.collect.Lists;

import edu.nyu.cs.sysproj.google_earth.util.ArabilityClassificationMatcher;

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

	public Image(String imageFileName) {
		this(new File(imageFileName));
	}
	
	public Image(File imageFile) {
		this(JAI.create("fileload", imageFile.getAbsolutePath()));
	}
	
	public Image(RenderedImage renderedImage) {
		originalImage = renderedImage;
	}
	
	public ArabilityClassification getDevelopmentState() {
		return ArabilityClassificationMatcher.getDevelopmentState(this);
	}
	
	public ImageFeatures getImageFeatures() {
		return new ImageFeatures(this);
	}

	public RenderedImage getOriginalImage() {
		return originalImage;
	}

	public Image getCroppedImage() {
		return cropOriginal(CROPPED_WIDTH, CROPPED_HEIGHT);
	}

	public List<Image> getChoppedImages() {
		return Collections.unmodifiableList(chop(this, CHOPPED_COLUMNS, CHOPPED_ROWS));
	}
	
	private List<Image> chop(Image image, int columns, int rows) {
		List<Image> choppedImages = Lists.newArrayList();
		RenderedImage renderedImage = image.getOriginalImage();
		float columnWidth = renderedImage.getWidth()/columns;
		float rowHeight = renderedImage.getHeight()/rows;
		for(int colCounter = 0; colCounter < columns; colCounter++) {
			for(int rowCounter = 0; rowCounter < rows; rowCounter++) {
				float originX = 
					colCounter * columnWidth + renderedImage.getMinX();
				float originY = 
					rowCounter * rowHeight + renderedImage.getMinY();
				choppedImages.add(crop(image, originX, originY, columnWidth, rowHeight));
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
		RenderedImage renderedImage = image.getOriginalImage();
		if (renderedImage.getWidth() < width || 
				renderedImage.getHeight() < height)
			throw new IllegalArgumentException(
				"Crop dimensions are larger than the image.");
		ParameterBlock croppedImageParams = 
			new ParameterBlock().addSource(renderedImage).add(originX).
				add(originY).add(width).add(height);
		return new Image(JAI.create("crop", croppedImageParams));
	}
}