using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using NUnit.Framework;

namespace RelevantCodes.ExtentReports.Tests.NUnit
{
    [Parallelizable(ParallelScope.Fixtures)]
    [TestFixture]
    class ExceptionViewTest : ExtentBase
    {
        [Parallelizable(ParallelScope.Self)]
        [Test]
        public void EnablesExceptionView()
        {
            test = extent
                .StartTest("ExceptionTestEnablesExceptionView")
                .AssignCategory("Exception");
            test.Log(LogStatus.Pass, "Details");
            test.Log(LogStatus.Fail, new Exception("Exception"));

            Assert.True(test.GetTest().ExceptionList.Count == 1);
        }
    }
}
