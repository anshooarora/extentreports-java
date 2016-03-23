package com.relevantcodes.extentreports;

import java.util.Date;

import com.relevantcodes.extentreports.model.ITest;

public interface IExtentTestClass {
	void log(LogStatus logStatus, String stepName, String details);

	void log(LogStatus logStatus, String details);

	void log(LogStatus logStatus, String stepName, Throwable t);

	void log(LogStatus logStatus, Throwable t);

	void setDescription(String description);
	
	String getDescription();
	
	void setStartedTime(Date startedTime);
	
	Date getStartedTime();
	
	void setEndedTime(Date endedTime);
	
	Date getEndedTime();
	
	String addScreenCapture(String imgPath);

    /**
     * Adds the base64 screenshot into the report
     * @param base64 The base64 string
     * @return The image tag html with base64 image as src
     */
	String addBase64ScreenShot(String base64);

	String addScreencast(String screencastPath);

	ExtentTest assignCategory(String... categories);

	ExtentTest assignAuthor(String... authors);

	ExtentTest appendChild(ExtentTest node);

	LogStatus getRunStatus();

	ITest getTest();
}
