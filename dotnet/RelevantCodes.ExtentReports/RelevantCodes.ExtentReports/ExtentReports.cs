using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using RelevantCodes.ExtentReports.Config;
using RelevantCodes.ExtentReports.Model;

namespace RelevantCodes.ExtentReports
{
    public class ExtentReports : Report
    {
        public ExtentReports(string FilePath, DisplayOrder Order = DisplayOrder.OldestFirst)
        {
            this.FilePath = FilePath;
            this.DisplayOrder = DisplayOrder;

            Attach(new HTMLReporter());
        }

        new public ExtentReports ConfigurationFromFile(string FilePath)
        {
            base.ConfigurationFromFile(FilePath);

            return this;
        }

        public ExtentTest StartTest(string Name, string Description = "")
        {
            if (TestList == null)
            {
                TestList = new List<ExtentTest>();
            }

            var test = new ExtentTest(Name, Description);
            TestList.Add(test);

            return test;
        }

        public void EndTest(ExtentTest Test)
        {
            Test.GetTest().Ended = true;

            AddTest(Test.GetTest());

            RemoveChildTests();
        }

        public ExtentReports AddSystemInfo(string Param, string Value)
        {
            SystemInfo[Param] = Value;

            return this;
        }

        public void AddTestRunnerOutput(string log)
        {
            TestRunnerLogs.Add(log);
        }

        new public void Flush()
        {
            base.Flush();
        }

        public void Close()
        {
            Flush();

            base.Terminate();
        }

        private void RemoveChildTests()
        {
            TestList = TestList.Where(x => !x.GetTest().ChildNode).ToList();
        }
    }
}
