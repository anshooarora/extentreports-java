package com.relevantcodes.extentreports.api;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.Base;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.reporter.ExtentHtmlReporter;

public class TestStartEndDateTimeTests extends Base {
    
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
    public void verifyStartTime(Method method) {
        Date init = Calendar.getInstance().getTime();
        ExtentTest test = extent.createTest(method.getName());
        Date end = Calendar.getInstance().getTime();
        
        Assert.assertTrue(test.getModel().getStartTime().getTime() >= init.getTime());
        Assert.assertTrue(test.getModel().getStartTime().getTime() <= end.getTime());
    }

    
    @Test
    public void verifyStartTimeWithLogs(Method method) {
        Date init = Calendar.getInstance().getTime();
        ExtentTest test = extent.createTest(method.getName()).pass("pass");
        Date end = Calendar.getInstance().getTime();
        
        Assert.assertTrue(test.getModel().getStartTime().getTime() >= init.getTime());
        Assert.assertTrue(test.getModel().getStartTime().getTime() <= end.getTime());
    }
    
    @Test
    public void verifyEndTimeWithLogs(Method method) {
        Date init = Calendar.getInstance().getTime();
        ExtentTest test = extent.createTest(method.getName()).pass("pass");
        Date end = Calendar.getInstance().getTime();
        
        Assert.assertTrue(test.getModel().getEndTime().getTime() >= init.getTime());
        Assert.assertTrue(test.getModel().getEndTime().getTime() <= end.getTime());
    }
    
}
