package com.relevantcodes.extentreports.testngexamples;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.ReporterType;

public class SingleLogTest extends BaseExample {
	private final String filePath = "src/test/java/com/relevantcodes/extentreports/results/" + SingleLogTest.class.getSimpleName() + ".html";
	
	@BeforeClass
	public void beforeClass() {
		extent = new ExtentReports(filePath, true);
		extent.startReporter(ReporterType.DB, (new File(filePath)).getParent() + File.separator + "extent.db");
		
		extent.addSystemInfo("Host Name", "Anshoo");
	}
	
	@Test
	public void infoTest() {
		test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.INFO, "Info");
		
		Assert.assertEquals(test.getRunStatus(), LogStatus.PASS);
	}
	
	@Test
	public void passTest() {
		test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.PASS, "Pass");
		
		Assert.assertEquals(test.getRunStatus(), LogStatus.PASS);
	}
	
	@Test
	public void failTest() {
		test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.FAIL, "Fail");
		
		Assert.assertEquals(test.getRunStatus(), LogStatus.FAIL);
	}
	
	@Test
	public void fatalTest() {
		test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.FATAL, "Fatal");
		
		Assert.assertEquals(test.getRunStatus(), LogStatus.FATAL);
	}
	
	@Test
	public void errorTest() {
		test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.ERROR, "Error");
		
		Assert.assertEquals(test.getRunStatus(), LogStatus.ERROR);
	}
	
	@Test
	public void warningTest() {
		test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.WARNING, "Warning");
		
		Assert.assertEquals(test.getRunStatus(), LogStatus.WARNING);
	}
	
	@Test
	public void skipTest() {
		test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.SKIP, "Skip");
		
		Assert.assertEquals(test.getRunStatus(), LogStatus.SKIP);
	}
	
	@Test
	public void unknownTest() {
		test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		Assert.assertEquals(test.getRunStatus(), LogStatus.UNKNOWN);
	}
}
