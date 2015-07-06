namespace RelevantCodes.ExtentReports
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    using Model;
    using Source;

    internal class TestBuilder
    {
        public static string GetTestSource(Test Test)
        {
            string testSource = TestHtml.GetSource(3);
            string stepSource = StepHtml.GetSource(2);

            if (Test.Logs.Count > 0 && Test.Logs[0].StepName != "")
            {
                testSource = TestHtml.GetSource(4);
                stepSource = StepHtml.GetSource(-1);
            }

            if (Test.Description == "")
            {
                testSource = testSource.Replace(ExtentFlag.GetPlaceHolder("descVis"), "style='display:none;'");
            }

            testSource = testSource.Replace(ExtentFlag.GetPlaceHolder("testName"), Test.Name)
                        .Replace(ExtentFlag.GetPlaceHolder("testStatus"), Test.Status.ToString().ToLower())
                        .Replace(ExtentFlag.GetPlaceHolder("testStartTime"), Test.StartedTime.ToString())
                        .Replace(ExtentFlag.GetPlaceHolder("testEndTime"), Test.EndedTime.ToString())
                        .Replace(ExtentFlag.GetPlaceHolder("testTimeTaken"), Test.EndedTime.Subtract(Test.StartedTime).TotalMinutes + "m " + Test.EndedTime.Subtract(Test.StartedTime).TotalSeconds + "s")
                        .Replace(ExtentFlag.GetPlaceHolder("testDescription"), Test.Description)
                        .Replace(ExtentFlag.GetPlaceHolder("descVis"), "")
                        .Replace(ExtentFlag.GetPlaceHolder("category"), "")
                        .Replace(ExtentFlag.GetPlaceHolder("testWarnings"), TestHtml.GetWarningSource(Test.InternalWarning));
            
            foreach (TestAttribute t in Test.CategoryList)
            {
                testSource = testSource.Replace(ExtentFlag.GetPlaceHolder("testCategory"), TestHtml.GetCategorySource() + ExtentFlag.GetPlaceHolder("testCategory"))
        			.Replace(ExtentFlag.GetPlaceHolder("category"), t.GetName());
            }

            foreach (var log in Test.Logs)
            {
                testSource = testSource.Replace(ExtentFlag.GetPlaceHolder("step"), stepSource + ExtentFlag.GetPlaceHolder("step"))
                        .Replace(ExtentFlag.GetPlaceHolder("timeStamp"), log.Timestamp.ToShortTimeString())
                        .Replace(ExtentFlag.GetPlaceHolder("stepStatusU"), log.LogStatus.ToString().ToUpper())
                        .Replace(ExtentFlag.GetPlaceHolder("stepStatus"), log.LogStatus.ToString().ToLower())
                        .Replace(ExtentFlag.GetPlaceHolder("statusIcon"), Icon.GetIcon(log.LogStatus))
                        .Replace(ExtentFlag.GetPlaceHolder("stepName"), log.StepName)
                        .Replace(ExtentFlag.GetPlaceHolder("details"), log.Details);
            }

            testSource = testSource.Replace(ExtentFlag.GetPlaceHolder("step"), "");

            return testSource;
        }

        public static string GetQuickSummary(Test Test)
        {
            string source = TestHtml.GetQuickSummarySource();

            int passed = Test.Logs.Count(x => x.LogStatus == LogStatus.Pass);
            int failed = Test.Logs.Count(x => x.LogStatus == LogStatus.Fail);
            int fatal = Test.Logs.Count(x => x.LogStatus == LogStatus.Fatal);
            int error = Test.Logs.Count(x => x.LogStatus == LogStatus.Error);
            int warning = Test.Logs.Count(x => x.LogStatus == LogStatus.Warning);
            int skipped = Test.Logs.Count(x => x.LogStatus == LogStatus.Skip);
            int info = Test.Logs.Count(x => x.LogStatus == LogStatus.Info);
            int unknown = Test.Logs.Count(x => x.LogStatus == LogStatus.Unknown);

            source = source.Replace(ExtentFlag.GetPlaceHolder("testName"), Test.Name)
                .Replace(ExtentFlag.GetPlaceHolder("currentTestPassedCount"), "" + passed)
                .Replace(ExtentFlag.GetPlaceHolder("currentTestFailedCount"), "" + failed)
                .Replace(ExtentFlag.GetPlaceHolder("currentTestFatalCount"), "" + fatal)
                .Replace(ExtentFlag.GetPlaceHolder("currentTestErrorCount"), "" + error)
                .Replace(ExtentFlag.GetPlaceHolder("currentTestWarningCount"), "" + warning)
                .Replace(ExtentFlag.GetPlaceHolder("currentTestInfoCount"), "" + info)
                .Replace(ExtentFlag.GetPlaceHolder("currentTestSkippedCount"), "" + skipped)
                .Replace(ExtentFlag.GetPlaceHolder("currentTestUnknownCount"), "" + unknown)
                .Replace(ExtentFlag.GetPlaceHolder("currentTestRunStatus"), "" + Test.Status.ToString().ToLower())
                .Replace(ExtentFlag.GetPlaceHolder("currentTestRunStatusU"), "" + Test.Status.ToString());

            return source;
        }
    }
}
