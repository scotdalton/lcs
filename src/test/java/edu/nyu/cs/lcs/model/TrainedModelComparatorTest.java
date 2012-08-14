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

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.Utils;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

/**
 * @author Scot Dalton
 * 
 * */
@Ignore
public class TrainedModelComparatorTest {
	private File classifiersFile = 
		new File("src/test/resources/META-INF/classifiers.yml");
	private File featuresFile = 
		new File("src/test/resources/META-INF/features.yml");
	private File serializationDirectory = new File(".classifiers/default_features_with_neighbors");
	private String encoding = "UTF-8";

	@Test
	@SuppressWarnings("unchecked")
	public void comparator() throws Exception {
		boolean quit = false;
		Yaml yaml = new Yaml();
		List<String> features = 
			(List<String>) yaml.load(new FileReader(featuresFile));
		Map<String, List<String>> classifiers = 
			(Map<String, List<String>>) yaml.load(new FileReader(classifiersFile));
		for (Entry<String, List<String>> entry: classifiers.entrySet()) {
			List<String> lines = Lists.newArrayList();
			String classifierName = entry.getKey();
			File comparisons = new File(serializationDirectory + "/" + classifierName + ".comparisons");
			if(comparisons.exists()) continue;
			Files.write("".getBytes(), comparisons);
			List<String> classifierOptions = Lists.newArrayList();
			if (entry.getValue() != null) 
				classifierOptions = entry.getValue();
			lines.add("---");
			lines.add(classifierName);
			try {
				TrainedModel trainedModel;
				long startGet = Calendar.getInstance().getTimeInMillis();
				if (serializationDirectory.exists() || serializationDirectory.mkdirs()) {
					if (classifierName.equals("default")) {
						trainedModel = new TrainedModel(serializationDirectory);
					} else {
						AbstractClassifier classifier = 
							(AbstractClassifier) Utils.forName(Classifier.class, classifierName, classifierOptions.toArray(new String[0]));
	 					trainedModel = 
							new TrainedModel(serializationDirectory, classifier);
					}
					long endGet = Calendar.getInstance().getTimeInMillis();
					long startTest = Calendar.getInstance().getTimeInMillis();
					lines.add(trainedModel.test());
					long endTest = Calendar.getInstance().getTimeInMillis();
					lines.add("Time to train: " + (endGet- startGet)/(double)1000);
					lines.add("Time to test: " + (endTest- startTest)/(double)1000);
				} else {
					quit = true;
					throw new Exception("Problems with the serialization directory");
				}
			} catch (Exception e) {
				e.printStackTrace();
				lines.add(e.getClass().getName());
				for (StackTraceElement ste : e.getStackTrace())
					lines.add("\tat " + ste.toString());
			}
			lines.add(null);
			FileUtils.writeLines(comparisons, encoding, lines);
			lines = null;
			if (quit) System.exit(1);
		}
	}
}