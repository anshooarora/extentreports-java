using NUnit.Framework;

namespace RelevantCodes.ExtentReports.Tests.NUnit
{
    [Parallelizable(ParallelScope.Fixtures)]
    [TestFixture]
    public class CategoryAndAuthorTest : ExtentBase
    {
        public CategoryAndAuthorTest()
        {
            extent = new ExtentReports("CategoryAndAuthorTest.html", true);
        }

        [Parallelizable(ParallelScope.Self)]
        [Test]
        public void SingleCategorySingleAuthor()
        {
            test = extent
                .StartTest("SingleCategorySingleAuthor")
                .AssignCategory("Extent")
                .AssignAuthor("Anshoo");
            test.Log(LogStatus.Pass, "Details");

            Assert.True(test.GetTest().CategoryList.Count == 1);
            Assert.True(test.GetTest().AuthorList.Count == 1);
        }

        [Parallelizable(ParallelScope.Self)]
        [Test]
        public void ManyCategoriesManyAuthors()
        {
            test = extent
                .StartTest("ManyCategoriesManyAuthors")
                .AssignCategory("Extent", "Regression", "Smoke", "HelloWorld")
                .AssignAuthor("Author1", "Author2", "Author3", "Author4");
            test.Log(LogStatus.Pass, "Details");

            Assert.True(test.GetTest().CategoryList.Count == 4);
            Assert.True(test.GetTest().AuthorList.Count == 4);
        }

        [Test]
        public void SingleCategoryManyAuthors()
        {
            test = extent
                .StartTest("SingleCategoryManyAuthors")
                .AssignCategory("Extent")
                .AssignAuthor("Author1", "Author2", "Author3", "Author4");
            test.Log(LogStatus.Pass, "Details");

            Assert.True(test.GetTest().CategoryList.Count == 1);
            Assert.True(test.GetTest().AuthorList.Count == 4);
        }

        [Test]
        public void ManyCategoriesSingleAuthor()
        {
            test = extent
                .StartTest("ManyCategoriesSingleAuthor")
                .AssignCategory("Extent", "Regression", "Smoke", "HelloWorld")
                .AssignAuthor("Anshoo");
            test.Log(LogStatus.Pass, "Details");

            Assert.True(test.GetTest().CategoryList.Count == 4);
            Assert.True(test.GetTest().AuthorList.Count == 1);
        }


    }
}
