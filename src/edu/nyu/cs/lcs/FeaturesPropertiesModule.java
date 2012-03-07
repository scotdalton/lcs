/**
 * 
 */
package edu.nyu.cs.lcs;

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
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;

import edu.nyu.cs.lcs.ImageModule.ChoppedHeight;
import edu.nyu.cs.lcs.ImageModule.ChoppedWidth;
import edu.nyu.cs.lcs.ImageModule.DownSampleSquareRoot;

/**
 * @author Scot Dalton
 *
 */
public class FeaturesPropertiesModule extends AbstractModule {

	private final static String PROPERTIES_FILE_NAME = 
		"./config/features.properties";
	private String propertiesFileName;
	private Properties properties;

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface MeanPixelBands {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface DCTWidth {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface DCTHeight {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface DCTDownSampleXs {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface DCTDownSampleYs {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface GradientMagnitudeWidth {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface GradientMagnitudeHeight {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface GradientMagnitudeBands {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface NumSurfs {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface SurfLength {};

	public FeaturesPropertiesModule() {
		this(PROPERTIES_FILE_NAME);
	}

	public FeaturesPropertiesModule(String propertiesFileName) {
		super();
		this.properties = new Properties();
		this.propertiesFileName = propertiesFileName;
		
	}

	@Override
	protected void configure() {
		try {
			properties.load(new FileReader(propertiesFileName));
			Injector injector = 
				Guice.createInjector(new ImageModule());
			bind(Integer.class).annotatedWith(MeanPixelBands.class).
				toInstance(Integer.valueOf(properties.
					getProperty("meanPixelsBands")));
			bind(Integer.class).annotatedWith(DCTWidth.class).
				toInstance(injector.getInstance(Key.
					get(Float.class, ChoppedWidth.class)).intValue());
			bind(Integer.class).annotatedWith(DCTHeight.class).
				toInstance(injector.getInstance(Key.
					get(Float.class, ChoppedHeight.class)).intValue());
			bind(Integer.class).annotatedWith(DCTDownSampleXs.class).
				toInstance(injector.getInstance(Key.
					get(Integer.class, DownSampleSquareRoot.class)));
			bind(Integer.class).annotatedWith(DCTDownSampleYs.class).
				toInstance(injector.getInstance(Key.
					get(Integer.class, DownSampleSquareRoot.class)));
			bind(Integer.class).annotatedWith(GradientMagnitudeWidth.class).
				toInstance(injector.getInstance(Key.
					get(Float.class, ChoppedWidth.class)).intValue());
			bind(Integer.class).annotatedWith(GradientMagnitudeHeight.class).
				toInstance(injector.getInstance(Key.
					get(Float.class, ChoppedHeight.class)).intValue());
			bind(Integer.class).annotatedWith(GradientMagnitudeBands.class).
				toInstance(Integer.valueOf(properties.
					getProperty("gradientMagnitudeBands")));
			bind(Integer.class).annotatedWith(NumSurfs.class).
				toInstance(Integer.valueOf(properties.
					getProperty("numSurfs")));
			bind(Integer.class).annotatedWith(SurfLength.class).
				toInstance(Integer.valueOf(properties.
					getProperty("surfLength")));
		} catch (Exception e) {
			addError(e);
		}
	}

}
