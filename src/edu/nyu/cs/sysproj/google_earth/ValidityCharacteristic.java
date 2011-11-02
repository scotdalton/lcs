/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

/**
 * @author Scot Dalton
 *
 */
public enum ValidityCharacteristic {
	CLOUDY {
		boolean isValid(Image image) {
			return true;
		}
	},
	BLURRY {
		boolean isValid(Image image) {
			return true;
		}
	},
	BRIGHT {
		boolean isValid(Image image) {
			return true;
		}
	};

	abstract boolean isValid(Image image);

}
