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
        
        test.name = testName == null ? "" : testName.trim(); 
        test.description = description.trim();
        test.startedTime = Calendar.getInstance().getTime();
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
        
        evt.logStatus = logStatus;
        evt.stepName = stepName == null ? "" : stepName.trim(); 
        evt.details = details == null ? "" : details.trim(); 
        evt.timestamp = Calendar.getInstance().getTime();
                
        test.log.add(evt);
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
        String screenCaptureHtml;
        
        if (isPathRelative(imgPath)) {
            screenCaptureHtml = ImageHtml.getSource(imgPath).replace("file:///", "");
        }
        else {
            screenCaptureHtml = ImageHtml.getSource(imgPath);
        }
        
        ScreenCapture img = new ScreenCapture();
        img.src = screenCaptureHtml;
        img.testName = test.name;
        
        test.screenCapture.add(img);

        return screenCaptureHtml;
    }
    
    /**
     * Allows for adding a screen cast to the log event
     * 
     * @param screencastPath Path of the screencast
     * @return A formed HTML video tag with the supplied path
     */
    public String addScreencast(String screencastPath) {
        String screencastHtml;
        
        if (isPathRelative(screencastPath)) {
            screencastHtml = ScreencastHtml.getSource(screencastPath).replace("file:///", "");
        }
        else {
            screencastHtml = ScreencastHtml.getSource(screencastPath);
        }
        
        Screencast sc = new Screencast();
        sc.src = screencastHtml;
        sc.testName = test.name;
        
        test.screencast.add(sc);
        
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
            if (!list.contains(c)) {
                test.categoryList.add(new Category(c));
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
        node.getTest().endedTime = Calendar.getInstance().getTime();
        node.getTest().isChildNode = true;
        node.getTest().trackLastRunStatus();
        
        test.hasChildNodes = true;
        
        List<String> list = new ArrayList<String>();
        
        for (TestAttribute attr : this.test.categoryList) {
            if (!list.contains(attr.getName())) {            
                list.add(attr.getName());
            }
        }
        
        for (TestAttribute attr : node.getTest().categoryList) {
            if (!list.contains(attr.getName())) {
                this.test.categoryList.add(attr);
            }
        }
        
        test.nodeList.add(node.getTest());
                
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
