/**
 * 
 */
package edu.nyu.cs.sysproj.arability.utility;

import java.io.File;
import java.util.List;

import com.google.common.collect.Lists;

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
}
