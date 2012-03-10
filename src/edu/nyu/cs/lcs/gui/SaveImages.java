/**
 * 
 */
package edu.nyu.cs.lcs.gui;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.Action;
import javax.swing.JButton;

import edu.nyu.cs.lcs.TrainedModel;
import edu.nyu.cs.lcs.utility.FileUtil;

/**
 * @author Scot Dalton
 *
 */
public class SaveImages extends LcsAction {

	private static final long serialVersionUID = 1431883738094298260L;

	/**
	 * @param string
	 */
	public SaveImages(TrainedModel trainedModel) {
		super("Archive", trainedModel);
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton saveButton = (JButton) event.getSource();
		setComponentsFromButton(saveButton);
		save("Save Images", address);
	}
	
	protected void save(String title, String filename) {
		if(resultsPanel.getComponentCount() > 0 && 
				resultsPanel.getComponent(0) instanceof ImageTabbedPane) {
			ImageTabbedPane imageTabbedPane = 
				(ImageTabbedPane) resultsPanel.getComponent(0);
			FileDialog fileDialog = new FileDialog(lcsFrame, title, FileDialog.SAVE);
			fileDialog.setFile(filename + ".zip");
			fileDialog.setFilenameFilter(new ZipFileNameFilter());
			fileDialog.setVisible(true);
			String fileName = fileDialog.getDirectory() + fileDialog.getFile();
			try {
				FileUtil.zipImages(fileName, imageTabbedPane.getImages());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private class ZipFileNameFilter implements FilenameFilter {
		@Override
		public boolean accept(File arg0, String arg1) {
			return arg1.endsWith(".zip"); 
		}
	}
}
