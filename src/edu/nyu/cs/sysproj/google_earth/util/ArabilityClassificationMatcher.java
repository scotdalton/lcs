/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth.util;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import edu.nyu.cs.sysproj.google_earth.ArabilityClassification;
import edu.nyu.cs.sysproj.google_earth.Image;

/**
 * @author Scot Dalton
 *
 */
public class ArabilityClassificationMatcher {
	private static List<ArabilityClassificationPredicate> predicates = 
		Lists.newArrayList(new NonArablePredicate(),
			
			new ArablePredicate());
	private static ArabilityClassification DEFAULT_DEVELOPMENT_STATE =
		ArabilityClassification.UNDETERMINED;

	public static ArabilityClassification getDevelopmentState(Image image) {
		List<ArabilityClassification> arabilityClassifications = 
			Lists.newArrayList();
		for(ArabilityClassificationPredicate predicate : predicates)
			if(predicate.apply(image))
				arabilityClassifications.add(predicate.getDevelopmentState());
		return getPrimaryDevelopmentState(arabilityClassifications);
	}

	private static ArabilityClassification getPrimaryDevelopmentState(
			List<ArabilityClassification> arabilityClassifications) {
		if(arabilityClassifications.isEmpty())
			return DEFAULT_DEVELOPMENT_STATE;
		return arabilityClassifications.get(0);
	}
}
