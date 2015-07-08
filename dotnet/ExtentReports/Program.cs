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

            t = r.StartTest("Test 2", "desc");
            t.Log(LogStatus.Pass, "First step");
            t.Log(LogStatus.Error, "First step");
            t.Log(LogStatus.Info, "First step");
            t.Log(LogStatus.Skip, "First step");
            t.Log(LogStatus.Unknown, "First step");
            t.Log(LogStatus.Warning, "First step");
            r.EndTest(t);

            t = r.StartTest("Test 3", "desc");
            t.Log(LogStatus.Pass, "First step");
            t.Log(LogStatus.Info, "First step");
            t.Log(LogStatus.Skip, "First step");
            t.Log(LogStatus.Warning, "First step");
            r.EndTest(t);

            t = r.StartTest("Test 4");
            t.Log(LogStatus.Pass, "First step");
            t.Log(LogStatus.Info, "First step");
            t.Log(LogStatus.Skip, "First step");
            r.EndTest(t);

            r.Flush();
        }
    }
}
