package com.relevantcodes.extentreports;

import java.util.HashMap;

public class ExtentReports {
    private ReportInstance reportInstance;
    private SystemInfo systemInfo;
    private ReportInstance.ReportConfig reportConfig;
    
    public ExtentReports(String filePath, Boolean replace, DisplayOrder displayOrder) {
        reportInstance = new ReportInstance();
        reportInstance.initialize(filePath, replace, displayOrder);
        
        systemInfo = new SystemInfo();
    }
    
    public ExtentReports(String filePath, Boolean replace) {
        this(filePath, replace, DisplayOrder.OLDEST_FIRST);
    }
    
    public ExtentTest startTest(String testName) {
        return new ExtentTest(testName, "");
    }
    
    public ExtentTest startTest(String testName, String description) {
        return new ExtentTest(testName, description);
    }
    
    public void endTest(ExtentTest test) {
        reportInstance.addTest(test.getTest());
    }
    
    public ReportInstance.ReportConfig config() {
        if (reportConfig == null) {
            reportConfig = reportInstance.new ReportConfig();
        }
        
        return reportConfig;
    }
    
    public ExtentReports addSystemInfo(HashMap<String, String> info) {
        systemInfo.setInfo(info);
        
        return this;
    }

    public ExtentReports addSystemInfo(String param, String value) {
        systemInfo.setInfo(param, value);
        
        return this;
    }
    
    public void flush() {
        reportInstance.terminate(systemInfo);
    }
}
