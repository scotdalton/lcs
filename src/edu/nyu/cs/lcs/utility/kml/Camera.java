/**
 * 
 */
package edu.nyu.cs.sysproj.arability.utility.kml;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.nyu.cs.sysproj.arability.utility.google_earth.GoogleEarth;

/**
 * @author Scot Dalton
 *
 */
public class Camera extends edu.nyu.cs.sysproj.arability.utility.kml.AbstractBase {
	private double latitude;
	private double longitude;
	private Date date;
	private SimpleDateFormat dateFormat;

	public static class Builder {
		private double latitude;
		private double longitude;
		private Date date;

		/**
		 * <p>Public Constructor for the nested Builder class</p>
		 */
		public Builder() {
		}

		/**
		 * <p>Add the Camera's longitude.</p>
		 * @param longitude - the Camera's longitude
		 * @return the Builder itself for chaining purposes
		 */
		public Builder longitude(double longitude) {
			this.longitude = longitude;
			return this;
		}

		/**
		 * <p>Add the Camera's latitude.</p>
		 * @param latitude - the Camera's latitude
		 * @return the Builder itself for chaining purposes
		 */
		public Builder latitude(double latitude) {
			this.latitude = latitude;
			return this;
		}

		/**
		 * <p>Add the Camera's date.</p>
		 * @param date - the Camera's date
		 * @return the Builder itself for chaining purposes
		 */
		public Builder date(Date date) {
			this.date = date;
			return this;
		}

		/**
		 * <p>Returns an instance of the Camera class
		 * with the fields specified in the Builder.</p>
		 * @return a new Camera
		 */
		public Camera build() {
			return new Camera(this);
		}
	}
	
	private Camera(Camera.Builder builder) {
		this.latitude = builder.latitude;
		this.longitude = builder.longitude;
		this.date = builder.date;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		this.kml = buildKml();
	}
	
	private String buildKml() {
		return "<Camera xmlns:gx=\"http://www.google.com/kml/ext/2.2\">"+
			"<longitude>" + this.longitude + "</longitude>" +
			"<latitude>" + this.latitude + "</latitude>" +
			"<altitude>" + GoogleEarth.ALTITUDE + "</altitude>" +
			"<heading>0</heading>" +
			"<tilt>0</tilt>" +
			"<altitudeMode>relativeToGround</altitudeMode>" +
			"<gx:TimeStamp>" +
			"<when>" + this.dateFormat.format(this.date) + "</when>" +
			"</gx:TimeStamp>" +
			"<flyToView>1</flyToView>" +
			"</Camera>";
	}
}