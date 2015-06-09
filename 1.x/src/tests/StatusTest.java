package tests;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

public class StatusTest {
	static final ExtentReports extent = ExtentReports.get(TestLogWith2Args.class);
	
	public void test() {
		extent.log(LogStatus.ERROR, "ERROR");
		extent.log(LogStatus.FAIL, "FAIL");
		extent.log(LogStatus.FATAL, "FATAL");
		extent.log(LogStatus.INFO, "INFO");
		extent.log(LogStatus.PASS, "PASS");
		extent.log(LogStatus.SKIP, "SKIP");
		extent.log(LogStatus.WARNING, "WARNING");
	}
}
