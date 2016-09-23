package com.relevantcodes.extentreports;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.Status;
import com.relevantcodes.extentreports.common.ExtentTestManager;

public class ParallelClass1 extends ParallelClassesBase {
    
    @Test
    public void parallelClass1TestResultMustEqualPass(Method method) {
        ExtentTestManager.createTest(method.getName()).info("Log from threadId: " + Thread.currentThread().getId());
        ExtentTestManager.getTest().pass("Log from threadId: " + Thread.currentThread().getId());
        
        Assert.assertEquals(ExtentTestManager.getTest().getStatus(), Status.PASS);
    }
    
    @Test
    public void parallelClass1TestResultMustEqualFail(Method method) {
        ExtentTestManager.createTest(method.getName()).error("Log from threadId: " + Thread.currentThread().getId());
        ExtentTestManager.getTest().fail("Log from threadId: " + Thread.currentThread().getId());
        
        Assert.assertEquals(ExtentTestManager.getTest().getStatus(), Status.FAIL);
    }
    
}
