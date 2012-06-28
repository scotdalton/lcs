/**
 * 
 */
package edu.nyu.cs.lcs;

import java.io.File;
import java.util.List;

import edu.nyu.cs.lcs.utility.ImageUtil;

/**
 * @author Scot Dalton
 *
 */
public class Region {
	private File imageDirectory;
	private List<Image> images;
	private Image image;
	private int columns;
	private int rows;
	
	public Region(List<Image> images, int columns, int rows) {
		this.images = images;
		this.columns = columns;
		this.rows = rows;
	}
	
	public Region(File imageDirectory, int columns, int rows) {
		this.imageDirectory = imageDirectory;
		this.columns = columns;
		this.rows = rows;
	}
	
	public Image getImage() {
		if(image == null)
			if(images != null)
				image = ImageUtil.getImageForRegion(images, columns, rows);
			else if(imageDirectory != null)
				image = 
					ImageUtil.getImageForRegion(imageDirectory, columns, rows);
			else
				throw new NullPointerException("Neither image nor image directory was defined.");
		return image;
	}
	
	public Image getClassificationImage() throws Exception {
		return getImage().getClassificationImage();
	}
	
	public Image getComparisonImage(Image fromImage) throws Exception {
		return getImage().getComparisonImage(fromImage);
	}
}