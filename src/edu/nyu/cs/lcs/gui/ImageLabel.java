/**
 * 
 */
package edu.nyu.cs.lcs.gui;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import edu.nyu.cs.lcs.Image;
import edu.nyu.cs.lcs.TrainedModel;

/**
 * @author Scot Dalton
 *
 */
public class ImageLabel extends JLabel {
	private static final long serialVersionUID = -6916284700645272240L;

	public ImageLabel(Image image) {
		super(new ImageIcon(image.getAsBufferedImage()));
	}
	
	public ImageLabel(File file, TrainedModel trainedModel) {
		this(new Image(file, trainedModel));
	}
	
	public ImageLabel(String fileName, TrainedModel trainedModel) {
		this(new Image(fileName, trainedModel));
	}
}