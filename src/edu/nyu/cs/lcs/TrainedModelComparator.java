/**
 * 
 */
package edu.nyu.cs.lcs;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import com.google.common.collect.Lists;

import edu.nyu.cs.lcs.Features.FeatureSet;


/**
 * @author Scot Dalton
 * Possible values:
 * 	"weka.classifiers.rules.ConjunctiveRule" 
 * 	"weka.classifiers.trees.DecisionStump"
 * 	"weka.classifiers.rules.DecisionTable"
 * 	"weka.classifiers.misc.HyperPipes"
 * 	"weka.classifiers.lazy.IB1"
 * 	"weka.classifiers.lazy.IBk" 
 * 	"weka.classifiers.trees.J48" 
 * 	"weka.classifiers.rules.JRip" 
 * 	"weka.classifiers.lazy.KStar" 
 * 	"weka.classifiers.trees.LMT" 
 * 	"weka.classifiers.functions.Logistic" 
 * 	"weka.classifiers.trees.lmt.LogisticBase" 
 * 	"weka.classifiers.trees.m5.M5Base" 
 * 	"weka.classifiers.functions.MultilayerPerceptron" 
 * 	"weka.classifiers.bayes.NaiveBayes" 
 * 	"weka.classifiers.bayes.NaiveBayesMultinomial" 
 * 	"weka.classifiers.bayes.NaiveBayesSimple" 
 * 	"weka.classifiers.trees.NBTree" 
 * 	"weka.classifiers.rules.NNge" 
 * 	"weka.classifiers.rules.OneR" 
 * 	"weka.classifiers.functions.PaceRegression" 
 * 	"weka.classifiers.rules.PART" 
 * 	"weka.classifiers.trees.m5.PreConstructedLinearModel" 
 * 	"weka.classifiers.rules.Prism" 
 * 	"weka.classifiers.trees.RandomForest" 
 * 	"weka.classifiers.trees.RandomTree" 
 * 	"weka.classifiers.functions.RBFNetwork" 
 * 	"weka.classifiers.trees.REPTree" 
 * 	"weka.classifiers.rules.Ridor" 
 * 	"weka.classifiers.trees.m5.RuleNode" 
 * 	"weka.classifiers.functions.SimpleLinearRegression" 
 * 	"weka.classifiers.functions.SimpleLogistic" 
 * 	"weka.classifiers.functions.SMO" 
 * 	"weka.classifiers.functions.SMOreg" 
 * 	"weka.classifiers.trees.UserClassifier" 
 * 	"weka.classifiers.misc.VFI" 
 * 	"weka.classifiers.functions.VotedPerceptron" 
 * 	"weka.classifiers.functions.Winnow" 
 * 	"weka.classifiers.rules.ZeroR"
 * 
 * */
public class TrainedModelComparator {

	public static void main(String[] args) throws Exception {
		double confidenceThreshold = 0.95;
		List<String> classifierNames = Lists.newArrayList(args);
		for (String classifierName: classifierNames) {
			for(List<FeatureSet> featureSets: getFeatureSetsList()) {
				try {
					List<String> classifierOptions = 
						Lists.newArrayList();
					long startGet = Calendar.getInstance().getTimeInMillis();
					TrainedModel trainedModel = 
						new TrainedModel(
							classifierName, 
							classifierOptions, 
							featureSets, new File("/~/.arability"), confidenceThreshold);
					long endGet = Calendar.getInstance().getTimeInMillis();
					System.out.println("---");
					System.out.println(classifierName);
					String featureSetName = "";
					for(FeatureSet featureSet: featureSets)
						featureSetName += featureSet.toString();
					System.out.println(featureSetName);
					long startTest = Calendar.getInstance().getTimeInMillis();
					System.out.println(trainedModel.test());
					long endTest = Calendar.getInstance().getTimeInMillis();
					System.out.println("Time to get: " + (endGet- startGet)/(double)1000);
					System.out.println("Time to test: " + (endTest- startTest)/(double)1000);
					System.out.println();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static List<List<FeatureSet>> getFeatureSetsList() {
		List<List<FeatureSet>> featureSetsList = Lists.newArrayList();
		featureSetsList.add(Lists.newArrayList(FeatureSet.MEAN_PIXELS, FeatureSet.DCT));
		featureSetsList.add(Lists.newArrayList(FeatureSet.MEAN_PIXELS, FeatureSet.SURF));
		featureSetsList.add(Lists.newArrayList(FeatureSet.MEAN_PIXELS, FeatureSet.DCT_DOWN_SAMPLE));
		featureSetsList.add(Lists.newArrayList(FeatureSet.MEAN_PIXELS, FeatureSet.GRADIENT_MAGNITUDE));
		featureSetsList.add(Lists.newArrayList(FeatureSet.MEAN_PIXELS));
		featureSetsList.add(Lists.newArrayList(FeatureSet.DCT));
		featureSetsList.add(Lists.newArrayList(FeatureSet.SURF));
		featureSetsList.add(Lists.newArrayList(FeatureSet.DCT_DOWN_SAMPLE));
		featureSetsList.add(Lists.newArrayList(FeatureSet.GRADIENT_MAGNITUDE));
		return featureSetsList;
	}
}