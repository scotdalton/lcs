/**
 * 
 */
package edu.nyu.cs.lcs.gui;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import edu.nyu.cs.lcs.Image;

/**
 * @author Scot Dalton
 *
 */
public class ImageLabel extends JLabel {
	private static final long serialVersionUID = -6916284700645272240L;

	public ImageLabel(Image image) {
		super(new ImageIcon(image.getAsBufferedImage()));
	}
	
	public ImageLabel(File file) {
		this(new Image(file));
	}
	
	public ImageLabel(String fileName) {
		this(new Image(fileName));
	}
}