/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import static com.googlecode.javacv.cpp.opencv_core.CV_AA;
import static com.googlecode.javacv.cpp.opencv_core.CV_RGB;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSeqElem;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_core.cvLine;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_GRAY2BGR;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_HOUGH_PROBABILISTIC;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCanny;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvHoughLines2;

//import static com.googlecode.javacv.cpp.opencv_imgproc.CV_HOUGH_STANDARD;

import com.googlecode.javacpp.Pointer;
import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_core.CvPoint;
//import com.googlecode.javacv.cpp.opencv_core.CvPoint2D32f;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

/**
 * @author Scot Dalton
 *
 */
public class ImageEdges {
	private EarthImage earthImage;
	private IplImage cannyEdgeImage;
	private CvSeq houghLines;
	private IplImage houghEdgeImage;
	
	public ImageEdges(EarthImage earthImage) {
		this.earthImage = earthImage;
		setCannyEdgeImage();
		setHoughLines();
	}
	
	protected void writeEdgeImages(String cannyFileName, String houghFileName) {
		cvSaveImage(cannyFileName, cannyEdgeImage);
		cvSaveImage(houghFileName, houghEdgeImage);
	}
	
	private void setCannyEdgeImage() {
		IplImage grayScaleCandidateImage = 
			cvLoadImage(earthImage.getFilename(), CV_LOAD_IMAGE_GRAYSCALE);
		IplImage returnImage = 
			IplImage.create(earthImage.getOpenCvImage().cvSize(), 
				IPL_DEPTH_8U, grayScaleCandidateImage.nChannels());
		cvCanny(grayScaleCandidateImage, returnImage, 50, 200, 3);
		cannyEdgeImage = returnImage;
	}
	
	private void setHoughLines() {
		IplImage srcImage = earthImage.getOpenCvImage();
        houghLines = new CvSeq();
        houghEdgeImage = 
        		cvCreateImage(cvGetSize(srcImage), srcImage.depth(), 3);
        cvCvtColor(cannyEdgeImage, houghEdgeImage, CV_GRAY2BGR);
        probabilisticHough();
	}
	
	private void probabilisticHough() {
        CvMemStorage storage = CvMemStorage.create(0);
		houghLines = 
			cvHoughLines2(cannyEdgeImage, storage, CV_HOUGH_PROBABILISTIC, 1, Math.PI / 180, 40, 50, 10);
		for (int i = 0; i < houghLines.total(); i++) {
			Pointer line = cvGetSeqElem(houghLines, i);
			CvPoint pt1  = new CvPoint(line).position(0);
			CvPoint pt2  = new CvPoint(line).position(1);
			// Draw the segment on the image
			cvLine(houghEdgeImage, pt1, pt2, CV_RGB(255, 0, 0), 3, CV_AA, 0);
		}
	}
	
//	private void standardHough() {
//        CvMemStorage storage = CvMemStorage.create(0);
//		houghLines = 
//			cvHoughLines2(cannyEdgeImage, storage, 
//				CV_HOUGH_STANDARD, 1, Math.PI/180, 90, 0, 0 );
//        for (int i = 0; i < houghLines.total(); i++) {
//            CvPoint2D32f point = 
//            		new CvPoint2D32f(cvGetSeqElem(houghLines, i));
//            float rho = point.x();
//            float theta = point.y();
//            double a = Math.cos((double) theta);
//            double b = Math.sin((double) theta);
//            double x0 = a * rho;
//            double y0 = b * rho;
//            CvPoint pt1 = 
//            		new CvPoint((int) Math.round(x0 + 10 * (-b)), 
//            			(int) Math.round(y0 + 10 * (a))), 
//            				pt2 = new CvPoint((int) Math.round(x0 - 1000 * (-b)), 
//            					(int) Math.round(y0 - 1000 * (a)));
//            cvLine(houghEdgeImage, pt1, pt2, CV_RGB(255, 0, 0), 3, CV_AA, 0);
//        }
//	}
}