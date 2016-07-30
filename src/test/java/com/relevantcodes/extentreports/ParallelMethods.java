package com.relevantcodes.extentreports;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.Status;
import com.relevantcodes.extentreports.common.ExtentManager;
import com.relevantcodes.extentreports.common.ExtentTestManager;

public class ParallelMethods extends Base {
    
    final String filePath = getOutputFolder() + getClass().getName() + ".html";
    
    @BeforeClass
    public void beforeClass() {
        ExtentManager.createInstance(filePath);
    }
    
    @AfterClass
    public void afterClass() {
        ExtentManager.getInstance().collectRunInfo();
    }
    
    @Test
    public void method1(Method method) {
        ExtentTestManager.createTest(method.getName()).pass("pass");
        Assert.assertEquals(ExtentTestManager.getTest().getRunStatus(), Status.PASS);
    }
    
    @Test
    public void method2(Method method) {
        ExtentTestManager.createTest(method.getName()).fail("fail");
        Assert.assertEquals(ExtentTestManager.getTest().getRunStatus(), Status.FAIL);
    }
    
    @Test
    public void method3(Method method) {
        ExtentTestManager.createTest(method.getName()).error("error");
        Assert.assertEquals(ExtentTestManager.getTest().getRunStatus(), Status.ERROR);
    }
    
    @Test
    public void method4(Method method) {
        ExtentTestManager.createTest(method.getName()).warning("warning");
        Assert.assertEquals(ExtentTestManager.getTest().getRunStatus(), Status.WARNING);
    }
    
    @Test
    public void method5(Method method) {
        ExtentTestManager.createTest(method.getName()).info("info");
        Assert.assertEquals(ExtentTestManager.getTest().getRunStatus(), Status.PASS);
    }
    
    @Test
    public void method6(Method method) {
        ExtentTestManager.createTest(method.getName()).fatal("fatal");
        Assert.assertEquals(ExtentTestManager.getTest().getRunStatus(), Status.FATAL);
    }
    
    @Test
    public void method7(Method method) {
        ExtentTestManager.createTest(method.getName());
        Assert.assertEquals(ExtentTestManager.getTest().getRunStatus(), Status.UNKNOWN);
    }
    
}
