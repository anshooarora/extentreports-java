package com.aventstack.extentreports;

import java.io.File;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentXReporter;

public abstract class Base {
    
    protected final String fileName = getClass().getSimpleName();
    protected final String htmlFilePath = getOutputFolder() + fileName + ".html";
    protected final String emailFilePath = getOutputFolder() + "email-" + fileName + ".html";
    protected ExtentReports extent;
    
    public Base() {
        File folder = new File(getOutputFolder());
        folder.mkdirs();
    }
    
    @BeforeClass
    public void baseSetup() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(htmlFilePath);
        
        ExtentXReporter extentx = new ExtentXReporter("localhost");
        extentx.config().setProjectName("extentreports-pro");
        extentx.config().setReportName(fileName);
        extentx.config().setServerUrl("http://localhost:1337/");
        
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }
    
    @AfterClass
    public void tearDown() {
        extent.flush();
    }
    
    protected String getOutputFolder() { 
        return "test-output/";
    }
    
}