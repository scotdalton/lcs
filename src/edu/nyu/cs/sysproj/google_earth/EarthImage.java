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

import edu.nyu.cs.sysproj.google_earth.util.DevelopmentStateMatcher;

/**
 * @author Scot Dalton
 *
 */
public class EarthImage {
	private String filename;
	private IplImage openCvImage;
	private BufferedImage java2dImage;
	private RenderedOp javaAiImage;

	public EarthImage(String imageFileName) {
		this(new File(imageFileName));
	}
	
	public EarthImage(File imageFile) {
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
	
	public DevelopmentState getDevelopmentState() {
		return DevelopmentStateMatcher.getDevelopmentState(this);
	}
	
	public ImageFeatures getImageFeatures() {
		return new ImageFeatures(this);
	}

	protected RenderedOp getJavaAiImage() {
		return javaAiImage;
	}

	protected BufferedImage getJava2dImage() {
		return java2dImage;
	}

	protected IplImage getOpenCvImage() {
		return openCvImage;
	}
}