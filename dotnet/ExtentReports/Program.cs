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
            var r = new ExtentReports("Extent.html", true);
            
            r.Config()
                .AddStylesheet("C:\test.css")
                .DocumentTitle("New Title")
                .InsertJs("function a() {}")
                .ReportHeadline("headline")
                .ReportName("reportname");
            

            var t = r.StartTest("Test 1", "desc");
            t.Log(LogStatus.Pass, "First step");
            t.Log(LogStatus.Error, "First step");
            t.Log(LogStatus.Fail, "First step");
            t.Log(LogStatus.Fatal, "First step");
            t.Log(LogStatus.Info, "First step");
            t.Log(LogStatus.Skip, "First step");
            t.Log(LogStatus.Unknown, "First step");
            t.Log(LogStatus.Warning, "First step");
            r.EndTest(t);
            r.Flush();

            t = r.StartTest("Test 2", "desc");
            t.AssignCategory("Extent", "ExtentAPI");
            t.Log(LogStatus.Pass, "First step " + t.AddScreenCapture("./1.png"));
            t.Log(LogStatus.Error, "First step" + t.AddScreenCapture("./1.png"));
            t.Log(LogStatus.Info, "First step" + t.AddScreenCapture("./1.png"));
            t.Log(LogStatus.Skip, "First step");
            t.Log(LogStatus.Unknown, "First step");
            t.Log(LogStatus.Warning, "First step");
            r.EndTest(t);
            r.Flush();

            t = r.StartTest("Test 3, Ends Abruptly", "desc");
            t.AssignCategory("Regression", "Cat1", "Cat2");
            t.AssignCategory("ExtentAPI");
            t.Log(LogStatus.Pass, "First step" + t.AddScreenCapture("./1.png"));
            t.Log(LogStatus.Info, "First step" + t.AddScreencast("./1.mp4"));
            t.Log(LogStatus.Skip, "First step");
            t.Log(LogStatus.Warning, "First step");

            t = r.StartTest("Test 4");
            t.Log(LogStatus.Pass, "First step");
            t.Log(LogStatus.Info, "First step");
            t.Log(LogStatus.Skip, "First step");
            r.EndTest(t);

            r.Flush();
            r.Close();
        }
    }
}
