/**
 * 
 */
package edu.nyu.cs.sysproj.arability.features;

import edu.nyu.cs.sysproj.arability.Image;

/**
 * @author Scot Dalton
 *
 */
public class SURF extends Feature {

	@Override
	public void setValue(Image image) {
		Integer a = (Integer) options.get("a");
		Integer i = (Integer) options.get("i");
		value = (float) image.getSURF().get(a)[i];
	}
	
	@Override
	public String toString() {
		return "SURF" + options.values().toString();
	}
}