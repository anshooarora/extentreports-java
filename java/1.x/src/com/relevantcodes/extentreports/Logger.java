/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/


package com.relevantcodes.extentreports;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.relevantcodes.extentreports.markup.*;
import com.relevantcodes.extentreports.support.*;

class Logger extends AbstractLog {
	// HTML report file path
	private String filePath;
	
	// package where markup files are created 
	private String packagePath = "com/relevantcodes/extentreports/markup/";
	
	// default DisplayOrder = OLDEST tests first, followed by NEWEST
	private DisplayOrder testDisplayOrder = DisplayOrder.BY_OLDEST_TO_LATEST;
	
	// default GridType = STANDARD
	private GridType gridType = GridType.STANDARD;
	
	
	@Override
	protected void log() {
		String markup = "";
		
		if (screenCapturePath != "") {
			String img = ExtentMarkup.img(screenCapturePath);
			
			if (screenCapturePath.indexOf("http") == 0 || screenCapturePath.indexOf(".") == 0 || screenCapturePath.indexOf("/") == 0) {
				img = img.replace("file:///", "");
			}
			
			details += img;
			screenCapturePath = "";
		}
		
		markup = FileReaderEx.readAllText(filePath);
				
		if (stepName == null && logStatus != null && details != null) {
			markup = markup.replace(ExtentMarkup.getPlaceHolder("step"), 
					Resources.getText(htmlSource(Source.STEP_COLSPAN_2)) + ExtentMarkup.getPlaceHolder("step"));
		}
		else {
			markup = markup.replace(ExtentMarkup.getPlaceHolder("step"), Resources.getText(htmlSource(Source.STEP)) + ExtentMarkup.getPlaceHolder("step"))
					.replace(ExtentMarkup.getPlaceHolder("stepname"), stepName);
		}

		markup = markup.replace(ExtentMarkup.getPlaceHolder("stepstatus"), logStatus.toString().toLowerCase())
					.replace(ExtentMarkup.getPlaceHolder("stepstatusu"), logStatus.toString().toUpperCase())
					.replace(ExtentMarkup.getPlaceHolder("statusicon"), FontAwesomeIco.get(logStatus))
					.replace(ExtentMarkup.getPlaceHolder("details"), details)
					.replace(ExtentMarkup.getPlaceHolder("timestamp"), new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()))
					.replace(RegexMatcher.getNthMatch(markup, ExtentMarkup.getPlaceHolder("testEndTime") + ".*" + ExtentMarkup.getPlaceHolder("testEndTime"), 0), ExtentMarkup.getPlaceHolder("testEndTime") + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()).toString() + ExtentMarkup.getPlaceHolder("testEndTime"))
					.replace(RegexMatcher.getNthMatch(markup, ExtentMarkup.getPlaceHolder("timeEnded") + ".*" + ExtentMarkup.getPlaceHolder("timeEnded"), 0), ExtentMarkup.getPlaceHolder("timeEnded") + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()).toString() + ExtentMarkup.getPlaceHolder("timeEnded"));
				
		FileWriterEx.write(filePath, markup);
	}
	
	@Override
	protected void startTest() {		
		// this order of creating entries in markup is important
		String markup = FileReaderEx.readAllText(filePath)
				.replace(ExtentMarkup.getPlaceHolder("teststatus"), getLastRunStatus().toString().toLowerCase())
				.replace(ExtentMarkup.getPlaceHolder("step"), "")
				.replace(ExtentMarkup.getPlaceHolder("testEndTime"), "");
		
		String source = htmlSource(Source.TEST);
		
		if (testDisplayOrder == DisplayOrder.BY_LATEST_TO_OLDEST) {
			markup = markup.replace(ExtentMarkup.getPlaceHolder("test"), ExtentMarkup.getPlaceHolder("test") + Resources.getText(source));
		}
		else {
			markup = markup.replace(ExtentMarkup.getPlaceHolder("test"), Resources.getText(source) + ExtentMarkup.getPlaceHolder("test"));
		}
		
		if (testDescription == "") {
			markup = markup.replace(ExtentMarkup.getPlaceHolder("descvis"), "style='display:none;'");
		}
		
		markup = markup.replace(ExtentMarkup.getPlaceHolder("testname"), testName)
				.replace(ExtentMarkup.getPlaceHolder("testdescription"), testDescription)
				.replace(ExtentMarkup.getPlaceHolder("descvis"), "")
				.replace(ExtentMarkup.getPlaceHolder("testStartTime"), new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()).toString());
		
		FileWriterEx.write(filePath, markup);
	}
	
	@Override
	protected void endTest() {
		String markup = FileReaderEx.readAllText(filePath)
				.replace(ExtentMarkup.getPlaceHolder("teststatus"), getLastRunStatus().toString().toLowerCase())
				.replace(ExtentMarkup.getPlaceHolder("step"), "")
				.replace(ExtentMarkup.getPlaceHolder("testEndTime"), "");
		
		testName = "";
		
		FileWriterEx.write(filePath, markup);
	}	
	
	@Override
	protected void attachScreenshot() {
		if (screenCapturePath != "") {
			String img = ExtentMarkup.imgSingle(screenCapturePath);
			
			if (screenCapturePath.indexOf("http") == 0 || screenCapturePath.indexOf(".") == 0 || screenCapturePath.indexOf("/") == 0) {
				img = img.replace("file:///", "");
			}
			
			message += img;
			
			String markup = FileReaderEx.readAllText(filePath)
								.replace(ExtentMarkup.getPlaceHolder("step"), Resources.getText(htmlSource(Source.STEP_COLSPAN_3)) + ExtentMarkup.getPlaceHolder("step"))
								.replace(ExtentMarkup.getPlaceHolder("details"), message)
								.replace(ExtentMarkup.getPlaceHolder("timestamp"), new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
			
			markup = markup.replace(RegexMatcher.getNthMatch(markup, ExtentMarkup.getPlaceHolder("testEndTime") + ".*" + ExtentMarkup.getPlaceHolder("testEndTime"), 0), ExtentMarkup.getPlaceHolder("testEndTime") + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()).toString() + ExtentMarkup.getPlaceHolder("testEndTime"))
								.replace(RegexMatcher.getNthMatch(markup, ExtentMarkup.getPlaceHolder("timeEnded") + ".*" + ExtentMarkup.getPlaceHolder("timeEnded"), 0), ExtentMarkup.getPlaceHolder("timeEnded") + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()).toString() + ExtentMarkup.getPlaceHolder("timeEnded"));
			
			FileWriterEx.write(filePath, markup);
		}
	}
	
	private void writeBaseMarkup(Boolean replaceExisting) throws IOException {
		String targetSource = htmlSource(Source.STANDARD);
		
		if (gridType == GridType.MASONRY) {
			targetSource = htmlSource(Source.MASONRY);
		}

		if (replaceExisting) {
			FileWriterEx.createNewFile(filePath, Resources.getText(targetSource));
		}
	}
	
	private String htmlSource(Source htmlSourceName) {
		if (htmlSourceName == Source.MASONRY || htmlSourceName == Source.STANDARD)
			return packagePath + htmlSourceName.toString() + ".html";			
			
		return packagePath + htmlSourceName.toString() + ".txt";
	}
	
	public Logger(String filePath, Boolean replaceExisting, DisplayOrder order, GridType gridType) {
		this.filePath = filePath;
		this.gridType = gridType;
		
		if (!new File(filePath).isFile()) {
			replaceExisting = true;
		}
		
		try {
			writeBaseMarkup(replaceExisting);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		testDisplayOrder = order;
	}
	
	public Logger(String filePath) throws IOException {
		this(filePath, false);
	}
	
	public Logger(String filePath, Boolean replaceExisting) {
		this(filePath, replaceExisting, DisplayOrder.BY_OLDEST_TO_LATEST, GridType.STANDARD);
	}
	
	public Logger(String filePath, Boolean replaceExisting, GridType gridType) {
		this(filePath, replaceExisting, DisplayOrder.BY_OLDEST_TO_LATEST, gridType);
	}
	
	public Logger(String filePath, DisplayOrder order) {
		this(filePath, false, order, GridType.STANDARD);
	}
}
