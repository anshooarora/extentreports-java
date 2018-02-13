package com.aventstack.extentreports.api;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;

public class BuildReportWithoutAttachedReportersTest {

	private ExtentReports extent;
	
	@BeforeClass
	public void setup() {
		extent = new ExtentReports();
	}
	
	@Test(expectedExceptions=IllegalStateException.class)
	public void createTestWithoutAttachedReporter(Method method) {
		extent.createTest(method.getName()).pass("pass");
	}
	
	@Test(expectedExceptions=IllegalStateException.class)
	public void flushWithoutAttachedReporter() {
		extent.flush();
	}
}
