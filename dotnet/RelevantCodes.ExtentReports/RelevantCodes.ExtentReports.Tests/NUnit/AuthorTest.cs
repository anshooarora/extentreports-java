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
    public class AuthorTest : ExtentBase
    {
        [Parallelizable(ParallelScope.Self)]
        [Test]
        public void SingleAuthor()
        {
            test = extent
                .StartTest("SingleAuthor")
                .AssignAuthor("Anshoo");
            test.Log(LogStatus.Pass, "Details");

            Assert.True(test.GetTest().AuthorList.Count == 1);
        }

        [Parallelizable(ParallelScope.Self)]
        [Test]
        public void ManyAuthors()
        {
            test = extent
                .StartTest("ManyAuthors")
                .AssignAuthor("Author1", "Author2", "Author3", "Author4");
            test.Log(LogStatus.Pass, "Details");

            Assert.True(test.GetTest().AuthorList.Count == 4);
        }
    }
}
