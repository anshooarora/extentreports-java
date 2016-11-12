package com.aventstack.extentreports.api;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class TestWithoutLogs extends Base {
    
    @Test
    public void verifyTestHasPassStatusIfNoLogsAdded(Method method) {
        ExtentTest test = extent.createTest(method.getName());
        
        Assert.assertEquals(test.getModel().getLogContext().size(), 0);
        Assert.assertEquals(test.getStatus(), Status.PASS);
    }
}
