package com.aventstack.extentreports;

import java.util.Calendar;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

public class NaturalConfTest {
    @Test
    public void useNaturalConfReport() throws InterruptedException {
        ExtentReports extent = new ExtentReports();
        extent.setReportUsesManualConfiguration(true);
        ExtentTest test = extent.createTest("Test").pass("init");
        Thread.sleep(500);
        test.pass("complete");
        // must flush to determine time for report
        extent.flush();
        Assert.assertTrue(extent.getReport().timeTaken() < 5);
    }

    @Test
    public void useNaturalConfReportWithTimeChanged() throws InterruptedException {
        ExtentReports extent = new ExtentReports();
        extent.setReportUsesManualConfiguration(true);
        ExtentTest test = extent.createTest("Test").pass("init");
        test.getModel().setEndTime(new Date(Calendar.getInstance().getTime().getTime() + 5000));
        test.pass("complete");
        // must flush to determine time for report
        extent.flush();
        Assert.assertTrue(extent.getReport().timeTaken() >= 5000);
    }

    @Test
    public void useNaturalConfTest() throws InterruptedException {
        ExtentReports extent = new ExtentReports();
        extent.setReportUsesManualConfiguration(true);
        ExtentTest test = extent.createTest("Test").pass("init");
        Thread.sleep(500);
        test.pass("complete");
        Assert.assertTrue(test.getModel().timeTaken() < 5);
    }

    @Test
    public void useNaturalConfTestWithTimeChanged() throws InterruptedException {
        ExtentReports extent = new ExtentReports();
        extent.setReportUsesManualConfiguration(true);
        ExtentTest test = extent.createTest("Test").pass("init");
        test.getModel().setEndTime(new Date(Calendar.getInstance().getTime().getTime() + 5000));
        test.pass("complete");
        Assert.assertTrue(test.getModel().timeTaken() >= 5000);
    }

    @Test
    public void useNaturalConfTestWithNodes() throws InterruptedException {
        ExtentReports extent = new ExtentReports();
        extent.setReportUsesManualConfiguration(true);
        ExtentTest test = extent.createTest("Test");
        ExtentTest child = test.createNode("Node").pass("init");
        Thread.sleep(500);
        child.pass("complete");
        Assert.assertTrue(test.getModel().timeTaken() < 5);
        Assert.assertTrue(child.getModel().timeTaken() < 5);
    }
}
