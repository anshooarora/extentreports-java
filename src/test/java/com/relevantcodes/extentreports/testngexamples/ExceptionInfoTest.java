package com.relevantcodes.extentreports.testngexamples;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.TestExtentReports;
import com.relevantcodes.extentreports.TestReporter;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ExceptionInfoTest extends BaseExample {
    private final String filePath = "src/test/java/com/relevantcodes/extentreports/results/" + ExceptionInfoTest.class.getSimpleName() + ".html";
    private TestReporter testReporter;

    @BeforeClass
    public void beforeClass() {
        extent = new TestExtentReports(filePath);
        extent.addSystemInfo("Host Name", "Anshoo");
        testReporter = ((TestExtentReports)extent).getTestReporter();
    }

    @Test
    public void twoExceptionTest() {
        Throwable exception = new NumberFormatException("Two test Exception");

        String testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        ExtentTest test1 = extent.startTest(testName + 1);
        test1.log(LogStatus.FAIL, exception);
        extent.endTest(test1);

        ExtentTest test2 = extent.startTest(testName + 2);
        test2.log(LogStatus.FAIL, exception);
        extent.endTest(test2);

        Assert.assertEquals(testReporter.getExceptionTestMap().get(NumberFormatException.class.getName()).size(), 2);

    }

    @Test
    public void childTestExceptionTest() {
        Throwable exception = new IllegalStateException("Main/Child Exception");

        String testName = Thread.currentThread().getStackTrace()[1].getMethodName();
        ExtentTest mainTest = extent.startTest(testName);
        ExtentTest childTest = extent.startTest(testName + "Child");

        childTest.log(LogStatus.FAIL, exception);
        extent.endTest(childTest); // will fail without it
        mainTest.appendChild(childTest);
        extent.endTest(mainTest);

        Assert.assertEquals(testReporter.getExceptionTestMap().get(IllegalStateException.class.getName()).size(), 1);
    }
}
