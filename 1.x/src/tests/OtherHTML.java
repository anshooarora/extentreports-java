package tests;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

public class OtherHTML {
	static final ExtentReports extent = ExtentReports.get(TestLogWith2Args.class);
	
	public void test() {
		extent.log(LogStatus.INFO, "You can insert a <a href='http://relevantcodes.com'>link</a> using <p></p> <pre>&lt;a href='http://url'&gt;&lt;/a&gt; tag</pre>");
		extent.log(LogStatus.INFO, "This is a <span class='label success'>success label</span> which you can create using: <p></p> <pre>&lt;span class='label success'&gt;description&lt;/span&gt;</pre>");
		extent.log(LogStatus.INFO, "This is a <span class='label failure'>fail label</span> which you can create using: <p></p> <pre>&lt;span class='label failure'&gt;description&lt;/span&gt;</pre>");
		extent.log(LogStatus.INFO, "This is a <span class='label info'>info label</span> which you can create using: <p></p> <pre>&lt;span class='label info'&gt;description&lt;/span&gt;</pre>");
		extent.log(LogStatus.INFO, "This is a <span class='label warn'>warning label</span> which you can create using: <p></p> <pre>&lt;span class='label warn'&gt;description&lt;/span&gt;</pre>");
		extent.log(LogStatus.ERROR, "Ths log message shows a pre block.<p></p> <pre>You can insert anything in code-style format by enclosing your string in &lt;pre&gt;&lt;/pre&gt; tags</pre>");
	}
}
