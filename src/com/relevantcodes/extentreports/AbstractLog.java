/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/


package com.relevantcodes.extentreports;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

abstract class AbstractLog {
	protected Date startTime;
	protected Date endTime;
	protected Integer testCounter = 0;
	protected Integer testsPassed = 0;
	protected Integer testsFailed = 0;
	protected Integer stepCounter = 0;
	protected Integer stepsPassed = 0;
	protected Integer stepsFailed = 0;
	protected LogStatus logStatus;
	protected Long timeDiff;
	protected String stepName;
	protected String details;
	protected String message;
	protected String screenCapturePath = "";
	protected String testName;
	protected String testDescription;
	protected String summary;
	protected String timeUnit;
	protected String caller;
	
	private LogLevel level = LogLevel.ALLOW_ALL;
	private LogStatus lastRunStatus = LogStatus.PASS;
	
	public void log(LogStatus logStatus, String stepName, String details, String screenCapturePath)
	{	
		if (testName == "") return;
		
		stepCounter++;
		
		if (logStatus == LogStatus.PASS)
			stepsPassed++;
		else if (logStatus == LogStatus.FAIL || logStatus == LogStatus.FATAL)
			stepsFailed++;
		
		this.logStatus = logStatus;
		this.stepName = stepName;
		this.details = details;
		this.screenCapturePath = screenCapturePath;
		
		if (stepName != null)
			this.stepName = "[" + caller + "] " + stepName;
		else
			this.details = "[" + caller + "] " + details;
		
		trackLastRunStatus();
		
		if (canLog()) {
			log();
		}
	}
	
	public void log(LogStatus logStatus, String stepName, String details) {
        log(logStatus, stepName, details, "");
	}
	
	public void log(LogStatus logStatus, String details) {
		log(logStatus, null, details, "");
	}

	protected abstract void log();
	
	public void startTest(String name, String description) {
		if (testCounter != 0)
			if (getLastRunStatus() == LogStatus.PASS)
				testsPassed++;
			else if (getLastRunStatus() == LogStatus.FAIL || getLastRunStatus() == LogStatus.FATAL || getLastRunStatus() == LogStatus.ERROR)
				testsFailed++;
		
		testCounter++;
		
		testName = name;
		testDescription = description;
		startTime = Calendar.getInstance().getTime();	
		
		startTest();
		
		lastRunStatus = LogStatus.PASS;
	}
	
	public void startTest(String name) {
		startTest(name, "");
	}
	
	protected abstract void startTest();
	
	public void attachScreenshot(String screenCapturePath, String message) {
		this.message = message;
		this.screenCapturePath = screenCapturePath;
		
		attachScreenshot();
		
		this.message = "";
		this.screenCapturePath = "";
	}
	
	protected abstract void attachScreenshot();
	
	protected void endTest(String name) {
		endTime = Calendar.getInstance().getTime();
		timeDiff = TimeUnit.MILLISECONDS.toSeconds(endTime.getTime() - startTime.getTime());
		timeDiff = timeDiff > 60 ? timeDiff / 60 : timeDiff;
		timeUnit = timeDiff > 60 ? "mins" : "secs";
		lastRunStatus = lastRunStatus == LogStatus.INFO ? LogStatus.PASS : lastRunStatus; 
		
		endTest();
		
		lastRunStatus = LogStatus.PASS;
	}
	
	protected abstract void endTest();
	
	public void setLogLevel(LogLevel level) {
		this.level = level;
	}
	
	public LogStatus getLastRunStatus() {				
		return lastRunStatus;
	}
	
	private Boolean canLog() {
		switch (level) {
			case ALLOW_ALL:
				return true;
			case OFF: 
				break;
			case FAIL:
				if (logStatus == LogStatus.FAIL) 
					return true;
				break;
			case ERROR:
				if (logStatus == LogStatus.FAIL || logStatus == LogStatus.ERROR) 
					return true;
				break;
			case WARNING:
				if (logStatus == LogStatus.FAIL || logStatus == LogStatus.WARNING) 
					return true;
				break;
			case ERRORS_AND_WARNINGS:
				if (logStatus == LogStatus.FAIL || logStatus == LogStatus.WARNING || logStatus == LogStatus.ERROR)
					return true;
				break;
			default: 
				break;
		}
		
		return false;
	}
	
	private void trackLastRunStatus() {
		switch (lastRunStatus) {
			case FATAL:
				return;
			case FAIL:
				if (logStatus == LogStatus.FATAL) {
					lastRunStatus = logStatus;
				}
				return;
			case ERROR: 
				if (logStatus == LogStatus.FAIL) {
					lastRunStatus = logStatus;
				}
				return;
			case WARNING: 
				if (logStatus == LogStatus.ERROR) {
					lastRunStatus = logStatus;
				}
				return;
			default: break;
		}
		
		if (logStatus == LogStatus.INFO || logStatus == LogStatus.SKIP)
			lastRunStatus = LogStatus.PASS;
		else
			lastRunStatus = logStatus;
	}
}
