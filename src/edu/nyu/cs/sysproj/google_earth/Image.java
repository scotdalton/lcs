/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import com.googlecode.javacv.cpp.opencv_core.IplImage;

import edu.nyu.cs.sysproj.google_earth.util.ArabilityClassificationMatcher;

/**
 * @author Scot Dalton
 *
 */
public class Image {
	private String filename;
	private IplImage openCvImage;
	private BufferedImage java2dImage;
	private RenderedOp javaAiImage;

	public Image(String imageFileName) {
		this(new File(imageFileName));
	}
	
	public Image(File imageFile) {
		filename = imageFile.getAbsolutePath();
		openCvImage = cvLoadImage(filename);
		try {
			java2dImage = ImageIO.read(imageFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		javaAiImage = 
			JAI.create("fileload", filename);
	}
	
	public String getFilename() {
		return filename;
	}
	
	public ArabilityClassification getDevelopmentState() {
		return ArabilityClassificationMatcher.getDevelopmentState(this);
	}
	
	public ImageFeatures getImageFeatures() {
		return new ImageFeatures(this);
	}

	public RenderedOp getJavaAiImage() {
		return javaAiImage;
	}

	public BufferedImage getJava2dImage() {
		return java2dImage;
	}

	public IplImage getOpenCvImage() {
		return openCvImage;
	}
}