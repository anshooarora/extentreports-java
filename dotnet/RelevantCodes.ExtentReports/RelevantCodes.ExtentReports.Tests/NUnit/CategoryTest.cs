using NUnit.Framework;

namespace RelevantCodes.ExtentReports.Tests.NUnit
{
    [Parallelizable(ParallelScope.Fixtures)]
    [TestFixture]
    public class CategoryTest : ExtentBase
    {
        public CategoryTest()
        {
            extent = new ExtentReports("CategoryTest.html", true);
        }

        [Parallelizable(ParallelScope.Self)]
        [Test]
        public void SingleCategory()
        {
            test = extent
                .StartTest("SingleCategory")
                .AssignCategory("Extent");
            test.Log(LogStatus.Pass, "Details");

            Assert.True(test.GetTest().CategoryList.Count == 1);
        }

        [Parallelizable(ParallelScope.Self)]
        [Test]
        public void ManyCategories()
        {
            test = extent
                .StartTest("ManyCategories")
                .AssignCategory("Extent", "Regression", "Smoke", "HelloWorld");
            test.Log(LogStatus.Pass, "Details");

            Assert.True(test.GetTest().CategoryList.Count == 4);
        }
    }
}
