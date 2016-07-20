package com.relevantcodes.extentreports.testngexamples;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.ReporterType;

public class ExtentReporterNG implements IReporter {
	private ExtentReports extent;

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		extent = new ExtentReports("src/test/java/com/relevantcodes/extentreports/results/ReportListenerTest.html", true);
		extent.startReporter(ReporterType.DB, "src/test/java/com/relevantcodes/extentreports/results/extent.db");
		
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();
			
			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();
				
				buildTestNodes(context.getPassedTests(), LogStatus.PASS);
				buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
				buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
			}
		}
		
		for (String s : Reporter.getOutput()) {
			extent.setTestRunnerOutput(s);
		}
		
		extent.flush();
		extent.close();
	}
	
	private void buildTestNodes(IResultMap tests, LogStatus status) {
		ExtentTest test;
		
		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				test = extent.startTest(result.getMethod().getMethodName());

				test.setStartedTime(getTime(result.getStartMillis()));
				test.setEndedTime(getTime(result.getEndMillis()));
				
				for (String group : result.getMethod().getGroups())
					test.assignCategory(group);

				if (result.getThrowable() != null) {
					test.log(status, result.getThrowable());
				}
				else {
					test.log(status, "Test " + status.toString().toLowerCase() + "ed");
				}
				
				extent.endTest(test);
			}
		}
	}
	
	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();		
	}
}