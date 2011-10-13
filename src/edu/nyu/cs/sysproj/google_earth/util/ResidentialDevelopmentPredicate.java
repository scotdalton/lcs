/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth.util;

import edu.nyu.cs.sysproj.google_earth.DevelopmentState;
import edu.nyu.cs.sysproj.google_earth.EarthImage;

/**
 * @author Scot Dalton
 *
 */
public class ResidentialDevelopmentPredicate extends DevelopmentStatePredicate {

	public ResidentialDevelopmentPredicate() {
		this.developmentState = DevelopmentState.RESIDENTIAL;
	}

	/* (non-Javadoc)
	 * @see com.google.common.base.Predicate#apply(java.lang.Object)
	 */
	@Override
	public boolean apply(EarthImage arg0) {
		// TODO Implement logic for EarthImage
		return false;
	}

}
