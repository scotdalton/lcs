/**
 * 
 */
package edu.nyu.cs.sysproj.arability.features;

import edu.nyu.cs.sysproj.arability.Image;

/**
 * @author Scot Dalton
 *
 */
public class DCTDownSample implements Feature {
	private float value;
	
	public DCTDownSample(Image image, int x, int y) {
		Image dct = image.getDiscreteCosineTransform();
		value = dct.getDownSample(x, y, 0);
	}
	
	public float getValue() {
		return value;
	}
}
