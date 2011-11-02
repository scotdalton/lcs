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
		boolean isInvalid(Image image) {
			return false;
		}
	},
	BLURRY {
		boolean isInvalid(Image image) {
			return false;
		}
	},
	BRIGHT {
		boolean isInvalid(Image image) {
			return false;
		}
	};

	abstract boolean isInvalid(Image image);

}
