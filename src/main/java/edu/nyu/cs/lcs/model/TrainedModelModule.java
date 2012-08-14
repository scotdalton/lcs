/**
 * 
 */
package edu.nyu.cs.lcs.model;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.Utils;

import com.google.common.collect.Lists;
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
				String classifierName = "weka.classifiers.bayes.NaiveBayes";
				List<String> classifierOptions = Lists.newArrayList();
				AbstractClassifier classifier = 
					(AbstractClassifier) Utils.forName(Classifier.class, classifierName, classifierOptions.toArray(new String[0]));
				trainedModel = new TrainedModel(serializationDirectory, classifier);
			} catch (Exception e) {
				addError(e);
			}
		}
		return trainedModel;
	}
}