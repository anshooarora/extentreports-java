package com.relevantcodes.extentreports.testngexamples;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.common.ExtentManager;

@Test
public class ParallelMethodsTest extends BaseExample {
	private final String filePath = "src/test/java/com/relevantcodes/extentreports/results/" + ParallelMethodsTest.class.getSimpleName() + ".html";
	
	@BeforeClass
	public void beforeClass() {
		extent = ExtentManager.getReporter(filePath);
	}
	
	@Test
	public void parallelTest01() {
		ExtentTest test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.INFO, "Log from threadId: " + Thread.currentThread().getId());
		test.log(LogStatus.INFO, "Log from threadId: " + Thread.currentThread().getId());
		extent.endTest(test);
	}
	
	@Test
	public void parallelTest02() {
		ExtentTest test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.ERROR, "Log from threadId: " + Thread.currentThread().getId());
		test.log(LogStatus.ERROR, "Log from threadId: " + Thread.currentThread().getId());
		extent.endTest(test);
	}
	
	@Test
	public void parallelTest03() {
		ExtentTest test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.PASS, "Log from threadId: " + Thread.currentThread().getId());
		test.log(LogStatus.PASS, "Log from threadId: " + Thread.currentThread().getId());
		extent.endTest(test);
	}
	
	@Test
	public void parallelTest04() {
		ExtentTest test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.WARNING, "Log from threadId: " + Thread.currentThread().getId());
		test.log(LogStatus.WARNING, "Log from threadId: " + Thread.currentThread().getId());
		extent.endTest(test);
	}
	
	@Test
	public void parallelTest05() {
		ExtentTest test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.SKIP, "Log from threadId: " + Thread.currentThread().getId());
		test.log(LogStatus.SKIP, "Log from threadId: " + Thread.currentThread().getId());
		extent.endTest(test);
	}
	
	@Test
	public void parallelTest06() {
		ExtentTest test = extent.startTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		test.log(LogStatus.FAIL, "Log from threadId: " + Thread.currentThread().getId());
		test.log(LogStatus.FAIL, "Log from threadId: " + Thread.currentThread().getId());
		extent.endTest(test);
	}
}
