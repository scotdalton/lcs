/**
 * 
 */
package edu.nyu.cs.lcs.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.Lists;


/**
 * @author Scot Dalton
 *
 */
public class TrainingDataCullerTest {
	private File serializationDirectory = new File(".classifiers");

	@Test
	public void cullDefaultFeaturesTrainingData() 
			throws FileNotFoundException, IOException {
		File serializationDirectory = 
			new File(this.serializationDirectory + "/default_features");
		cullTrainingData(serializationDirectory);
	}

	@Test
	public void cullDefaultFeaturesWithNeighborsTrainingData() 
			throws FileNotFoundException, IOException {
		File serializationDirectory = 
			new File(this.serializationDirectory + "/default_features_with_neighbors");
		cullTrainingData(serializationDirectory);
	}
	
	private void cullTrainingData(File serializationDirectory) throws FileNotFoundException, IOException {
		File originalDataFile = 
			new File(serializationDirectory + "/training.arff.orig");
		File dataFile = 
			new File(serializationDirectory + "/training.arff");
		File culledDataFile = 
			new File(serializationDirectory + "/culled_training.arff");
		if(!originalDataFile.exists()) FileUtils.moveFile(dataFile, originalDataFile);
		Writer culledDataWriter = new FileWriter(culledDataFile);
		BufferedReader reader = new BufferedReader(new FileReader(originalDataFile));
		boolean writeData = true;
		boolean writeAttributes = true;
		int dataCount = 0;
		String datum = reader.readLine();
		while (datum != null) {
			if(writeAttributes || writeData)
				IOUtils.writeLines(Lists.newArrayList(datum), IOUtils.LINE_SEPARATOR, culledDataWriter);
			if(datum.equals("@data")) writeAttributes = false;
			if ((dataCount++ % 2) == 1) {
				writeData = true;
			} else {
				writeData = false;
			}
			datum = reader.readLine();
		}
		IOUtils.writeLines(Lists.newArrayList(""), IOUtils.LINE_SEPARATOR, culledDataWriter);
		culledDataWriter.close();
		FileUtils.moveFile(culledDataFile, dataFile);
	}
}
