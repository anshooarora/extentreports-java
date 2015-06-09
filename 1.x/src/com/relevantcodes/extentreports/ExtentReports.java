/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/


package com.relevantcodes.extentreports;

import java.util.ArrayList;
import java.util.List;
import com.relevantcodes.extentreports.markup.DocumentConfig;
import com.relevantcodes.extentreports.support.RegexMatcher;

/**
 * ExtentReports (by Anshoo Arora) is a HTML reporting library for Selenium WebDriver 
 * for Java which is extremely easy to use and creates beautiful execution reports. 
 * It shows test and step summary, test steps and status in a toggle view for quick analysis
 * 
 * <p>
 * Project URL: http://relevantcodes.com/extentreports-for-selenium/
 * 
 * @author Anshoo Arora
 */
public class ExtentReports {
	
	/**
	 * Static instance of ExtentReports.class
	 */
	private final static ExtentReports instance = new ExtentReports();
	
	/**
	 * Allows fluent configuration of ExtentReports HTML reports
	 */
	private DocumentConfig config;
	
	/**
	 * Contains the list of classes that call ExtentReports to build reports 
	 */
	private static List<String> classList = new ArrayList<String>();
	
	/**
	 * AbstractLog
	 */
	private AbstractLog extent;
	
	/**
	 * This is path of the file in which the HTML source is written by ExtentReports
	 */
	private String filePath;
	
	
	/**
	 * @param clazz The class that uses a region level ExtentReports object 
	 * @return an ExtentReports object
	 */
	public static ExtentReports get(Class<?> clazz) {
		classList.add(clazz.getName());
		return instance;
	}
	
	/**
	 * @param className The name of the class that uses a region level ExtentReports object, passed as a string
	 * @return an ExtentReports object
	 */
	public static ExtentReports get(String className) {
		classList.add(className);
		return instance;
	}
	
	/**
	 * Calling startTest(arg) generates a toggle for the test in the HTML file and adds all
	 * log events under this level. This is a required step and without calling this method
	 * the toggle will not be created for the test and the log events may not appear, or
	 * appear under an incorrect test.
	 *  
	 * <p><b>Usage:</b>
	 * 		extent.startTest("MyTestName");
	 *  
	 * @param testName Name of the test that is currently being run and report is being generated for
	 */
	public void startTest(String testName) {
		startTest(testName, "");
	}
	
	/**
	 * Calling startTest(arg, arg) generates a toggle for the test in the HTML file and adds all
	 * log events under this level. This is a required step and without calling this method
	 * the toggle will not be created for the test and the log events may not appear, or
	 * appear under an incorrect test. 
	 * 
	 * <p>
	 * This overloaded method allows a description to be written for the testName.
	 * 
	 * <p><b>Usage:</b>
	 * 		extent.startTest("Test - With Description", "This description will show up under Test.");
	 * 
	 * @param testName Name of the test that is currently being run and report is being generated for
	 * @param testDescription Description of the test
	 */
	public void startTest(String testName, String testDescription) {
		extent.startTest(testName, testDescription);
	}
	
	/**
	 * Ends the current toggle level
	 */
	public void endTest() {
		extent.endTest("");
	}
	
	/**
	 * Provides ability to log events for each started test. 
	 * 
	 * <p><b>Usage:</b>
	 * 		extent.log(LogStatus.INFO, "Image", "Image example:", "C:\\img.png");
	 * 
	 * <p>
	 *  It is possible to use relative path for the snapshot. <b>Note:</b> Relative paths starting
	 *  with '/' and '.' are supported. If you are using an absolute path, 'file:///' will 
	 *  be automatically appended for the image to load correctly.
	 * 
	 * <p><b>Usage</b>
	 * 		<br>extent.log(LogStatus.INFO, "Image", "Image example:", "./pathToImg.png");
	 * 		<br>extent.log(LogStatus.INFO, "Image", "Image example:", "/pathToImg.png");
	 * 
	 * @param logStatus LogStatus of the log event
	 * @param stepName Name of the step
	 * @param details Details of the step
	 * @param screenCapturePath Path of the snapshot to be attached to step
	 */
	public void log(LogStatus logStatus, String stepName, String details, String screenCapturePath) {
		if (LogSettings.displayCallerClass)
			extent.caller = callerClass(Thread.currentThread().getStackTrace());

		extent.log(logStatus, stepName, details, screenCapturePath);
	}
	
