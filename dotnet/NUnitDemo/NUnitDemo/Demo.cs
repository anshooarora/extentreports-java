namespace NUnitDemo
{
    using System;
    using NUnit.Framework;
    using RelevantCodes.ExtentReports;

    public class ExtentManager
    {
        private static ExtentReports extent;

        public static ExtentReports Instance
        {
            get
            {
                if (extent == null)
                {
                    extent = new ExtentReports(@"C:\Users\Anshoo\Documents\workspace\extent2examples\Extent.NET.html", true);
                }
                return extent;
            }
        }
    }

    [TestFixture]
    public class AssertDemo
    {
        private ExtentReports extent = ExtentManager.Instance;
        private ExtentTest test;

        [SetUp]
        public void Setup()
        {

        }

        [TearDown]
        public void Teardown()
        {
            extent.EndTest(test);
            extent.Flush();
        }

        [Test]
        public void AssertTestFail()
        {
            test = extent.StartTest("Assert Test - Failure", "With Description");
            test.Log(LogStatus.Info, "Info");

            try
            {
                Assert.AreEqual(true, false);
                test.Log(LogStatus.Pass, "Test passed");
            }
            catch (AssertionException ex)
            {
                test.Log(LogStatus.Fail, "<pre>" + ex.StackTrace + "</pre>");
            }
        }

        [Test]
        public void AssertTestPass()
        {
            test = extent.StartTest("Assert Test - Pass", "With Description");
            test.Log(LogStatus.Info, "Info");

            try
            {
                Assert.AreEqual(true, true);
                test.Log(LogStatus.Pass, "Test passed");
            }
            catch (AssertionException ex)
            {
                test.Log(LogStatus.Fail, ex.StackTrace);
            }
        }
    }

    [TestFixture]
    public class Demo
    {
        private ExtentReports extent = ExtentManager.Instance;
        private ExtentTest test;

        [SetUp]
        public void Setup()
        {
            
        }

        [TearDown]
        public void Teardown()
        {
            extent.EndTest(test);
            extent.Flush();
        }

        [Test]
        public void FirstTest()
        {
            test = extent.StartTest("First Test", "With Description");
            test.Log(LogStatus.Info, "Info");
        }

        [Test]
        public void SecondTest()
        {
            test = extent.StartTest("Second Test");
            test.Log(LogStatus.Error, "Error");
        }
    }
}
