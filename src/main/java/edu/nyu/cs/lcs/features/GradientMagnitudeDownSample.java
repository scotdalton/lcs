/**
 * 
 */
package edu.nyu.cs.lcs.features;

import edu.nyu.cs.lcs.Image;

/**
 * @author Scot Dalton
 *
 */
public class GradientMagnitudeDownSample extends Feature {


	@Override
	public void setValue(Image image) {
		Integer x = (Integer) options.get("x");
		Integer y = (Integer) options.get("y");
		Integer b = (Integer) options.get("b");
		value = image.getGradientMagnitude().getDownSample(x, y, b);
	}
	
	@Override
	public String toString() {
		return "GradientMagnitude" + options.values().toString();
	}
}
