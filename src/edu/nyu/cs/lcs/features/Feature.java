/**
 * 
 */
package edu.nyu.cs.lcs.features;

import java.util.Map;

import edu.nyu.cs.lcs.Image;


//import javax.media.jai.RenderedOp;

/**
 * @author Scot Dalton
 *
 */
public abstract class Feature {
	Map<String, Object> options;
	Image image;
	float value;

	public abstract void setValue(Image image);

	public void setOptions(Map<String, Object> options) {
		this.options = options;
	}

	public float getValue() {
		return value;
	}
}
