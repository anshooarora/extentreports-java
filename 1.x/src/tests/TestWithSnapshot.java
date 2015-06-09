package tests;

import com.relevantcodes.extentreports.*;

public class TestWithSnapshot {
	static final ExtentReports extent = ExtentReports.get(TestWithSnapshot.class);
	
	public void test() {
		extent.log(LogStatus.ERROR, "Absolute Path", "This step shows a screenshot with ERROR status", "http://relevantcodes.com/Tools/ExtentReports/Extent2.png");
		extent.log(LogStatus.INFO, "Relative Path", "This step shows a screenshot with a relative path", "/Tools/ExtentReports/Extent2.png");
	}
}
