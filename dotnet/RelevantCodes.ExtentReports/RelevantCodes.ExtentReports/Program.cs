using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RelevantCodes.ExtentReports
{
    class Program
    {
        static void Main(string[] args)
        {
            var extent = new ExtentReports("Extent.Net.html");

            extent.ConfigurationFromFile(@"C:\Users\Anshoo\git\extentreports\dotnet\RelevantCodes.ExtentReports\RelevantCodes.ExtentReports\bin\Debug\config.xml");

            extent.AddTestRunnerOutput("You can send all logs from your TestRunner (such as NUnit, Gallio, MSTest etc) to Extent by using:");
            extent.AddTestRunnerOutput("<pre>for (string s : Reporter.GetOutput()) { <br />    extent.AddTestRunnerOutput(s); <br />}</pre>");
            extent.AddTestRunnerOutput("Visit <a href='https://github.com/anshooarora/extentreports/blob/master/java/extentreports/src/test/java/com/relevantcodes/extentreports/testngexamples/ExtentReporterNG.java'>this link</a> for more info.");

            extent.AddSystemInfo("Machine Name", "Anshoo");
            extent.AddSystemInfo("Domain", "Anshoo");
            extent.AddSystemInfo("Selenium Version", "2.46");
            extent.AddSystemInfo("Environment", "Prod");
            extent.AddSystemInfo("Host Name", "Test");

            var test = extent.StartTest("Extent", "A very basic step with a few.Logs.").AssignAuthor("Anshoo");
            test.Log(LogStatus.Pass, "Step Info");
            test.Log(LogStatus.Pass, "Step Info");
            test.Log(LogStatus.Pass, "Step Info");
            test.Log(LogStatus.Pass, "Step Info");
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

            test = extent.StartTest("Snapshot test", "This test shows snapshots.").AssignCategory("Snapshots");
            test.Log(LogStatus.Fail, "Snapshot " + test.AddScreenCapture("./1.png"));
            test.Log(LogStatus.Fail, "Snapshot " + test.AddScreenCapture("./2.png"));
            extent.EndTest(test);

            test = extent.StartTest("Test with Nodes", "This example shows a test with 2 child nodes (methods).").AssignAuthor("Anshoo");
            var child1 = extent.StartTest("Child Node 1").AssignCategory("ExtentAPI", "Regression");
            child1.Log(LogStatus.Fail, "Failed Step");
            child1.Log(LogStatus.Fail, "Snapshot " + test.AddScreenCapture("./1.png"));
            var child2 = extent.StartTest("Child Node 2").AssignCategory("ExtentAPI", "Regression");
            child2.Log(LogStatus.Warning, "Warning Step");
            child2.Log(LogStatus.Pass, "Snapshot " + test.AddScreenCapture("./2.png"));
            test.AppendChild(child1);
            test.AppendChild(child2);
            extent.EndTest(test);

            test = extent.StartTest("Test Shows pre block");
            test.AssignCategory("CategoryName");
            test.Log(LogStatus.Skip, "<pre>java.lang.NullPointerException \n" +
                    "at java.lang.String.replace(Unknown Source) \n" +
                    "at com.relevantcodes.extentreports.Logger.Log.Logger.java:63) \n" +
                    "at com.relevantcodes.extentreports.Abstrac.Log.Log(Abstrac.Log.java:64) \n" +
                    "at com.relevantcodes.extentreports.Abstrac.Log.Log(Abstrac.Log.java:73) \n" +
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

            extent.Flush();
            extent.Close();
        }
    }
}
