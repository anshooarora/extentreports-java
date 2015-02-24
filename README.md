# ExtentReports Library

ExtentReports is a HTML reporting library for Selenium WebDriver for Java and creates easy to use, attractive reports. It shows test and step summary, test steps and status in a toggle view for quick analysis.

View ExtentReports sample <a href='http://relevantcodes.com/ExtentReports/Extent.html'>here</a>.

For most complete and up to date documentation, visit <a href='http://relevantcodes.com/extentreports-documentation/'>this link</a>.

### Download

Download the jar and view latest details & comments from <a href='http://relevantcodes.com/extentreports-for-selenium/'>this</a> link.

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
        
        // *REQUIRED for v0.94 and earlier
        // *OPTIONAL v1.0 onwards, this step is not required
        // endTest()
        //    use to end current toggle level
        //    not using this command may cause the test not have its final status (v0.94 and earlier)
        extent.endTest();
        
        // start other tests
    }
}
```

> ALERT:  It is not required to call endTest() version 1.0 onwards. 

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

#### Using custom CSS

You have options to use custom CSS directly (version 1.0+) in the document or bring in your own stylesheet.

```java
// custom styles
String style = "p{font-size:20px;} .test{background-color:#000 !important;color:#fff !important;}";
extent.configuration().documentHead().addCustomStyles(style);

// custom stylesheet
extent.configuration().documentHead().addCustomStylesheet("C:\\css.css");
```

#### Using custom JS

Just like having the ability to change document styles, you can also add your own custom scripts (version 1.0+).

```java
extent.configuration().scripts().insertJS("$('.test').click(function(){ alert('test clicked'); });");
```

#### Add/Remove Extent footer

Its possible to add/remove the Extent footer using the following configuration:

```java
// remove the footer
extent.configuration().footer().useExtentFooter(false);

// use the footer
extent.configuration().footer().useExtentFooter(true);
```

#### Change status icons

Not a lot of people would do this, but you can choose to use your own icons for log status (PASS, FAIL, WARNING etc.) by choosing one of the icons from Fontawesome website: http://fortawesome.github.io/Font-Awesome/icons/.

```java
extent.configuration().statusIcon(LogStatus.PASS, "check-circle");
```

#### Changing the top-level summary

You can remove or add your own summary by using the following configuration:

```java
// this changes the top level summary
extent.configuration().header().introSummary("My custom report summary.");
```

### Examples

Below is an example with multiple logical classes called by a single driver class. It will generate the following report: <a href='http://relevantcodes.com/ExtentReports/Example1.html'>Example 1</a>.

```java
import com.relevantcodes.extentreports.*;

public class TestDriver {
    static final ExtentReports extent = ExtentReports.get("FrameworkDriver");
    
    public static void main(String[] args) {
        extent.init("C:\\Extent.html", false);
        extent.configuration().footer().useExtentFooter(false);
        
        extent.startTest("Test Login");
        new Login().test();

        extent.startTest("Test Logout");
        new Login().test();
        new Logout().test();
        
        extent.startTest("Add Item To Cart");
        new Login().test();
        new AddToCart().test();
        new Logout().test();
        
        extent.startTest("Checkout Test");
        new Login().test();
        new AddToCart().test();
        new Checkout().test();
        new Logout().test();
    }
}

public class Login {
    static final ExtentReports extent = ExtentReports.get(Login.class);
    
    public void test() {
        extent.log(LogStatus.PASS, "Login->Step", "Some details");
    }
}

public class AddToCart {
    static final ExtentReports extent = ExtentReports.get(AddToCart.class);
    
    public void test() {
        extent.log(LogStatus.PASS, "AddToCart->Step", "Some details");
    }
}

public class Checkout {
    static final ExtentReports extent = ExtentReports.get(Checkout.class);
    
    public void test() {
        extent.log(LogStatus.WARNING, "Checkout->Step", "Some details");
    }
}

public class Logout {
    static final ExtentReports extent = ExtentReports.get("Logout");
    
    public void test() {
        extent.log(LogStatus.INFO, "Logout->Step", "Some details");
    }
}
```

### Important Version Changes

Below are version specific changes that you will find helpful.

#### Version 1.0+

<ul>
<li>If you are coming from v0.94 or earlier, it is no longer required to call endTest after each test.  Its still recommend to use it at the very end of run-session, but not required. See below example:
<pre>
extent.startTest("Test 1");
extent.log(LogStatus.PASS, "Step", "Details");

// no need to call endTest here, you can now just start the next test
// if you are already using endTest, you don't need to remove it or change your code

extent.startTest("Test 2");
extent.log(LogStatus.INFO, "Step", "Details");

// .. more tests
</pre>
</li>
<li>scripts().insertJS(script) introduced in this version</li>
<li>documentHead().addCustomStyles(styles) introduced in this version</li>
<li>footer().removeExtentFooter() is deprecated in this version, utilize useExtentFooter(false) instead</li>
<li>footer().addExtentFooter() is deprecated in this version, utilize useExtentFooter(true) instead</li>
</ul>
        
#### Version 0.94+

<ul>
<li>Ability to add snapshots directly to the report using log method:
<pre>
extent.log(LogStatus.INFO, "Image", "Image example:", "C:\\img.png");
</pre>
</li>

<li>Ability to add description for the test when using startTest:
<pre>
etent.startTest("Test - With Description", "This description will show up under Test.");
</pre>
</li>
</ul>


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
