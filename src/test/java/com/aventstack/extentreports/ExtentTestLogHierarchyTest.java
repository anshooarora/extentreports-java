package com.aventstack.extentreports;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ExtentTestLogHierarchyTest {
    private ExtentTest test() {
        return new ExtentReports().createTest("Test");
    }

    @Test
    public void overall() {
        ExtentTest test = test();
        test.pass("Pass");
        test.fail("Fail");
        test.info("Info");
        test.skip("Skip");
        test.warning("Warning");
        Assert.assertEquals(test.getStatus(), Status.FAIL);
    }

    @Test
    public void info() {
        ExtentTest test = test();
        test.info("Info");
        Assert.assertEquals(test.getStatus(), Status.PASS);
    }

    @Test
    public void passed() {
        ExtentTest test = test();
        test.pass("Pass");
        test.info("Info");
        Assert.assertEquals(test.getStatus(), Status.PASS);
    }

    @Test
    public void skipped() {
        ExtentTest test = test();
        test.pass("Pass");
        test.info("Info");
        test.skip("Skip");
        Assert.assertEquals(test.getStatus(), Status.SKIP);
    }

    @Test
    public void warning() {
        ExtentTest test = test();
        test.pass("Pass");
        test.info("Info");
        test.skip("Skip");
        test.warning("Warning");
        Assert.assertEquals(test.getStatus(), Status.WARNING);
    }
}
