// ***********************************************************************
// Copyright (c) 2015, Anshoo Arora (Relevant Codes). All rights reserved.
//
// Copyrights licensed under the New BSD License.
//
// See the accompanying LICENSE file for terms.
// ***********************************************************************

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace RelevantCodes.ExtentReports
{
    class Program
    {
        static void Main(string[] args)
        {
            var extent = new ExtentReports(@"C:\Users\Anshoo\Documents\workspace\extent2examples\Extent.NET.html", true);
            ExtentTest test, child1, child2, child3, child4;

            test = extent.StartTest("Test 1");
            test.Log(LogStatus.Error, "Error");
            extent.EndTest(test);

            test = extent.StartTest("Nodes");
            child1 = extent.StartTest("Child 1");
            child1.Log(LogStatus.Pass, "Pass");
            child1.Log(LogStatus.Pass, "Pass");
            child2 = extent.StartTest("Child 2");
            child2.Log(LogStatus.Info, "info");
            child2.Log(LogStatus.Info, "info");
            test.AppendChild(child1);
            test.AppendChild(child2);
            extent.EndTest(test);

            test = extent.StartTest("Nodes");
            child1 = extent.StartTest("Child 1");
            child1.Log(LogStatus.Error, "Error" + child1.AddScreenCapture("1.png"));
            child1.Log(LogStatus.Pass, "Pass");
            child2 = extent.StartTest("Child 2");
            child2.Log(LogStatus.Info, "info");
            child2.Log(LogStatus.Info, "info");
            test.AppendChild(child1);
            test.AppendChild(child2);
            extent.EndTest(test);

            test = extent.StartTest("Nodes");
            child1 = extent.StartTest("Child 1");
            child1.Log(LogStatus.Pass, "Pass");
            child1.Log(LogStatus.Pass, "Pass");
            child2 = extent.StartTest("Child 2");
            child2.Log(LogStatus.Info, "info");
            child2.Log(LogStatus.Error, "error");
            child1.AppendChild(child2);
            test.AppendChild(child1);
            extent.EndTest(test);

            extent.Flush();

            /*
            string js = "$(document).ready(function() { $('.test.Fatal, .test.Fail').click(); })";
            string css = "body, .test .right span, .collapsible-header { background: #333; color: #fff; }" +
                    "nav, .tab, .card-panel { background: #000 !important; }" +
                    "table { border: 1px solid #555 !important; }" +
                    "pre { background: #333; border: 1px solid #777 !important; color: #eee !important; }" +
                    ".select-dropdown { background: #333; border-bottom: 1px solid #777 !important; }" +
                    ".select-dropdown li:hover, .select-dropdown li.active { background: #555; }" +
                    "table.bordered > thead > tr, table.bordered > tbody > tr, th, td { border-bottom: 1px solid #555 !important; }" +
                    "th, td, .test-name, .test-desc, .test .right span { color: #fff !important; }" +
                    ".test-body .collapsible > li { border: 1px solid #777; }";
            //css = ".report-name { padding-left: 10px; } .report-name > img { border: none !important; float: left;height: 90%;margin-left: 30px;margin-top: 2px;width: auto; }";
            //js = "$('.report-name').append('<img src=\"http://testiap.globalenglish.com/adminpage/images/iap_logo.png\" />');";

            extent.Config()
                .DocumentTitle("DocumentTitle")
                .ReportName("Name")
                .ReportHeadline("Headline");
            //.InsertStyles(css)
            //.InsertJs(js);
            //.insertCustomStyles(".is-expanded { border:2px solid #444; }")
            //.addCustomStylesheet("C:\\Users\\Anshoo\\git\\extentreports\\site\\css\\css.css");
            //.insertJs("$('.card-panel').each(function() { $(this).css('border', '1px solid #ccc') });")

            //String js = "$(document).ready(function() {function newFunc() {var ended = $('.suite-ended-time').text().replace('-', '/').replace('-', '/');var started = $('.suite-started-time').text().replace('-', '/').replace('-', '/');var diff = new Date(new Date(ended) - new Date(started));var hours = Math.floor(diff / 36e5),minutes = Math.floor(diff % 36e5 / 60000),seconds = Math.floor(diff % 60000 / 1000);$('.suite-total-time-taken').text(hours + 'h ' + minutes + 'm ' + seconds + 's');} newFunc();})";
            //extent.config().insertJs(js);

            extent.AddSystemInfo("Selenium Version", "2.46");
            extent.AddSystemInfo("Environment", "Prod");

            js = " var testList = []; var testName; $($('.test').get().reverse()).each(function() { testName = $(this).find('.test-name').text(); if (testList.indexOf(testName) == -1) testList.push(testName); else $(this).addClass('disabled-test'); }); testList = []; $($('.quick-view-test').get().reverse()).each(function() { testName = $(this).text(); if (testList.indexOf(testName) == -1) testList.push(testName); else $(this).closest('tr').addClass('disabled-test'); })";
            css = ".disabled-test { display: none !important; }";

            test = extent.StartTest("Shows all Status Types");
            test.AssignCategory("ExtentAPI");
            test.Log(LogStatus.Error, "ERROR");

            test.Log(LogStatus.Info, "INFO");
            test.Log(LogStatus.Pass, "PASS");
            test.Log(LogStatus.Skip, "SKIP");
            test.Log(LogStatus.Warning, "WARNING");
            test.Log(LogStatus.Unknown, "UNKNOWN");
            test.Log(LogStatus.Fail, "Fail");
            test.Log(LogStatus.Fatal, "Fatal");
            extent.EndTest(test);

            test = extent.StartTest("Skipped Test");
            test.Log(LogStatus.Skip, "Skipped");
            extent.EndTest(test);

            test = extent.StartTest("Test with Nodes", "This example shows a test with 2 child nodes (methods).");
            child1 = extent.StartTest("Child Node 1");
            child1.Log(LogStatus.Info, "INFO");
            child2 = extent.StartTest("Child Node 2");
            child2.Log(LogStatus.Pass, "PASS");
            child3 = extent.StartTest("Child Node 3");
            child3.Log(LogStatus.Pass, "PASS");
            child4 = extent.StartTest("Child Node 4");
            child4.Log(LogStatus.Pass, "PASS");
            test.AppendChild(child1);
            test.AppendChild(child2);
            test.AppendChild(child3);
            test.AppendChild(child4);
            extent.EndTest(test);

            test = extent.StartTest("Test with Nodes - Failure", "Parent node uses the runStatus from child nodes.");
            test.Log(LogStatus.Pass, "details 1");
            test.Log(LogStatus.Pass, "details 2");
            child1 = extent.StartTest("Child Node 1");
            child1.AssignCategory("ExtentAPI", "CategoryName", "CategoryName");
            child1.Log(LogStatus.Info, "INFO");
            child1.Log(LogStatus.Pass, "PASS");
            child2 = extent.StartTest("Child Node 2");
            child2.AssignCategory("ExtentAPI", "CategoryName");
            child2.Log(LogStatus.Fail, "FAIL");
            child2.Log(LogStatus.Pass, "PASS");
            test
                .AppendChild(child1)
                .AppendChild(child2);
            extent.EndTest(test);

            ExtentTest level1, level2, level3, level4, level5;

            test = extent.StartTest("Shows 2 Levels");

            level1 = extent.StartTest("Level 1");
            level1.Log(LogStatus.Info, "Level 1");

            level2 = extent.StartTest("Level 2");
            level2.Log(LogStatus.Info, "Level 2 " + test.AddScreenCapture("./1.png"));

            level1.AppendChild(level2);
            test.AppendChild(level1);
            extent.EndTest(test);

            level1 = extent.StartTest("Level 1");
            level1.Log(LogStatus.Info, "Level 1");

            level2 = extent.StartTest("Level 2");
            level2.Log(LogStatus.Info, "Level 2 " + test.AddScreenCapture("./1.png"));

            level3 = extent.StartTest("Level 3");
            level3.Log(LogStatus.Fail, "Level 3 " + test.AddScreenCapture("./1.png"));

            level4 = extent.StartTest("Level 4");
            level4.Log(LogStatus.Info, "Level 4 " + test.AddScreenCapture("./1.png"));

            level5 = extent.StartTest("Level 5");
            level5.Log(LogStatus.Info, "Level 5");

            test = extent.StartTest("Multiple Levels");
            level4.AppendChild(level5);
            level3.AppendChild(level4);
            level2.AppendChild(level3);
            level1.AppendChild(level2);
            test.AppendChild(level1);
            extent.EndTest(test);

            test = extent.StartTest("Uses Media", "Example of image and video sources");
            test.AssignCategory("ExtentAPI", "ExtentAPI");
            test.Log(LogStatus.Error, "Snapshot: " + test.AddScreenCapture("1.png"));
            test.Log(LogStatus.Error, "Snapshot: " + test.AddScreenCapture("folder/1.png"));
            test.Log(LogStatus.Error, "Screencast: <br /><br />" + test.AddScreencast("./vid.mp4"));
            extent.EndTest(test);

            test = extent.StartTest("Test without description");
            test.Log(LogStatus.Skip, "This test has no description.");
            test.Log(LogStatus.Pass, "StepName", "Тест тест тест");
            extent.EndTest(test);

            test = extent
                    .StartTest("HTML Tags", "This is a short description")
                    .AssignCategory("Regression", "ExtentAPI", "ExtentAPI");
            test.Log(LogStatus.Info, "You can insert a <a href='http://relevantcodes.com'>link</a> using <p></p> <pre>&lt;a href='http://url'&gt;&lt;/a&gt; tag</pre>");
            test.Log(LogStatus.Info, "This is a <span class='label success'>success label</span> which you can create using: <p></p> <pre>&lt;span class='label success'&gt;description&lt;/span&gt;</pre>");
            test.Log(LogStatus.Info, "This is a <span class='label failure'>fail label</span> which you can create using: <p></p> <pre>&lt;span class='label failure'&gt;description&lt;/span&gt;</pre>");
            test.Log(LogStatus.Info, "This is a <span class='label info'>info label</span> which you can create using: <p></p> <pre>&lt;span class='label info'&gt;description&lt;/span&gt;</pre>");
            test.Log(LogStatus.Info, "This is a <span class='label warn'>warning label</span> which you can create using: <p></p> <pre>&lt;span class='label warn'&gt;description&lt;/span&gt;</pre>");
            extent.EndTest(test);

            test = extent.StartTest("Test Shows &lt;pre&gt; block");
            test.AssignCategory("CategoryName");
            test.Log(LogStatus.Info, "The log message below shows a pre block.<p></p> <pre>You can insert anything in code-style format by enclosing your string in &lt;pre&gt;&lt;/pre&gt; tags</pre>");
            test.Log(LogStatus.Warning, "<pre>java.lang.NullPointerException \n" +
                            "at java.lang.String.replace(Unknown Source) \n" +
                            "at com.relevantcodes.extentreports.Logger.Log(Logger.java:63) \n" +
                            "at com.relevantcodes.extentreports.AbstractLog.Log(AbstractLog.java:64) \n" +
                            "at com.relevantcodes.extentreports.AbstractLog.Log(AbstractLog.java:73) \n" +
                            "at com.relevantcodes.extentreports.ExtentReports.Log(ExtentReports.java:160) \n" +
                            "at com.mytests.TestOne.testA(TestOne.java:37) \n" +
                            "at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) \n" +
                            "at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source) \n" +
                            "at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source) \n" +
                            "at java.lang.reflect.Method.invoke(Unknown Source) \n" +
                            "at org.testng.internal.MethodInvocationHelper.invokeMethod(MethodInvocationHelper.java:84) \n" +
                            "at org.testng.internal.Invoker.invokeMethod(Invoker.java:714) \n" +
                            "at org.testng.internal.Invoker.invokeTestMethod(Invoker.java:901) \n" +
                            "at org.testng.internal.Invoker.invokeTestMethods(Invoker.java:1231) \n" +
                            "at org.testng.internal.TestMethodWorker.invokeTestMethods(TestMethodWorker.java:127) \n" +
                            "at org.testng.internal.TestMethodWorker.run(TestMethodWorker.java:111) \n" +
                            "at java.util.concurrent.ThreadPoolExecutor.runWorker(Unknown Source) \n" +
                            "at java.util.concurrent.ThreadPoolExecutor$Worker.run(Unknown Source) \n" +
                            "at java.lang.Thread.run(Unknown Source) \n" +
                        "</pre>");
            extent.EndTest(test);

            test = extent.StartTest("Unknown");
            extent.EndTest(test);

            extent.Flush();*/
        }
    }
}
