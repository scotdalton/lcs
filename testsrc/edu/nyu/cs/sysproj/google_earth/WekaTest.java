/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import static edu.nyu.cs.sysproj.google_earth.TestUtility.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import edu.nyu.cs.sysproj.google_earth.features.MeanPixel;


/**
 * @author Scot Dalton
 * @param <classValues>
 *
 */
public class WekaTest {
	@Test
	public void testWeka() {
		// Declare two numeric attributes
		Attribute redMean = new Attribute("redMean");
		Attribute greenMean = new Attribute("greenMean");
		Attribute blueMean = new Attribute("blueMean");
//		Attribute texture = new Attribute("texture");
		// Declare the class attribute along with its values
		FastVector classValues = new FastVector(2);
		classValues.addElement("arable");
		classValues.addElement("nonarable");
		Attribute classAttribute = new Attribute("theClass", classValues);
		// Declare the feature vector
		FastVector attributes = new FastVector(4);
		attributes.addElement(redMean);    
		attributes.addElement(greenMean);    
		attributes.addElement(blueMean);    
//		attributes.addElement(texture);    
		attributes.addElement(classAttribute);    
		// Create an empty training set
		Instances trainingSet = new Instances("Train", attributes, 1000);           
		// Set class index
		trainingSet.setClassIndex(3);	
		for(File file : getArableTrainingImageFiles()) {
			Image image = new Image(file);
			List<Image> choppedImages = image.getChoppedImages();
			for(Image choppedImage : choppedImages) {
				// Create the instance
				Instance iExample = new Instance(4);
				iExample.setValue((Attribute)attributes.elementAt(0), 
						new MeanPixel(choppedImage, 0).getValue());      
				iExample.setValue((Attribute)attributes.elementAt(1), 
						new MeanPixel(choppedImage, 1).getValue());      
				iExample.setValue((Attribute)attributes.elementAt(2), 
						new MeanPixel(choppedImage, 2).getValue());      
				iExample.setValue((Attribute)attributes.elementAt(3), 
						ArabilityClassification.ARABLE.toString());
				// add the instance
				trainingSet.add(iExample);
			}
		}
		Classifier cModel = (Classifier)new NaiveBayes();
		try {
			cModel.buildClassifier(trainingSet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Test the model
		Evaluation eTest;
		try {
			eTest = new Evaluation(trainingSet);
			// Create an empty testing set
			Instances testingSet = new Instances("Test", attributes, 900);           
			// Set class index
			testingSet.setClassIndex(3);	
			Image image = getTestImage1();
			List<Image> choppedImages = image.getChoppedImages();
			for (Image choppedImage:choppedImages) {
				// Create the instance
				Instance iExample = new Instance(4);
				iExample.setValue((Attribute)attributes.elementAt(0), 
						new MeanPixel(choppedImage, 0).getValue());      
				iExample.setValue((Attribute)attributes.elementAt(1), 
						new MeanPixel(choppedImage, 1).getValue());      
				iExample.setValue((Attribute)attributes.elementAt(2), 
						new MeanPixel(choppedImage, 2).getValue());      
				iExample.setValue((Attribute)attributes.elementAt(3), 
						ArabilityClassification.ARABLE.toString());
				// add the instance
				testingSet.add(iExample);
			}
			try {
				eTest.evaluateModel(cModel, testingSet);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			// Print the result à la Weka explorer:
			String strSummary = eTest.toSummaryString();
			System.out.println(strSummary);
			// Get the confusion matrix
//			double[][] cmMatrix = eTest.confusionMatrix();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
