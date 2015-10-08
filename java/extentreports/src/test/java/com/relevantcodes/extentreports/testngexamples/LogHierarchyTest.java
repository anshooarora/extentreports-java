package com.relevantcodes.extentreports.testngexamples;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.ReporterType;

public class LogHierarchyTest extends BaseExample {
	private final String filePath = "src/test/java/com/relevantcodes/extentreports/results/" + LogHierarchyTest.class.getSimpleName() + ".html";
	
	@BeforeClass
	public void beforeSuite() {
		extent = new ExtentReports(filePath, true);
		extent.startReporter(ReporterType.DB, (new File(filePath)).getParent() + File.separator + "extent.db");
		
		extent.addSystemInfo("Host Name", "Anshoo");
	}
	
	@Test
	public void hierarchicalLogsResultMustEqualFatal() {
		test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.INFO, "Info");
		test.log(LogStatus.PASS, "Pass");
		test.log(LogStatus.FATAL, "Fatal");
		test.log(LogStatus.FAIL, "Fail");
		test.log(LogStatus.ERROR, "Error");
		test.log(LogStatus.WARNING, "Warning");
		test.log(LogStatus.UNKNOWN, "Unknown");
		test.log(LogStatus.SKIP, "Skip");
		
		Assert.assertEquals(test.getRunStatus(), LogStatus.FATAL);
	}
	
	@Test
	public void hierarchicalLogsResultMustEqualFail() {
		test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.INFO, "Info");
		test.log(LogStatus.PASS, "Pass");
		test.log(LogStatus.FAIL, "Fail");
		test.log(LogStatus.ERROR, "Error");
		test.log(LogStatus.WARNING, "Warning");
		test.log(LogStatus.UNKNOWN, "Unknown");
		test.log(LogStatus.SKIP, "Skip");
		
		Assert.assertEquals(test.getRunStatus(), LogStatus.FAIL);
	}
	
	@Test
	public void hierarchicalLogsResultMustEqualError() {
		test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.INFO, "Info");
		test.log(LogStatus.PASS, "Pass");
		test.log(LogStatus.ERROR, "Error");
		test.log(LogStatus.WARNING, "Warning");
		test.log(LogStatus.UNKNOWN, "Unknown");
		test.log(LogStatus.SKIP, "Skip");
		
		Assert.assertEquals(test.getRunStatus(), LogStatus.ERROR);
	}
	
	@Test
	public void hierarchicalLogsResultMustEqualWarning() {
		test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.INFO, "Info");
		test.log(LogStatus.PASS, "Pass");
		test.log(LogStatus.WARNING, "Warning");
		test.log(LogStatus.UNKNOWN, "Unknown");
		test.log(LogStatus.SKIP, "Skip");
		
		Assert.assertEquals(test.getRunStatus(), LogStatus.WARNING);
	}
	
	@Test
	public void hierarchicalLogsResultMustEqualPass1() {
		test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.INFO, "Info");
		test.log(LogStatus.PASS, "Pass");
		test.log(LogStatus.UNKNOWN, "Unknown");
		test.log(LogStatus.SKIP, "Skip");
		
		Assert.assertEquals(test.getRunStatus(), LogStatus.PASS);
	}
	
	@Test
	public void hierarchicalLogsResultMustEqualPass2() {
		test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.INFO, "Info");
		test.log(LogStatus.UNKNOWN, "Unknown");
		test.log(LogStatus.SKIP, "Skip");
		
		Assert.assertEquals(test.getRunStatus(), LogStatus.PASS);
	}
	
	@Test
	public void hierarchicalLogsResultMustEqualSkip() {
		test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.UNKNOWN, "Unknown");
		test.log(LogStatus.SKIP, "Skip");
		
		Assert.assertEquals(test.getRunStatus(), LogStatus.SKIP);
	}
	
	@Test
	public void hierarchicalLogsResultMustEqualPass3() {
		test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.INFO, "Info");
		test.log(LogStatus.UNKNOWN, "Unknown");
		
		Assert.assertEquals(test.getRunStatus(), LogStatus.PASS);
	}
}
