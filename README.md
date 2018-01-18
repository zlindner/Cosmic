# Cosmic [![Build Status](https://travis-ci.org/zlindner/Cosmic.svg?branch=master)](https://travis-ci.org/zlindner/Cosmic)

### Compiling Cosmic
1. Ensure that `Java` (found [here](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)), `Git` (found [here](http://git-scm.com/)) are installed correctly on your system.
    * Optional: Install `Gradle` (found [here](http://www.gradle.org/downloads))
1. Clone the repository using `git clone https://github.com/zlindner/Cosmic.git`
1. Navigate to the newly cloned folder in a shell and run:
 * `gradlew setupCIWorkspace build` to just build a current jar (this may take a while).
 * `gradlew setupDecompWorkspace` to setup a complete development environment.
 * With `Gradle` installed: use `gradle` instead of `gradlew`
 * On Windows: use `gradlew.bat` instead of `gradlew`