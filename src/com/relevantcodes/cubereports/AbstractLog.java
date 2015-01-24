/*
Copyright 2015 Cube Reports committer(s)

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


package com.relevantcodes.cubereports;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

abstract class AbstractLog {
	protected LogStatus logStatus;
	protected String stepName;
	protected String details;
	protected String screenCapturePath;
	protected String testName;
	protected String summary;
	protected Date startTime;
	protected Date endTime;
	protected Long timeDiff;
	protected String timeUnit;
	
	private LogLevel level = LogLevel.ALLOW_ALL;
	private LogStatus lastRunStatus = LogStatus.PASS;
	
	public void log(LogStatus logStatus, String stepName, String details, String screenCapturePath)
	{	
		if (testName == "") return;
		
		this.logStatus = logStatus;
		this.stepName = stepName;
		this.details = details;
		this.screenCapturePath = screenCapturePath;
		
		trackLastRunStatus();
		
		if (canLog()) {
			log();
		}
	}
	
	public void log(LogStatus logStatus, String stepName, String details)
	{
        log(logStatus, stepName, details, "");
	}
	
	protected abstract void log();
	
	public void startTest(String name) {
		testName = name;
		lastRunStatus = LogStatus.PASS;
		startTime = Calendar.getInstance().getTime();
		
		startTest();
	}
	
	protected abstract void startTest();
	
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
			case FATAL: return;
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
		
		lastRunStatus = logStatus;
	}
}
