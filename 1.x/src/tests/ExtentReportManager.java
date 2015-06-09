package tests;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReportManager {
	public ExtentReports extent = null;

	public ExtentReportManager(String reportName,String className) {
		extent = ExtentReports.get(className); 
		extent.init(reportName, true);
		extent.config().statusIcon(LogStatus.PASS, "check-circle");
		extent.config().statusIcon(LogStatus.FAIL, "times-circle");
	}

	public void reportPass(String stepName, String details) {
		extent.log(LogStatus.PASS, stepName, details);
	}

	public void reportFail(String stepName,String details,String screenshotPath) {
		extent.log(LogStatus.FAIL, stepName, details,screenshotPath);
	}

	public void reportException(String stepName, String details,String screenshotPath) {
		extent.log(LogStatus.FATAL, stepName, details,screenshotPath);
	}

	public void reportInfo(String stepName, String details) {
		extent.log(LogStatus.INFO, stepName, details);
	}

	public void reportWarning(String stepName, String details) {
		extent.log(LogStatus.WARNING, stepName, details);
	}

	public void reportStartTest(String name, String description) {
		extent.startTest(name, description);
	}

	public void reportEndTest() {
		extent.endTest();
	}
}
