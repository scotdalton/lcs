/**
 * 
 */
package edu.nyu.cs.sysproj.arability;

import java.util.List;

import edu.nyu.cs.sysproj.arability.Features.FeatureSet;

/**
 * @author Scot Dalton
 *
 */
class TrainedModelKey {
	private String classifier;
	private List<FeatureSet> featureSets;
	
	protected TrainedModelKey(String classifier, List<FeatureSet> featureSets) {
		this.classifier = classifier;
		this.featureSets = featureSets;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof TrainedModelKey))
			return false;
		TrainedModelKey tmk = (TrainedModelKey) o;
		return classifier.equals(tmk.classifier) && 
			featureSets.equals(tmk.featureSets);
	}
}