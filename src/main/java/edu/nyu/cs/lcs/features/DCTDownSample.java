/**
 * 
 */
package edu.nyu.cs.lcs.features;

import edu.nyu.cs.lcs.Image;

/**
 * @author Scot Dalton
 *
 */
public class DCTDownSample extends Feature {
	
	@Override
	public void setValue(Image image) {
		Integer x = (Integer) options.get("x");
		Integer y = (Integer) options.get("y");
		value = image.getDiscreteCosineTransform().getDownSample(x, y, 0);
	}
	
	@Override
	public String toString() {
		return "DCTDownSample" + options.values().toString();
	}
}