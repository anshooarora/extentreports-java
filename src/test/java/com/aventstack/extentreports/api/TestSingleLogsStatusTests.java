package com.aventstack.extentreports.api;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class TestSingleLogsStatusTests extends Base {

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
