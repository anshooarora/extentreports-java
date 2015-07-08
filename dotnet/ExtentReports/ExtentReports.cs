namespace RelevantCodes.ExtentReports
{
    using System;
    using System.Collections.Generic;

    public class ExtentReports// : IDisposable
    {
        private List<ExtentTest> testList;
        private ReportConfig config; 
        private ReportInstance reportInstance;
        private SystemInfo systemInfo;
        
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
            if (testList == null)
            {
                testList = new List<ExtentTest>();
            }

            var test = new ExtentTest(TestName, Description);
            testList.Add(test);

            return test;
        }

        public void EndTest(ExtentTest Test)
        {
            Test.GetTest().HasEnded = true;

            reportInstance.AddTest(Test.GetTest());
        }

        public ReportConfig Config()
        {
            if (config == null)
            {
                if (reportInstance == null)
                    throw new Exception("Cannot apply config before ExtentReports is initialized");

                config = new ReportConfig(reportInstance);
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
            reportInstance.WriteAllResources(testList, systemInfo);

            systemInfo.Clear();
        }

        public void Close()
        {
            testList.Clear();
        }
    }
}
