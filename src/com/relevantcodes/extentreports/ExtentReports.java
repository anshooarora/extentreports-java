/*
Copyright 2015 ExtentReports committer(s)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0
	
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/


package com.relevantcodes.extentreports;

import com.relevantcodes.extentreports.markup.Configuration;

public class ExtentReports {
	private Configuration configuration;
	private final static ExtentReports instance = new ExtentReports();
	private static Class<?> clazz;
	private static String className;
	private AbstractLog extent;
	private String filePath;
	
			
	//region Public Methods
	
	public static ExtentReports get(Class<?> clazz) {
		ExtentReports.clazz = clazz;
		ExtentReports.className = "";
		return instance;
	}
	
	public static ExtentReports get(String className) {
		ExtentReports.className = className;
		ExtentReports.clazz = null;
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
		String name = clazz == null ? className : clazz.getName().split("\\.")[clazz.getName().split("\\.").length - 1];

		extent.log(logStatus, "[" + name + "] " + stepName, details, screenCapturePath);
	}
	
	public void log(LogStatus logStatus, String stepName, String details) {
		log(logStatus, stepName, details, "");
	}
	
	public void setLogLevel(LogLevel logLevel) {
		extent.setLogLevel(logLevel);
	}
	
	public Configuration configuration() {
		if (!(configuration instanceof Configuration))
			configuration = new Configuration();
		
		return configuration;
	}
	
	public void init(String filePath, Boolean replaceExisting) {
		init(filePath, replaceExisting, DisplayOrder.BY_OLDEST_TO_LATEST);
	}
	
	public void init(String filePath, Boolean replaceExisting, DisplayOrder displayOrder) {
		this.filePath = filePath;
		
		extent = new Logger(filePath, replaceExisting, displayOrder);
		
		initialProc();
	}
	
	
	// region Private Methods
	
	private void initialProc() {
		configuration().params("filePath", filePath);
		configuration().content().renewSystemInfo();
	}


//region Constructor(s)
	private ExtentReports() {}
}