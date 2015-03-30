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

public class ExtentReports {
	private final static ExtentReports instance = new ExtentReports();
	private DocumentConfig config;
	private static List<String> classList = new ArrayList<String>();
	private AbstractLog extent;
	private String filePath;
	
	//region Public Methods
	
	public static ExtentReports get(Class<?> clazz) {
		classList.add(clazz.getName());
		return instance;
	}
	
	public static ExtentReports get(String className) {
		classList.add(className);
		return instance;
	}
	
	public void startTest(String testName) {
		startTest(testName, "");
	}
	
	public void startTest(String testName, String testDescription) {
		extent.startTest(testName, testDescription);
	}
	
	public void endTest() {
		extent.endTest("");
	}
	
	public void log(LogStatus logStatus, String stepName, String details, String screenCapturePath) {
		extent.caller = callerClass(Thread.currentThread().getStackTrace());
		extent.log(logStatus, stepName, details, screenCapturePath);
	}
	
	public void log(LogStatus logStatus, String stepName, String details) {
		log(logStatus, stepName, details, "");
	}
	
	public void log(LogStatus logStatus, String details) {
		extent.caller = callerClass(Thread.currentThread().getStackTrace());
		extent.log(logStatus, details);
	}
	
	public void attachScreenshot(String screenCapturePath, String message) {
		extent.attachScreenshot(screenCapturePath, message);
	}

	public void attachScreenshot(String screenCapturePath) {
		attachScreenshot(screenCapturePath, "");
	}

	public void setLogLevel(LogLevel logLevel) {
		extent.setLogLevel(logLevel);
	}
	
	@Deprecated
	public void configuration() { }
	
	public DocumentConfig config() {
		if (!(config instanceof DocumentConfig))
			config = new DocumentConfig(filePath);
		
		return config;
	}
	
	public void init(String filePath, Boolean replaceExisting) {
		init(filePath, replaceExisting, DisplayOrder.BY_OLDEST_TO_LATEST);
	}
	
	public void init(String filePath, Boolean replaceExisting, DisplayOrder displayOrder) {
		this.filePath = filePath;
		config = null;
		
		extent = new Logger(filePath, replaceExisting, displayOrder);
		
		initialProc();
	}
	
	
	// region Private Methods
	
	private void initialProc() {
		config().renewSystemInfo();
	}
	
	private String callerClass(StackTraceElement[] element) {
		String name = null;
				
		try {
			name = RegexMatcher.getNthMatch(element[element.length - 2].toString(), "([\\w\\.]+)(:.*)?", 0);
			
			if (name.indexOf("com.relevantcodes") >= 0)
				name = RegexMatcher.getNthMatch(element[element.length - 1].toString(), "([\\w\\.]+)(:.*)?", 0);
		}
		catch (Exception e) {
			try {
				name = RegexMatcher.getNthMatch(element[element.length - 3].toString(), "([\\w\\.]+)(:.*)?", 0);
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
	

	//region Constructor(s)
	
	private ExtentReports() {}
}