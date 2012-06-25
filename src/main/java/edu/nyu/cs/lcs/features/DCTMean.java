/**
 * 
 */
package edu.nyu.cs.lcs.features;

//import java.util.Arrays;
//
//import math.transform.jwave.Transform;
//import math.transform.jwave.handlers.DiscreteWaveletTransform;
//import math.transform.jwave.handlers.wavelets.Daub02;
import edu.nyu.cs.lcs.Image;


/**
 * @author Scot Dalton
 *
 */
public class DCTMean extends Feature{
	
	@Override
	public void setValue(Image image) {
		value = (float) image.getDiscreteCosineTransform().getMeans()[0];
	}
	
	@Override
	public String toString() {
		return "DCTMean" + options.values().toString();
	}
}