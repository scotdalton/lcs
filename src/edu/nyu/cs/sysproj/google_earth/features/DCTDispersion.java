/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth.features;

import edu.nyu.cs.sysproj.google_earth.Image;

/**
 * @author Scot Dalton
 *
 */
public class DCTDispersion implements Feature {
	private float value;
	
	public DCTDispersion(Image image) {
		value = (float) image.getDiscreteCosineTransform().getStandardDeviations()[0];
	}

	@Override
	public float getValue() {
		return value;
	}

}
