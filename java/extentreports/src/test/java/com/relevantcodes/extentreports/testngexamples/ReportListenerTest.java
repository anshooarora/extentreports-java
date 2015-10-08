package com.relevantcodes.extentreports.testngexamples;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

@Listeners(ExtentReporterNG.class)
public class ReportListenerTest {		
	@Test
	public void intentionalFailure() {
		Assert.assertEquals(LogStatus.PASS, LogStatus.WARNING);
	}
	
	@Test
	public void passTest() {
		Assert.assertEquals(LogStatus.PASS, LogStatus.PASS);
	}
}
