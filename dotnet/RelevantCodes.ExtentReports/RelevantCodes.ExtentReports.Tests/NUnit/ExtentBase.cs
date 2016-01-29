using NUnit.Framework;
using NUnit.Framework.Interfaces;

using RelevantCodes.ExtentReports.Tests.Common;

namespace RelevantCodes.ExtentReports.Tests.NUnit
{
    public abstract class ExtentBase
    {
        protected ExtentReports extent;
        protected ExtentTest test;

        [OneTimeSetUp]
        public void FixtureInit()
        {
            //extent = ExtentManager.Instance;
        }

        [TearDown]
        public void TearDown()
        {
            var status = TestContext.CurrentContext.Result.Outcome.Status;
            var stacktrace = string.IsNullOrEmpty(TestContext.CurrentContext.Result.StackTrace)
                    ? ""
                    : string.Format("<pre>{0}</pre>", TestContext.CurrentContext.Result.StackTrace);
            LogStatus logstatus;

            switch (status)
            {
                case TestStatus.Failed:
                    logstatus = LogStatus.Fail;
                    break;
                case TestStatus.Inconclusive:
                    logstatus = LogStatus.Warning;
                    break;
                case TestStatus.Skipped:
                    logstatus = LogStatus.Skip;
                    break;
                default:
                    logstatus = LogStatus.Pass;
                    break;
            }

            test.Log(logstatus, "Test ended with " + logstatus + stacktrace);

            extent.EndTest(test);
            extent.Flush();
        }
    }
}
