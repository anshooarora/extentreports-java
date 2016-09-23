package com.relevantcodes.extentreports.api;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.Base;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.Status;
import com.relevantcodes.extentreports.reporter.ExtentHtmlReporter;

public class TestSingleLogsStatusTests extends Base {

    private final String filePath = getOutputFolder() + getClass().getName() + ".html";
    
    private ExtentReports extent;
    
    @BeforeClass
    public void setup() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filePath);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }
    
    @AfterClass
    public void tearDown() {
        extent.flush();
    }
    
    @Test
    public void verifyIfTestHasStatusPass(Method method) {
        ExtentTest test = extent.createTest(method.getName()).pass("pass");

        Assert.assertEquals(test.getModel().getLogContext().getAll().size(), 1);
        Assert.assertEquals(test.getStatus(), Status.PASS);
    }
    
    @Test
    public void verifyIfTestHasStatusSkip(Method method) {
        ExtentTest test = extent.createTest(method.getName()).skip("skip");

        Assert.assertEquals(test.getModel().getLogContext().getAll().size(), 1);
        Assert.assertEquals(test.getStatus(), Status.SKIP);
    }
    
    @Test
    public void verifyIfTestHasStatusWarning(Method method) {
        ExtentTest test = extent.createTest(method.getName()).warning("warning");

        Assert.assertEquals(test.getModel().getLogContext().getAll().size(), 1);
        Assert.assertEquals(test.getStatus(), Status.WARNING);
    }
    
    @Test
    public void verifyIfTestHasStatusError(Method method) {
        ExtentTest test = extent.createTest(method.getName()).error("error");

        Assert.assertEquals(test.getModel().getLogContext().getAll().size(), 1);
        Assert.assertEquals(test.getStatus(), Status.ERROR);
    }
    
    @Test
    public void verifyIfTestHasStatusFail(Method method) {
        ExtentTest test = extent.createTest(method.getName()).fail("fail");

        Assert.assertEquals(test.getModel().getLogContext().getAll().size(), 1);
        Assert.assertEquals(test.getStatus(), Status.FAIL);
    }
    
    @Test
    public void verifyIfTestHasStatusFatal(Method method) {
        ExtentTest test = extent.createTest(method.getName()).fatal("fatal");

        Assert.assertEquals(test.getModel().getLogContext().getAll().size(), 1);
        Assert.assertEquals(test.getStatus(), Status.FATAL);
    }
    
    @Test
    public void verifyIfTestHasStatusPassWithOnlyInfoSingle(Method method) {
        ExtentTest test = extent.createTest(method.getName()).info("info");

        Assert.assertEquals(test.getModel().getLogContext().getAll().size(), 1);
        Assert.assertEquals(test.getStatus(), Status.PASS);
    }
}
