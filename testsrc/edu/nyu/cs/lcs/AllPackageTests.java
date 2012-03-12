package edu.nyu.cs.lcs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { 
	GeocoderTest.class, GoogleEarthTest.class, ImageUtilTest.class,
	ImageModuleTest.class, ImageTest.class, TrainedModelTest.class,
	UnknownImageTest.class, ValidityCharacteristicTest.class})

/**
 * @author Scot Dalton
 *
 */
public class AllPackageTests {
}
