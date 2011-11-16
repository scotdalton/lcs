/**
 * 
 */
package edu.nyu.cs.sysproj.arability.features;

import edu.nyu.cs.sysproj.arability.Image;

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
