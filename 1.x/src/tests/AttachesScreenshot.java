package tests;

import com.relevantcodes.extentreports.*;

public class AttachesScreenshot {
	static final ExtentReports extent = ExtentReports.get(AttachesScreenshot.class);
	
	public void test() {
		extent.attachScreenshot("http://relevantcodes.com/Tools/ExtentReports/Extent2.png", "This step only attaches a screenshot without a status.");
		extent.attachScreenshot("http://relevantcodes.com/Tools/ExtentReports/Extent2.png");
	}
}
