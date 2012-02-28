/**
 * 
 */
package edu.nyu.cs.lcs.gui;

import java.util.Collections;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.google.common.collect.Maps;

import edu.nyu.cs.lcs.Image;

/**
 * @author Scot Dalton
 *
 */
public class ImageTabbedPane extends JTabbedPane {
	private static final long serialVersionUID = -6928245339429177984L;
	private Map<String, Image> images;

	public ImageTabbedPane(Map<String, Image> images) {
		super();
		this.images = images;
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
		Map<String, JScrollPane> imageLabels = Maps.newLinkedHashMap();
		for(String imageKey: images.keySet())
			imageLabels.put(imageKey, new JScrollPane(new ImageLabel(images.get(imageKey))));
		
		for(String imageLabelKey: imageLabels.keySet())
			addTab(imageLabelKey, imageLabels.get(imageLabelKey));
	}

	/**
	 * @return the images
	 */
	public Map<String, Image> getImages() {
		return Collections.unmodifiableMap(images);
	}
}