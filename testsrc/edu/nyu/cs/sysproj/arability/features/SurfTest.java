/**
 * 
 */
package edu.nyu.cs.sysproj.arability.features;

import org.junit.Test;

import edu.nyu.cs.sysproj.arability.Image;
import edu.nyu.cs.sysproj.arability.TestUtility;


/**
 * @author Scot Dalton
 *
 */
public class SurfTest {
	@Test
	public void testSurf() {
		for(Image image: TestUtility.getTestImage1().getChoppedImages()) {
			for(float[] fs: image.getSURF())
				for (float f: fs)
				System.out.println(f);
			System.out.println();
		}
	}
}
