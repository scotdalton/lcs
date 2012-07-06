/**
 * 
 */
package edu.nyu.cs.lcs;

import ij.ImagePlus;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.ParameterBlock;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.media.jai.Histogram;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

import edu.nyu.cs.lcs.classifications.Classification;

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
	private Map<Point, Classification> classificationMap;
	private Image classificationImage;
	private ImagePlus imagePlus;
	private RenderedImage renderedImage;
	private Histogram histogram;
	private int width;
	private int height;
	private int minX;
	private int minY;
	private Date date;
	private String name;

	/**
	 * Constructor for general use.
	 * @param filename
	 */
	public Image(String filename) {
		this(new File(filename));
	}
	
	/**
	 * Constructor for general use.
	 * @param filename
	 * @param date
	 */
	public Image(String filename, Date date) {
		this(new File(filename), date);
	}
	
	/**
	 * Constructor for general use.
	 * @param file
	 */
	public Image(File file) {
		this(JAI.create("fileload", file.getAbsolutePath()));
		this.name = file.getName();
	}
	
	/**
	 * Constructor for general use.
	 * @param file
	 * @param date
	 */
	public Image(File file, Date date) {
		this(JAI.create("fileload", file.getAbsolutePath()));
		this.name = file.getName();
		this.date = date;
	}
	
	/**
	 * Constructor for general use.
	 * @param image
	 */
	public Image(Image image, Date date) {
		this(image);
		this.date = date;
	}

	/**
	 * Constructor for general use.
	 * @param image
	 */
	public Image(Image image) {
		renderedImage = image.renderedImage;
		setDimensions(this.renderedImage);
		histogram = image.histogram;
		classificationImage = image.classificationImage;
		imagePlus = image.imagePlus;
		date = image.date;
	}

	/**
	 * Public constructor
	 * @param renderedImage
	 * @param date
	 */
	public Image(RenderedImage renderedImage, Date date) {
		this(renderedImage);
		this.date = date;
	}
	
	/**
	 * Public constructor
	 * @param renderedImage
	 */
	public Image(RenderedImage renderedImage) {
		this.renderedImage = PlanarImage.wrapRenderedImage(renderedImage);
		setDimensions(this.renderedImage);
	}
	
	public ImagePlus getImagePlus() {
		if (imagePlus == null) {
			imagePlus = 
				new ImagePlus(this.name, this.getAsBufferedImage());
		}
		return imagePlus;
	}
	
	/**
	 * @param classificationMap the classificationMap to set
	 */
	public void setClassificationMap(Map<Point, Classification> classificationMap) {
		this.classificationMap = classificationMap;
	}

	/**
	 * @return the classificationMap
	 * @throws Exception 
	 */
	public Map<Point, Classification> getClassificationMap() throws Exception {
		return classificationMap;
	}

	public Image getComparisonImage(Image fromImage) throws Exception {
		BufferedImage bufferedImage = getAsBufferedImage();
//		Graphics graphics = bufferedImage.getGraphics();
//		for(int index=0; index<getChoppedImages().size(); index++) {
//			Image toImage = getChoppedImages().get(index);
//			Classification toImageClassification = 
//				toImage.getClassification();
//			Classification fromImageClassification = 
//				fromImage.getChoppedImages().get(index).getClassification();
//			if(fromImageClassification.equals(Classification.CROPLAND) && 
//					!toImageClassification.equals(fromImageClassification)) {
//				graphics.setColor(new Color(255, 0, 0, 63));
//				int rectX = toImage.getMinX()-getMinX();
//				int rectY = toImage.getMinY()-getMinY();
//				graphics.fillRect(rectX, rectY, toImage.getWidth(), 
//					toImage.getHeight());
//				graphics.setColor(Color.black);
//				graphics.setFont(new Font("Serif", Font.BOLD, 10));
//				FontMetrics fontMetrics = graphics.getFontMetrics();
//				String fromToString = fromImageClassification.toString() + 
//					" to " + toImageClassification.toString();
//				int stringWidth = fontMetrics.stringWidth(fromToString);
//				int stringHeight = fontMetrics.getHeight();
//				int stringX = rectX + toImage.getWidth()/2 - stringWidth/2;
//				int stringY = rectY + toImage.getHeight()/2 - stringHeight/2;
//				graphics.drawString(fromToString, stringX, stringY);
//			}
//		}
		return new Image(bufferedImage);
	}
	
	/**
	 * Returns the classification image of the image.
	 * @return
	 * @throws Exception
	 */
	public Image getClassificationImage() throws Exception {
		if(classificationImage == null) {
			classificationMap = getClassificationMap();
			if(classificationMap == null)
				throw new Exception("Classification map was not set.");
			BufferedImage bufferedImage = getAsBufferedImage();
			Graphics graphics = bufferedImage.getGraphics();
			for(Entry<Point, Classification> entry: classificationMap.entrySet()) {
				Point point = entry.getKey();
				int x = (int) point.getX();
				int y = (int) point.getY();
				Classification classification = entry.getValue();
				int red = classification.getRed();
				int green = classification.getGreen();
				int blue = classification.getBlue();
				int alpha = classification.getAlpha();
				graphics.setColor(new Color(red, green, blue, alpha));
				graphics.fillRect(x, y, 1, 1);
			}
			graphics.dispose();
			classificationImage = new Image(bufferedImage);
		}
		return classificationImage;
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
	 * @return the name
	 */
	public String getName() {
		return name;
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
	
	public float getSample(int x, int y, int b) {
		x = getMinX() + x;
		y = getMinY() + y;
		return renderedImage.getData().getSampleFloat(x, y, b);
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

	public InputStream getAsInputStream() throws Exception {
		InputStream inputStream = new ByteArrayInputStream(getBytes());
		return inputStream;
	}
	
	public byte[] getBytes() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream = 
			new ByteArrayOutputStream();
		ImageIO.write(getAsBufferedImage(), "png", byteArrayOutputStream);
		byteArrayOutputStream.flush();
		byteArrayOutputStream.close();
		return byteArrayOutputStream.toByteArray();
	}
	
	public BufferedImage getAsBufferedImage() {
		BufferedImage bufferedImage;
		if(renderedImage instanceof BufferedImage)
			bufferedImage = (BufferedImage) renderedImage;
		else if(renderedImage instanceof PlanarImage)
			bufferedImage = 
				getAsPlanarImage().getAsBufferedImage();
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
		return bufferedImage;
	}
	
	public PlanarImage getAsPlanarImage() {
		return PlanarImage.wrapRenderedImage(renderedImage);
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
	 * Persist the image to a file with the given filename.
	 * @param filename
	 */
	public void persist(String filename) {
		File file = new File(filename);
		File parent = file.getParentFile();
		if(!parent.exists()) parent.mkdirs();
		ParameterBlock fileStoreParams = (new ParameterBlock()).
			addSource(renderedImage).add(filename).add("PNG");
		JAI.create("filestore", fileStoreParams);
	}
	
	public Image overlay(Image image) {
		ParameterBlock overlayParams = 
			new ParameterBlock().addSource(renderedImage).
				addSource(image.renderedImage);
		return new Image(JAI.create("overlay", overlayParams));
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

	private void setDimensions(RenderedImage renderedImage) {
		width = renderedImage.getWidth();
		height = renderedImage.getHeight();
		minX = renderedImage.getMinX();
		minY = renderedImage.getMinY();
	}
}