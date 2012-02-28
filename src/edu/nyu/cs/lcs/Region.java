/**
 * 
 */
package edu.nyu.cs.lcs;

import java.io.File;
import java.util.List;

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
				image = Image.getImageForRegion(images, columns, rows);
			else if(imageDirectory != null)
				image = 
					Image.getImageForRegion(imageDirectory, columns, rows);
			else
				throw new NullPointerException("Neither image nor image directory was defined.");
		return image;
	}
	
	public Image getClassificationHeatMap() throws Exception {
		return getImage().getClassificationHeatMap();
	}
	
	public Image getComparisonImage(Image fromImage) throws Exception {
		return getImage().getComparisonImage(fromImage);
	}
}