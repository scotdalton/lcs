/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

import static edu.nyu.cs.sysproj.arability.utility.Configuration.INSTANCES_DIRECTORY;

import java.io.File;
import java.util.List;
import java.util.Map;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import edu.nyu.cs.sysproj.arability.Features.FeatureSet;
import edu.nyu.cs.sysproj.arability.features.Feature;

/**
 * Singleton representing the training set.
 * 
 * @author Scot Dalton
 *
 */
public class TrainingSet {
	private static Map<FeatureSet[], TrainingSet> trainingSets;
	private static Map<FeatureSet[], TrainingSet> testingSets;
	private Instances instances;
	private String name;
	private File instancesFile;
	private List<Image> knownImages;
	private FastVector attributes;
	private FeatureSet[] featureSets;
	
	private TrainingSet(List<Image> knownImages, 
			String serializationDirectory, 
			FeatureSet[] featureSets) throws Exception {
		this.knownImages = knownImages;
		this.featureSets = featureSets;
		// Set attributes
		List<Feature> features = Features.getFeatures(featureSets);
		attributes = new FastVector(features.size() + 1);
		for(int i=0; i<features.size(); i++)
			attributes.addElement(new Attribute(features.get(i).toString(), i));
		FastVector classifications = 
			new FastVector(Classification.values().length-1);
		for(Classification classification: Classification.values())
			if(classification.isTrainable())
				classifications.addElement(classification.toString());
		// Class attribute is last
		attributes.addElement(new Attribute("classification", classifications, features.size()));
		instancesFile = new File(serializationDirectory + "/" + name);
		if(instancesFile.exists()) {
			instances = deserializeInstances(instancesFile);
		} else {
			refresh();
		}
	}
	
	protected void refresh() throws Exception {
		instances = 
			new Instances(name, attributes, knownImages.size());
		// Class attribute is last
		int classAttributeIndex = attributes.capacity() -1;
		instances.setClassIndex(classAttributeIndex);
		for(Image trainingImage: knownImages) {
			float[] values = 
				Features.getFeatureValuesForImage(trainingImage, featureSets);
			Instance instance = new Instance(attributes.size());
			for(int i=0; i<values.length; i++)
				instance.setValue((Attribute)attributes.elementAt(i), values[i]);
			instance.setValue(
				(Attribute)attributes.elementAt(classAttributeIndex), 
					trainingImage.getClassification().toString());
			instances.add(instance);
		}
		serializeInstances(instancesFile, instances);
	}
	
	protected Instances getInstances() {
		return instances;
	}
	
	protected FastVector getAttributes() {
		return attributes;
	}
	
	protected static TrainingSet getTrainingSet() throws Exception {
		return getTrainingSet(getTrainingImages(), Features.DEFAULT_FEATURE_SET);
	}
	
	protected static TrainingSet getTrainingSet(List<Image> knownImages, 
			FeatureSet[] featureSets) throws Exception {
		if (trainingSets == null) trainingSets = Maps.newHashMap();
		if (!trainingSets.containsKey(featureSets))
			trainingSets.put(featureSets, new TrainingSet(knownImages, 
				INSTANCES_DIRECTORY, Features.DEFAULT_FEATURE_SET));
		return trainingSets.get(featureSets);
	}
	
	protected static TrainingSet getTestingSet() throws Exception {
		return getTestingSet(getTestingImages(), Features.DEFAULT_FEATURE_SET);
	}
	
	protected static TrainingSet getTestingSet(List<Image> knownImages, 
			FeatureSet[] featureSets) throws Exception {
		if (testingSets == null) testingSets = Maps.newHashMap();
		if (!testingSets.containsKey(featureSets))
			testingSets.put(featureSets, new TrainingSet(knownImages, 
				INSTANCES_DIRECTORY, Features.DEFAULT_FEATURE_SET));
		return testingSets.get(featureSets);
	}
	
	private static List<Image> getTrainingImages() {
		List<Image> knownImages = Lists.newArrayList();
		for(Classification classification : Classification.values())
			knownImages.addAll(classification.getTrainingImages());
		return knownImages;
	}
	
	private static List<Image> getTestingImages() {
		List<Image> knownImages = Lists.newArrayList();
		for(Classification classification : Classification.values())
			knownImages.addAll(classification.getTestingImages());
		return knownImages;
	}

	private void serializeInstances(File instancesFile, 
			Instances instances) throws Exception {
		File serializationDirectory = instancesFile.getParentFile();
		if(!serializationDirectory.exists()) serializationDirectory.mkdirs();
		String instancesFileName = instancesFile.getAbsolutePath();
		SerializationHelper.write(instancesFileName, instances);
	}
	
	private Instances deserializeInstances(
			File instancesFile) throws Exception {
		String instancesFileName = instancesFile.getAbsolutePath();
		return (Instances) SerializationHelper.read(instancesFileName);
	}
}