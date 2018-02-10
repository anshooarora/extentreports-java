package com.aventstack.extentreports.api;

import java.lang.reflect.Method;
import java.util.Optional;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Base;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.BasicFileReporter;

public class RemoveStartedTestsFromExtentTest extends Base {

	private BasicFileReporter fileReporter;
	
	@BeforeMethod
	public void beforeClass() {
		setup();
		
		Optional<BasicFileReporter> fileReporter = extent
			.getStartedReporters()
			.stream()
			.filter(x -> x instanceof BasicFileReporter)
			.map(x -> (BasicFileReporter)x)
			.findFirst();
		if (fileReporter.get() != null)
			this.fileReporter = fileReporter.get();
	}
	
	@Test
	public void removeAnErroredTest(Method method) {
		if (fileReporter == null)
			throw new SkipException("No BasicFileReporter were started.");		
		
		extent.createTest("Pass").pass("Hello");
		ExtentTest test = extent.createTest("Error").error("Hello");
		extent.removeTest(test);
		extent.flush();
		
		boolean b = fileReporter.getStatusCollection().contains(Status.ERROR);
		Assert.assertFalse(b, "Error status was removed, collection still contains it");
		
		b = fileReporter.getStatusCollection().contains(Status.PASS);
		Assert.assertTrue(b, "Pass status was not removed, collection does not contains it");
	}
	
	@Test
	public void removeAPassedTest(Method method) {
		if (fileReporter == null)
			throw new SkipException("No BasicFileReporter were started.");		
		
		ExtentTest test = extent.createTest("Pass").pass("Hello");
		extent.createTest("Error").error("Hello");
		extent.removeTest(test);
		extent.flush();
		
		boolean b = fileReporter.getStatusCollection().contains(Status.PASS);
		Assert.assertFalse(b, "Pass status was removed, collection still contains it");
		
		b = fileReporter.getStatusCollection().contains(Status.ERROR);
		Assert.assertTrue(b, "Error status was not removed, collection does not contains it");
	}
	
}
