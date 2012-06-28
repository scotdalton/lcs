/**
 * 
 */
package edu.nyu.cs.lcs.model;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

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
					"src/main/resources/META-INF/trainedmodel.properties"));
				File serializationDirectory = 
					new File(properties.getProperty("serializationDirectory"));
				trainedModel = new TrainedModel(serializationDirectory);
			} catch (Exception e) {
				addError(e);
			}
		}
		return trainedModel;
	}
}