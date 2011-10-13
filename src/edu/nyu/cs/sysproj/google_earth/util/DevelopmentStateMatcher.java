/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth.util;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import edu.nyu.cs.sysproj.google_earth.DevelopmentState;
import edu.nyu.cs.sysproj.google_earth.EarthImage;

/**
 * @author Scot Dalton
 *
 */
public class DevelopmentStateMatcher {
	private static ArrayList<DevelopmentStatePredicate> predicates = 
		Lists.newArrayList(
			new CommercialDevelopmentPredicate(),
			new ResidentialDevelopmentPredicate(),
			new UndevelopedPredicate());
	private static DevelopmentState DEFAULT_DEVELOPMENT_STATE =
		DevelopmentState.UNDETERMINED;

	public static DevelopmentState getDevelopmentState(EarthImage image) {
		List<DevelopmentState> developmentStates = 
			Lists.newArrayList();
		for(DevelopmentStatePredicate predicate : predicates)
			if(predicate.apply(image))
				developmentStates.add(predicate.getDevelopmentState());
		return getPrimaryDevelopmentState(developmentStates);
	}

	private static DevelopmentState getPrimaryDevelopmentState(
			List<DevelopmentState> developmentStates) {
		if(developmentStates.isEmpty())
			return DEFAULT_DEVELOPMENT_STATE;
		return developmentStates.get(0);
	}
}
