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
    [TestFixture]
    public class SingleLogTests : ExtentBase
    {
        [Test]
        public void Pass()
        {
            test = extent.StartTest("Pass");
            test.Log(LogStatus.Pass, "Current status: " + test.GetCurrentStatus());

            Assert.True(test.GetCurrentStatus() == LogStatus.Pass);
        }

        [Test]
        public void Info()
        {
            test = extent.StartTest("Info");
            test.Log(LogStatus.Info, "Current status: " + test.GetCurrentStatus());

            Assert.True(test.GetCurrentStatus() == LogStatus.Pass);
        }

        [Test]
        public void Warning()
        {
            test = extent.StartTest("Warning");
            test.Log(LogStatus.Warning, "Current status: " + test.GetCurrentStatus());

            Assert.True(test.GetCurrentStatus() == LogStatus.Warning);
        }

        [Test]
        public void Error()
        {
            test = extent.StartTest("Error");
            test.Log(LogStatus.Error, "Current status: " + test.GetCurrentStatus());

            Assert.True(test.GetCurrentStatus() == LogStatus.Error);
        }

        [Test]
        public void Fail()
        {
            test = extent.StartTest("Fail");
            test.Log(LogStatus.Fail, "Current status: " + test.GetCurrentStatus());

            Assert.True(test.GetCurrentStatus() == LogStatus.Fail);
        }

        [Test]
        public void Fatal()
        {
            test = extent.StartTest("Fatal");
            test.Log(LogStatus.Fatal, "Current status: " + test.GetCurrentStatus());

            Assert.True(test.GetCurrentStatus() == LogStatus.Fatal);
        }

        [Test]
        public void Skip()
        {
            test = extent.StartTest("Skip");
            test.Log(LogStatus.Skip, "Current status: " + test.GetCurrentStatus());

            Assert.True(test.GetCurrentStatus() == LogStatus.Skip);
        }

        [Test]
        public void Unknown()
        {
            test = extent.StartTest("Unknown");
            test.Log(LogStatus.Unknown, "Current status: " + test.GetCurrentStatus());

            Assert.True(test.GetCurrentStatus() == LogStatus.Unknown);
        }
    }
}
