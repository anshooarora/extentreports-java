# Extent - v3 Java

[![Join the chat at https://gitter.im/anshooarora/extentreports](https://badges.gitter.im/anshooarora/extentreports.svg)](https://gitter.im/anshooarora/extentreports?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://travis-ci.org/anshooarora/extentreports-java.svg?branch=3.0.0-dev)](https://travis-ci.org/anshooarora/extentreports-java)

Branch for v3 of Extent. Java8 only. Java7 and lower versions, use Extent v2. 

Note: This is a development branch, so there are several features still broken and yet to be developed.

Note: Use [0.2.10 branch](https://github.com/anshooarora/extentx/tree/0.2.10-alpha-extent3) of ExtentX with this version.

### Samples

 * <a href='http://relevantcodes.com/Tools/extent-3/Extent.html'>Standard</a>
 * <a href='http://relevantcodes.com/Tools/extent-3/ExtentBDD.html'>BDD</a>

#### What doesn't work

 * Email Reporter - in progress
 * ~~ExtentX report level stats need to be fixed (new ExtentX version will be released)~~
 * ~~ExtentHTMLReporter - search functionality not yet integrated~~
 * ~~ExtentX still does not show images (base64 will work but their use is highly discouraged)~~
 * you tell me..  

### Shortcuts

#### Views

```
t - test-view
c - category-view
x - exception-view
d - dashboard
```

#### Filters

```
p - show passed tests
e - show error tests
f - show failed tests
s - show skipped tests
w - show warning tests
esc - clear filters
```

#### Scroll

```
down-arrow - scroll down
up-arrow - scroll up
```

### Starting and Attaching Reporters

```
ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("filePath");
ExtentXReporter extentxReporter = new ExtentXReporter("host");
ExtentEmailReporter emailReporter = new ExtentEmailReporter("filePath");

ExtentReports extent = new ExtentReports();
extent.attachReporter(htmlReporter, extentxReporter, emailReporter);
```

### Simple Test

```
extent.createTest("My First Test").pass("pass");
```

### Logs

```
ExtentTest test = extent.createTest("TestName");
test.log(Status.PASS, "pass");
test.pass("pass");

test.log(Status.FAIL, "fail");
test.fail("fail");
```

### Assign Category

```
extent.createTest("My First Test").assignCategory("Category").pass("pass");
```

### Create Nodes

```
ExtentTest test = extent.createTest("Test With Nodes");

ExtentTest child = test.createNode("Node 1").assignCategory("Nodes");
child.pass("pass");

test.createNode("Node 2").warning("warning");
```

### Screenshots

```
extent.createTest("Media").addScreenCaptureFromPath("file.png").fail("fail");
```

To automatically create relative paths from the report, use configuration:

```
reporter.config().setAutoCreateRelativePathMedia(true);
```

> This configuration will copy the media-file to a directory relative to the file

### BDD Style

```
// source: https://cucumber.io/docs/reference

// feature
ExtentTest feature = extent.createTest("Refund item");

// scenario
ExtentTest scenario = feature.createNode(Scenario.class, "Jeff returns a faulty microwave");
scenario.createNode(Given.class, "Jeff has bought a microwave for $100").pass("pass");
scenario.createNode(And.class, "he has a receipt").pass("pass");
scenario.createNode(When.class, "he returns the microwave").pass("pass");
scenario.createNode(Then.class, "Jeff should be refunded $100").fail("fail");
```

If you do not want to deal with Gherkin classes, you can pass in strings:

```
// feature
ExtentTest feature = extent.createTest("Refund item");

// scenario
ExtentTest scenario = feature.createNode(new GherkinKeyword("Scenario") , "Jeff returns a faulty microwave");
scenario.createNode(new GherkinKeyword("Given"), "Jeff has bought a microwave for $100").pass("pass");
scenario.createNode(new GherkinKeyword("And"), "he has a receipt").pass("pass");
scenario.createNode(new GherkinKeyword("When"), "he returns the microwave").pass("pass");
scenario.createNode(new GherkinKeyword("Then"), "Jeff should be refunded $100").fail("fail");
```

### Writing Results to File/Database

```
extent.flush();
```

### Reporter Configuration

To access configuration of each reporter, use `config()`:

```
htmlReporter.config()
emailReporter.config()
extentxReporter.config()
```

To use external configuration files, use:

```
// if loading from a properties file
reporter.loadConfig(properties-file);

// if loading from an xml file
reporter.loadXMLConfig(xml-file);
```

#### ExtentHtmlReporter Configuration

```
// chart location (top, bottom)
htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);

// show chart on report open
htmlReporter.config().setChartVisibilityOnOpen(false);

// set theme
htmlReporter.config().setTheme(Theme.STANDARD);

// allow automatic saving of media files relative to the report
htmlReporter.config().setAutoCreateRelativePathMedia(true);

// protocol for resources (http, https)
htmlReporter.config().setProtocol(Protocol.HTTPS);

htmlReporter.config().setReportName("report-name");
htmlReporter.config().setDocumentTitle("doc-title");
htmlReporter.config().setEncoding("utf-8");
htmlReporter.config().setJS("js-string");
htmlReporter.config().setCSS("css-string");
```

#### ExtentX Configuration

```
ExtentXReporter extentx = new ExtentXReporter("hostName");
extentx.config().setProjectName("Project");
extentx.config().setReportName("ReportName");

extent.attach(extentx);
```

