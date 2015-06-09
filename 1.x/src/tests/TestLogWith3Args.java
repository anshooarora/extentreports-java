package tests;

import com.relevantcodes.extentreports.*;

public class TestLogWith3Args {
	static final ExtentReports extent = ExtentReports.get(TestLogWith3Args.class);
	
	public void test() {
		extent.log(LogStatus.SKIP, "Usage", "Usage: log(logStatus, stepName, details)");
        extent.log(LogStatus.PASS, "Passed!", "This step shows a warning");
        extent.log(LogStatus.FAIL, "Failed!", "<pre>System.Exception : Intentional Exception ->    at NUnit.Tests.Assemblies.MockTestFixture.MethodThrowsException()<br/>Deliberate failure to illustrate ]]> in message  -> at NUnit.Tests.CDataTestFixure.DemonstrateIllegalSequenceInFailureMessage()</pre>");
	}
}