	/**
	 * Overloaded method to allow LogStatus, stepName and details for the log event
	 * 
	 * <p><b>Usage:</b>
	 * 		extent.log(LogStatus.PASS, "StepName", "PASS Details"); 
	 * 
	 * @param logStatus LogStatus of the log event
	 * @param stepName Name of the step
	 * @param details Details of the step
	 */
	public void log(LogStatus logStatus, String stepName, String details) {
		log(logStatus, stepName, details, "");
	}
	
	/**
	 * Overloaded method to allow only setting the LogStatus and details of the log event
	 * 
	 * <p><b>Usage:</b>
	 * 		extent.log(LogStatus.INFO, "This step shows usage of log(logStatus, details)");
	 * 
	 * @param logStatus LogStatus of the log event
	 * @param details Details of the step
	 */
	public void log(LogStatus logStatus, String details) {
		if (LogSettings.displayCallerClass)
			extent.caller = callerClass(Thread.currentThread().getStackTrace());
		
		extent.log(logStatus, details);
	}
	
	/**
	 * Allows attaching a snapshot as a step.
	 * 
	 * <p><b>Usage:</b>
	 * 		extent.attachScreenshot("pathToImg.png", "This step only attaches a screenshot without a status.");
	 * 
	 *  <p>It is possible to use relative path for the snapshot. Note: Relative paths starting
	 *  with '/' and '.' are supported. If you are using an absolute path, 'file:///' will 
	 *  be automatically appended for the image to load correctly.
	 * 
	 * @param screenCapturePath Path of the snapshot file
	 * @param message Message to attach for the step
	 */
	public void attachScreenshot(String screenCapturePath, String message) {
		extent.attachScreenshot(screenCapturePath, message);
	}

	/**
	 * Allows attaching a snapshot as a step.
	 * 
	 * <p>Usage:
	 * 		extent.attachScreenshot("pathToImg.png");
	 * 
	 *  <p>It is possible to use relative path for the snapshot. Note: Relative paths starting
	 *  with '/' and '.' are supported. If you are using an absolute path, 'file:///' will 
	 *  be automatically appended for the image to load correctly.
	 * 
	 * @param screenCapturePath Path of the snapshot file
	 */
	public void attachScreenshot(String screenCapturePath) {
		attachScreenshot(screenCapturePath, "");
	}

	/**
	 * This allows a log level for the report. It filters any LogStatus that does not conform
	 * to the level set. 
	 * 
	 * <p><b>ALLOW_ALL</b>: default setting which passes all log events to the report
	 * <br><b>FAI</b>L:  Allows FAIL and FATAL events sent to the report
	 * <br><b>WARNING</b>:  Allows WARNING and FAIL events to be sent to the report
	 * <br><b>ERROR</b>:  Allows ERROR and FAIL events to be sent to the report
	 * <br><b>ERRORS_AND_WARNING</b>:  Allows Errors, Warnings and FAIL/FATAL events 
	 * <br><b>OFF</b>:  No events are allowed to be written to the report  
	 * 
	 * @param logLevel Level of logging to be used
	 */
	public void setLogLevel(LogLevel logLevel) {
		extent.setLogLevel(logLevel);
	}

	
	/**
	 * Returns the DocumentConfig object that allows various configurations to be applied
	 * to the report file
	 * 
	 * @return DocumentConfig
	 */
	public DocumentConfig config() {
		if (!(config instanceof DocumentConfig))
			config = new DocumentConfig(filePath);

		return config;
	}
	
	/**
	 * Initializes the reporting by setting the file-path
	 * 
	 * @param filePath Path of the file, in .htm or .html format
	 * @param replaceExisting Setting to overwrite (TRUE) the existing file or append (FALSE) to it
	 * 			using the DisplayOrder order
	 * 			true:  the file will be replaced with brand new markup, 
	 *	                and all existing data will be lost. Use this option to create a brand new report
	 *	        false:  existing data will remain, new tests will be appended to the existing report
	 */
	public void init(String filePath, Boolean replaceExisting) {
		init(filePath, replaceExisting, DisplayOrder.BY_OLDEST_TO_LATEST);
	}
	
