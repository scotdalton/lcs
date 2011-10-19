/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth.util;

import edu.nyu.cs.sysproj.google_earth.ArabilityClassification;
import edu.nyu.cs.sysproj.google_earth.Image;

/**
 * @author Scot Dalton
 *
 */
public class ArablePredicate extends ArabilityClassificationPredicate {

	public ArablePredicate() {
		this.arabilityClassification = ArabilityClassification.ARABLE;
	}

	/* (non-Javadoc)
	 * @see com.google.common.base.Predicate#apply(java.lang.Object)
	 */
	@Override
	public boolean apply(Image arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}