# CubeReports Library

Cube Reports is a light-weight reporting library developed for Selenium WebDriver. Cube Reports uses jQuery, FontAwesome, Google Charts and Fonts for its presentation. It shows test and step summary, test steps and status in a toggle view for quick analysis. 

View CubeReports sample <a href='http://relevantcodes.com/CubeReports/Cube.html'>here</a>.

### Current Version

0.9 (cubereports-java-0.9.jar)

### Download

<a href='http://relevantcodes.com/CubeReports/cubereports-java-0.9.jar.zip'>cubereports-java-0.9.jar.zip</a>

### Basic Usage

CubeReports is very simple to use. Below is some basic usage to get you started using this library.  

```java
import com.relevantcodes.cubereports.*;

public class Main {
	// *REQUIRED
    // put this in every class 
    // * Main.class will become TheClassName.class
	static CubeReports cube = CubeReports.get(Main.class); 
	
	public static void main(String[] args) {
		// *REQUIRED
        // init( filePath, replaceExisting )
        //    initializes the reporter at a given path
        //    filePath - path of new report filePath
        //    replaceExisting - 
        //        true - overwrite existing file (if any)
        //        false - use existing file to create reports, 
		//					tests will be appended at the very top
        cube.init("C:\\Cube.htm", true);
 
        // *REQUIRED
        // startTest( testName )
        //    creates a toggle for the given test, adds all log events under it    
        cube.startTest("Main");
 
        // this step does the logging
        // log(logStatus, stepName, details)
        cube.log(LogStatus.PASS, "StepName", "PASS Details");      
        cube.log(LogStatus.ERROR, "StepName", "Err details");
        cube.log(LogStatus.WARNING, "StepName", "Warning details");
        cube.log(LogStatus.FAIL, "StepName", "Fail details");
        cube.log(LogStatus.INFO, "StepName", "Info details");
        cube.log(LogStatus.FATAL, "StepName", "Info details");
 
        // *REQUIRED
        // endTest()
        //    use to end current toggle level
        //    not using this command may cause the test not have its final status
        cube.endTest();
	}
}
```

### Customization

You can customize the report as you want. Changes can be easily made to the overall CSS by bringing your own custom css, changes to the icons can be made by picking your own from font-awesome etc. Below is some basic usage to demonstrate this library's customization.

```java
// use this if you have your own custom css to change the design
// as per your needs
cube.configure().addCustomStylesheet("C:\\custom.css");

// this changes the top level summary
cube.configure().introSummary("Change the default summary under the main header");

// this changes the icons
// see http://fortawesome.github.io/Font-Awesome/3.2.1/icons/ for more info
cube.configure().statusIcon(LogStatus.PASS, "check-circle");
```

### License

Copyright 2015 Cube Reports committer(s)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0
	
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.