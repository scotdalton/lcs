/**
 * 
 */
package edu.nyu.cs.lcs;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.File;
import java.io.FileReader;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Properties;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.TypeLiteral;

import edu.nyu.cs.lcs.Features.FeatureSet;

/**
 * @author Scot Dalton
 *
 */
public class TrainedModelPropertiesModule extends AbstractModule {

	private Properties properties;
	private String propertiesFileName;
	
	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface ClassifierName {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface ClassifierOptions {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface SerializationDirectory {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface ConfidenceThreshold {};

	public TrainedModelPropertiesModule() {
		this("./config/trainedmodel.properties");
	}
	
	public TrainedModelPropertiesModule(String propertiesFileName) {
		super();
		this.properties = new Properties();
		this.propertiesFileName = propertiesFileName;
	}

	@Override
	protected void configure() {
		try {
			properties.load(new FileReader(propertiesFileName));
			bind(String.class).annotatedWith(ClassifierName.class).
				toInstance(properties.getProperty("classifierName"));
			List<String> classifierOptions = Lists.newArrayList(); 
			bind(new TypeLiteral<List<String>>() {}).
				annotatedWith(ClassifierOptions.class).
					toInstance(classifierOptions);
			bind(File.class).annotatedWith(SerializationDirectory.class).
				toInstance(new File(properties.
					getProperty("serializationDirectory")));
			bind(new TypeLiteral<List<FeatureSet>>() {}).
				toInstance(Lists.newArrayList(FeatureSet.MEAN_PIXELS));
			bind(Double.class).annotatedWith(ConfidenceThreshold.class).
				toInstance(Double.valueOf(
					properties.getProperty("confidenceThreshold")));
		} catch (Exception e) {
			addError(e);
		}
	}
}