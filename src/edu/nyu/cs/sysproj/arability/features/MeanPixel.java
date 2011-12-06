/**
 * 
 */
package edu.nyu.cs.sysproj.arability.features;

import edu.nyu.cs.sysproj.arability.Image;



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