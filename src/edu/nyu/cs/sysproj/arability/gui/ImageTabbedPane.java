/**
 * 
 */
package edu.nyu.cs.sysproj.arability.gui;

import java.util.Map;

import javax.swing.JTabbedPane;

import com.google.common.collect.Maps;

import edu.nyu.cs.sysproj.arability.Image;

/**
 * @author Scot Dalton
 *
 */
public class ImageTabbedPane extends JTabbedPane {
	private static final long serialVersionUID = -6928245339429177984L;

	public ImageTabbedPane(Map<String, Image> images) {
		super();
		addImageTabs(images);
	}
	
	public ImageTabbedPane(int tabPlacement, Map<String, Image> images) {
		super(tabPlacement);
		addImageTabs(images);
	}
	
	public ImageTabbedPane(int tabPlacement, int tabLayoutPolicy, Map<String, Image> images) {
		super(tabPlacement, tabLayoutPolicy);
		addImageTabs(images);
	}
	
	public void addImageTabs(Map<String, Image> images) {
		// Create the image labels
		Map<String, ImageLabel> imageLabels = Maps.newLinkedHashMap();
		for(String imageKey: images.keySet())
			imageLabels.put(imageKey, new ImageLabel(images.get(imageKey)));
		
		for(String imageLabelKey: imageLabels.keySet())
			addTab(imageLabelKey, imageLabels.get(imageLabelKey));
	}
}
