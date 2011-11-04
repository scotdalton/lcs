/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.media.jai.Histogram;
import javax.media.jai.ImageLayout;
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
	private Image discreteCosineTransform;
	private RenderedImage greyscaleImage;
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
			centeredCrop(renderedImage, CROPPED_WIDTH, CROPPED_HEIGHT);
		width = this.renderedImage.getWidth();
		height = this.renderedImage.getHeight();
		minX = this.renderedImage.getMinX();
		minY = this.renderedImage.getMinY();
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
	 * Returns the means for all the bands of the histogram 
	 * @return
	 */
	public double[] getMeans() {
		double[] means = getHistogram().getMean();
		return Arrays.copyOf(means, means.length);
	}
	
	/**
	 * Returns the standard deviations for all the bands of the histogram 
	 * @return
	 */
	public double[] getStandardDeviations() {
		double[] standardDeviations = getHistogram().getStandardDeviation();
		return Arrays.copyOf(standardDeviations, standardDeviations.length);
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
	 * Returns the RenderedImage.
	 * TODO: Make this private to encapsulate implementation
	 * @return
	 */
	public RenderedImage getRenderedImage() {
		return renderedImage;
	}
	
	/**
	 * Returns a RenderedImage that is the greyscale representation 
	 * of the image.
	 * @return
	 */
	public RenderedImage getGreyscaleImage() {
		if(greyscaleImage == null) {
			ColorModel cm = 
				new ComponentColorModel(ColorSpace.getInstance(
					ColorSpace.CS_GRAY), new int[] {8}, false, false, 
						Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
			ParameterBlock greyscalePB = 
				(new ParameterBlock()).addSource(renderedImage).add(cm);
			ImageLayout greyscaleLayout = 
				(new ImageLayout()).setMinX(minX).
					setMinY(minY).setColorModel(cm);
			RenderingHints greyscaleRH = 
				new RenderingHints(JAI.KEY_IMAGE_LAYOUT, greyscaleLayout);
			greyscaleImage = 
				JAI.create("ColorConvert", greyscalePB, greyscaleRH);
		}
		return greyscaleImage;
	}
	
	/**
	 * Returns a RenderedImage that representst the coefficients from
	 * a Discrete Cosine Transform.
	 * @return
	 */
	public Image getDiscreteCosineTransform() {
		if (discreteCosineTransform == null) {
			ParameterBlock dctPB = 
				(new ParameterBlock()).addSource(this.getGreyscaleImage());
			discreteCosineTransform = 
				new Image(JAI.create("dct", dctPB, null));
		}
		return discreteCosineTransform;
	}
	
	/**
	 * Gets the histogram for the image.  If the histogram doesn't
	 * exist, this function creates it.
	 * @return
	 */
	private Histogram getHistogram() {
		if (histogram == null) {
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
		return histogram;
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
	 * Returns a cropped RenderedImage centered on the input RenderedImage, 
	 * with the given dimensions.
	 * @param width
	 * @param height
	 * @return
	 */
	private RenderedImage centeredCrop(RenderedImage renderedImage,
			float width, float height) {
		float originX = 
			renderedImage.getWidth()/2 - width/2 + renderedImage.getMinX();
		float originY = 
			renderedImage.getHeight()/2 - height/2 + renderedImage.getMinY();
		return crop(renderedImage, originX, originY, width, height);
	}
	
	/**
	 * Returns a cropped Image based on the input specifications.
	 * @param image
	 * @param originX
	 * @param originY
	 * @param width
	 * @param height
	 * @return
	 */
	private Image crop(Image image, float originX, float originY, 
			float width, float height) {
		Image croppedImage = 
			new Image(crop(image.renderedImage, 
				originX, originY, width, height));
		// If the (parent) image has a classification, 
		// it's crop (child) has the same classification 
		if(classification != null)
			croppedImage.classification = image.classification;
		return croppedImage;
	}
	
	/**
	 * Returns a cropped RenderedImage based on the input specifications.
	 * @param renderedImage
	 * @param originX
	 * @param originY
	 * @param width
	 * @param height
	 * @return
	 */
	private RenderedImage crop(RenderedImage renderedImage, float originX, 
			float originY, float width, float height) {
		// Don't crop if smaller than crop dimensions.
		if (renderedImage.getWidth() < width || 
				renderedImage.getHeight() < height)
			return renderedImage;
		ParameterBlock croppedImageParams = 
			new ParameterBlock().addSource(renderedImage).
				add(originX).add(originY).add(width).add(height);
		return JAI.create("crop", croppedImageParams);
	}
}