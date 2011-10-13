/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth.kml;

/**
 * @author Scot Dalton
 *
 */
public class Placemark extends edu.nyu.cs.sysproj.google_earth.kml.AbstractBase {
	private String name;
	private Camera camera;

	public static class Builder {
		private String name;
		private Camera camera;

		/**
		 * <p>Public Constructor for the nested Builder class</p>
		 */
		public Builder() {
		}

		/**
		 * <p>Add the Placemark name.</p>
		 * @param longitude - the Placemark name
		 * @return the Builder itself for chaining purposes
		 */
		public Builder name(String name) {
			this.name = name;
			return this;
		}

		/**
		 * <p>Add the Placemark's Camera.</p>
		 * @param latitude - the Placemark's Camera
		 * @return the Builder itself for chaining purposes
		 */
		public Builder camera(Camera camera) {
			this.camera = camera;
			return this;
		}

		/**
		 * <p>Returns an instance of the Placemark class
		 * with the fields specified in the Builder.</p>
		 * @return a new Placemark
		 */
		public Placemark build() {
			return new Placemark(this);
		}
	}
	
	private Placemark(Placemark.Builder builder) {
		this.name = builder.name;
		this.camera = builder.camera;
		this.kml = buildKml();
	}

	private String buildKml() {
		String kml = "<Placemark><name>" + this.name + "</name>";
		kml += camera.toKml();
		kml += "</Placemark>";
		return kml;
	}
}