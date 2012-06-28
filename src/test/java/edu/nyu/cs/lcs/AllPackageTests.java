package edu.nyu.cs.lcs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import edu.nyu.cs.lcs.model.TrainedModelTest;
import edu.nyu.cs.lcs.utility.google_earth.GoogleEarthTest;

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
