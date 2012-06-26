/**
 * 
 */
package edu.nyu.cs.lcs;

import java.io.File;
import java.util.List;

import edu.nyu.cs.lcs.utility.FileUtil;

/**
 * @author Scot Dalton
 * 
 */
public class TestUtility {
	private final static String tstPath = "src/test/resources/META-INF";
	public final static String IMAGE1 = tstPath
			+ "/Johannesburg_South_Africa_1_20031004.png";
	public final static String IMAGE2 = tstPath
			+ "/Johannesburg_South_Africa_1_20051004.png";
	public final static String IMAGE3 = tstPath
			+ "/Johannesburg_South_Africa_1_20071004.png";
	public final static String IMAGE4 = tstPath
			+ "/Johannesburg_South_Africa_1_20091004.png";
	public final static String IMAGE5 = tstPath
			+ "/Johannesburg_South_Africa_1_20111004.png";
	public final static String CROPLAND = tstPath + "/cropland.png";
	public final static String DESERT = tstPath + "/desert.png";
	public final static String CHOPPED = tstPath + "/chopped.png";
	public final static String CLOUDY = tstPath
			+ "/Nanvura_Mozambique_1_20091004.png";
	public final static String BLURRY = tstPath
			+ "/Dar_es_Salaam_Tanzania_1_20031004.png";
	public final static String BRIGHT = tstPath
			+ "/Johannesburg_South_Africa_2_20111004.png";

	public static List<File> getImageFiles() {
		return FileUtil.getFiles(new File("tst"));
	}
}