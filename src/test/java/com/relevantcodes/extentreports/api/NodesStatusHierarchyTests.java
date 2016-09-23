package com.relevantcodes.extentreports.api;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.Base;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.Status;
import com.relevantcodes.extentreports.reporter.ExtentHtmlReporter;

public class NodesStatusHierarchyTests extends Base {

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
    public void verifyPassHasHigherPriorityThanInfoLevelsShallow(Method method) {
        ExtentTest parent = extent.createTest(method.getName());
        ExtentTest child = parent.createNode("Child");
        child.info("info");
        child.pass("pass");
        
        Assert.assertEquals(child.getModel().getLevel(), 1);
        Assert.assertEquals(parent.getStatus(), Status.PASS);
        Assert.assertEquals(child.getStatus(), Status.PASS);
    }
    
    @Test
    public void verifyPassHasHigherPriorityThanInfoLevelsDeep(Method method) {
        ExtentTest parent = extent.createTest(method.getName());
        ExtentTest child = parent.createNode("Child");
        ExtentTest grandchild = child.createNode("GrandChild");
        grandchild.info("info");
        grandchild.pass("pass");
        
        Assert.assertEquals(child.getModel().getLevel(), 1);
        Assert.assertEquals(grandchild.getModel().getLevel(), 2);
        Assert.assertEquals(parent.getStatus(), Status.PASS);
        Assert.assertEquals(child.getStatus(), Status.PASS);
        Assert.assertEquals(grandchild.getStatus(), Status.PASS);
    }
    
    @Test
    public void verifySkipHasHigherPriorityThanPassLevelsShallow(Method method) {
        ExtentTest parent = extent.createTest(method.getName());
        ExtentTest child = parent.createNode("Child");
        child.pass("pass");
        child.skip("skip");
        
        Assert.assertEquals(child.getModel().getLevel(), 1);
        Assert.assertEquals(parent.getStatus(), Status.SKIP);
        Assert.assertEquals(child.getStatus(), Status.SKIP);
    }
    
    @Test
    public void verifySkipHasHigherPriorityThanPassLevelsDeep(Method method) {
        ExtentTest parent = extent.createTest(method.getName());
        ExtentTest child = parent.createNode("Child");
        ExtentTest grandchild = child.createNode("GrandChild");
        grandchild.pass("pass");
        grandchild.skip("skip");
        
        Assert.assertEquals(child.getModel().getLevel(), 1);
        Assert.assertEquals(grandchild.getModel().getLevel(), 2);
        Assert.assertEquals(parent.getStatus(), Status.SKIP);
        Assert.assertEquals(child.getStatus(), Status.SKIP);
        Assert.assertEquals(grandchild.getStatus(), Status.SKIP);
    }
    
    @Test
    public void verifyWarningHasHigherPriorityThanSkipLevelsShallow(Method method) {
        ExtentTest parent = extent.createTest(method.getName());
        ExtentTest child = parent.createNode("Child");
        child.skip("skip");
        child.warning("warning");
        
        Assert.assertEquals(child.getModel().getLevel(), 1);
        Assert.assertEquals(parent.getStatus(), Status.WARNING);
        Assert.assertEquals(child.getStatus(), Status.WARNING);
    }
    
    @Test
    public void verifyWarningHasHigherPriorityThanSkipLevelsDeep(Method method) {
        ExtentTest parent = extent.createTest(method.getName());
        ExtentTest child = parent.createNode("Child");
        ExtentTest grandchild = child.createNode("GrandChild");
        grandchild.skip("skip");
        grandchild.warning("warning");
        
        Assert.assertEquals(child.getModel().getLevel(), 1);
        Assert.assertEquals(grandchild.getModel().getLevel(), 2);
        Assert.assertEquals(parent.getStatus(), Status.WARNING);
        Assert.assertEquals(child.getStatus(), Status.WARNING);
        Assert.assertEquals(grandchild.getStatus(), Status.WARNING);
    }
    
    @Test
    public void verifyErrorHasHigherPriorityThanWarningLevelsShallow(Method method) {
        ExtentTest parent = extent.createTest(method.getName());
        ExtentTest child = parent.createNode("Child");
        child.warning("warning");
        child.error("error");
        
        Assert.assertEquals(child.getModel().getLevel(), 1);
        Assert.assertEquals(parent.getStatus(), Status.ERROR);
        Assert.assertEquals(child.getStatus(), Status.ERROR);
    }
    
    @Test
    public void verifyErrorHasHigherPriorityThanWarningLevelsDeep(Method method) {
        ExtentTest parent = extent.createTest(method.getName());
        ExtentTest child = parent.createNode("Child");
        ExtentTest grandchild = child.createNode("GrandChild");
        grandchild.warning("warning");
        grandchild.error("error");
        
        Assert.assertEquals(child.getModel().getLevel(), 1);
        Assert.assertEquals(grandchild.getModel().getLevel(), 2);
        Assert.assertEquals(parent.getStatus(), Status.ERROR);
        Assert.assertEquals(child.getStatus(), Status.ERROR);
        Assert.assertEquals(grandchild.getStatus(), Status.ERROR);
    }
    
    @Test
    public void verifFailHasHigherPriorityThanErrorLevelsShallow(Method method) {
        ExtentTest parent = extent.createTest(method.getName());
        ExtentTest child = parent.createNode("Child");
        child.error("error");
        child.fail("fail");
        
        Assert.assertEquals(child.getModel().getLevel(), 1);
        Assert.assertEquals(parent.getStatus(), Status.FAIL);
        Assert.assertEquals(child.getStatus(), Status.FAIL);
    }
    
    @Test
    public void verifFailHasHigherPriorityThanErrorLevelsDeep(Method method) {
        ExtentTest parent = extent.createTest(method.getName());
        ExtentTest child = parent.createNode("Child");
        ExtentTest grandchild = child.createNode("GrandChild");
        grandchild.error("error");
        grandchild.fail("fail");
        
        Assert.assertEquals(child.getModel().getLevel(), 1);
        Assert.assertEquals(grandchild.getModel().getLevel(), 2);
        Assert.assertEquals(parent.getStatus(), Status.FAIL);
        Assert.assertEquals(child.getStatus(), Status.FAIL);
        Assert.assertEquals(grandchild.getStatus(), Status.FAIL);
    }
    
    @Test
    public void verifFatalHasHigherPriorityThanFailLevelsShallow(Method method) {
        ExtentTest parent = extent.createTest(method.getName());
        ExtentTest child = parent.createNode("Child");
        child.fail("fail");
        child.fatal("fatal");
        
        Assert.assertEquals(child.getModel().getLevel(), 1);
        Assert.assertEquals(parent.getStatus(), Status.FATAL);
        Assert.assertEquals(child.getStatus(), Status.FATAL);
    }
    
    @Test
    public void verifFatalHasHigherPriorityThanFailLevelsDeep(Method method) {
        ExtentTest parent = extent.createTest(method.getName());
        ExtentTest child = parent.createNode("Child");
        ExtentTest grandchild = child.createNode("GrandChild");
        grandchild.fail("fail");
        grandchild.fatal("fatal");
        
        Assert.assertEquals(child.getModel().getLevel(), 1);
        Assert.assertEquals(grandchild.getModel().getLevel(), 2);
        Assert.assertEquals(parent.getStatus(), Status.FATAL);
        Assert.assertEquals(child.getStatus(), Status.FATAL);
        Assert.assertEquals(grandchild.getStatus(), Status.FATAL);
    }
}
