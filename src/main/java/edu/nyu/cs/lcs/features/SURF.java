/**
 * 
 */
package edu.nyu.cs.lcs.features;

import edu.nyu.cs.lcs.Image;

/**
 * @author Scot Dalton
 *
 */
public class SURF extends Feature {

	@Override
	public void setValue(Image image) {
		Integer a = (Integer) options.get("a");
		Integer i = (Integer) options.get("i");
		value = image.getSURF().get(a)[i];
	}
	
	@Override
	public String toString() {
		return "SURF" + options.values().toString();
	}
}