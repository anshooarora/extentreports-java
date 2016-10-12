package com.aventstack.extentreports;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.common.ExtentManager;
import com.aventstack.extentreports.common.ExtentTestManager;

public class ParallelClass2 extends ParallelClassesBase {

    @Test
    public void parallelClass1TestResultMustEqualWarning(Method method) {
        ExtentTestManager.createTest(method.getName()).info("Log from threadId: " + Thread.currentThread().getId());
        ExtentTestManager.getTest().warning("Log from threadId: " + Thread.currentThread().getId());
        ExtentManager.getInstance().flush();
        
        Assert.assertEquals(ExtentTestManager.getTest().getStatus(), Status.WARNING);
    }
    
    @Test
    public void parallelClass1TestResultMustEqualSkip(Method method) {
        ExtentTestManager.createTest(method.getName()).skip("Log from threadId: " + Thread.currentThread().getId());
        ExtentTestManager.getTest().pass("Log from threadId: " + Thread.currentThread().getId());
        ExtentManager.getInstance().flush();
        
        Assert.assertEquals(ExtentTestManager.getTest().getStatus(), Status.SKIP);
    }
    
}
