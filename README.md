The LCS land classification system uses historical satellite imagery from
Google Earth and determines the classification of land in the image for 
comparison purposes.  

This system is a prototype and should be treated a such.  Currently only 
Mac OS X is supported.  The system has only been tested with Java 1.6. 

For optimum performance:
Open GoogleEarth and select View -> Historical Imagery.
You can also maximize screen real estate by hiding the Toolbar and Sidebar
and selecting View -> Navigation -> Never.

The project uses [Maven](http://maven.apache.org) to manage dependencies, test,
build and produce javadocs.


    $ mvn install:install-file -Dfile=BoofCV.jar -DgroupId=boofcv -DartifactId=boofcv -Dversion=0.8 -Dpackaging=jar
    $ mvn install:install-file -Dfile=libpja.jar -DgroupId=libpja -DartifactId=libpja -Dversion=1.0 -Dpackaging=jar
    $ mvn install:install-file -Dfile=GeoRegression.jar -DgroupId=georegression -DartifactId=georegression -Dversion=1.0 -Dpackaging=jar