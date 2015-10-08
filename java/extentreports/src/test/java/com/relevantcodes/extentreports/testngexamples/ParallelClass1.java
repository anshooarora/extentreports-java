package com.relevantcodes.extentreports.testngexamples;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ParallelClass1 extends ParallelClassBase {
	private ExtentTest test;
	
	@Test
	public void parallelClass1TestResultMustEqualPass() {
		test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.PASS, "Log from threadId: " + Thread.currentThread().getId());
		test.log(LogStatus.PASS, "Log from threadId: " + Thread.currentThread().getId());
		test.log(LogStatus.INFO, "Log from threadId: " + Thread.currentThread().getId());
		test.log(LogStatus.INFO, "Log from threadId: " + Thread.currentThread().getId());
		
		Assert.assertEquals(test.getRunStatus(), LogStatus.PASS);
	}
	
	@Test
	public void parallelClass1TestResultMustEqualError() {
		test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.ERROR, "Log from threadId: " + Thread.currentThread().getId());
		test.log(LogStatus.ERROR, "Log from threadId: " + Thread.currentThread().getId());
		test.log(LogStatus.WARNING, "Log from threadId: " + Thread.currentThread().getId());
		test.log(LogStatus.WARNING, "Log from threadId: " + Thread.currentThread().getId());
		
		Assert.assertEquals(test.getRunStatus(), LogStatus.ERROR);
	}
	
	@AfterMethod
	public void afterEachTest(ITestResult result) {
		if (!result.isSuccess()) {
			test.log(LogStatus.FAIL, "<pre>" + getStackTrace(result.getThrowable()) + "</pre>");
		}
		
		extent.endTest(test);
		extent.flush();
	}
}
