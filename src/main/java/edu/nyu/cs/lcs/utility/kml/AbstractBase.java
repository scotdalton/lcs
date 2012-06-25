/**
 * 
 */
package edu.nyu.cs.lcs.utility.kml;

/**
 * @author Scot Dalton
 *
 */
public abstract class AbstractBase {
	protected String kml;

	public String toKml() {
		return kml;
	}
	
	public String toString() {
		return toKml();
	}
}