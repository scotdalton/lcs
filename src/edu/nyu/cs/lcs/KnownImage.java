/**
 * 
 */
package edu.nyu.cs.lcs;

import java.io.File;

/**
 * KnownImage extends Image for training and testing images.
 *  
 * @author Scot Dalton
 * 
 */
public class KnownImage extends Image {

	public KnownImage(File imageFile, LandClassification classfication) {
		super(imageFile, classfication);
	}
}