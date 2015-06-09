package tests;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

public class SimpleTest {
	static final ExtentReports extent = ExtentReports.get(TestLogWith2Args.class);
	
	public void testName() {
		extent.log(LogStatus.INFO, "This step shows usage of log(logStatus, details)");
		extent.log(LogStatus.PASS, "Notice the StepName column missing for this test.");
		extent.log(LogStatus.WARNING, "This type of log provides details without specifying the stepName.");
		extent.log(LogStatus.WARNING, "This test has no description..");
		extent.log(LogStatus.INFO, "The log message below shows a pre block.<p></p> <pre>You can insert anything in code-style format by enclosing your string in &lt;pre&gt;&lt;/pre&gt; tags</pre>");
		extent.log(LogStatus.INFO, "<pre>java.lang.NullPointerException \n" +
						"at java.lang.String.replace(Unknown Source) \n" +
						"at com.relevantcodes.extentreports.Logger.log(Logger.java:63) \n" +
						"at com.relevantcodes.extentreports.AbstractLog.log(AbstractLog.java:64) \n" +
						"at com.relevantcodes.extentreports.AbstractLog.log(AbstractLog.java:73) \n" +
						"at com.relevantcodes.extentreports.ExtentReports.log(ExtentReports.java:160) \n" +
						"at com.mytests.TestOne.testA(TestOne.java:37) \n" +
						"at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) \n" +
						"at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source) \n" +
						"at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source) \n" +
						"at java.lang.reflect.Method.invoke(Unknown Source) \n" +
						"at org.testng.internal.MethodInvocationHelper.invokeMethod(MethodInvocationHelper.java:84) \n" +
						"at org.testng.internal.Invoker.invokeMethod(Invoker.java:714) \n" +
						"at org.testng.internal.Invoker.invokeTestMethod(Invoker.java:901) \n" +
						"at org.testng.internal.Invoker.invokeTestMethods(Invoker.java:1231) \n" +
						"at org.testng.internal.TestMethodWorker.invokeTestMethods(TestMethodWorker.java:127) \n" +
						"at org.testng.internal.TestMethodWorker.run(TestMethodWorker.java:111) \n" +
						"at java.util.concurrent.ThreadPoolExecutor.runWorker(Unknown Source) \n" +
						"at java.util.concurrent.ThreadPoolExecutor$Worker.run(Unknown Source) \n" +
						"at java.lang.Thread.run(Unknown Source) \n" +
					"</pre>");
	}
	
	public void testName(ExtentReports extent) {
		extent.log(LogStatus.INFO, "This step shows usage of log(logStatus, details)");
		extent.log(LogStatus.PASS, "Notice the StepName column missing for this test.");
		extent.log(LogStatus.WARNING, "This type of log provides details without specifying the stepName.");
		extent.log(LogStatus.WARNING, "This test has no description..");
	}
}
