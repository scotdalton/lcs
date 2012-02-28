/**
 * 
 */
package edu.nyu.cs.lcs.utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.google.common.collect.Lists;

import edu.nyu.cs.lcs.Image;

/**
 * @author Scot Dalton
 *
 */
public class FileUtil {
	public static List<File> getFiles(String directoryName) {
		List<File> fileList = Lists.newArrayList();
		File directory = new File(directoryName);
		if (directory.isDirectory()) {
			String[] filenames = directory.list();
			for (String filename: filenames) {
				File file = 
					new File(directoryName + "/" + filename);
				if(file.isFile() && !file.isHidden()) {
					fileList.add(file);
				}
			}
		}
		return fileList;
	}
	
	public static void zipImages(String zipFileName, Map<String, Image> images) throws Exception {
        byte data[] = new byte[2048];
		FileOutputStream zipFileOutputStream = 
			new FileOutputStream(zipFileName);
		ZipOutputStream zipOutputStream = 
			new ZipOutputStream(new BufferedOutputStream(zipFileOutputStream));
		for(String imageName: images.keySet()) {
			String imageFileName = imageName + ".png";
			BufferedInputStream imageInputStream = 
				new BufferedInputStream(
					images.get(imageName).getAsInputStream());
			zipOutputStream.putNextEntry(new ZipEntry(imageFileName));
			int count;
			while((count = imageInputStream.read(data, 0, 2048)) != -1)
				zipOutputStream.write(data, 0, count);
			imageInputStream.close();
		}
		zipOutputStream.close();
	}
}
