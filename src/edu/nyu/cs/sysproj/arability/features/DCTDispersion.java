/**
 * 
 */
package edu.nyu.cs.sysproj.arability.features;

import edu.nyu.cs.sysproj.arability.Image;

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