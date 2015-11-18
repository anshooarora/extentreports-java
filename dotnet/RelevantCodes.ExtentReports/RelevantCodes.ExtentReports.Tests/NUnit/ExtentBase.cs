using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using NUnit.Framework;

using RelevantCodes.ExtentReports.Tests.Common;

namespace RelevantCodes.ExtentReports.Tests.NUnit
{
    public abstract class ExtentBase
    {
        protected ExtentReports extent;
        protected ExtentTest test;

        [TestFixtureSetUp]
        public void FixtureInit()
        {
            extent = ExtentManager.Instance;
            extent.LoadConfig(@"C:\Users\Anshoo\Desktop\extentreports-net-v2.40.0-beta-2\ExtentReports.NET\extent-config.xml");
        }

        [TearDown]
        public void TearDown()
        {
            extent.EndTest(test);
            extent.Flush();
        }
    }
}
