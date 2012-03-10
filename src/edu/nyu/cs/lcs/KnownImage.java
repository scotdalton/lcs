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

	public KnownImage(File imageFile, Classification classfication) {
		super(imageFile, classfication, null);
	}
}