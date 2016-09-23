package com.relevantcodes.extentreports.listenertests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.Status;
import com.relevantcodes.extentreports.common.ExtentTestNGListener;

@Listeners(ExtentTestNGListener.class)
public class ListenerTests {

    @Test(groups = "pass")
    public void passTest() {
        Reporter.log("Started passTest");
        try { Thread.sleep(2000); } catch(Exception e) { }
        Assert.assertEquals(Status.PASS, Status.PASS);
    }
 
    @Test(expectedExceptions = SkipException.class, groups = "skip")
    public void skipTest() {
        try { Thread.sleep(2000); } catch(Exception e) { }
        throw new SkipException("Intentionally skipped test.");
    }
    
    @Test(expectedExceptions = RuntimeException.class, groups = "fail")
    public void failTest() {
        try { Thread.sleep(2000); } catch(Exception e) { }
        throw new RuntimeException("Intentionally failed test.");
    }
    
}
