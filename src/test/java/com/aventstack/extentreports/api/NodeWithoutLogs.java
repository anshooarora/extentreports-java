package com.aventstack.extentreports.api;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class NodeWithoutLogs extends Base {

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
    public void verifyNodeAndParentHasPassStatusIfNoLogsAdded(Method method) {
        ExtentTest test = extent.createTest(method.getName());
        ExtentTest node = test.createNode("Child");
        
        Assert.assertEquals(node.getModel().getLevel(), 1);
        Assert.assertEquals(test.getModel().getLogContext().getAll().size(), 0);
        Assert.assertEquals(test.getStatus(), Status.UNKNOWN);
        Assert.assertEquals(node.getModel().getLogContext().getAll().size(), 0);
        Assert.assertEquals(node.getStatus(), Status.UNKNOWN);
    }
}
