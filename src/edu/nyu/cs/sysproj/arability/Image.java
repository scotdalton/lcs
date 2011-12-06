/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.media.jai.BorderExtender;
import javax.media.jai.Histogram;
import javax.media.jai.ImageLayout;
import javax.media.jai.ImagePyramid;
import javax.media.jai.InterpolationNearest;
import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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
	private Image gradientMagnitude;
	private Image downImage;
	private RenderedImage greyscaleImage;
	private Histogram histogram;
	private BufferedImage bufferedImage;
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
	private Classification classification;
	private Date date;
	private static final float[] FREI_CHEN_HORIZONTAL = { 1.0F, 0.0F, -1.0F, 
		1.414F, 0.0F, -1.414F, 1.0F, 0.0F, -1.0F };
	private static final float[] FREI_CHEN_VERTICAL = { -1.0F, -1.414F, -1.0F,
		0.0F, 0.0F, 0.0F, 1.0F, 1.414F, 1.0F };
	private static final float[] PREWITT_HORIZONTAL = { 1.0F, 0.0F, -1.0F, 
		1.0F, 0.0F, -1.0F, 1.0F, 0.0F, -1.0F };
	private static final float[] PREWITT_VERTICAL = { -1.0F, -1.0F, -1.0F,
		0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F };
	private static final float[] ROBERTS_HORIZONTAL = { 0.0F, 0.0F, -1.0F, 0.0F,  
		1.0F, 0.0F, 0.0F, 0.0F, 0.0F };
	private static final float[] ROBERTS_VERTICAL = { -1.0F,  0.0F,  0.0F, 0.0F, 
		1.0F, 0.0F, 0.0F, 0.0F, 0.0F };
	private enum KernelDimension {HORIZONTAL, VERTICAL};
	public enum GradientKernel{
		FREI_CHEN(FREI_CHEN_HORIZONTAL, FREI_CHEN_VERTICAL),
		PREWITT(PREWITT_HORIZONTAL, PREWITT_VERTICAL),
		ROBERTS_CROSS(ROBERTS_HORIZONTAL, ROBERTS_VERTICAL),
		SOBEL(KernelJAI.GRADIENT_MASK_SOBEL_HORIZONTAL, KernelJAI.GRADIENT_MASK_SOBEL_VERTICAL);

		private EnumMap<KernelDimension, KernelJAI> kernelMap;
		
		private GradientKernel(float[] horizontalData, float[] verticalData) {
			this(new KernelJAI(3, 3, horizontalData), new KernelJAI(3, 3, verticalData));
		}
		
		private GradientKernel(KernelJAI horizontalKernel, KernelJAI verticalKernel) {
			kernelMap = Maps.newEnumMap(KernelDimension.class);
			kernelMap.put(KernelDimension.HORIZONTAL, horizontalKernel);
			kernelMap.put(KernelDimension.VERTICAL, verticalKernel);
		}
		
		private Map<KernelDimension, KernelJAI> getKernelMap() {
			return Collections.unmodifiableMap(kernelMap);
		}
	};
	private GradientKernel gradientKernel;

	public static Image takeScreenShot(int cropFactor, Date date, int delay) throws AWTException {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		Rectangle rectangle = new Rectangle(cropFactor, cropFactor, 
			screenSize.width-cropFactor, screenSize.height-cropFactor);
		Robot robot = new Robot();
		robot.setAutoWaitForIdle(true);
		// Wait for 4 seconds.
		robot.delay(delay);
		return new Image(robot.createScreenCapture(rectangle), date);
	}
	
	/**
	 * Constructor for general use.
	 * @param imageFileName
	 */
	public Image(String imageFileName) {
		this(new File(imageFileName));
	}

	/**
	 * Constructor for general use.
	 * @param imageFileName
	 * @param date
	 */
	public Image(String imageFileName, Date date) {
		this(new File(imageFileName), date);
	}
	
	/**
	 * Constructor for general use.
	 * @param imageFile
	 */
	public Image(File imageFile) {
		this(JAI.create("fileload", imageFile.getAbsolutePath()));
	}
	
	/**
	 * Constructor for general use.
	 * @param imageFile
	 * @param date
	 */
	public Image(File imageFile, Date date) {
		this(JAI.create("fileload", imageFile.getAbsolutePath()));
		this.date = date;
	}
	
	/**
	 * Constructor for general use.
	 * @param image
	 */
	public Image(Image image, Date date) {
		this(image);
	}

	/**
	 * Constructor for general use.
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
	 * Protected contructor for known images (training data).
	 * @param imageFileName
	 * @param classification
	 */
	protected Image(String imageFileName, Classification classification) {
		this(new File(imageFileName), classification);
	}
	
	/**
	 * Protected contructor for known images (training data).
	 * @param imageFile
	 * @param classification
	 */
	protected Image(File imageFile, Classification classification) {
		this(imageFile);
		this.classification = classification;
	}
	
	/**
	 * Private constructor
	 * @param renderedImage
	 */
	private Image(RenderedImage renderedImage) {
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
	 * Private constructor
	 * @param renderedImage
	 * @param date
	 */
	private Image(RenderedImage renderedImage, Date date) {
		setDefaults();
		this.renderedImage = 
			centeredCrop(renderedImage, croppedWidth, croppedHeight);
		setDimensions(this.renderedImage);
		this.date = date;
	}
	
	/**
	 * Returns the classification of the image.
	 * @return
	 * @throws Exception
	 */
	public Classification getClassification() throws Exception {
		if(classification == null)
			classification = 
				TrainedModel.getTrainedModel().classifyImage(this);
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
	
	public GradientKernel getGradientKernel() {
		return gradientKernel;
	}

	public void setGradientKernel(GradientKernel gradientKernel) {
		this.gradientKernel = gradientKernel;
	}
	
	public int getNumBands() {
		return renderedImage.getData().getNumBands();
	}
	
	public double[] getPixels(double[] data) {
		return renderedImage.getData().
			getPixels(minX, minY, width, height, data);
	}
	
	public float[] getPixels(float[] data) {
		return renderedImage.getData().
			getPixels(minX, minY, width, height, data);
	}
	
	public int[] getPixels(int[] data) {
		return renderedImage.getData().
			getPixels(minX, minY, width, height, data);
	}
	
	public float getDownSample(int x, int y, int b) {
		Image image = getDownImage();
		x = image.getMinX() + x;
		y = image.getMinY() + y;
//		return image.getRenderedImage().getData().
//			getPixels(x, y, 0, 0, (float[])null)[0];
		return image.getRenderedImage().getData().getSampleFloat(x, y, b);
	}
	
	public float getSample(int x, int y, int b) {
		x = getMinX() + x;
		y = getMinY() + y;
		return getRenderedImage().getData().getSampleFloat(x, y, b);
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
	 * Returns the means for all the bands of the histogram 
	 * @return
	 */
	public double[] getMeans() {
		double[] means = getHistogram().getMean();
		return Arrays.copyOf(means, means.length);
	}
	
	/**
	 * Returns the standard deviations for all the bands of the histogram. 
	 * @return
	 */
	public double[] getStandardDeviations() {
		double[] standardDeviations = 
			getHistogram().getStandardDeviation();
		return Arrays.copyOf(standardDeviations, standardDeviations.length);
	}

	/**
	 * Returns an Image that represents the Discrete Cosine Transform.
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
	 * Returns an Image that represents the Inverse 
	 * Discrete Cosine Transform.
	 * @return
	 */
	public Image getInverseDiscreteCosineTransform() {
		ParameterBlock idctParams = (new ParameterBlock()).
			addSource(getDiscreteCosineTransform().renderedImage);
		return new Image(JAI.create("idct", idctParams, null));
	}
	
	public Image getGradientMagnitude() {
		if(gradientMagnitude == null)
			gradientMagnitude = 
				getGradientMagnitude(gradientKernel);
		return gradientMagnitude;
	}
	
	/**
	 * Returns an Image that represents a convolution along given data.
	 * @param width
	 * @param height
	 * @param data
	 * @return
	 */
	public Image convolve(int width, int height, float[] data) {
		BorderExtender convolutionBorderExtender = 
			BorderExtender.createInstance(BorderExtender.BORDER_ZERO);
		RenderingHints convolutionRH = 
			new RenderingHints(JAI.KEY_BORDER_EXTENDER, convolutionBorderExtender);
 
		return new Image((PlanarImage) JAI.create("convolve", renderedImage, 
			new KernelJAI(width, height, data), convolutionRH));
	}
	
	/**
	 * Returns an image that represents an addition with the given image.
	 * @param image
	 * @return
	 */
	public Image add(Image image) {
		ParameterBlock addParams = 
			new ParameterBlock().addSource(renderedImage).
				addSource(image.renderedImage);
		return new Image(JAI.create("add", addParams));
	}
	
	/**
	 * Returns an image that represents an subtraction of the given image.
	 * @param image
	 * @return
	 */
	public Image subtract(Image image) {
		ParameterBlock subtractParams = 
			new ParameterBlock().addSource(renderedImage).
				addSource(image.renderedImage);
		return new Image(JAI.create("subtract", subtractParams));
	}
	
	/**
	 * Persist the image to a file with the given filename.
	 * @param filename
	 */
	public void persist(String filename) {
		ParameterBlock fileStoreParams = (new ParameterBlock()).
			addSource(renderedImage).add(filename).add("PNG");
		JAI.create("filestore", fileStoreParams);
	}
	
	public BufferedImage convertToBufferedImage() {
		if(bufferedImage == null) {
			if(renderedImage instanceof BufferedImage)
				bufferedImage = (BufferedImage) renderedImage;
			else if(renderedImage instanceof PlanarImage)
				bufferedImage = 
					((PlanarImage) renderedImage).getAsBufferedImage();
			else {
				ColorModel colorModel = renderedImage.getColorModel();
				WritableRaster raster = 
					colorModel.createCompatibleWritableRaster(width, height);
				Hashtable<String, Object> properties = 
					new Hashtable<String, Object>();
				for (String key: renderedImage.getPropertyNames()) {
					properties.put(key, renderedImage.getProperty(key));
				}
				bufferedImage = 
					new BufferedImage(colorModel, raster, 
						colorModel.isAlphaPremultiplied(), properties);
			}
		}
		return bufferedImage;
	}
	
	/**
	 * Returns an Image that represents Gradient Magnitude.
	 * Protected for testing. Should
	 * @return
	 */
	private Image getGradientMagnitude(GradientKernel gradientKernel) {
		KernelJAI horizontalKernel = 
			gradientKernel.getKernelMap().get(KernelDimension.HORIZONTAL);
		KernelJAI verticalKernel = 
			gradientKernel.getKernelMap().get(KernelDimension.VERTICAL);
	     // Create the Gradient operation.
		Image gradientMagnitude = 
			new Image(JAI.create("gradientmagnitude", renderedImage,
				horizontalKernel, verticalKernel));
		return gradientMagnitude;
	}
	
	/**
	 * Returns the RenderedImage.
	 * @return
	 */
	private RenderedImage getRenderedImage() {
		return renderedImage;
	}
	
	/**
	 * Returns a RenderedImage that is the greyscale representation 
	 * of the image.
	 * @return
	 */
	private RenderedImage getGreyscaleImage() {
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
	 * Returns the image scaled down based on the downSampleSize
	 * @return
	 */
	private Image getDownImage() {
		if(downImage == null) {
			RenderedOp downSampler = 
				createScaleOp(renderedImage, downSampleSize);
			RenderedOp upSampler = 
				createScaleOp(renderedImage, 1/downSampleSize);
			RenderedOp differencer = 
				(RenderedOp) subtract(this).renderedImage;
			RenderedOp combiner = (RenderedOp) add(this).renderedImage;
			ImagePyramid pyramid = 
				new ImagePyramid(renderedImage, downSampler, upSampler, differencer, combiner);
			downImage = new Image(pyramid.getDownImage());
		}
		return downImage;
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
		gradientKernel = GradientKernel.SOBEL;
	}
}