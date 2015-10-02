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
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.relevantcodes.extentreports.model.SuiteTimeInfo;
import com.relevantcodes.extentreports.model.Test;

// Report abstract
abstract class Report extends LogSettings {
	private String filePath;
	private DisplayOrder displayOrder;
	private NetworkMode networkMode;
	private Boolean replaceExisting;
	private LogStatus reportStatus = LogStatus.UNKNOWN;	
	
	private Date startedTime;
	
	private String testRunnerLogs;
	private List<IReporter> reporters;

	private Test test;
	
	private UUID reportId;

	protected SuiteTimeInfo suiteTimeInfo;
	protected SystemInfo systemInfo;
	
	protected List<ExtentTest> testList;
	
	protected void attach(IReporter reporter) {
		if (reporters == null) {
			reporters = new ArrayList<IReporter>();
		}
		
		suiteTimeInfo = new SuiteTimeInfo();
		
		reporters.add(reporter);
		reporter.start(this);
	}
	
	protected void detach(IReporter reporter) {
		reporter.stop();
		reporters.remove(reporter);
	}
	
	protected void addTest(Test test) {		
		if (test.getEndedTime() == null) {
            test.setEndedTime(Calendar.getInstance().getTime());
        }
		
		test.prepareFinalize();
		
		this.test = test;
		
		for (IReporter reporter : reporters) {
			reporter.addTest();
		}
		
		updateReportStatus(test.getStatus());
	}
	
	protected void terminate() {
		for (IReporter reporter : reporters) {
			detach(reporter);
		}
	}
	
	protected void flush() {
		for (IReporter reporter : reporters) {
			reporter.flush();			
		}
	}
	
	protected void setTestRunnerLogs(String logs) {
		testRunnerLogs = logs;
		
		for (IReporter reporter : reporters) {
			reporter.setTestRunnerLogs();	
		}
	}
	
	protected String getTestRunnerLogs() {
		return testRunnerLogs;
	}
	
	protected Test getTest() {
		return test;
	}
	
	protected void setFilePath(String filePath) {
		this.filePath = filePath;
		
		File reportFile = new File(filePath);
		
		if (!reportFile.getParentFile().exists()) {
            reportFile.getParentFile().mkdirs();
        }
	}
	
	protected String getFilePath() {
		return filePath;
	}
	
	protected void setReplaceExisting(Boolean replaceExisting) {
		this.replaceExisting = replaceExisting;
	}
	
	protected Boolean getReplaceExisting() {
		return replaceExisting;
	}
	
	protected void setDisplayOrder(DisplayOrder displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	protected DisplayOrder getDisplayOrder() {
		return displayOrder;
	}
	
	protected void setNetworkMode(NetworkMode networkMode) {
		this.networkMode = networkMode;
	}
	
	protected NetworkMode getNetworkMode() {
		return networkMode;
	}
	
	protected SystemInfo getSystemInfo() {
		return systemInfo;
	}
	
	protected List<ExtentTest> getTestList() {
		return testList;
	}
	
	protected UUID getId() {
		return reportId;
	}
	
	protected Date getStartedTime() {
		return startedTime;
	}
	
	protected LogStatus getStatus() {
		return reportStatus;
	}
	
	protected SuiteTimeInfo getSuiteTimeInfo() {
		return suiteTimeInfo;
	}
	
	private void updateReportStatus(LogStatus logStatus) {
        if (reportStatus == LogStatus.FATAL) return;
        
        if (logStatus == LogStatus.FATAL) {
            reportStatus = logStatus;
            return;
        }
        
        if (reportStatus == LogStatus.FAIL) return;
        
        if (logStatus == LogStatus.FAIL) {
            reportStatus = logStatus;
            return;
        }
        
        if (reportStatus == LogStatus.ERROR) return;
        
        if (logStatus == LogStatus.ERROR) {
            reportStatus = logStatus;
            return;
        }
        
        if (reportStatus == LogStatus.WARNING) return;
        
        if (logStatus == LogStatus.WARNING) {
            reportStatus = logStatus;
            return;
        }
        
        if (reportStatus == LogStatus.PASS) return;
        
        if (logStatus == LogStatus.PASS) {
            reportStatus = LogStatus.PASS;
            return;
        }
        
        if (reportStatus == LogStatus.SKIP) return;
        
        if (logStatus == LogStatus.SKIP) {
            reportStatus = LogStatus.SKIP;
            return;
        }
        
        if (reportStatus == LogStatus.INFO) return;
        
        if (logStatus == LogStatus.INFO) {
            reportStatus = LogStatus.INFO;
            return;
        }
        
        reportStatus = LogStatus.UNKNOWN;
    }
	
	public void setStartedTime(long startTime) {
		suiteTimeInfo.setSuiteStartTimestamp(startTime);
	}
	
	protected Report() {
		reportId = UUID.randomUUID();
		startedTime = Calendar.getInstance().getTime();
	}
}
