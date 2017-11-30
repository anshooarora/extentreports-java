package com.aventstack.extentreports.testng.components;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

public class ParallelTest2 extends ExtentTestNGReportBuilder {

	@Test(groups = "pass")
    public void passTest2() {
        Reporter.log("Started passTest");
        try { Thread.sleep(2000); } catch(Exception e) { }
        Assert.assertEquals(Status.PASS, Status.PASS);
    }
 
    @Test(expectedExceptions = SkipException.class, groups = "skip")
    public void skipTest2() {
        try { Thread.sleep(2000); } catch(Exception e) { }
        throw new SkipException("Intentionally skipped test.");
    }
    
    @Test(expectedExceptions = RuntimeException.class, groups = "fail")
    public void failTest2() {
        try { Thread.sleep(2000); } catch(Exception e) { }
        throw new RuntimeException("Intentionally failed test.");
    }
    
}
