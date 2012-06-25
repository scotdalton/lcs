/**
 * 
 */
package edu.nyu.cs.lcs.utility.google_earth;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author Scot Dalton
 *
 */
public abstract class GoogleEarth {
	public final static double SCALE = 0.00215;
	public final static double ALTITUDE = 3000.0;
	public final long LAUNCH_WAIT = 15000;
	public final long OPEN_KML_WAIT = 15000;
	private List<String> openKmlCommand;
	private ProcessBuilder launchProcessBuilder;
	private Process launchProcess;
	private BufferedReader inputReader;
	private BufferedWriter outputWriter;
	
	protected GoogleEarth(List<String> launchCommand, List<String> openKmlCommand) {
		this.openKmlCommand = openKmlCommand;
		launchProcessBuilder = new ProcessBuilder(launchCommand);
	}
	
	public Process launch() throws Exception {
		if(launchProcess == null) {
			launchProcess = launchProcessBuilder.start();
			inputReader = 
				new BufferedReader(
					new InputStreamReader(
						new BufferedInputStream(launchProcess.getInputStream())));
			outputWriter = 
				new BufferedWriter(
					new OutputStreamWriter(
						new BufferedOutputStream(launchProcess.getOutputStream())));
			waitForLaunch();
		}
		return launchProcess;
	}
	
	public abstract void waitForLaunch() throws Exception;
	public abstract void waitForKml(int waitForKml) throws Exception;

	public Process openKml(String kmlFileName, int waitForKml) throws Exception {
		List<String> command = Lists.newArrayList(openKmlCommand);
		command.add(kmlFileName);
		Process kmlProcess = new ProcessBuilder(command).start();
		waitForKml(waitForKml);
		return kmlProcess;
	}
	
	public void destroy() {
		launchProcess.destroy();
	}
	
	protected Process getLaunchProcess() {
		return launchProcess;
	}
	
	protected BufferedReader getInputReader() {
		return inputReader;
	}
	
	protected BufferedWriter getOutputWriter() {
		return outputWriter;
	}
}