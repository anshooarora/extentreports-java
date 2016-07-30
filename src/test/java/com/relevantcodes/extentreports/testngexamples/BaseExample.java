package com.relevantcodes.extentreports.testngexamples;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public abstract class BaseExample {
	protected ExtentReports extent;
	protected ExtentTest test;
	
	protected String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}
	
	@AfterMethod
	protected void afterEachTest(ITestResult result) {
		if (!result.isSuccess() && test != null) {
			test.log(LogStatus.FAIL, result.getThrowable());
		}
		
		if (test != null) {
			extent.endTest(test);
		}
		
		extent.flush();
	}
	
	@AfterSuite
	protected void afterSuite() {
		extent.close();
	}
}
