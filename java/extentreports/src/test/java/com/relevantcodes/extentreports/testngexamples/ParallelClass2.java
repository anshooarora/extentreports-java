package com.relevantcodes.extentreports.testngexamples;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ParallelClass2 extends ParallelClassBase {
	private ExtentTest test;
	
	@Test
	public void parallelClass2TestResultMustEqualFail() {
		test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.FAIL, "Log from threadId: " + Thread.currentThread().getId());
		test.log(LogStatus.FAIL, "Log from threadId: " + Thread.currentThread().getId());
		test.log(LogStatus.INFO, "Log from threadId: " + Thread.currentThread().getId());
		test.log(LogStatus.INFO, "Log from threadId: " + Thread.currentThread().getId());
		
		Assert.assertEquals(test.getRunStatus(), LogStatus.FAIL);
	}
	
	@Test
	public void parallelClass2TestResultMustEqualFatal() {
		test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.FATAL, "Log from threadId: " + Thread.currentThread().getId());
		test.log(LogStatus.FATAL, "Log from threadId: " + Thread.currentThread().getId());
		test.log(LogStatus.FAIL, "Log from threadId: " + Thread.currentThread().getId());
		test.log(LogStatus.FAIL, "Log from threadId: " + Thread.currentThread().getId());
		
		Assert.assertEquals(test.getRunStatus(), LogStatus.FATAL);
	}
	
	@AfterMethod
	protected void afterEachTest(ITestResult result) {
		if (!result.isSuccess()) {
			test.log(LogStatus.FAIL, "<pre>" + getStackTrace(result.getThrowable()) + "</pre>");
		}
		
		extent.endTest(test);
		extent.flush();
	}
}
