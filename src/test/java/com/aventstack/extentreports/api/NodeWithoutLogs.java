package com.aventstack.extentreports.api;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class NodeWithoutLogs extends Base {
    
    @Test
    public void verifyNodeAndParentHasPassStatusIfNoLogsAdded(Method method) {
        ExtentTest test = extent.createTest(method.getName());
        ExtentTest node = test.createNode("Child");
        
        Assert.assertEquals(node.getModel().getLevel(), 1);
        Assert.assertEquals(test.getModel().getLogContext().size(), 0);
        Assert.assertEquals(test.getStatus(), Status.PASS);
        Assert.assertEquals(node.getModel().getLogContext().size(), 0);
        Assert.assertEquals(node.getStatus(), Status.PASS);
    }
}
