/**
 * 
 */
package edu.nyu.cs.sysproj.arability.features;

import edu.nyu.cs.sysproj.arability.Image;

/**
 * @author Scot Dalton
 *
 */
public class DCTDownSample extends Feature {
	
	@Override
	public void setValue(Image image) {
		Integer x = (Integer) options.get("x");
		Integer y = (Integer) options.get("y");
		value = 
			(float) image.getDiscreteCosineTransform().getDownSample(x, y, 0);
	}
	
	@Override
	public String toString() {
		return "DCTDownSample" + options.values().toString();
	}
}