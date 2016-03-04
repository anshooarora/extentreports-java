package com.relevantcodes.extentreports.testngexamples;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

@Listeners(ExtentReporterNG.class)
public class ReportListenerTest {		
	@Test
	public void intentionalFailure() {
		Reporter.log("Started intentionalFailure");
		
		try { Thread.sleep(2000); } catch(Exception e) { }
		
		Assert.assertTrue(false);
		Assert.assertEquals(LogStatus.PASS, LogStatus.WARNING);
	}
	
	@Test
	public void passTest() {
		Reporter.log("Started passTest");
		
		try { Thread.sleep(2000); } catch(Exception e) { }
		
		Assert.assertEquals(LogStatus.PASS, LogStatus.PASS);
	}
}
