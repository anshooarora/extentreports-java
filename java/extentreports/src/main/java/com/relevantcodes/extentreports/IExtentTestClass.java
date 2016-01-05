package com.relevantcodes.extentreports;

import com.relevantcodes.extentreports.model.ITest;

public interface IExtentTestClass {
	void log(LogStatus logStatus, String stepName, String details);

	void log(LogStatus logStatus, String details);

	void log(LogStatus logStatus, String stepName, Throwable t);

	void log(LogStatus logStatus, Throwable t);

	String addScreenCapture(String imgPath);

	String addScreencast(String screencastPath);

	ExtentTest assignCategory(String... categories);

	ExtentTest assignAuthor(String... authors);

	ExtentTest appendChild(ExtentTest node);

	LogStatus getRunStatus();

	ITest getTest();
}
