/**
 * 
 */
package edu.nyu.cs.lcs;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;

/**
 * @author Scot Dalton
 *
 */
public class TrainedModelModule extends AbstractModule {

	private final static String PROPERTIES_FILE_NAME = 
		"./config/trainedmodel.properties";
	private Properties properties;
	
	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface SerializationDirectory {};

	public TrainedModelModule() 
			throws FileNotFoundException, IOException {
		this(PROPERTIES_FILE_NAME);
	}
	
	public TrainedModelModule(String propertiesFileName) 
			throws FileNotFoundException, IOException {
		super();
		properties = new Properties();
		properties.load(new FileReader(propertiesFileName));
	}

	@Override
	protected void configure() {
		bind(String.class).annotatedWith(SerializationDirectory.class).
			toInstance(properties.getProperty("serializationDirectory"));
	}
}