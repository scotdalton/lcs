/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth.util;

import com.google.common.base.Predicate;

import edu.nyu.cs.sysproj.google_earth.DevelopmentState;
import edu.nyu.cs.sysproj.google_earth.EarthImage;

/**
 * @author Scot Dalton
 *
 */
public abstract class DevelopmentStatePredicate implements Predicate<EarthImage> {
	
	protected DevelopmentState developmentState;

	/* (non-Javadoc)
	 * @see com.google.common.base.Predicate#apply(java.lang.Object)
	 */
	@Override
	public boolean apply(EarthImage arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public DevelopmentState getDevelopmentState() {
		return developmentState;
	}

}
