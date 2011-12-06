/**
 * 
 */
package edu.nyu.cs.sysproj.arability.utility;

import java.util.Calendar;
import java.util.List;

import com.google.common.collect.Lists;

import edu.nyu.cs.sysproj.arability.TrainedModel;

/**
 * @author Scot Dalton
 *
 */
public class TrainedModelComparator {

	public static void main(String[] args) throws Exception {
		List<String> classifiers = 
			Lists.newArrayList(
				"weka.classifiers.rules.ConjunctiveRule", 
				"weka.classifiers.trees.DecisionStump", 
				"weka.classifiers.rules.DecisionTable", 
				"weka.classifiers.misc.HyperPipes", 
				"weka.classifiers.lazy.IB1", 
				"weka.classifiers.lazy.IBk", 
				"weka.classifiers.trees.Id3", 
				"weka.classifiers.trees.J48", 
				"weka.classifiers.rules.JRip",
				"weka.classifiers.lazy.KStar",
				"weka.classifiers.lazy.LBR",
				"weka.classifiers.functions.LeastMedSq",
				"weka.classifiers.functions.LinearRegression",
				"weka.classifiers.trees.LMT",
				"weka.classifiers.functions.Logistic",
				"weka.classifiers.trees.lmt.LogisticBase",
				"weka.classifiers.trees.m5.M5Base",
				"weka.classifiers.functions.MultilayerPerceptron",
				"weka.classifiers.MultipleClassifiersCombiner",
				"weka.classifiers.bayes.NaiveBayes",
				"weka.classifiers.bayes.NaiveBayesMultinomial",
				"weka.classifiers.bayes.NaiveBayesSimple",
				"weka.classifiers.trees.NBTree",
				"weka.classifiers.rules.NNge",
				"weka.classifiers.rules.OneR",
				"weka.classifiers.functions.PaceRegression",
				"weka.classifiers.rules.PART",
				"weka.classifiers.trees.m5.PreConstructedLinearModel",
				"weka.classifiers.rules.Prism",
				"weka.classifiers.trees.RandomForest",
				"weka.classifiers.RandomizableClassifier",
				"weka.classifiers.trees.RandomTree",
				"weka.classifiers.functions.RBFNetwork",
				"weka.classifiers.trees.REPTree",
				"weka.classifiers.rules.Ridor",
				"weka.classifiers.trees.m5.RuleNode",
				"weka.classifiers.functions.SimpleLinearRegression",
				"weka.classifiers.functions.SimpleLogistic",
				"weka.classifiers.SingleClassifierEnhancer",
				"weka.classifiers.functions.SMO",
				"weka.classifiers.functions.SMOreg",
				"weka.classifiers.trees.UserClassifier",
				"weka.classifiers.misc.VFI",
				"weka.classifiers.functions.VotedPerceptron",
				"weka.classifiers.functions.Winnow",
				"weka.classifiers.rules.ZeroR");
		for (String classifier: classifiers) {
			try {
				String[] classifierOptions = {};
				long startGet = Calendar.getInstance().getTimeInMillis();
				TrainedModel trainedModel = 
					TrainedModel.getTrainedModel(classifier, classifierOptions);
				long endGet = Calendar.getInstance().getTimeInMillis();
				System.out.println("---");
				System.out.println(classifier);
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