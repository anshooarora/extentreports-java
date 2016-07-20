package com.relevantcodes.extentreports.testngexamples;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.annotations.BeforeClass;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.common.ExtentManager;

public abstract class ParallelClassBase {
	protected ExtentReports extent;
	protected final String filePath = "src/test/java/com/relevantcodes/extentreports/results/ParallelClassesTest.html";
	
	@BeforeClass
	public void beforeClass() {
		extent = ExtentManager.getReporter(filePath);
	}
	
	protected String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}
}
