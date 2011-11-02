/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import static com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;

import java.io.File;

import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_imgproc.CvHistogram;

/**
 * @author Scot Dalton
 *
 */
public class ImageProcessor {
	private IplImage candidateImage;
	private IplImage grayScaleCandidateImage;
	private CvHistogram cvHistogram;
	private CvSize candidateSize;

	public ImageProcessor(File file) {
		this(file.getAbsolutePath());
	}
	
	public ImageProcessor(String filename) {
		candidateImage = cvLoadImage(filename);
		candidateSize = candidateImage.cvSize();
		grayScaleCandidateImage = 
			cvLoadImage(filename, CV_LOAD_IMAGE_GRAYSCALE);
		IplImage[] channels = new IplImage[3];
		for(int i=0; i<3; i++) 
            channels[i] = IplImage.create(candidateSize, IPL_DEPTH_8U, 1); 
		cvSplit(candidateImage, channels[0] ,channels[1] ,channels[2] , null); 
        int[] numBins = {256}; 
        float range[] = {0, 255}; 
        float[][] ranges = {range}; 
        cvHistogram = CvHistogram.create(1, numBins, CV_HIST_ARRAY, ranges, 1); 
		cvCalcHist(channels, cvHistogram, 0, null);
		cvNormalizeHist(cvHistogram, 1.0);
	}
	
	public CvHistogram getHistorgram() {
		return cvHistogram;
	}
	
	public IplImage getHistorgramImage() {
		IplImage returnImage = drawHistogram();
		return returnImage;
	}
	
	public IplImage getEdgeImage() {
		return getCannyEdgeImage();
	}
	
	protected IplImage getCannyEdgeImage() {
		IplImage returnImage = 
			IplImage.create(candidateSize, 
				IPL_DEPTH_8U, grayScaleCandidateImage.nChannels());
		cvCanny(grayScaleCandidateImage, returnImage, 1, 2, 7);
		return returnImage;
	}
	
	protected IplImage getHarrisEdgeImage() {
		IplImage returnImage = 
			IplImage.create(candidateSize, 
				IPL_DEPTH_32F, grayScaleCandidateImage.nChannels());
		cvCornerHarris(grayScaleCandidateImage, returnImage, 1000, 7, 0.04);
		return returnImage;
	}
	
	protected IplImage getEigenEdgeImage() {
		IplImage returnImage = 
			IplImage.create(candidateSize, 
				IPL_DEPTH_32F, grayScaleCandidateImage.nChannels());
		cvCornerMinEigenVal(grayScaleCandidateImage, returnImage, 1000, 7);
		return returnImage;
	}
	
	public void toFile(String filename, IplImage image) {
		cvSaveImage(filename, image);
	}
	
	private IplImage drawHistogram() {
		return drawHistogram(1, 1);
	}
	
	private IplImage drawHistogram(int scaleX, int scaleY) {
		IplImage returnImage =
			IplImage.create(cvSize(256*scaleX, 64*scaleY), 8 ,1);
		 return returnImage;
	}
}
