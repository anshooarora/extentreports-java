using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using RelevantCodes.ExtentReports.Model;

namespace RelevantCodes.ExtentReports
{
    public class ExtentReports : Report
    {
        public ExtentReports(string FilePath, bool ReplaceExisting = true, DisplayOrder Order = DisplayOrder.OldestFirst, NetworkMode NetworkMode = NetworkMode.Online)
        {
            this.FilePath = FilePath;
            this.ReplaceExisting = ReplaceExisting;
            this.DisplayOrder = DisplayOrder;
            this.NetworkMode = NetworkMode;

            Attach(new HTMLReporter());
        }

        public ExtentReports StartReporter()
        {
            throw new NotImplementedException();
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
        }

        public ExtentReports AddSystemInfo(string Param, string Value)
        {
            return this;
        }

        public void Flush()
        {
            base.Flush();
        }

        public void Terminate()
        {
            Flush();

            base.Terminate();
        }
    }
}
