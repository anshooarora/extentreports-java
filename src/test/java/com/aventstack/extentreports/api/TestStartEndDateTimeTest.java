package com.aventstack.extentreports.api;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;
import com.aventstack.extentreports.ExtentTest;

public class TestStartEndDateTimeTest extends Base {
    
    @Test
    public void verifyStartTime(Method method) {
        Date init = Calendar.getInstance().getTime();
        ExtentTest test = extent.createTest(method.getName());
        Date end = Calendar.getInstance().getTime();
        
        Assert.assertTrue(test.getModel().getStartTime().getTime() >= init.getTime());
        Assert.assertTrue(test.getModel().getStartTime().getTime() <= end.getTime());
    }
    
    @Test
    public void verifyEndTime(Method method) {
        Date init = Calendar.getInstance().getTime();
        ExtentTest test = extent.createTest(method.getName());
        Date end = Calendar.getInstance().getTime();
        
        Assert.assertTrue(test.getModel().getEndTime().getTime() >= init.getTime());
        Assert.assertTrue(test.getModel().getEndTime().getTime() <= end.getTime());
    }
    
    @Test
    public void verifyTimeWithManualSetting(Method method) {
        extent.setReportUsesManualConfiguration(true);
        
        Date init = Calendar.getInstance().getTime();
        ExtentTest test = extent.createTest(method.getName());
        Date end = Calendar.getInstance().getTime();
        
        Assert.assertTrue(test.getModel().getEndTime().getTime() >= init.getTime());
        Assert.assertTrue(test.getModel().getEndTime().getTime() <= end.getTime());
    }
    
    @Test
    public void verifyEndTimeWithManualSetting(Method method) {
        extent.setReportUsesManualConfiguration(true);
        
        Date init = Calendar.getInstance().getTime();
        ExtentTest test = extent.createTest(method.getName());
        Date end = Calendar.getInstance().getTime();
        
        Assert.assertTrue(test.getModel().getEndTime().getTime() >= init.getTime());
        Assert.assertTrue(test.getModel().getEndTime().getTime() <= end.getTime());
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
