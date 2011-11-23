/**
 * 
 */
package edu.nyu.cs.sysproj.arability.utility;

import static edu.nyu.cs.sysproj.arability.utility.Configuration.*;

import java.io.File;
import java.util.List;

import edu.nyu.cs.sysproj.arability.Image;

/**
 * @author Scot Dalton
 *
 */
public class ImageChopper {
	
	public static void main(String[] args) throws Exception {
		String imageDirectory = IMAGE_PATH;
		for(String arg: args)
			if (arg.startsWith("imageDirectory="))
				imageDirectory=arg.split("=")[1];
		for(File file: getFiles(imageDirectory)) {
			Image image = new Image(file);
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
				persistImage(outputFile, choppedImage.getRenderedImage());
			}
		}
	}
}