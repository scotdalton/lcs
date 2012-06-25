/**
 * 
 */
package edu.nyu.cs.lcs.features;

import edu.nyu.cs.lcs.Image;

/**
 * @author Scot Dalton
 *
 */
public class DCTDispersion extends Feature {
	
	@Override
	public void setValue(Image image) {
		value = 
			(float) image.getDiscreteCosineTransform().getStandardDeviations()[0];
	}
	
	@Override
	public String toString() {
		return "DCTDispersion" + options.values().toString();
	}
}