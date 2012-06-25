/**
 * 
 */
package edu.nyu.cs.lcs;

import java.io.File;

import edu.nyu.cs.lcs.classifications.Classification;

/**
 * KnownImage extends Image for training and testing images.
 *  
 * @author Scot Dalton
 * 
 */
public class KnownImage extends Image {

	/**
	 * Public constructor for known images (training data).
	 * @param imageFile
	 * @param classfication
	 */
	public KnownImage(File imageFile, Classification classification) {
		super(imageFile);
		this.classification = classification;
	}

	/**
	 * Public contructor for known images (training data).
	 * @param imageFileName
	 * @param classification
	 */
	public KnownImage(String imageFileName, Classification classification) {
		this(new File(imageFileName), classification);
	}
	
}