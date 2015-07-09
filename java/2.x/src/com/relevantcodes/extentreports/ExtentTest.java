/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

import java.util.Calendar;

import com.relevantcodes.extentreports.model.Category;
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.ScreenCapture;
import com.relevantcodes.extentreports.model.Screencast;
import com.relevantcodes.extentreports.model.Test;
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
        
        test.name = testName.trim();
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
        
        evt.timestamp = Calendar.getInstance().getTime();
        evt.logStatus = logStatus;
        evt.stepName = stepName.trim();
        evt.details = details.trim();
                
        test.log.add(evt);
        
        trackLastRunStatus(logStatus);
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
     * @param category Category object
     * @return {@link ExtentTest}
     */
    public ExtentTest assignCategory(String... category) {
        for (String c : category) {
            test.categoryList.add(new Category(c));
        }

        return this;
    }
    
    private Boolean isPathRelative(String path) {
        if (path.indexOf("http") == 0 || path.indexOf(".") == 0 || path.indexOf("/") == 0) {
            return true;            
        }
        
        return false;
    }
    
    private void trackLastRunStatus(LogStatus logStatus) {
        if (runStatus == LogStatus.UNKNOWN) {
            if (logStatus == LogStatus.INFO) {
                runStatus = LogStatus.PASS;
            }
            else {
                runStatus = logStatus;
            }
            
            return;
        }
        
        if (runStatus == LogStatus.FATAL) return;
        
        if (logStatus == LogStatus.FATAL) {
            runStatus = logStatus;
            return;
        }
        
        if (runStatus == LogStatus.FAIL) return;
        
        if (logStatus == LogStatus.FAIL) {
            runStatus = logStatus;
            return;
        }
        
        if (runStatus == LogStatus.ERROR) return;
        
        if (logStatus == LogStatus.ERROR) {
            runStatus = logStatus;
            return;
        }
        
        if (runStatus == LogStatus.WARNING) return;
        
        if (logStatus == LogStatus.WARNING) {
            runStatus = logStatus;
            return;
        }
        
        if (runStatus == LogStatus.PASS || runStatus == LogStatus.INFO) {
            runStatus = LogStatus.PASS;
            return;
        }
        
        runStatus = LogStatus.SKIP;        
    }
    
    public Test getTest() {
        test.status = runStatus;
        
        return test;
    }
}
