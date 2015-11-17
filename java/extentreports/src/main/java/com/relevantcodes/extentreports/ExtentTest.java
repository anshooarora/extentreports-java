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

import com.relevantcodes.extentreports.model.Author;
import com.relevantcodes.extentreports.model.Category;
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.ScreenCapture;
import com.relevantcodes.extentreports.model.Screencast;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.model.TestAttribute;
import com.relevantcodes.extentreports.utils.ExceptionUtil;
import com.relevantcodes.extentreports.view.ScreencastHtml;
import com.relevantcodes.extentreports.view.ScreenshotHtml;

/** 
 * <p>
 * Defines a node in the report file.
 * 
 * <p>
 * By default, each started node is top-level. If <code>appendChild</code> method
 * is used on any test, it automatically becomes a child-node. When this happens:
 * 
 * <ul>
 * 	<li>parent test -> <code>hasChildNodes = true</code></li>
 * 	<li>child test -> <code>isChildNode = true</code></li>
 * </ul>
 * 
 * @author Anshoo
 */
public class ExtentTest {
    private LogStatus runStatus = LogStatus.UNKNOWN;
    private Test test;
    
    /**
     * <p>
     * This method creates a test node as a top-most level test
     * 
     * @param testName 
     * 		Test name
     * @param description 
     * 		A short description of the test
     */
    public ExtentTest(String testName, String description) {
        test = new Test();
        
        test.setName(testName == null ? "" : testName.trim()); 
        test.setDescription(description.trim());
    }
    
    /**
     * <p>
     * Logs events for the test
     * 
     * <p>
     * Log event with this signature is shown in the report with 4 columns:
     * 
     * <ul>
     * 	<li>Timestamp</li>
     * 	<li>Status</li>
     * 	<li>StepName</li>
     * 	<li>Details</li>
     * </ul>
     * 
     * @param logStatus 
     * 		Status (see {@link LogStatus})
     * 
     * @param stepName 
     * 		Name of the step
     * 
     * @param details 
     * 		Details of the step
     */
    public void log(LogStatus logStatus, String stepName, String details) {
        Log evt = new Log();
        
        evt.setLogStatus(logStatus);
        evt.setStepName(stepName == null ? null : stepName.trim()); 
        evt.setDetails(details == null ? "" : details.trim());
                
        test.setLog(evt);
        
        test.trackLastRunStatus();
        runStatus = test.getStatus();
    }
    
    /**
     * <p>
     * Logs events for the test
     * 
     * <p>
     * Log event with this signature is shown in the report with 4 columns:
     * 
     * <ul>
     * 	<li>Timestamp</li>
     * 	<li>Status</li>
     * 	<li>StepName</li>
     * 	<li>Details</li>
     * </ul>
     * 
     * @param logStatus 
     * 		Status (see {@link LogStatus})
     * 
     * @param stepName 
     * 		Name of the step
     * 
     * @param t 
     * 		Exception
     */
    public void log(LogStatus logStatus, String stepName, Throwable t) {
    	log(logStatus, stepName, "<pre>" + ExceptionUtil.getStackTrace(t) + "</pre>");
    }
    
    /**
     * <p>
     * Logs events for the test
     * 
     * <p>
     * Log event with this signature is shown in the report with 3 columns:
     * 
     * <ul>
     * 	<li>Timestamp</li>
     * 	<li>Status</li>
     * 	<li>Details</li>
     * </ul>
     * 
     * @param logStatus 
     * 		Status (see {@link LogStatus})
     * 
     * @param t 
     * 		Exception
     */
    public void log(LogStatus logStatus, Throwable t) {
    	log(logStatus, null, "<pre>" + ExceptionUtil.getStackTrace(t) + "</pre>");
    }
    
    /**
     * <p>
     * Logs events for the test
     * 
     * <p>
     * Log event with this signature is shown in the report with 3 columns:
     * 
     * <ul>
     * 	<li>Timestamp</li>
     * 	<li>Status</li>
     * 	<li>Details</li>
     * </ul>
     * 
     * @param logStatus 
     * 		Status (see {@link LogStatus})
     * 
     * @param details 
     * 		Details of the step
     */
    public void log(LogStatus logStatus, String details) {
        log(logStatus, null, details);
    }
    
    /**
     * <p>
     * Allows for adding a snapshot to the log event details
     * 
     * @param imgPath 
     * 		Path of the image
     * 
     * @return 
     * 		A formed HTML img tag with the supplied path
     */
    public String addScreenCapture(String imgPath) {
        String screenCaptureHtml = isPathRelative(imgPath) 
                ? ScreenshotHtml.getSource(imgPath).replace("file:///", "") 
                        : ScreenshotHtml.getSource(imgPath);
        
        ScreenCapture img = new ScreenCapture();
        img.setSource(screenCaptureHtml);
        img.setTestName(test.getName());
        img.setTestId(test.getId());
        
        test.setScreenCapture(img);

        return screenCaptureHtml;
    }
    
    /**
     * <p>
     * Allows for adding a screen cast to the log event details
     * 
     * @param screencastPath 
     * 		Path of the screencast
     * 
     * @return 
     * 		A formed HTML video tag with the supplied path
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
     * <p>
     * Assigns category to test
     * 
     * <p>
     * Usage: <code>test.assignCategory("ExtentAPI");</code>
     * <br>
     * Usage: <code>test.assignCategory("ExtentAPI", "Regression", ...);</code>
     * 
     * @param categories 
     * 		Category name
     * 
     * @return 
     * 		A {@link ExtentTest} object
     */
    public ExtentTest assignCategory(String... categories) {
        List<String> list = new ArrayList<String>();
        
        for (String c : categories) {
            if (!c.trim().equals("") && !list.contains(c)) {
                test.setCategory(new Category(c));
            }
            
            list.add(c);
        }

        return this;
    }
    
    /**
     * <p>
     * Assigns author(s) to test
     * 
     * <p>
     * Usage: <code>test.assignAuthor("AuthorName");</code>
     * <br>
     * Usage: <code>test.assignAuthor("Author1", "Author2", ...);</code>
     * 
     * @param authors Author name
     * @return {@link ExtentTest}
     */
    public ExtentTest assignAuthor(String... authors) {
        List<String> list = new ArrayList<String>();
        
        for (String author : authors) {
            if (!author.trim().equals("") && !list.contains(author)) {
                test.setAuthor(new Author(author));
            }
            
            list.add(author);
        }

        return this;
    }
    
    /**
     * <p>
     * Appends a child test to the current test
     * 
     * @param node 
     * 		An {@link ExtentTest} object. Test that is added as the node.
     * 
     * @return 
     * 		An {@link ExtentTest} object. Parent test which adds the node as its child.
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
     * <p>
     * Provides the current run status of the test
     * 
     * @return 
     * 		{@link LogStatus}
     */
    public LogStatus getRunStatus() {
        return runStatus;
    }
    
    /**
     * <p>
     * Returns the underlying test which controls the internal model
     * 
     * @return 
     * 		A {@link Test} object
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
