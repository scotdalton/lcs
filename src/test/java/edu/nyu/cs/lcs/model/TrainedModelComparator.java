/**
 * 
 */
package edu.nyu.cs.lcs.model;

import java.io.File;
import java.io.FileReader;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import com.google.common.collect.Lists;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.Utils;

/**
 * @author Scot Dalton
 * 
 * */
public class TrainedModelComparator {
	private static final File classifiersFile = 
		new File("src/test/resources/META-INF/classifiers.yml");
	private static final File featuresFile = 
		new File("src/test/resources/META-INF/features.yml");
	private static final File serializationDirectory = new File(".classifiers/default");

	@Test
	@SuppressWarnings("unchecked")
	public void comparator() throws Exception {
		Yaml yaml = new Yaml();
		List<String> features = 
			(List<String>) yaml.load(new FileReader(featuresFile));
		Map<String, List<String>> classifiers = 
			(Map<String, List<String>>) yaml.load(new FileReader(classifiersFile));
		for (Entry<String, List<String>> entry: classifiers.entrySet()) {
			String classifierName = entry.getKey();
			List<String> classifierOptions = Lists.newArrayList();
			if (entry.getValue() != null) 
				classifierOptions = entry.getValue();
			System.out.println("---");
			System.out.println(classifierName);
			try {
				AbstractClassifier classifier = 
					(AbstractClassifier) Utils.forName(Classifier.class, classifierName, classifierOptions.toArray(new String[0]));
				long startGet = Calendar.getInstance().getTimeInMillis();
				if (serializationDirectory.exists() || serializationDirectory.mkdirs()) {
					TrainedModel trainedModel = 
						new TrainedModel(serializationDirectory, classifier);
					long endGet = Calendar.getInstance().getTimeInMillis();
					long startTest = Calendar.getInstance().getTimeInMillis();
					System.out.println(trainedModel.test());
					long endTest = Calendar.getInstance().getTimeInMillis();
					System.out.println("Time to train: " + (endGet- startGet)/(double)1000);
					System.out.println("Time to test: " + (endTest- startTest)/(double)1000);
				} else {
					throw new Exception("Problems with the serialization directory");
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
			System.out.println();
		}
	}
}