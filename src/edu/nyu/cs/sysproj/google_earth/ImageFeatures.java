/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import edu.nyu.cs.sysproj.google_earth.features.Color;

//import javax.media.jai.RenderedOp;

/**
 * @author Scot Dalton
 *
 */
public class ImageFeatures {

	private Color color;
    private Image image;
//    private RenderedOp javaAiImage;
	
	public ImageFeatures(Image image) {
		this.image = image;
		setMeanPixels();
	}

	public Color getMeanPixels() {
		return color;
	}

	private void setMeanPixels() {
	}
}
