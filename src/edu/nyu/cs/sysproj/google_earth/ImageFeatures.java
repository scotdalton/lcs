/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

//import javax.media.jai.RenderedOp;

/**
 * @author Scot Dalton
 *
 */
public class ImageFeatures {

	private ImageMeanPixels meanPixels;
    private ImageHistogram histogram;
    private EarthImage earthImage;
//    private RenderedOp javaAiImage;
	
	public ImageFeatures(EarthImage earthImage) {
		this.earthImage = earthImage;
		setMeanPixels();
		histogram = new ImageHistogram(this.earthImage);
	}

	public ImageMeanPixels getMeanPixels() {
		return meanPixels;
	}

	public ImageHistogram getHistorgram() {
		return histogram;
	}

	private void setMeanPixels() {
	}
}
