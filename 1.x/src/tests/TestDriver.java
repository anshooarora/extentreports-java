/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/


package tests;

import com.relevantcodes.extentreports.*;

class TestDriver {
	static final ExtentReports extent = ExtentReports.get("FrameworkDriver");
	
	public static void main(String[] args) {
		String strPath = "C:\\Users\\Anshoo\\Documents\\workspace\\Extent.html";
		
		extent.init(strPath, false, DisplayOrder.BY_OLDEST_TO_LATEST, GridType.STANDARD);
		
		
		extent.config()
			.reportHeadline("My first report.")
			.useExtentFooter(false)
			.displayCallerClass(false)
			.reportTitle("MyReports")
			.documentTitle("Insert your title here..")
			.addCustomStylesheet("C:\\testFile.css")
			.addCustomStylesheet("/a.css")
			.addCustomStylesheet("./b.css");
		
		String js = "$('td.step-name').text(function () { " +
            "});";

		extent.config().insertJS(js);
		
		extent.startTest("Log with 2 Args, Shows Labels", "This test creates logs with 2 args: log(LogStatus, details).");
		new TestLogWith2Args().test();
		
		extent.startTest("Test Shows &lt;pre&gt; block");
		new SimpleTest().testName();
			
		extent.startTest("Log with 3 Args", "This test creates logs with 3 args: log(LogStatus, stepname, details)");
		new TestLogWith3Args().test();
		
		extent.startTest("Shows All Status Types");
		new StatusTest().test();
		
		extent.startTest("Log with 4 Args, Attaches Screenshot", "This test creates logs with 4 args: log(LogStatus, stepName, details, screenCapturePath)");
		new TestWithSnapshot().test();
		
		extent.startTest("Attaches Screenshot only", "This test creates logs with only snapshots using attachScreenshot(screenCapturePath)");
		new AttachesScreenshot().test();
		
		extent.startTest("HTML Tags");
		new OtherHTML().test();

		
		/** REPORT 2 **/
		/*strPath = "C:\\Users\\Sai Baba\\Documents\\workspace\\Extent2.html";
		js = "$('td.step-name').text(function () { " +
	            "});";
		
		extent.init(strPath, true, GridType.MASONRY);
		
		extent.config()
			.reportHeadline("I am using ExtentReports.")
			.useExtentFooter(false)
			.documentTitle("DocumentTitle..")
			.displayCallerClass(false)
			.setImageSize("30%");
			//.reportTitle("Extent2Title")
			//.chartTitle(Chart.TEST, "Your New Title")
			//.chartTitle(Chart.TEST_SET, "Your New Title")
			//.insertJS(js);
		
		extent.startTest("Log with 2 Args, Shows Labels", "This test creates logs with 2 args: log(LogStatus, details).");
		new TestLogWith2Args().test();
		
		extent.startTest("Test Shows &lt;pre&gt; block");
		new SimpleTest().testName();
			
		extent.startTest("Log with 3 Args", "This test creates logs with 3 args: log(LogStatus, stepname, details)");
		new TestLogWith3Args().test();
		
		extent.startTest("Shows All Status Types");
		new StatusTest().test();
		
		extent.startTest("Log with 4 Args, Attaches Screenshot", "This test creates logs with 4 args: log(LogStatus, stepName, details, screenCapturePath)");
		new TestWithSnapshot().test();
		
		extent.startTest("Attaches Screenshot only", "This test creates logs with only snapshots using attachScreenshot(screenCapturePath)");
		new AttachesScreenshot().test();
		
		extent.startTest("HTML Tags");
		new OtherHTML().test();
		*/
	}
}
