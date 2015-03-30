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
	
	@Override
	protected void log() {
		String markup = "";
		
		if (screenCapturePath != "") {
			String img = MarkupFlag.img(screenCapturePath);
			
			if (screenCapturePath.indexOf("http") == 0 || screenCapturePath.indexOf(".") == 0 || screenCapturePath.indexOf("/") == 0) {
				img = img.replace("file:///", "");
			}
			
			details += img;
			screenCapturePath = "";
		}
		
		markup = FileReaderEx.readAllText(filePath);
				
		if (stepName == null && logStatus != null && details != null) {
			markup = markup.replace(MarkupFlag.get("step"), Resources.getText(packagePath + "step-colspan-2.txt") + MarkupFlag.get("step"));
		}
		else {
			markup = markup.replace(MarkupFlag.get("step"), Resources.getText(packagePath + "step.txt") + MarkupFlag.get("step"))
					.replace(MarkupFlag.get("stepname"), stepName);
		}

		markup = markup.replace(MarkupFlag.get("stepstatus"), logStatus.toString().toLowerCase())
					.replace(MarkupFlag.get("stepstatusu"), logStatus.toString().toUpperCase())
					.replace(MarkupFlag.get("statusicon"), FontAwesomeIco.get(logStatus))
					.replace(MarkupFlag.get("details"), details)
					.replace(MarkupFlag.get("timestamp"), new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()))
					.replace(RegexMatcher.getNthMatch(markup, MarkupFlag.get("testEndTime") + ".*" + MarkupFlag.get("testEndTime"), 0), MarkupFlag.get("testEndTime") + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()).toString() + MarkupFlag.get("testEndTime"))
					.replace(RegexMatcher.getNthMatch(markup, MarkupFlag.get("timeEnded") + ".*" + MarkupFlag.get("timeEnded"), 0), MarkupFlag.get("timeEnded") + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()).toString() + MarkupFlag.get("timeEnded"));
				
		FileWriterEx.write(filePath, markup);
	}
	
	@Override
	protected void startTest() {		
		// this order of creating entries in markup is important
		String markup = FileReaderEx.readAllText(filePath)
				.replace(MarkupFlag.get("teststatus"), getLastRunStatus().toString().toLowerCase())
				.replace(MarkupFlag.get("step"), "")
				.replace(MarkupFlag.get("testEndTime"), "");
		
		if (testDisplayOrder == DisplayOrder.BY_LATEST_TO_OLDEST) {
			markup = markup.replace(MarkupFlag.get("test"), MarkupFlag.get("test") + Resources.getText(packagePath + "test.txt"));
		}
		else {
			markup = markup.replace(MarkupFlag.get("test"), Resources.getText(packagePath + "test.txt") + MarkupFlag.get("test"));
		}
		
		if (testDescription == "") {
			markup = markup.replace(MarkupFlag.get("descvis"), "style='display:none;'");
		}
		
		markup = markup.replace(MarkupFlag.get("testname"), testName)
				.replace(MarkupFlag.get("testdescription"), testDescription)
				.replace(MarkupFlag.get("descvis"), "")
				.replace(MarkupFlag.get("testStartTime"), new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()).toString());
		
		FileWriterEx.write(filePath, markup);
	}
	
	@Override
	protected void endTest() {
		String markup = FileReaderEx.readAllText(filePath)
				.replace(MarkupFlag.get("teststatus"), getLastRunStatus().toString().toLowerCase())
				.replace(MarkupFlag.get("step"), "")
				.replace(MarkupFlag.get("testEndTime"), "");
		
		testName = "";
		
		FileWriterEx.write(filePath, markup);
	}	
	
	@Override
	protected void attachScreenshot() {
		if (screenCapturePath != "") {
			String img = MarkupFlag.imgSingle(screenCapturePath);
			
			if (screenCapturePath.indexOf("http") == 0 || screenCapturePath.indexOf(".") == 0 || screenCapturePath.indexOf("/") == 0) {
				img = img.replace("file:///", "");
			}
			
			message += img;
			
			String markup = FileReaderEx.readAllText(filePath)
								.replace(MarkupFlag.get("step"), Resources.getText(packagePath + "step-colspan-3.txt") + MarkupFlag.get("step"))
								.replace(MarkupFlag.get("details"), message)
								.replace(MarkupFlag.get("timestamp"), new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
			
			markup = markup.replace(RegexMatcher.getNthMatch(markup, MarkupFlag.get("testEndTime") + ".*" + MarkupFlag.get("testEndTime"), 0), MarkupFlag.get("testEndTime") + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()).toString() + MarkupFlag.get("testEndTime"))
								.replace(RegexMatcher.getNthMatch(markup, MarkupFlag.get("timeEnded") + ".*" + MarkupFlag.get("timeEnded"), 0), MarkupFlag.get("timeEnded") + new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()).toString() + MarkupFlag.get("timeEnded"));
			
			FileWriterEx.write(filePath, markup);
		}
	}
	
	private void writeBaseMarkup(Boolean replaceExisting) throws IOException {
		if (replaceExisting) {
			FileWriterEx.createNewFile(filePath, Resources.getText(packagePath + "base.txt"));
		}
	}
	
	public Logger(String filePath, Boolean replaceExisting, DisplayOrder order) {
		this.filePath = filePath;
		
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
		this(filePath, replaceExisting, DisplayOrder.BY_OLDEST_TO_LATEST);
	}
	
	public Logger(String filePath, DisplayOrder order) {
		this(filePath, false, order);
	}
}
