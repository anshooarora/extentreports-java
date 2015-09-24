/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.relevantcodes.extentreports.model.Category;
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.ScreenCapture;
import com.relevantcodes.extentreports.model.Screencast;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.model.TestAttribute;
import com.relevantcodes.extentreports.source.ImageHtml;
import com.relevantcodes.extentreports.source.ScreencastHtml;

/** 
 * Defines a node in the report file
 * 
 * @author Anshoo
 *
 */
public class ExtentTest {
    private LogStatus runStatus = LogStatus.UNKNOWN;
    private Test test;
    
    /**
     * Builds a test toggle in the report with the TestName
     * 
     * @param testName Test name
     * @param description A short description of the test
     */
    public ExtentTest(String testName, String description) {
        test = new Test();
        
        test.setName(testName == null ? "" : testName.trim()); 
        test.setDescription(description.trim());
        test.setStartedTime(Calendar.getInstance().getTime());
    }
    
    /**
     * Logs events for the test
     * 
     * @param logStatus Status (see {@link LogStatus})
     * @param stepName Name of the step
     * @param details Details of the step
     */
    public void log(LogStatus logStatus, String stepName, String details) {
        Log evt = new Log();
        
        evt.setLogStatus(logStatus);
        evt.setStepName(stepName == null ? "" : stepName.trim()); 
        evt.setDetails(details == null ? "" : details.trim());
        evt.setTimestamp(Calendar.getInstance().getTime());
                
        test.setLog(evt);
        
        test.trackLastRunStatus();
        runStatus = test.getStatus();
    }
    
    /**
     * Logs events for the test
     * 
     * @param logStatus Status (see {@link LogStatus})
     * @param details Details of the step
     */
    public void log(LogStatus logStatus, String details) {
        log(logStatus, "", details);
    }
    
    /**
     * Allows for adding a snapshot to the log event
     * 
     * @param imgPath Path of the image
     * @return A formed HTML img tag with the supplied path
     */
    public String addScreenCapture(String imgPath) {
        String screenCaptureHtml = isPathRelative(imgPath) 
                ? ImageHtml.getSource(imgPath).replace("file:///", "") 
                        : ImageHtml.getSource(imgPath);
        
        ScreenCapture img = new ScreenCapture();
        img.setSource(screenCaptureHtml);
        img.setTestName(test.getName());
        img.setTestId(test.getId());
        
        test.setScreenCapture(img);

        return screenCaptureHtml;
    }
    
    /**
     * Allows for adding a screen cast to the log event
     * 
     * @param screencastPath Path of the screencast
     * @return A formed HTML video tag with the supplied path
     */
    public String addScreencast(String screencastPath) {
        String screencastHtml = isPathRelative(screencastPath) 
                ? ScreencastHtml.getSource(screencastPath).replace("file:///", "") 
                        : ScreencastHtml.getSource(screencastPath);
        
        Screencast sc = new Screencast();
        sc.setSource(screencastHtml);
        sc.setTestName(test.getName());
        sc.setTestId(test.getId());
        
        test.setScreencast(sc);
        
        return screencastHtml;
    }
    
    /**
     * Assigns category to test
     * 
     * <p><b>Usage:</b> test.assignCategory("ExtentAPI", "Regression");
     * 
     * @param category Category name
     * @return {@link ExtentTest}
     */
    public ExtentTest assignCategory(String... category) {
        List<String> list = new ArrayList<String>();
        
        for (String c : category) {
            if (!c.trim().equals("") && !list.contains(c)) {
                test.setCategory(new Category(c));
            }
            
            list.add(c);
        }

        return this;
    }
    
    /**
     * Appends a child test to the current test
     * 
     * @param node {@link ExtentTest}
     * @return {@link ExtentTest}
     */
    public ExtentTest appendChild(ExtentTest node) {
        node.getTest().setEndedTime(Calendar.getInstance().getTime());
        node.getTest().isChildNode = true;
        node.getTest().trackLastRunStatus();
        
        test.hasChildNodes = true;
        
        List<String> list = new ArrayList<String>();
        
        // categories to strings
        for (TestAttribute attr : this.test.getCategoryList()) {
            if (!list.contains(attr.getName())) {            
                list.add(attr.getName());
            }
        }
        
        // add all categories to parent-test
        for (TestAttribute attr : node.getTest().getCategoryList()) {
            if (!list.contains(attr.getName())) {
                this.test.setCategory(attr);
            }
        }

        test.setNode(node.getTest());
                
        return this;
    }
    
    /**
     * Provides the current run status of the test
     * 
     * @return {@link LogStatus}
     */
    public LogStatus getRunStatus() {
        return runStatus;
    }
    
    /**
     * Returns the underlying test
     * 
     * @return {@link Test}
     */
    public Test getTest() {        
        return test;
    }
    
    private Boolean isPathRelative(String path) {
        if (path.indexOf("http") == 0 || !new File(path).isAbsolute()) {
            return true;
        }
        
        return false;
    }
}
