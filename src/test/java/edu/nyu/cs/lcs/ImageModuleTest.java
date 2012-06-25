/**
 * 
 */
package edu.nyu.cs.lcs;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;

import edu.nyu.cs.lcs.ImageModule.ChoppedHeight;
import edu.nyu.cs.lcs.ImageModule.ChoppedWidth;
import edu.nyu.cs.lcs.ImageModule.DownSampleSquareRoot;


/**
 * @author Scot Dalton
 *
 */
public class ImageModuleTest {

	@Test
	public void testImageModule() 
			throws FileNotFoundException, IOException {
		Injector injector = 
			Guice.createInjector(new ImageModule());
		assertEquals(Integer.valueOf(8), 
				injector.getInstance(Key.get(Integer.class, 
					DownSampleSquareRoot.class)));
		assertEquals(Float.valueOf(100), 
				injector.getInstance(Key.get(Float.class, 
					ChoppedWidth.class)));
		assertEquals(Float.valueOf(100), 
				injector.getInstance(Key.get(Float.class, 
					ChoppedHeight.class)));
	}
}
