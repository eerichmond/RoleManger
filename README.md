RoleManger
==========

A sample user/role manger app for testing out AngularJS/Java Spring MVC features


#### Required Tools:
* [Java 7+](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* Maven
* Git
* [Nodejs](http://nodejs.org/)
* Grunt `npm install grunt-cli -g`
* Bower `npm install bower -g`

#### Project Compile:
* `npm install`
* `grunt`
* `mvn tomcat7:run`

#### Unit Testing
* Java: `mvn test`

#### Registered Grunt Commands
* `default` : runs the package command
* `package` : runs bower install and copies the non-minified libraries to resources/lib folder
* `build` : runs the clean command to remove any previous build files, jshint, concat, less, and usemin. `concat` takes all the files in the build.js block of the index.jsp files and concatenates them together and places it in resources/scripts/build.js. `less` compiles the master.less and places the resulted css file in resources/css/build.css. Finally, `usemin` replaces the less file and angular files with the compiled build files.