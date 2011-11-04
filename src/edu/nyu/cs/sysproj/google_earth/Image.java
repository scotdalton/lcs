/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.media.jai.Histogram;
import javax.media.jai.JAI;

import com.google.common.collect.Lists;

import edu.nyu.cs.sysproj.google_earth.features.Feature;

/**
 * Image is the core class of the project.  It represents a Google Earth
 * satellite image, gives a set of features that can be used to 
 * determine the arability classification of the image and provides
 * methods for extracting the arability classification.
 *  
 * @author Scot Dalton
 * 
 */
public class Image {
	private final float CROPPED_WIDTH = 1000;
	private final float CROPPED_HEIGHT = 1000;
	private final int CHOPPED_COLUMNS =10;
	private final int CHOPPED_ROWS = 10;
	private RenderedImage renderedImage;
	private Histogram histogram;
	private int width;
	private int height;
	private int minX;
	private int minY;
	private ArabilityClassification classification;
	private List<Feature> features;

	/**
	 * Protected constructor for general use.
	 * @param imageFileName
	 */
	protected Image(String imageFileName) {
		this(new File(imageFileName));
	}
	
	/**
	 * Protected contructor for known images (training data).
	 * @param imageFile
	 * @param classification
	 */
	protected Image(File imageFile, ArabilityClassification classification) {
		this(imageFile);
		this.classification = classification;
	}
	
	/**
	 * Constructor set to protected for testing purposes.
	 * Should be private.  Allows for flexibility in the implementation
	 * so we can use other libraries if necessary.
	 * @param imageFile
	 */
	protected Image(File imageFile) {
		this(JAI.create("fileload", imageFile.getAbsolutePath()));
	}
	
	/**
	 * Private constructor for general use case
	 * @param renderedImage
	 */
	private Image(RenderedImage renderedImage) {
		this.renderedImage = 
			cropOriginal(CROPPED_WIDTH, CROPPED_HEIGHT).
				getRenderedImage();
		width = renderedImage.getWidth();
		height = renderedImage.getHeight();
		minX = renderedImage.getMinX();
		minY = renderedImage.getMinY();
		// Get the histogram from JAI and store it
		int[] bins = {256, 256, 256};
		double[] low = {0.0D, 0.0D, 0.0D};
		double[] high = {256.0D, 256.0D, 256.0D};
		histogram = new Histogram(bins, low, high);
		ParameterBlock histogramPB = (new ParameterBlock()).
			addSource(renderedImage).add(null).add(1).add(1);
		RenderedImage histogramImage = 
			(RenderedImage)JAI.create("histogram", histogramPB, null);
	     // Retrieve the histogram data.
	     histogram = (Histogram) histogramImage.getProperty("histogram");
	}
	
	/**
	 * Returns the list of features for the image. 
	 * @return
	 */
	public List<Feature> getFeatures() {
		features = Lists.newArrayList();
		for(ArabilityFeature arabilityFeature : ArabilityFeature.values())
			features.add(arabilityFeature.instantiate(this));
		return features;
	}

	/**
	 * Returns the classification of the image.
	 * @return
	 * @throws Exception
	 */
	public ArabilityClassification getClassification() throws Exception {
		if(classification == null)
			classification = TrainedModel.getTrainedModel().eval(this);
		return classification;
	}
	
	/**
	 * Returns the images width
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns the images height
	 * @return
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Returns the minimum X value
	 * @return
	 */
	public int getMinX() {
		return minX;
	}
	
	/**
	 * Returns the minimum Y value.
	 * @return
	 */
	public int getMinY() {
		return minY;
	}
	
	/**
	 * Returns the image's histogram
	 * TODO: Create histogram object that decorates JAI Histogram
	 * to encapsulate implementation 
	 * @return
	 */
	public Histogram getHistogram() {
		return histogram;
	}
	
	/**
	 * Returns true if the the image has passed all the validity checks.
	 * @return
	 */
	public boolean isValid() {
		for(ValidityCheck valididityCheck: ValidityCheck.values())
			if(!valididityCheck.isValid(this))
				return false;
		return true;
	}
	
	/**
	 * Return an unmodifiable list of chopped images.
	 * @return
	 */
	public List<Image> getChoppedImages() {
		return Collections.unmodifiableList(
			chop(this, CHOPPED_COLUMNS, CHOPPED_ROWS));
	}
	
	/**
	 * Get the rendered image.
	 * TODO: Make this private to encapsulate implementation
	 * @return
	 */
	public RenderedImage getRenderedImage() {
		return renderedImage;
	}

	/**
	 * Returns a column * rows list of chopped images for the given image
	 * @param image
	 * @param columns
	 * @param rows
	 * @return
	 */
	private List<Image> chop(Image image, int columns, int rows) {
		List<Image> choppedImages = Lists.newArrayList();
		float columnWidth = image.getWidth()/columns;
		float rowHeight = image.getHeight()/rows;
		for(int column = 0; column < columns; column++) {
			for(int row = 0; row < rows; row++) {
				float originX = 
					column * columnWidth + image.getMinX();
				float originY = 
					row * rowHeight + image.getMinY();
				choppedImages.add(crop(
					image, originX, originY, columnWidth, rowHeight));
			}
		}
		return choppedImages;
	}
	
	/**
	 * Returns a cropped image based on the original image, with the 
	 * given dimensions
	 * @param width
	 * @param height
	 * @return
	 */
	private Image cropOriginal(float width, float height) {
		float originX = 
			renderedImage.getWidth()/2 - width/2 + renderedImage.getMinX();
		float originY = 
			renderedImage.getHeight()/2 - height/2 + renderedImage.getMinY();
		return crop(this, originX, originY, width, height);
	}
	
	/**
	 * Returns a cropped image based on the input specifications.
	 * @param image
	 * @param originX
	 * @param originY
	 * @param width
	 * @param height
	 * @return
	 */
	private Image crop(Image image, float originX, float originY, 
			float width, float height) {
		// Don't crop if smaller than crop dimensions.
		if (image.getWidth() < width || image.getHeight() < height)
			return image;
		ParameterBlock croppedImageParams = 
			new ParameterBlock().addSource(image.renderedImage).
				add(originX).add(originY).add(width).add(height);
		Image croppedImage = 
			new Image(JAI.create("crop", croppedImageParams));
		// If the (parent) image has a classification, 
		// it's crop (child) has the same classification 
		if(classification != null)
			croppedImage.classification = image.classification;
		return croppedImage;
	}
}