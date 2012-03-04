/**
 * 
 */
package edu.nyu.cs.lcs.classifications;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.FileReader;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;

/**
 * @author Scot Dalton
 *
 */
public abstract class ClassificationModule extends AbstractModule {

	private Properties properties;
	private String propertiesFileName;
	
	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface ShortName {};
	
	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface TrainingDirectory {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface TestingDirectory {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface IsTrainable {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface Red {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface Green {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface Blue {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface Alpha {};

	public ClassificationModule(String propertiesFileName) {
		super();
		this.properties = new Properties();
		this.propertiesFileName = propertiesFileName;
	}

	@Override
	protected void configure() {
		try {
			properties.load(new FileReader(propertiesFileName));
			bind(String.class).annotatedWith(ShortName.class).
				toInstance(properties.getProperty("shortName"));
			Boolean isTrainable = 
				Boolean.valueOf(properties.getProperty("isTrainable"));
			bind(Boolean.class).annotatedWith(IsTrainable.class).
				toInstance(isTrainable);
			if (isTrainable) {
				bind(String.class).annotatedWith(TrainingDirectory.class).
					toInstance(properties.getProperty("trainingDirectory"));
				bind(String.class).annotatedWith(TestingDirectory.class).
					toInstance(properties.getProperty("testingDirectory"));
			}
			bind(Integer.class).annotatedWith(Red.class).
				toInstance(Integer.valueOf(properties.getProperty("red")));
			bind(Integer.class).annotatedWith(Green.class).
				toInstance(Integer.valueOf(properties.getProperty("green")));
			bind(Integer.class).annotatedWith(Blue.class).
				toInstance(Integer.valueOf(properties.getProperty("blue")));
			bind(Integer.class).annotatedWith(Alpha.class).
				toInstance(Integer.valueOf(properties.getProperty("alpha")));
		} catch (Exception e) {
			this.addError(e);
		}
	}
}