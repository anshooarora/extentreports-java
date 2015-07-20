/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.relevantcodes.extentreports.model.Test;

/**
 * 
 * @author Anshoo
 *
 */
public class ExtentReports {
    private ReportInstance reportInstance;
    private SystemInfo systemInfo;
    private ReportInstance.ReportConfig reportConfig;
    private List<ExtentTest> testList;
    
    /**
     * Initializes the reporting by setting the file-path and test DisplayOrder
     * 
     * @param filePath Path of the file, in .htm or .html format
     * @param replaceExisting Setting to overwrite (TRUE) the existing file or append (FALSE) to it
     *             <br>&nbsp;&nbsp;<b>true</b>:  the file will be replaced with brand new markup, and all existing data
     *                    will be lost. Use this option to create a brand new report
     *            <br>&nbsp;&nbsp;<b>false</b>:  existing data will remain, new tests will be appended to the existing report
     * @param displayOrder Determines the order in which your tests will be displayed
     *             <br>&nbsp;&nbsp;<b>OLDEST_FIRST</b> (default) - oldest test at the top, newest at the end
     *             <br>&nbsp;&nbsp;<b>NEWEST_FIRST</b> - newest test at the top, oldest at the end
     */
    public ExtentReports(String filePath, Boolean replaceExisting, DisplayOrder displayOrder) {        
        reportInstance = new ReportInstance();
        reportConfig = reportInstance.new ReportConfig();
        reportInstance.initialize(filePath, replaceExisting, displayOrder);
        
        systemInfo = new SystemInfo();
    }
    
    /**
     * Initializes the reporting by setting the file-path
     * 
     * @param filePath Path of the file, in .htm or .html format
     * @param replaceExisting Setting to overwrite (TRUE) the existing file or append (FALSE) to it
     *             <br>&nbsp;&nbsp;<b>true</b>:  the file will be replaced with brand new markup, and all existing data
     *                    will be lost. Use this option to create a brand new report
     *            <br>&nbsp;&nbsp;<b>false</b>:  existing data will remain, new tests will be appended to the existing report
     */
    public ExtentReports(String filePath, Boolean replaceExisting) {
        this(filePath, replaceExisting, DisplayOrder.OLDEST_FIRST);
    }
    
    /**
     * Calling startTest() generates a toggle for the test in the HTML file and adds all
     * log events under this level. This is a required step and without calling this method
     * the toggle will not be created for the test and log will not be added.
     * 
     * @param testName Name of the test
     * @return {@link ExtentTest}
     */
    public ExtentTest startTest(String testName) {
        return startTest(testName, "");
    }
    
    /**
     * Calling startTest() generates a toggle for the test in the HTML file and adds all
     * log events under this level. This is a required step and without calling this method
     * the toggle will not be created for the test and log will not be added.
     * 
     * @param testName Name of the test
     * @param description A short description of the test
     * @return {@link ExtentTest}
     */
    public ExtentTest startTest(String testName, String description) {
        if (testList == null) {
            testList = new ArrayList<ExtentTest>();
        }
        
        ExtentTest test = new ExtentTest(testName, description);
        testList.add(test);
        
        return test;
    }
    
    /**
     * Ends the current toggle level
     * 
     * @param test {@link ExtentTest}
     */
    public void endTest(ExtentTest test) {
        test.getTest().hasEnded = true;
        // #19 - allow user to set warning message for tests
        //test.getTest().internalWarning = "";
        
        reportInstance.addTest(test.getTest());
    }
    
    /**
     * Allows various configurations to be applied to the report file
     * 
     * @return {@link ReportInstance.ReportConfig}
     */
    public ReportInstance.ReportConfig config() {
        return reportConfig;
    }
    
    /**
     * Add system information to the SystemInfo view
     * 
     * @param info SystemInfo values as Key-Value pairs
     * @return {@link ExtentReports}
     */
    public ExtentReports addSystemInfo(Map<String, String> info) {
        systemInfo.setInfo(info);
        
        return this;
    }

    /**
     * Add system information to the SystemInfo view
     * 
     * @param param Name of system parameter
     * @param value Value
     * @return {@link ExtentReports}
     */
    public ExtentReports addSystemInfo(String param, String value) {
        systemInfo.setInfo(param, value);
        
        return this;
    }
    
    /**
     * Writes all info to the report file
     */
    public void flush() {
    	removeChildTests();
    	
        reportInstance.writeAllResources(testList, systemInfo);
        
        systemInfo.clear();
    }
    
    /**
     * Closes the underlying stream and clears all resources
     * <br><br>
     * If any of your test ended abruptly causing any side-affects 
     * (not all logs sent to ExtentReports, information missing), 
     * this method will ensure that the test is still appended to the report 
     * with a warning message.
     */
    public void close() {
    	removeChildTests();
    	
    	reportInstance.terminate(testList);
    	
    	testList.clear();
    }
    
    private void removeChildTests() {
    	Iterator<ExtentTest> iterator = testList.iterator();
        Test t;
        
        while (iterator.hasNext()) {
        	t = iterator.next().getTest();
        	
        	if (t.child) {
        		iterator.remove();
        	}
        }
    }
}
