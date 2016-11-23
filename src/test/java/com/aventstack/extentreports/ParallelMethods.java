package com.aventstack.extentreports;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.common.ExtentManager;
import com.aventstack.extentreports.common.ExtentTestManager;
import com.aventstack.extentreports.reporter.ExtentXReporter;

public class ParallelMethods {
    
    private final String filePath = getOutputFolder() + getClass().getName() + ".html";
    private final String fileName = getClass().getSimpleName();
    
    private String getOutputFolder() {
        return "test-output/";
    }
    
    @BeforeClass
    public void beforeClass() {
        ExtentManager.createInstance(filePath);
        
        ExtentXReporter extentx = new ExtentXReporter("localhost");
        extentx.config().setProjectName("extentreports-pro");
        extentx.config().setReportName(fileName);
        extentx.config().setServerUrl("http://localhost:1337/");
        
        ExtentManager.getInstance().attachReporter(extentx);
        
        ExtentTestManager.setReporter(ExtentManager.getInstance());;
    }
    
    @AfterClass
    public void afterClass() {
        ExtentManager.getInstance().flush();
    }
    
    @Test
    public void method1(Method method) {
        ExtentTestManager.createTest(method.getName()).pass("pass");
        Assert.assertEquals(ExtentTestManager.getTest().getStatus(), Status.PASS);
    }
    
    @Test
    public void method2(Method method) {
        ExtentTestManager.createTest(method.getName()).fail("fail");
        Assert.assertEquals(ExtentTestManager.getTest().getStatus(), Status.FAIL);
    }
    
    @Test
    public void method3(Method method) {
        ExtentTestManager.createTest(method.getName()).error("error");
        Assert.assertEquals(ExtentTestManager.getTest().getStatus(), Status.ERROR);
    }
    
    @Test
    public void method4(Method method) {
        ExtentTestManager.createTest(method.getName()).warning("warning");
        Assert.assertEquals(ExtentTestManager.getTest().getStatus(), Status.WARNING);
    }
    
    @Test
    public void method5(Method method) {
        ExtentTestManager.createTest(method.getName()).info("info");
        Assert.assertEquals(ExtentTestManager.getTest().getStatus(), Status.PASS);
    }
    
    @Test
    public void method6(Method method) {
        ExtentTestManager.createTest(method.getName()).fatal("fatal");
        Assert.assertEquals(ExtentTestManager.getTest().getStatus(), Status.FATAL);
    }
    
    @Test
    public void method7(Method method) {
        ExtentTestManager.createTest(method.getName());
        Assert.assertEquals(ExtentTestManager.getTest().getStatus(), Status.PASS);
    }
    
}
