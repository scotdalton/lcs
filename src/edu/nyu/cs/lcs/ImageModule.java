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
public class ImageModule extends AbstractModule {
	private final static String PROPERTIES_FILE_NAME = 
		"./config/image.properties";
	private Properties properties;

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface DownSampleSquareRoot {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface ChoppedWidth {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface ChoppedHeight {};

	public ImageModule() 
			throws FileNotFoundException, IOException {
		this(PROPERTIES_FILE_NAME);
	}

	public ImageModule(String propertiesFileName) 
			throws FileNotFoundException, IOException {
		super();
		properties = new Properties();
		properties.load(new FileReader(propertiesFileName));
	}

	@Override
	protected void configure() {
		bind(Integer.class).annotatedWith(DownSampleSquareRoot.class).
			toInstance(Integer.valueOf(properties.
				getProperty("downSampleSquareRoot")));
		bind(Float.class).annotatedWith(ChoppedWidth.class).
			toInstance(Float.valueOf(properties.
				getProperty("choppedWidth")));
		bind(Float.class).annotatedWith(ChoppedHeight.class).
			toInstance(Float.valueOf(properties.
				getProperty("choppedHeight")));
	}
}