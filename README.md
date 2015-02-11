# ExtentReports Library

ExtentReports is a HTML reporting library for Selenium WebDriver for Java and creates easy to use, attractive reports. It shows test and step summary, test steps and status in a toggle view for quick analysis.

View ExtentReports sample <a href='http://relevantcodes.com/ExtentReports/Extent.html'>here</a>.

### Download

Download the jar from <a href='http://relevantcodes.com/extentreports-for-selenium/'>this</a> link.

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
        // init( filePath, replaceExisting, displayOrder )
        //    initializes the reporter at a given path
        //    filePath - path of new report filePath
        //    replaceExisting - 
        //        true - overwrite existing file (if any)
        //        false - use existing file to create reports, 
        //          tests will be appended at the very top
        //    displayOrder
        //        use this to configure the order in which tests are to be displayed
        //            BY_OLDEST_TO_LATEST (default) - oldest test at the top, newest at the end
        //            BY_LATEST_TO_OLDEST - newest test at the top, oldest at the end
        // ** Use init method only once, at the beginning of the test session
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
 
        // to report with snapshot (this feature will be introduced in v0.94)
        // log(logStatus, stepName, details, screenCapturePath)
        extent.log(LogStatus.INFO, "Image", "Image example:", "C:\\img.png");
 
        // *REQUIRED
        // endTest()
        //    use to end current toggle level
        //    not using this command may cause the test not have its final status
        extent.endTest();
    }
}
```

> Note: Use "init" method only once, at the beginning of the run session to set the reporting path.

> Note: By default, the oldest test appears at the top. To change this behavior, or to allow the latest test to appear at the top, use the code below. Report started in one order will remain in that order unless the report is over-written or a new report is created.

```java
extent.init(filePath, false, DisplayOrder.BY_LATEST_TO_OLDEST);
```

#### Adding a Test Description

You can insert a description (v0.94 onwards) for your test using:

```java
extent.startTest("Test - With Description", "This description will show up under the TEST level.");
```

#### Inserting a Snapshot

You can insert snapshot (v0.94 onwards) as a link or directly into the report using:

```java
extent.log(LogStatus.INFO, "Image", "Image example:", "C:\\img.png");
```

#### Inserting a Link

You can use HTML anywhere in this report. Simply do this to insert a link:

```java
extent.log(LogStatus.PASS, "Link Example", "Usage: <a href='http://relevantcodes.com'>link</a>.");
```

#### Inserting Custom HTML

Just like above, you can insert any HTML tag to your report:

```java
extent.log(LogStatus.INFO, "HTML", "This will be <span style='font-weight:bold;'>BOLD</span>");
```

### Customization

You can customize the report as you want. Changes can be easily made to the overall CSS by bringing your own custom css, changes to the icons can be made by picking your own from font-awesome etc. Below is some basic usage to demonstrate this library's customization.

```java
// use this if you have your own custom css to change the design
// as per your needs
extent.configuration().documentHead().addCustomStylesheet("C:\\css.css");
 
// this changes the top level summary
extent.configuration().header().introSummary("HELLO WORLD");
 
// this removes the Extent footer section 
// v0.94 onwards, otherwise use removeExtentFooter() for version 0.93 and earlier
extent.configuration().footer().useExtentFooter(false);
 
// this adds the Extent footer section back 
// v0.94 onwards, otherwise use addExtentFooter() for version 0.93 and earlier
extent.configuration().footer().useExtentFooter(true);
 
// this changes the icons
// see http://fortawesome.github.io/Font-Awesome/3.2.1/icons/ for more info
extent.configuration().statusIcon(LogStatus.PASS, "check-circle");
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
