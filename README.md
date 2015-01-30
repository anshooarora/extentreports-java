# ExtentReports Library

Extent Reports is a light-weight reporting library developed for Selenium WebDriver. Extent Reports uses jQuery, FontAwesome, Google Charts and Fonts for its presentation. It shows test and step summary, test steps and status in a toggle view for quick analysis. 

View ExtentReports sample <a href='http://relevantcodes.com/ExtentReports/Extent.html'>here</a>.

### Download

Download the jar from <a href='http://relevantcodes.com/extentreports-for-selenium/'>this</a> link.

### Caution

This library is in its very early stages, there may be significant modifications that may break your code.

### Basic Usage

ExtentReports is very simple to use. Below is some basic usage to get you started using this library.  

```java
import com.relevantcodes.extentreports.*;

public class Main {
	// *REQUIRED
    // put this in every class 
    // * Main.class will become TheClassName.class
	static final ExtentReports extent = ExtentReports.get(Main.class); 
	
	public static void main(String[] args) {
		// *REQUIRED
        // init( filePath, replaceExisting )
        //    initializes the reporter at a given path
        //    filePath - path of new report filePath
        //    replaceExisting - 
        //        true - overwrite existing file (if any)
        //        false - use existing file to create reports, 
		//					tests will be appended at the very top
        extent.init("C:\\Extent.html", true);
 
        // *REQUIRED
        // startTest( testName )
        //    creates a toggle for the given test, adds all log events under it    
        extent.startTest("Main");
 
        // this step does the logging
        // log(logStatus, stepName, details)
        extent.log(LogStatus.PASS, "StepName", "PASS Details");      
        extent.log(LogStatus.ERROR, "StepName", "Err details");
        extent.log(LogStatus.WARNING, "StepName", "Warning details");
        extent.log(LogStatus.FAIL, "StepName", "Fail details");
        extent.log(LogStatus.INFO, "StepName", "Info details");
        extent.log(LogStatus.FATAL, "StepName", "Fatal details");
 
        // *REQUIRED
        // endTest()
        //    use to end current toggle level
        //    not using this command may cause the test not have its final status
        extent.endTest();
	}
}
```

### Customization

You can customize the report as you want. Changes can be easily made to the overall CSS by bringing your own custom css, changes to the icons can be made by picking your own from font-awesome etc. Below is some basic usage to demonstrate this library's customization.

```java
// use this if you have your own custom css to change the design
// as per your needs
extent.configure().documentHead().addCustomStylesheet("C:\\css.css");

// this changes the top level summary
extent.configure().header().introSummary("HELLO WORLD");

// this removes the Extent footer section
extent.configure().footer().removeExtentFooter();

// this adds the Extent footer section back
extent.configure().footer().addExtentFooter();

// this changes the icons
// see http://fortawesome.github.io/Font-Awesome/3.2.1/icons/ for more info
extent.configure().statusIcon(LogStatus.PASS, "check-circle");
```

### License

Copyright 2015 Extent Reports committer(s)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0
	
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
