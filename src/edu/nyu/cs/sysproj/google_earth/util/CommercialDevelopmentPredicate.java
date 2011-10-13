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
public class CommercialDevelopmentPredicate extends DevelopmentStatePredicate {
	
	public CommercialDevelopmentPredicate() {
		this.developmentState = DevelopmentState.COMMERCIAL;
	}

	/* (non-Javadoc)
	 * @see com.google.common.base.Predicate#apply(java.lang.Object)
	 */
	@Override
	public boolean apply(EarthImage arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