	/**
	 * Initializes the reporting by setting the file-path and test DisplayOrder
	 * 
	 * @param filePath Path of the file, in .htm or .html format
	 * @param replaceExisting Setting to overwrite (TRUE) the existing file or append (FALSE) to it
	 * 			using the DisplayOrder order
	 * 			true:  the file will be replaced with brand new markup, 
	 *	                and all existing data will be lost. Use this option to create a brand new report
	 *	        false:  existing data will remain, new tests will be appended to the existing report
	 * @param displayOrder Determines the order in which your tests will be displayed
	 * 			BY_OLDEST_TO_LATEST (default) - oldest test at the top, newest at the end
     *     		BY_LATEST_TO_OLDEST - newest test at the top, oldest at the end
	 */
	public void init(String filePath, Boolean replaceExisting, DisplayOrder displayOrder) {
		init(filePath, replaceExisting, displayOrder, GridType.STANDARD);
	}
	
	/**
	 * Initializes the reporting by setting the file-path and GridType
	 * 
	 * @param filePath Path of the file, in .htm or .html format
	 * @param replaceExisting Setting to overwrite (TRUE) the existing file or append (FALSE) to it
	 * 			using the DisplayOrder order
	 * 			true:  the file will be replaced with brand new markup, 
	 *	                and all existing data will be lost. Use this option to create a brand new report
	 *	        false:  existing data will remain, new tests will be appended to the existing report
	 * @param gridType Determines the type of grid to be used
	 * 			STANDARD (default) - standard grid with 1 test per row
	 * 			MASONRY - creates a masonry style grid with 2 tests per row
	 */
	public void init(String filePath, Boolean replaceExisting, GridType gridType) {
		init(filePath, replaceExisting, DisplayOrder.BY_OLDEST_TO_LATEST, gridType);
	}
	
	/**
	 * Initializes the reporting by setting the file-path, test DisplayOrder and GridType
	 * 
	 * @param filePath Path of the file, in .htm or .html format
	 * @param replaceExisting Setting to overwrite (TRUE) the existing file or append (FALSE) to it
	 * 			using the DisplayOrder order
	 * 			true:  the file will be replaced with brand new markup, 
	 *	                and all existing data will be lost. Use this option to create a brand new report
	 *	        false:  existing data will remain, new tests will be appended to the existing report
	 * @param displayOrder Determines the order in which your tests will be displayed
	 * 			BY_OLDEST_TO_LATEST (default) - oldest test at the top, newest at the end
     *     		BY_LATEST_TO_OLDEST - newest test at the top, oldest at the end
	 * @param gridType Determines the type of grid to be used
	 * 			STANDARD (default) - standard grid with 1 test per row
	 * 			MASONRY - creates a masonry style grid with 2 tests per row
	 */
	public void init(String filePath, Boolean replaceExisting, DisplayOrder displayOrder, GridType gridType) {
		this.filePath = filePath;
		config = null;
		
		extent = new Logger(filePath, replaceExisting, displayOrder, gridType);
		
		init();
	}
	
	
	private void init() {
		config().renewSystemInfo();
	}
	
	private String callerClass(StackTraceElement[] element) {
		String name = null;
		String pattern = "([\\w\\.]+)(:.*)?";
		
		try {
			name = RegexMatcher.getNthMatch(element[element.length - 2].toString(), pattern, 0);
			
			if (name.indexOf("com.relevantcodes") >= 0)
				name = RegexMatcher.getNthMatch(element[element.length - 1].toString(), pattern, 0);
		}
		catch (Exception e) {
			try {
				name = RegexMatcher.getNthMatch(element[element.length - 3].toString(), pattern, 0);
			}
			catch (Exception ex) {
				return name;
			}
		}
		
		String[] s = name.split("\\.");
		
		if (s.length >= 1) {
			return s[s.length - 2] + "." + s[s.length - 1];
		}
		
		return name;
	}
	

	public ExtentReports() {}
}