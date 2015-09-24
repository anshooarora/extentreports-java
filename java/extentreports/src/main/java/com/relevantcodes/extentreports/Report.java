package com.relevantcodes.extentreports;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.relevantcodes.extentreports.model.Test;

abstract class Report {
	private String filePath;
	private DisplayOrder displayOrder;
	private NetworkMode networkMode;
	private Boolean replaceExisting;
	
	private String testRunnerLogs;
	
	protected SystemInfo systemInfo;
	
	private List<IReporter> reporters;
	
	protected List<ExtentTest> testList;
	
	private Test test;
	
	protected void attach(IReporter reporter) {
		if (reporters == null) {
			reporters = new ArrayList<IReporter>();
		}
		
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
}
