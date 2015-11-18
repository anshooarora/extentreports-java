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
    public class AuthorTest : ExtentBase
    {
        [Test]
        public void SingleAuthor()
        {
            test = extent
                .StartTest("SingleAuthor")
                .AssignAuthor("Anshoo");
            test.Log(LogStatus.Pass, "Details");

            Assert.True(test.GetTest().AuthorList.Count == 1);
        }

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
