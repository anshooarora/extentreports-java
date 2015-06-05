package com.relevantcodes.extentreports;

import java.io.File;
import java.util.Calendar;

import com.relevantcodes.extentreports.model.ScreenCapture;
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.ReportInstance;
import com.relevantcodes.extentreports.model.RunInfo;
import com.relevantcodes.extentreports.model.SystemProperties;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.source.ExtentFlag;
import com.relevantcodes.extentreports.source.ImageHtml;
import com.relevantcodes.extentreports.support.DateTimeHelper;
import com.relevantcodes.extentreports.support.FileReaderEx;
import com.relevantcodes.extentreports.support.Resources;
import com.relevantcodes.extentreports.support.Writer;

public class ExtentReports {
	private String filePath;
	private Test test;
	private ReportInstance reportInstance;
	private RunInfo runInfo;
	private SystemProperties systemProperties;
	private SystemInfo systemInfo;
	private LogStatus runStatus = LogStatus.UNKNOWN;
	
	private String sourcePackage = "com/relevantcodes/extentreports/source/";
	
	public void log(LogStatus logStatus, String stepName, String details) {
		Log evt = new Log();
				
		evt.timestamp = DateTimeHelper.getFormattedDateTime(Calendar.getInstance().getTime(), "HH:mm:ss");
		evt.logStatus = logStatus;
		evt.stepName = stepName;
		evt.details = details;
				
		test.log.add(evt);
		
		runInfo.endedAt = evt.timestamp;
		
		trackLastRunStatus(logStatus);
	}
	
	public void log(LogStatus logStatus, String details) {
		log(logStatus, "", details);
	}
	
	public String getScreenshotMarkup(String imgPath) {	
		if (imgPath.indexOf("http") == 0 || imgPath.indexOf(".") == 0 || imgPath.indexOf("/") == 0) {
			imgPath = ImageHtml.getSrc(imgPath).replace("file:///", "");
		}
		else {
			imgPath = ImageHtml.getSrc(imgPath);
		}
		
		ScreenCapture img = new ScreenCapture();
		img.src = imgPath;
		img.testName = test.name;
		
		reportInstance.screenCapture.add(img);

		return imgPath;
	}
	
	public void startTest(String testName, String description) {
		endTest();
		
		runStatus = LogStatus.UNKNOWN;
		
		test = new Test();
		
		test.name = testName;
		test.description = description;
		test.startedAt = DateTimeHelper.getFormattedDateTime(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss");
	}
	
	public void startTest(String testName) {
		startTest(testName, "");
	}
	
	private void endTest() {
		if (test != null) {
			test.endedAt = DateTimeHelper.getFormattedDateTime(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss");
			test.status = runStatus;
			
			reportInstance.tests.add(test);
			reportInstance.runInfo = runInfo;
			
			ReportSource.getInstance().addTest(TestBuilder.getSource(test));
			
			test = null;
			runStatus = LogStatus.UNKNOWN;
		}
	}
	
	private void trackLastRunStatus(LogStatus logStatus) {
		if (runStatus == LogStatus.UNKNOWN) {
			if (logStatus == LogStatus.INFO) {
				runStatus = LogStatus.PASS;
			}
			else {
				runStatus = logStatus;
			}
			
			return;
		}
		
		if (runStatus == LogStatus.FATAL) return;
		
		if (logStatus == LogStatus.FATAL) {
			runStatus = logStatus;
			return;
		}
		
		if (runStatus == LogStatus.FAIL) return;
		
		if (logStatus == LogStatus.FAIL) {
			runStatus = logStatus;
			return;
		}
		
		if (runStatus == LogStatus.ERROR) return;
		
		if (logStatus == LogStatus.ERROR) {
			runStatus = logStatus;
			return;
		}
		
		if (runStatus == LogStatus.WARNING) return;
		
		if (logStatus == LogStatus.WARNING) {
			runStatus = logStatus;
			return;
		}
		
		if (runStatus == LogStatus.PASS || runStatus == LogStatus.INFO) {
			runStatus = LogStatus.PASS;
			return;
		}
		
		runStatus = LogStatus.SKIP;		
	}
	
	public void init(String filePath, Boolean replaceExisting, DisplayOrder displayOrder) {
		String src;
		
		this.filePath = filePath;
		runStatus = LogStatus.UNKNOWN;
		
		reportInstance = new ReportInstance();
		runInfo = new RunInfo();
		systemProperties = new SystemProperties();
		systemInfo = new SystemInfo();
		
		runInfo.startedAt = DateTimeHelper.getFormattedDateTime(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss");
		systemProperties.info = systemInfo.get();
		
		if (!new File(filePath).isFile()) {
			replaceExisting = true;
		}
		
		if (replaceExisting) {
			src = Resources.getText(sourcePackage + "STANDARD.html");
		} 
		else {
			src = FileReaderEx.readAllText(filePath);			
		}
		
		ReportSource.getInstance().setSrc(src);
	}
	
	public void init(String filePath, Boolean replaceExisting) {
		init(filePath, replaceExisting, DisplayOrder.OLDEST_FIRST);
	}
	
	public SystemInfo systemInfo() {
		return systemInfo;
	}
	
	public void updateSources() {
		endTest();
		
		runInfo.endedAt = DateTimeHelper.getFormattedDateTime(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss");
		
		String[] flags = { ExtentFlag.getPlaceHolder("suiteStartTime"), ExtentFlag.getPlaceHolder("suiteEndTime") };
		String[] values = { runInfo.startedAt, runInfo.endedAt };
			
		String src = SourceBuilder.build(ReportSource.getInstance().getSrc(), flags, values);
		
		systemProperties.info = systemInfo.get();
		String systemSrc = SystemInfoViewBuilder.getSource(systemProperties);
		src = SourceBuilder.build(src, new String[] { ExtentFlag.getPlaceHolder("systemInfoView") }, new String[] { systemSrc +ExtentFlag.getPlaceHolder("systemInfoView") });
				
		String imageSrc = ImageViewBuilder.getSource(reportInstance.screenCapture);
		src = SourceBuilder.build(src, new String[] { ExtentFlag.getPlaceHolder("imagesView") }, new String[] { imageSrc + ExtentFlag.getPlaceHolder("imagesView") });
		
		ReportSource.getInstance().setSrc(src);
	}
	
	public void terminate() {
		Writer.getInstance().write(new File(filePath), ReportSource.getInstance().getSrc());
	}
	
	public ExtentReports() {
		reportInstance = new ReportInstance();
	}
}
