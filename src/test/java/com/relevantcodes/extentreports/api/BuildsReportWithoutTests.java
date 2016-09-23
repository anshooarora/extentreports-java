package com.relevantcodes.extentreports.api;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.Base;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.reporter.ExtentHtmlReporter;

public class BuildsReportWithoutTests extends Base {
    final String filePath = getOutputFolder() + getClass().getName() + ".html";
    
    @Test
    public void buildReportWithoutTests() {
        File f = new File(filePath);
        if (f.exists())
            f.delete();
        
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filePath);
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        extent.flush();

        // if no tests are added and flush is called,
        // an empty report should be created
        Assert.assertEquals(f.exists(), false);
    }
}
