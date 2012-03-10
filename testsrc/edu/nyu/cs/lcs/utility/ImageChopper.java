/**
 * 
 */
package edu.nyu.cs.lcs.utility;

import java.io.File;
import java.util.List;

import edu.nyu.cs.lcs.Image;

/**
 * @author Scot Dalton
 * Utility class for creating training data.
 */
public class ImageChopper {
	
	public static void main(String[] args) throws Exception {
		String imageDirectory = "tmp";
		for(String arg: args)
			if (arg.startsWith("imageDirectory="))
				imageDirectory=arg.split("=")[1];
		for(File file: FileUtil.getFiles(new File(imageDirectory))) {
			Image image = new Image(file, null);
			List<Image> choppedImages = image.getChoppedImages();
			File choppedDir = new File(imageDirectory+"/chopped");
			if(!choppedDir.exists()) {
				choppedDir.mkdir();
			}
			for (Image choppedImage: choppedImages) {
				int x = choppedImage.getMinX();
				int y = choppedImage.getMinY();
				String outputFile =	choppedDir + "/" + file.getName() + 
					"_"+x+"_"+y+".png";
				choppedImage.persist(outputFile);
			}
		}
	}
}