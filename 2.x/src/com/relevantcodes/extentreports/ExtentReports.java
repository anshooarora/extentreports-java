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
import com.relevantcodes.extentreports.support.Writer;

public class ExtentReports {
	private String filePath;
	private DisplayOrder displayOrder;
	private Test test;
	private ReportInstance reportInstance;
	private RunInfo runInfo;
	private SystemProperties systemProperties;
	private SystemInfo systemInfo;
	private LogStatus runStatus = LogStatus.UNKNOWN;
	private ReportConfig config;

	public void log(LogStatus logStatus, String stepName, String details) {
		Log evt = new Log();
				
		evt.timestamp = DateTimeHelper.getFormattedDateTime(Calendar.getInstance().getTime(), LogSettings.logTimeFormat);
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
		test.startedAt = DateTimeHelper.getFormattedDateTime(Calendar.getInstance().getTime(), LogSettings.logDateTimeFormat);
	}
	
	public void startTest(String testName) {
		startTest(testName, "");
	}
	
	private void endTest() {
		if (test != null) {
			test.endedAt = DateTimeHelper.getFormattedDateTime(Calendar.getInstance().getTime(), LogSettings.logDateTimeFormat);
			test.status = runStatus;
			
			reportInstance.tests.add(test);
			reportInstance.runInfo = runInfo;
			
			ReportSource.getInstance().addTest(TestBuilder.getSource(test), displayOrder);
			
			test = null;
			runStatus = LogStatus.UNKNOWN;
		}
	}
	
	public void init(String filePath, boolean replaceExisting, DisplayOrder displayOrder) {
		this.filePath = filePath;
		this.displayOrder = displayOrder;
		runStatus = LogStatus.UNKNOWN;
		
		reportInstance = new ReportInstance();
		runInfo = new RunInfo();
		systemProperties = new SystemProperties();
		systemInfo = new SystemInfo();
		
		runInfo.startedAt = DateTimeHelper.getFormattedDateTime(Calendar.getInstance().getTime(), LogSettings.logDateTimeFormat);
		systemProperties.info = systemInfo.getInfo();
		
		ReportSource.getInstance().initialize(filePath, replaceExisting);
	}
	
	public void init(String filePath, boolean replaceExisting) {
		init(filePath, replaceExisting, DisplayOrder.OLDEST_FIRST);
	}
	
	public ReportConfig config() {
		if (config == null)
			config = new ReportConfig();
		
		config.setSource(ReportSource.getInstance().getSource());
		
		return config;
	}
		
	public void update() {
		endTest();
		
		// reported endedAt time
		runInfo.endedAt = DateTimeHelper.getFormattedDateTime(Calendar.getInstance().getTime(), LogSettings.logDateTimeFormat);
		
		// set flags and values (startedAt, endedAt) times in report html
		String[] flags = { ExtentFlag.getPlaceHolder("suiteStartTime"), ExtentFlag.getPlaceHolder("suiteEndTime") };
		String[] values = { runInfo.startedAt, runInfo.endedAt };
			
		String src = SourceBuilder.build(ReportSource.getInstance().getSource(), flags, values);
		
		ReportSource.getInstance().setSource(src);
	}
	
	public void terminate() {
		String src = getUpdatedMediaSource(ReportSource.getInstance().getSource());
		
		if (ReportSource.getInstance().getIndex() == 1) {
			systemProperties.info = systemInfo.getInfo();
			String systemSrc = SystemInfoViewBuilder.getSource(systemProperties);
			
			// set system information in the report
			String[] flags = new String[] { ExtentFlag.getPlaceHolder("systemInfoView") };
			String[] values = new String[] { systemSrc + ExtentFlag.getPlaceHolder("systemInfoView") };
			
			src = SourceBuilder.build(src, flags, values);
			systemProperties.info.clear();
		}
		
		ReportSource.getInstance().setSource(src);
		ReportSource.getInstance().terminate();
		
		Writer.getInstance().write(new File(filePath), ReportSource.getInstance().getSource());
	}
	
	public SystemInfo systemInfo() {
		return systemInfo;
	}
	
	private String getUpdatedMediaSource(String src) {
		// get all images from screenCapture list
		// MediaViewBuilder creates the HTML source by using all the images added
		String imageSrc = MediaViewBuilder.getSource(reportInstance.screenCapture);
		String[] flags = new String[] { ExtentFlag.getPlaceHolder("imagesView") };
		String[] values = new String[] { imageSrc + ExtentFlag.getPlaceHolder("imagesView") };
		
		// build sources by replacing the flag with the values
		src = SourceBuilder.build(src, flags, values);
		
		if (reportInstance.screenCapture.size() > 0) {
			//src = ExtentFlag.clearStringWithinPlaceHolder(src, "objectViewNull");
		}
		
		// clear the screenCapture list so the same images are not written twice
		reportInstance.screenCapture.clear();
		
		// do the same for all embedded videos
		// currently not supported, will be NULL
		String vidSrc = MediaViewBuilder.getSource(null);
		flags = new String[] { ExtentFlag.getPlaceHolder("videosView") };
		values = new String[] { vidSrc + ExtentFlag.getPlaceHolder("videosView") };
		
		// build sources by replacing the flag with the values
		src = SourceBuilder.build(src, flags, values);
		
		return src;
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
	
	public ExtentReports() {
		reportInstance = new ReportInstance();
	}
}
