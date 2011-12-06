/**
 * 
 */
package edu.nyu.cs.sysproj.arability.features;

import java.util.Map;

import edu.nyu.cs.sysproj.arability.Image;


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
