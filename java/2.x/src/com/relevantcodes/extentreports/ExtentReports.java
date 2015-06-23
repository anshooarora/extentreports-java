/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

import java.util.HashMap;

public class ExtentReports {
    private ReportInstance reportInstance;
    private SystemInfo systemInfo;
    private ReportInstance.ReportConfig reportConfig;
    
    /**
     * 
     * @param filePath
     * @param replace
     * @param displayOrder
     */
    public ExtentReports(String filePath, Boolean replace, DisplayOrder displayOrder) {
        reportInstance = new ReportInstance();
        reportInstance.initialize(filePath, replace, displayOrder);
        
        systemInfo = new SystemInfo();
    }
    
    /**
     * 
     * @param filePath
     * @param replace
     */
    public ExtentReports(String filePath, Boolean replace) {
        this(filePath, replace, DisplayOrder.OLDEST_FIRST);
    }
    
    /**
     * 
     * 
     * @param testName
     * @return {@link ExtentTest}
     */
    public ExtentTest startTest(String testName) {
        return new ExtentTest(testName, "");
    }
    
    /**
     * 
     * @param testName
     * @param description
     * @return
     */
    public ExtentTest startTest(String testName, String description) {
        return new ExtentTest(testName, description);
    }
    
    /**
     * 
     * @param test
     */
    public void endTest(ExtentTest test) {
        reportInstance.addTest(test.getTest());
    }
    
    /**
     * 
     * @return
     */
    public ReportInstance.ReportConfig config() {
        if (reportConfig == null) {
            reportConfig = reportInstance.new ReportConfig();
        }
        
        return reportConfig;
    }
    
    /**
     * 
     * @param info
     * @return
     */
    public ExtentReports addSystemInfo(HashMap<String, String> info) {
        systemInfo.setInfo(info);
        
        return this;
    }

    /**
     * 
     * @param param
     * @param value
     * @return
     */
    public ExtentReports addSystemInfo(String param, String value) {
        systemInfo.setInfo(param, value);
        
        return this;
    }
    
    public void flush() {
        reportInstance.terminate(systemInfo);
    }
}
