/**
 * 
 */
package edu.nyu.cs.sysproj.arability.features;

//import java.util.Arrays;
//
//import math.transform.jwave.Transform;
//import math.transform.jwave.handlers.DiscreteWaveletTransform;
//import math.transform.jwave.handlers.wavelets.Daub02;
import edu.nyu.cs.sysproj.arability.Image;


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