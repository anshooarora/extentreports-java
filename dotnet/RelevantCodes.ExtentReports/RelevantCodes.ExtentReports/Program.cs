using System;

namespace RelevantCodes.ExtentReports
{
    class Program
    {
        static void Main(string[] args)
        {
            var extent = new ExtentReports("Extent.Net.html");
            extent.AssignProject("Extent");
            //extent.X();

            string json = "{" +
                "\"id\": 1," +
                "\"name\": \"A green door\"," +
                "\"price\": 12.50," +
                "\"tags\": [\"home\", \"green\"]" +
            "}";

            string message = "This test shows how a category is displayed. Assigning a category also " +
                    "enables the Categories view.";
            ExtentTest test = extent.StartTest("CategoryTest", message);
            test.AssignCategory("ExtentAPI", "Category");
            test.Log(LogStatus.Pass, "A category was added");
            test.Log(LogStatus.Info, "<pre>" + json + "</pre>");
            extent.EndTest(test);

            message = "This test shows an example of an author assigned.";
            test = extent.StartTest("AuthorTest", message);
            test.AssignAuthor("Anshoo");
            test.Log(LogStatus.Fail, "An author was added");
            extent.EndTest(test);

            message = "This test shows an example of an author assigned.";
            test = extent.StartTest("AuthorTest", message);
            test.AssignAuthor("Anshoo");
            test.Log(LogStatus.Pass, "An author was added 2");
            extent.EndTest(test);

            Exception nullPointerEx = new NullReferenceException("A NullReferenceException occured.");
            message = "This test shows an example of how exceptions are displayed. " +
                            "Logging an exception also creates the Exceptions view.";
            test = extent.StartTest("ExceptionTest", message);
            test.AssignCategory("ExtentAPI", "IntentionalException");
            test.Log(LogStatus.Fail, nullPointerEx);
            extent.EndTest(test);

            message = "HTML can be embedded anywhere in the report to create meaningful.Logs and messages.";
            test = extent.StartTest("HTMLTagsTest", message);
            string labelMessage = "Labels can be created using: " +
                            "<span class='success label'>Success</span> " +
                            "<span class='fail label'>Fail</span> " +
                            "<span class='warning label'>Warning</span> " +
                            "<span class='info label'>Info</span> " +
                            "<span class='Skip label'>Skip</span>";
            test.Log(LogStatus.Pass, labelMessage);
            labelMessage = "<pre>" +
                    "&lt;span class='success label'&gt;Success&lt;/span&gt; <br />" +
                    "&lt;span class='fail label'&gt;Fail&lt;/span&gt; <br />" +
                    "&lt;span class='warning label'&gt;Warning&lt;/span&gt; <br />" +
                    "&lt;span class='info label'&gt;Info&lt;/span&gt; <br />" +
                    "&lt;span class='Skip label'&gt;Skip&lt;/span&gt;" +
                    "</pre>";
            test.Log(LogStatus.Pass, labelMessage);
            test.Log(LogStatus.Pass, "Link <a href='http://extentreports.relevantcodes.com/'>Linky</a>");
            test.Log(LogStatus.Pass, "<pre>&lt;a href='http://extentreports.relevantcodes.com/'&gt;Linky&lt;/a&gt;</pre>");
            extent.EndTest(test);

            test = extent.StartTest("NodesTest");
            ExtentTest child1 = extent.StartTest("Child Node Level 1").AssignCategory("Node");
            child1.Log(LogStatus.Info, "Info");
            ExtentTest childLevel2 = extent.StartTest("Child Node Level 2").AssignCategory("Node"); ;
            childLevel2.Log(LogStatus.Error, "Error");
            ExtentTest childLevel3 = extent.StartTest("Child Node Level 3").AssignCategory("Node"); ;
            childLevel3.Log(LogStatus.Warning, "Warning");
            ExtentTest childLevel4 = extent.StartTest("Child Node Level 4").AssignCategory("Node"); ;
            childLevel4.Log(LogStatus.Skip, "Skip");
            ExtentTest childLevel5 = extent.StartTest("Child Node Level 5").AssignCategory("Node"); ;
            childLevel5.Log(LogStatus.Pass, "Pass");
            test.AppendChild(child1);
            test.AppendChild(childLevel2);
            test.AppendChild(childLevel3);
            test.AppendChild(childLevel4);
            test.AppendChild(childLevel5);
            childLevel2 = extent.StartTest("Child Node Level 2").AssignCategory("Node"); ;
            childLevel2.Log(LogStatus.Error, "Error");
            test.AppendChild(childLevel2);
            extent.EndTest(test);

            test = extent.StartTest("MediaTest");
            test.Log(LogStatus.Info, test.AddScreenCapture("./1.png"));
            test.Log(LogStatus.Info, test.AddScreenCapture("2.png"));
            test.Log(LogStatus.Info, test.AddBase64ScreenCapture(" data:image/png;base64,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"));
            extent.EndTest(test);

            test = extent.StartTest("SkippedTest").AssignCategory("ExtentAPI"); ;
            test.Log(LogStatus.Skip, "Skip");
            test.Log(LogStatus.Pass, "Pass");
            test.Log(LogStatus.Info, "Info");
            extent.EndTest(test);

            extent.AddTestRunnerOutput("You can send all.Logs from your TestRunner (such as JUnit, TestNG etc) to Extent by using:");
            extent.AddTestRunnerOutput("<pre>for (string s : Reporter.getOutput()) { <br />    extent.setTestRunnerOutput(s); <br />}</pre>");

            extent.Flush();
        }
    }
}
