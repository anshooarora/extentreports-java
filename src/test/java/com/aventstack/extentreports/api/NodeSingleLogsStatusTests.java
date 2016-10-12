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

public class NodeSingleLogsStatusTests extends Base {
    
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
        ExtentTest test = extent.createTest(method.getName());
        ExtentTest node = test.createNode("Child").pass("pass");

        Assert.assertEquals(node.getModel().getLevel(), 1);
        Assert.assertEquals(node.getModel().getLogContext().getAll().size(), 1);
        Assert.assertEquals(node.getStatus(), Status.PASS);
        Assert.assertEquals(test.getStatus(), Status.PASS);
    }
    
    @Test
    public void verifyIfTestHasStatusSkip(Method method) {
        ExtentTest test = extent.createTest(method.getName());
        ExtentTest node = test.createNode("Child").skip("skip");

        Assert.assertEquals(node.getModel().getLevel(), 1);
        Assert.assertEquals(node.getModel().getLogContext().getAll().size(), 1);
        Assert.assertEquals(node.getStatus(), Status.SKIP);
        Assert.assertEquals(test.getStatus(), Status.SKIP);
    }
    
    @Test
    public void verifyIfTestHasStatusWarning(Method method) {
        ExtentTest test = extent.createTest(method.getName());
        ExtentTest node = test.createNode("Child").warning("warning");

        Assert.assertEquals(node.getModel().getLevel(), 1);
        Assert.assertEquals(node.getModel().getLogContext().getAll().size(), 1);
        Assert.assertEquals(node.getStatus(), Status.WARNING);
        Assert.assertEquals(test.getStatus(), Status.WARNING);
    }
    
    @Test
    public void verifyIfTestHasStatusError(Method method) {
        ExtentTest test = extent.createTest(method.getName());
        ExtentTest node = test.createNode("Child").error("error");

        Assert.assertEquals(node.getModel().getLevel(), 1);
        Assert.assertEquals(node.getModel().getLogContext().getAll().size(), 1);
        Assert.assertEquals(node.getStatus(), Status.ERROR);
        Assert.assertEquals(test.getStatus(), Status.ERROR);
    }
    
    @Test
    public void verifyIfTestHasStatusFail(Method method) {
        ExtentTest test = extent.createTest(method.getName());
        ExtentTest node = test.createNode("Child").fail("fail");

        Assert.assertEquals(node.getModel().getLevel(), 1);
        Assert.assertEquals(node.getModel().getLogContext().getAll().size(), 1);
        Assert.assertEquals(node.getStatus(), Status.FAIL);
        Assert.assertEquals(test.getStatus(), Status.FAIL);
    }
    
    @Test
    public void verifyIfTestHasStatusFatal(Method method) {
        ExtentTest test = extent.createTest(method.getName());
        ExtentTest node = test.createNode("Child").fatal("fatal");

        Assert.assertEquals(node.getModel().getLevel(), 1);
        Assert.assertEquals(node.getModel().getLogContext().getAll().size(), 1);
        Assert.assertEquals(node.getStatus(), Status.FATAL);
        Assert.assertEquals(test.getStatus(), Status.FATAL);
    }
    
    @Test
    public void verifyIfTestHasStatusPassWithOnlyInfoSingle(Method method) {
        ExtentTest test = extent.createTest(method.getName());
        ExtentTest node = test.createNode("Child").info("info");

        Assert.assertEquals(node.getModel().getLevel(), 1);
        Assert.assertEquals(node.getModel().getLogContext().getAll().size(), 1);
        Assert.assertEquals(node.getStatus(), Status.PASS);
        Assert.assertEquals(test.getStatus(), Status.PASS);
    }
}
