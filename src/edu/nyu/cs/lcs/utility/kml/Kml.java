/**
 * 
 */
package edu.nyu.cs.lcs.utility.kml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

/**
 * @author Scot Dalton
 *
 */
public class Kml extends edu.nyu.cs.lcs.utility.kml.AbstractBase {
	private List<Document> documents;

	public static class Builder {
		private List<Document> documents;

		/**
		 * <p>Public Constructor for the nested Builder class</p>
		 */
		public Builder() {
		}

		/**
		 * <p>Add the Kml's Documents.</p>
		 * @param latitude - the Kml's Documents
		 * @return the Builder itself for chaining purposes
		 */
		public Builder documents(List<Document> documents) {
			this.documents = documents;
			return this;
		}

		/**
		 * <p>Returns an instance of the Kml class
		 * with the fields specified in the Builder.</p>
		 * @return a new Kml
		 */
		public Kml build() {
			return new Kml(this);
		}
	}

	private Kml(Kml.Builder builder) {
		this.documents = builder.documents;
		this.kml = buildKml();
	}

	public void persistToFile(String filename) throws IOException {
		File file = new File(filename);
		File parent = file.getParentFile();
		if(!parent.exists()) parent.mkdirs();
		if(!file.exists()) file.createNewFile();
		persist(new FileOutputStream(file));
	}
	
	private String buildKml() {
		String kml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
			"<kml xmlns=\"http://www.opengis.net/kml/2.2\" " +
			"xmlns:kml=\"http://www.opengis.net/kml/2.2\" " +
			"xmlns:atom=\"http://www.w3.org/2005/Atom\">";
		for(Document document: documents)
			kml += document.toKml();
		kml += "</kml>";
		return kml;
	}

	private void persist(OutputStream out) throws IOException {
		Writer writer = new OutputStreamWriter(out, "UTF-8");
		writer.write(kml);
		writer.close();
	}
}