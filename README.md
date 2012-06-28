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

This project leverages the image processing package [Fiji](http://fiji.sc).  In order to load the necessary Fiji jars
you must clone the fiji project and install it in you local maven repository.

    $ git clone git://github.com/fiji/fiji.git
    $ cd fiji
    $ unset JAVA_HOME
    $ make
    $ cd jars
    $ mvn install:install-file -Dfile=fiji-lib.jar -DgroupId=sc.fiji -DartifactId=fiji-lib -Dversion=2.0.0 -Dpackaging=jar
    $ mvn install:install-file -Dfile=imagescience.jar -DgroupId=imagescience -DartifactId=imagescience -Dversion=2.4.1 -Dpackaging=jar
    $ cd ../plugins
    $ $ mvn install:install-file -Dfile=Trainable_Segmentation.jar -DgroupId=sc.fiji -DartifactId=trainable_segmentation -Dversion=2.0.0 -Dpackaging=jar

To run all tests and build the package

    $ mvn package

To run the application

    $ java -Xms256m -Xmx2048m -jar target/lcs.jar
    
If you think you have outdated compiled classes, clean up the build directory and run package again.

    $ mvn clean && mvn package
