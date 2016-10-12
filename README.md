# Extent - v3 Java

[![Join the chat at https://gitter.im/anshooarora/extentreports](https://badges.gitter.im/anshooarora/extentreports.svg)](https://gitter.im/anshooarora/extentreports?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://travis-ci.org/anshooarora/extentreports-java.svg)](https://travis-ci.org/anshooarora/extentreports-java)

Java8 only. Java7 and lower versions, use Extent v2. 

Note: Use [the latest version](https://github.com/anshooarora/extentx) of ExtentX with this version.

### Samples

 * <a href='http://extentreports.com/os/3/extent.html'>Standard</a>
 * <a href='http://extentreports.com/os/3/bdd.html'>BDD</a>

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

ExtentReports extent = new ExtentReports();
extent.attachReporter(htmlReporter, extentxReporter);
```

### Creating Tests

```
ExtentTest test = extent.createTest("My First Test");
test.log(Status.PASS, "pass");
// or, shorthand:
test.pass("pass");
```

The above can also be written in a single line:

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

#### Logging exceptions

To log exceptions, simply pass the exception.

> Note: doing this will also enable the defect/bug tab in the report.

```
Exception e;
test.fail(e);
```

### Assign Category

Assigning a category will enable the category-view.

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

### Test-runner output

Passing any output from your test-runner to extent will enable the test-runner logs view.

```
ExtentReports extent = new ExtentReports();

extent.setTestRunnerOutput("log 1");
extent.setTestRunnerOutput("<pre>Log 2</pre>");
extent.setTestRunnerOutput("<h2>heading 2</h2>");
```

### Reporter Configuration

To access configuration of each reporter, use `config()`:

```
htmlReporter.config()
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

// server-url must be supplied otherwise images will not be uploaded
// not setting a url for tests that add screen-shots wil result in a IOException
extentx.config().setServerUrl("http://localhost:1337/");

// if appending to an existing report
extentx.config().setReportObjectId(id);

extent.attach(extentx);
```

### Markup Helpers

A few helpers are provided to allow:

 * Code block
 * Table
 * Label

#### Code block

```
String code = "<xml>\n\t<node>\n\t\tText\n\t</node>\n</xml>";
Markup m = MarkupHelper.createCodeBlock(code);

test.pass(m);
// or
test.log(Status.PASS, m);
```

#### Label

```
String text = "extent";
Markup m = MarkupHelper.createLabel(text, ExtentColor.BLUE);

test.pass(m);
// or
test.log(Status.PASS, m);
```

