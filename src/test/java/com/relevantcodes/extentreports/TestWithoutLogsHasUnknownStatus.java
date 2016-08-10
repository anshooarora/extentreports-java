package com.relevantcodes.extentreports;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.Status;
import com.relevantcodes.extentreports.reporter.ExtentHtmlReporter;
import com.relevantcodes.extentreports.utils.Reader;

public class TestWithoutLogsHasUnknownStatus extends Base {
    
    final String filePath = getOutputFolder() + getClass().getName() + ".html";
    final String testName = getClass().getName();
    
    ExtentTest test;
    
    @BeforeClass
    public void setup() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filePath);
        
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        
        test = extent.createTest(testName);
        extent.flush();
    }
    
    @Test
    public void testShowsUnknownStatusIfNoLogsAreAddedAPI() {
        Assert.assertEquals(test.getRunStatus(), Status.UNKNOWN);
    }
    
    @Test
    public void testShowsUnknownStatusIfNoLogsAreAddedView() {
        String html = Reader.readAllText(filePath);
        Document doc = Jsoup.parse(html);
        
        Element htmlTest = doc.select("#test-collection .test").first();
        String status = htmlTest.attr("status");
        
        Assert.assertEquals(status, Status.UNKNOWN.toString().toLowerCase());
    }
}
