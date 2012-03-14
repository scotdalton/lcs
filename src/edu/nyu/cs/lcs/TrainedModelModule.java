/**
 * 
 */
package edu.nyu.cs.lcs;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import edu.nyu.cs.lcs.Features.FeatureSet;

/**
 * @author Scot Dalton
 * 
 */
public class TrainedModelModule extends AbstractModule {
	private TrainedModel trainedModel;
	
	@Override
	protected void configure() {
	}
	
	@Provides @Singleton
	TrainedModel provideTrainedModel() {
		if (trainedModel == null) {
			try {
				Properties properties = new Properties();
				properties.load(new FileReader(
					"./config/trainedmodel.properties"));
				String classifierName = 
					properties.getProperty("classifierName");
				List<String> classifierOptions = Lists.newArrayList(); 
				List<FeatureSet> featureSets = 
					Lists.newArrayList(FeatureSet.MEAN_PIXELS);
				File serializationDirectory = new File(properties.
						getProperty("serializationDirectory"));
				double confidenceThreshold = Double.valueOf(
						properties.getProperty("confidenceThreshold"));
				trainedModel = new TrainedModel(classifierName, classifierOptions, 
					featureSets, serializationDirectory, confidenceThreshold);
			} catch (Exception e) {
				addError(e);
			}
		}
		return trainedModel;
	}
}