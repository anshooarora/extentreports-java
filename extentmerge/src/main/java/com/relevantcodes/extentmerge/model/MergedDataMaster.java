package com.relevantcodes.extentmerge.model;

import java.util.List;

import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.Test;

/**
 * ReportMaster is the overall representation of the merged view
 * 
 * @author Anshoo
 *
 */
public class MergedDataMaster {
	private List<Report> reportList;
	
	private int testCount = -1;
	private int passedTestsCount = 0;
	private int failedTestsCount = 0;
	private int othersTestsCount = 0;
	
	private int stepCount = -1;
	private int passedStepsCount = 0;
	private int failedStepsCount = 0;
	private int othersStepsCount = 0;
	
	public int getOverallTestCount() {
		generateMasterCounts();
		
		return testCount;
	}
	
	public int getOverallTestPassedCount() {
		generateMasterCounts();
		
		return passedTestsCount;
	}
	
	public int getOverallTestFailedCount() {
		generateMasterCounts();
		
		return failedTestsCount;
	}
	
	public int getOverallTestOthersCount() {
		generateMasterCounts();
		
		return othersTestsCount;
	}
	
	public int getOverallStepCount() {
		generateMasterCounts();
		
		return stepCount;
	}
	
	public int getOverallStepPassedCount() {
		generateMasterCounts();
		
		return passedStepsCount;
	}
	
	public int getOverallStepFailedCount() {
		generateMasterCounts();
		
		return failedStepsCount;
	}
	
	public int getOverallStepOthersCount() {
		generateMasterCounts();
		
		return othersStepsCount;
	}
	
	private void generateMasterCounts() {
		if (testCount != -1) {
			return;
		}
		
		for (Report report : reportList) {
			generateMasterCounts(report.getTestList());
		}
	}
	
	private void generateMasterCounts(List<Test> testList) {
		for (Test test : testList) {
			if (test.hasChildNodes) {
				generateMasterCounts(test.getNodeList());
			}
			else {
				testCount++;
				
				passedTestsCount += test.getStatus() == LogStatus.PASS ? 1 : 0; 
				failedTestsCount += test.getStatus() == LogStatus.FAIL || test.getStatus() == LogStatus.FATAL ? 1 : 0;
				othersTestsCount += test.getStatus() != LogStatus.PASS && test.getStatus() != LogStatus.FAIL && test.getStatus() != LogStatus.FATAL ? 1 : 0;
				
				for (Log log : test.getLog()) {
					stepCount++;
					
					passedStepsCount += log.getLogStatus() == LogStatus.PASS ? 1 : 0;
					failedStepsCount += log.getLogStatus() == LogStatus.FAIL || log.getLogStatus() == LogStatus.FATAL ? 1 : 0;
					othersStepsCount += log.getLogStatus() != LogStatus.PASS && log.getLogStatus() != LogStatus.FAIL && log.getLogStatus() != LogStatus.FATAL ? 1 : 0;
				}
			}
		}
	}
	
	public MergedDataMaster(List<Report> reportList) {
		this.reportList = reportList;
	}
	
	
}
