package com.aventstack.extentreports.view.extenthtml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.utils.Reader;

public class TestWithoutLogsHasPassStatus extends Base {
    
    final String testName = getClass().getName();
    
    ExtentTest test;
    
    @BeforeClass
    public void localSetup() {
        test = extent.createTest(testName);
        extent.flush();
    }
    
    @Test
    public void testShowsUnknownStatusIfNoLogsAreAddedView() {
        String html = Reader.readAllText(htmlFilePath);
        Document doc = Jsoup.parse(html);
        
        Element htmlTest = doc.select("#test-collection .test").first();
        String status = htmlTest.attr("status");
        
        Assert.assertEquals(status, Status.PASS.toString().toLowerCase());
    }
}
