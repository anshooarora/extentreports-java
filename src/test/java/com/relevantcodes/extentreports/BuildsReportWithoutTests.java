package com.relevantcodes.extentreports;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.reporter.ExtentHtmlReporter;

public class BuildsReportWithoutTests extends Base {
    final String filePath = getOutputFolder() + getClass().getName() + ".html";
    
    @Test
    public void buildReportWithoutTests() {       
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filePath);
        
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        
        extent.collectRunInfo();
        
        File f = new File(filePath);
        
        // if no tests are added and flush is called,
        // no report should be created
        Assert.assertEquals(f.exists(), false);
    }
}
