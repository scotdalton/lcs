/**
 * 
 */
package edu.nyu.cs.lcs.features;

import edu.nyu.cs.lcs.Image;



/**
 * @author Scot Dalton
 *
 */
public class MeanPixel extends Feature {

	@Override
	public void setValue(Image image) {
		Integer band = (Integer) options.get("band");
		value = (float) image.getMeans()[band];
	}
	
	@Override
	public String toString() {
		return "MeanPixel" + options.values().toString();
	}
}