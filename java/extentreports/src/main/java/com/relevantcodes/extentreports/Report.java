package com.relevantcodes.extentreports;

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
	
	protected void flushAll() {
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
	
	public String getTestRunnerLogs() {
		return testRunnerLogs;
	}
	
	public Test getTest() {
		return test;
	}
	
	protected void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	protected void setReplaceExisting(Boolean replaceExisting) {
		this.replaceExisting = replaceExisting;
	}
	
	public Boolean getReplaceExisting() {
		return replaceExisting;
	}
	
	protected void setDisplayOrder(DisplayOrder displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	public DisplayOrder getDisplayOrder() {
		return displayOrder;
	}
	
	protected void setNetworkMode(NetworkMode networkMode) {
		this.networkMode = networkMode;
	}
	
	public NetworkMode getNetworkMode() {
		return networkMode;
	}
	
	public SystemInfo getSystemInfo() {
		return systemInfo;
	}
	
	public List<ExtentTest> getTestList() {
		return testList;
	}
}
