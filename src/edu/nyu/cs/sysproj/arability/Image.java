/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

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
import java.util.Date;
import java.util.List;

import javax.media.jai.Histogram;
import javax.media.jai.ImageLayout;
import javax.media.jai.ImagePyramid;
import javax.media.jai.InterpolationNearest;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import com.google.common.collect.Lists;

import edu.nyu.cs.sysproj.arability.features.Feature;
import edu.nyu.cs.sysproj.arability.utility.Configuration;

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
	private RenderedImage renderedImage;
	private Image discreteCosineTransform;
	private Image downImage;
	private RenderedImage greyscaleImage;
	private Histogram histogram;
	private int width;
	private int height;
	private int minX;
	private int minY;
	private int downSampleSquareRoot;
	private float downSampleSize;
	private float croppedWidth;
	private float croppedHeight;
	private float choppedWidth;
	private float choppedHeight;
	private int choppedColumns;
	private int choppedRows;
	private ArabilityClassification classification;
	private List<Feature> features;
	private Date date;

	/**
	 * Protected constructor for general use.
	 * @param imageFileName
	 */
	protected Image(String imageFileName) {
		this(new File(imageFileName));
	}

	/**
	 * Protected constructor for general use.
	 * @param imageFileName
	 * @param date
	 */
	protected Image(String imageFileName, Date date) {
		this(new File(imageFileName), date);
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
	 * Constructor set to public for testing and utility purposes.
	 * Should be private.  Allows for flexibility in the implementation
	 * so we can use other libraries if necessary.
	 * @param imageFile
	 */
	public Image(File imageFile) {
		this(JAI.create("fileload", imageFile.getAbsolutePath()));
	}
	
	/**
	 * Constructor set to protected for testing purposes.
	 * Should be private.  Allows for flexibility in the implementation
	 * so we can use other libraries if necessary.
	 * @param imageFile
	 */
	protected Image(File imageFile, Date date) {
		this(JAI.create("fileload", imageFile.getAbsolutePath()));
	}
	
	/**
	 * Constructor for general use case
	 * @param renderedImage
	 */
	public Image(RenderedImage renderedImage) {
		setDefaults();
		if(skipChop(renderedImage))
			this.renderedImage = renderedImage;
		else {
			croppedWidth = getCroppedWidth(renderedImage);
			croppedHeight = getCroppedHeight(renderedImage);
			this.renderedImage = 
				centeredCrop(renderedImage, croppedWidth, croppedHeight);
		}
		setDimensions(this.renderedImage);
	}
	
	/**
	 * Constructor for general use case
	 * @param renderedImage
	 * @param date
	 */
	public Image(RenderedImage renderedImage, Date date) {
		setDefaults();
		this.renderedImage = 
			centeredCrop(renderedImage, croppedWidth, croppedHeight);
		setDimensions(this.renderedImage);
		this.date = date;
	}
	
	/**
	 * Constructor for general use case
	 * @param image
	 */
	public Image(Image image, Date date) {
		this(image);
		this.date = date;
	}

	/**
	 * Constructor for general use case
	 * @param image
	 */
	public Image(Image image) {
		setDefaults();
		renderedImage = image.renderedImage;
		setDimensions(this.renderedImage);
		discreteCosineTransform = image.discreteCosineTransform;
		downImage = image.downImage;
		greyscaleImage = image.greyscaleImage;
		histogram = image.histogram;
		classification = image.classification;
		date = image.date;
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
	 * Returns the image's width
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns the image's height
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
	 * Returns the date.
	 * @return
	 */
	public Date getDate() {
		return date;
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
		double[] standardDeviations = 
			getHistogram().getStandardDeviation();
		return Arrays.copyOf(standardDeviations, standardDeviations.length);
	}

	/**
	 * Returns the image scaled down based on the downSampleSize
	 * @return
	 */
	public Image getDownImage() {
		if(downImage == null) {
			RenderedOp downSampler = 
				createScaleOp(renderedImage, downSampleSize);
			RenderedOp upSampler = 
				createScaleOp(renderedImage, 1/downSampleSize);
			RenderedOp differencer = 
				createSubtractOp(renderedImage, renderedImage);
			RenderedOp combiner = 
				createAddOp(renderedImage, renderedImage);
			ImagePyramid pyramid = 
				new ImagePyramid(renderedImage, downSampler, upSampler, differencer, combiner);
			downImage = new Image(pyramid.getDownImage());
		}
		return downImage;
	}
	
	public float getDownSample(int x, int y, int b) {
		Image image = getDownImage();
		x = image.getMinX() + x;
		y = image.getMinY() + y;
//		return image.getRenderedImage().getData().
//			getPixels(x, y, 0, 0, (float[])null)[0];
		return image.getRenderedImage().getData().getSampleFloat(x, y, b);
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
		choppedColumns = (int) (croppedWidth/choppedWidth);
		choppedRows = (int) (croppedHeight/choppedHeight);
		return Collections.unmodifiableList(
			chop(this, choppedColumns, choppedRows));
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
	
	protected int getChoppedRows() {
		return choppedRows;
	}
	
	protected int getChoppedColumns() {
		return choppedColumns;
	}
	
	protected float getChoppedWidth() {
		return choppedWidth;
	}
	
	protected float getChoppedHeight() {
		return choppedHeight;
	}
	
	private float getCroppedWidth(RenderedImage renderedImage) {
		if (croppedWidth > renderedImage.getWidth())
			croppedWidth = 
				(float) (Math.floor(renderedImage.getWidth()/(double)choppedWidth)*choppedWidth);
		return croppedWidth;
	}
	
	private float getCroppedHeight(RenderedImage renderedImage) {
		if (croppedHeight > renderedImage.getHeight())
			croppedHeight = 
				(float) (Math.floor(renderedImage.getHeight()/(double)choppedHeight)*choppedHeight);
		return croppedHeight;
	}
	
	private boolean skipChop(RenderedImage renderedImage) {
		return renderedImage.getWidth() <= choppedWidth && 
			renderedImage.getHeight() <= choppedHeight;
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

	private RenderedOp createScaleOp(RenderedImage im, float scale) {
		ParameterBlock pb = 
			new ParameterBlock().addSource(im).add(scale).add(scale).
				add(0.0F).add(0.0F).add(new InterpolationNearest());
		return JAI.create("scale", pb, null);
	}

	private RenderedOp createSubtractOp(RenderedImage src1,
            RenderedImage src2) {
		ParameterBlock pb = 
			new ParameterBlock().addSource(src1).addSource(src2);
		return JAI.create("subtract", pb);
	}

	private RenderedOp createAddOp(RenderedImage src1, 
			RenderedImage src2) {
		ParameterBlock pb = 
			new ParameterBlock().addSource(src1).addSource(src2);
		return JAI.create("add", pb);
	}
	
	private void setDimensions(RenderedImage renderedImage) {
		width = renderedImage.getWidth();
		height = renderedImage.getHeight();
		minX = renderedImage.getMinX();
		minY = renderedImage.getMinY();
		downSampleSize = downSampleSquareRoot/(float)width;
	}
	
	private void setDefaults() {
		downSampleSquareRoot = Configuration.DOWN_SAMPLE_SQUARE_ROOT;
		croppedWidth = Configuration.CROPPED_WIDTH;
		croppedHeight = Configuration.CROPPED_HEIGHT;
		choppedWidth = Configuration.CHOPPED_WIDTH;
		choppedHeight = Configuration.CHOPPED_HEIGHT;
//		choppedColumns = Configuration.CHOPPED_COLUMNS;
//		choppedRows = Configuration.CHOPPED_ROWS;
	}
}