namespace RelevantCodes.ExtentReports
{
    using System;
    using System.Collections.Generic;

    public class ExtentReports : IDisposable
    {
        private ReportInstance reportInstance;

        public ExtentReports(string FilePath, bool ReplaceExisting, DisplayOrder DisplayOrder = DisplayOrder.OldestFirst)
        {
            reportInstance = new ReportInstance();
            reportInstance.Initialize(FilePath, ReplaceExisting, DisplayOrder);
        }

        public ExtentTest StartTest(String testName)
        {
            return null;
        }

        public ExtentTest StartTest(String testName, String description)
        {
            return null;
        }

        public void EndTest(ExtentTest test)
        {
            
        }

        public ReportConfig Config() {
            return null;
        }

        public ExtentReports AddSystemInfo(Dictionary<string, string> info)
        {
            return this;
        }

        public ExtentReports AddSystemInfo(String param, String value)
        {
            return this;
        }

        public void Flush()
        {
            
        }
    }
}
