namespace RelevantCodes.ExtentReports
{
    using System;
    using System.Collections.Generic;

    public class ExtentReports// : IDisposable
    {
        private ReportInstance reportInstance;
        private SystemInfo systemInfo;
        private ReportConfig config;

        public ExtentReports(string FilePath, bool ReplaceExisting, DisplayOrder DisplayOrder = DisplayOrder.OldestFirst)
        {
            reportInstance = new ReportInstance();
            reportInstance.Initialize(FilePath, ReplaceExisting, DisplayOrder);

            systemInfo = new SystemInfo();
        }

        public ExtentTest StartTest(string TestName)
        {
            return new ExtentTest(TestName, "");
        }

        public ExtentTest StartTest(string TestName, string Description)
        {
            return new ExtentTest(TestName, Description);
        }

        public void EndTest(ExtentTest Test)
        {
            reportInstance.AddTest(Test.GetTest());
        }

        public ReportConfig Config()
        {
            if (config == null)
            {
                config = new ReportConfig();
            }

            return config;
        }

        public ExtentReports AddSystemInfo(Dictionary<string, string> SystemInfo)
        {
            systemInfo.SetInfo(SystemInfo);

            return this;
        }

        public ExtentReports AddSystemInfo(string Param, string Value)
        {
            systemInfo.SetInfo(Param, Value);

            return this;
        }

        public void Flush()
        {
            reportInstance.Terminate(systemInfo);
        }
    }
}
