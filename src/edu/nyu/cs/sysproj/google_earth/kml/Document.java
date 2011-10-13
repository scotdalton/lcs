/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth.kml;

import java.util.List;

/**
 * @author Scot Dalton
 *
 */
public class Document extends edu.nyu.cs.sysproj.google_earth.kml.AbstractBase {
	private String name;
	private List<Placemark> placemarks;

	public static class Builder {
		private String name;
		private List<Placemark> placemarks;

		/**
		 * <p>Public Constructor for the nested Builder class</p>
		 */
		public Builder() {
		}

		/**
		 * <p>Add the Document name.</p>
		 * @param longitude - the Document name
		 * @return the Builder itself for chaining purposes
		 */
		public Builder name(String name) {
			this.name = name;
			return this;
		}

		/**
		 * <p>Add the Document's Placemarks.</p>
		 * @param latitude - the Document's Placemarks
		 * @return the Builder itself for chaining purposes
		 */
		public Builder placemarks(List<Placemark> placemarks) {
			this.placemarks = placemarks;
			return this;
		}

		/**
		 * <p>Returns an instance of the Document class
		 * with the fields specified in the Builder.</p>
		 * @return a new Document
		 */
		public Document build() {
			return new Document(this);
		}
	}

	private Document(Document.Builder builder) {
		this.name = builder.name;
		this.placemarks = builder.placemarks;
		this.kml = buildKml();
	}

	private String buildKml() {
		String kml = "<Document><name>" + this.name + "</name>";
		for(Placemark placemark: placemarks)
			kml += placemark.toKml();
		kml += "</Document>";
		return kml;
	}
}