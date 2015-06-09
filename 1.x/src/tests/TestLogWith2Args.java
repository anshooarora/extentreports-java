package tests;

import com.relevantcodes.extentreports.*;

public class TestLogWith2Args {
	static final ExtentReports extent = ExtentReports.get(TestLogWith2Args.class);
	
	public void test() {
		extent.log(LogStatus.PASS, "This step shows usage of log(logStatus, details)");
		extent.log(LogStatus.INFO, "The log message below shows a pre block");
		extent.log(LogStatus.INFO, "<pre>You can insert anything in code-style format by enclosing your string in &lt;pre&gt;&lt;/pre&gt; tags</pre>");
		extent.log(LogStatus.INFO, "This is a <span class='label success'>success label</span> which you can create using: <p></p> <pre>&lt;span class='label success'&gt;description&lt;/span&gt;</pre>");
		extent.log(LogStatus.INFO, "This is a <span class='label failure'>fail label</span> which you can create using: <p></p> <pre>&lt;span class='label failure'&gt;description&lt;/span&gt;</pre>");
		extent.log(LogStatus.INFO, "This is a <span class='label info'>info label</span> which you can create using: <p></p> <pre>&lt;span class='label info'&gt;description&lt;/span&gt;</pre>");
		extent.log(LogStatus.INFO, "This is a <span class='label warn'>warning label</span> which you can create using: <p></p> <pre>&lt;span class='label warn'&gt;description&lt;/span&gt;</pre>");
	}
}