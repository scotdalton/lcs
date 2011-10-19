/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth.util;

import com.google.common.base.Predicate;

import edu.nyu.cs.sysproj.google_earth.ArabilityClassification;
import edu.nyu.cs.sysproj.google_earth.Image;

/**
 * @author Scot Dalton
 *
 */
public abstract class ArabilityClassificationPredicate implements Predicate<Image> {
	
	protected ArabilityClassification arabilityClassification;

	/* (non-Javadoc)
	 * @see com.google.common.base.Predicate#apply(java.lang.Object)
	 */
	@Override
	public boolean apply(Image arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public ArabilityClassification getDevelopmentState() {
		return arabilityClassification;
	}

}
