package com.aventstack.extentreports.view.extenthtml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.utils.Reader;

public class TestMarkupTest extends Base {
    
    final String filePath = getOutputFolder() + getClass().getName() + ".html";
    final String testName = getClass().getName();
    
    ExtentReports extent;
    ExtentTest test;    
    Elements htmlTest;
    
    @BeforeSuite
    public void beforeSuite() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filePath);
        
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }
    
    @BeforeClass
    public void beforeClass() {
        test = extent.createTest(testName);
        test.pass(testName);

        extent.flush();
        
        String html = Reader.readAllText(filePath);
        Document doc = Jsoup.parse(html);
        
        htmlTest = doc.select("#test-collection .test");
    }

    @Test
    public void validatesTestsCountView() {        
        int testCount = htmlTest.size();       
        Assert.assertEquals(1, testCount);
    }

    @Test
    public void validatesTestIDView() {
        String testId = htmlTest.attr("test-id");
        Assert.assertEquals(String.valueOf(test.getModel().getID()), testId);
    }
    
    @Test
    public void validatesTestStatusView() {
        String status = htmlTest.attr("status");
        Assert.assertEquals(String.valueOf(test.getStatus()).toLowerCase(), status);
    }
    
    @Test
    public void validatesIfBdd() {
        boolean isBdd = Boolean.valueOf(htmlTest.attr("bdd"));
        Assert.assertEquals(false, isBdd);
    }
    
    @Test
    public void validatesTestNameView() {
        String testName = htmlTest.select(".test-name").first().html();
        Assert.assertEquals(this.testName, testName);    
    }
    
    @Test
    public void validatesLogSizeView() {
        Elements log = htmlTest.select(".log");
        int logSize = log.size();
        Assert.assertEquals(1, logSize);
    }
    
    @Test
    public void validatesLogStatusView() {
        Elements log = htmlTest.select(".log");
        String logStatus = log.first().select(".status").first().className();
        Status logStatusStatus = test.getModel().getLogContext().get(0).getStatus();
        Assert.assertTrue(logStatus.indexOf(logStatusStatus.toString().toLowerCase()) > 0); 
    }
    
}
