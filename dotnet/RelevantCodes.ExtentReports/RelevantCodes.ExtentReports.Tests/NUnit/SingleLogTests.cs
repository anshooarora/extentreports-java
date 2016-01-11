using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using NUnit.Framework;

using RelevantCodes.ExtentReports;
using RelevantCodes.ExtentReports.Tests.Common;

namespace RelevantCodes.ExtentReports.Tests.NUnit
{
    [Parallelizable(ParallelScope.Fixtures)]
    [TestFixture]
    public class SingleLogTests : ExtentBase
    {
        [Parallelizable(ParallelScope.Self)]
        [Test]
        public void Pass()
        {
            test = extent.StartTest("Pass");
            test.Log(LogStatus.Pass, "Details");
            
            Assert.True(test.GetCurrentStatus() == LogStatus.Pass);
        }

        [Parallelizable(ParallelScope.Self)]
        [Test]
        public void Info()
        {
            test = extent.StartTest("Info");
            test.Log(LogStatus.Info, "Details");
            
            Assert.True(test.GetCurrentStatus() == LogStatus.Pass);
        }

        [Parallelizable(ParallelScope.Self)]
        [Test]
        public void Warning()
        {
            test = extent.StartTest("Warning");
            test.Log(LogStatus.Warning, "Details");
            
            Assert.True(test.GetCurrentStatus() == LogStatus.Warning);
        }

        [Parallelizable(ParallelScope.Self)]
        [Test]
        public void Error()
        {
            test = extent.StartTest("Error");
            test.Log(LogStatus.Error, "Details");
            
            Assert.True(test.GetCurrentStatus() == LogStatus.Error);
        }

        [Parallelizable(ParallelScope.Self)]
        [Test]
        public void Fail()
        {
            test = extent.StartTest("Fail");
            test.Log(LogStatus.Fail, "Details");
            
            Assert.True(test.GetCurrentStatus() == LogStatus.Fail);
        }

        [Parallelizable(ParallelScope.Self)]
        [Test]
        public void Fatal()
        {
            test = extent.StartTest("Fatal");
            test.Log(LogStatus.Fatal, "Details");
            
            Assert.True(test.GetCurrentStatus() == LogStatus.Fatal);
        }

        [Parallelizable(ParallelScope.Self)]
        [Test]
        public void Skip()
        {
            test = extent.StartTest("Skip");
            test.Log(LogStatus.Skip, "Details");
            
            Assert.True(test.GetCurrentStatus() == LogStatus.Skip);
        }

        [Parallelizable(ParallelScope.Self)]
        [Test]
        public void Unknown()
        {
            test = extent.StartTest("Unknown");
            test.Log(LogStatus.Unknown, "Details");
            
            Assert.True(test.GetCurrentStatus() == LogStatus.Unknown);
        }
    }
}
