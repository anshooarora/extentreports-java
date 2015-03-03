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

import java.util.ArrayList;
import java.util.List;

import com.relevantcodes.extentreports.markup.Configuration;
import com.relevantcodes.extentreports.markup.DocumentConfig;
import com.relevantcodes.extentreports.support.RegexMatcher;

public class ExtentReports {
	private final static ExtentReports instance = new ExtentReports();
	private Configuration configuration;
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
		String cls = callerClass(Thread.currentThread().getStackTrace());
		
		if (cls != null) {
			extent.log(logStatus, "[" + cls + "] " + stepName, details, screenCapturePath);
		} 
		else {
			extent.log(logStatus, stepName, details, screenCapturePath);
		}
	}
	
	public void log(LogStatus logStatus, String stepName, String details) {
		log(logStatus, stepName, details, "");
	}
	
	public void setLogLevel(LogLevel logLevel) {
		extent.setLogLevel(logLevel);
	}
	
	@Deprecated
	public Configuration configuration() {
		if (!(configuration instanceof Configuration))
			configuration = new Configuration();
		
		return configuration;
	}
	
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
		configuration().params("filePath", filePath);
		config().renewSystemInfo();
	}
	
	private String callerClass(StackTraceElement[] element) {
		String name = null;
				
		try {
			name = RegexMatcher.getNthMatch(element[3].toString(), "([\\w\\.]+)(:.*)?", 0);
		}
		catch (Exception e) {
			try {
				name = RegexMatcher.getNthMatch(element[2].toString(), "([\\w\\.]+)(:.*)?", 0);
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