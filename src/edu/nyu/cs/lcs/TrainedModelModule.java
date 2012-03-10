/**
 * 
 */
package edu.nyu.cs.lcs;

import java.io.File;
import java.util.List;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

import edu.nyu.cs.lcs.Features.FeatureSet;
import edu.nyu.cs.lcs.TrainedModelPropertiesModule.ClassifierName;
import edu.nyu.cs.lcs.TrainedModelPropertiesModule.ClassifierOptions;
import edu.nyu.cs.lcs.TrainedModelPropertiesModule.ConfidenceThreshold;
import edu.nyu.cs.lcs.TrainedModelPropertiesModule.SerializationDirectory;

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
			Injector injector = 
				Guice.createInjector(new TrainedModelPropertiesModule());
			String classifierName = injector.getInstance(
				Key.get(String.class, ClassifierName.class));
			List<String> classifierOptions = injector.getInstance(
				Key.get(new TypeLiteral<List<String>>() {}, 
					ClassifierOptions.class));
			List<FeatureSet> featureSets = injector.getInstance(
				Key.get(new TypeLiteral<List<FeatureSet>>() {}));
			File serializationDirectory = injector.getInstance(
				Key.get(File.class, SerializationDirectory.class));
			double confidenceThreshold = injector.getInstance(
				Key.get(Double.class, ConfidenceThreshold.class));
			try {
				trainedModel = new TrainedModel(classifierName, classifierOptions, 
					featureSets, serializationDirectory, confidenceThreshold);
			} catch (Exception e) {
				addError(e);
			}
		}
		return trainedModel;
	}
}