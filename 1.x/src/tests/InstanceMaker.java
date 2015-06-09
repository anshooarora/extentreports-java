package tests;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

public class InstanceMaker {
	/*
	public static void main(String[] args) {
		ExtentReports e1 = new ExtentReports();
		ExtentReports e2 = new ExtentReports();
		
		e1.init("C:\\Users\\Sai Baba\\Documents\\workspace\\Extent1.html", true);
		e2.init("C:\\Users\\Sai Baba\\Documents\\workspace\\Extent2.html", true);
		
		e1.config()
		.reportHeadline("E1.")
		.useExtentFooter(false)
		.documentTitle("E1")
		.displayCallerClass(false)
		.setImageSize("40%")
		.addCustomStylesheet("C:\\testFile.css")
		.addCustomStylesheet("/a.css")
		.addCustomStylesheet("./b.css");
		
		e2.config()
		.reportHeadline("E2.")
		.useExtentFooter(true)
		.documentTitle("E2")
		.displayCallerClass(false)
		.setImageSize("40%")
		.addCustomStylesheet("C:\\testFile.css")
		.addCustomStylesheet("/a.css")
		.addCustomStylesheet("./b.css");
		
		e1.startTest("E1");
		e2.startTest("E2");

		new SimpleTest().testName(e1);
		new SimpleTest().testName(e2);
		e1.log(LogStatus.FAIL, "MORE DETAILS");
		e2.log(LogStatus.ERROR, "Additional status");
	}
	*/
}
