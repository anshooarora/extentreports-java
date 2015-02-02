/*
Copyright 2015 ExtentReports committer(s)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0
	
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/


package com.relevantcodes.extentreports;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.relevantcodes.extentreports.markup.*;
import com.relevantcodes.extentreports.support.*;

class Logger extends AbstractLog {
	private String filePath;
	private String packagePath = "com/relevantcodes/extentreports/markup/";
	private DisplayOrder testDisplayOrder = DisplayOrder.BY_OLDEST_TO_LATEST;
	
	
	//region Protected Methods
	
	@Override
	protected void log() {
		String statusIcon = FontAwesomeIco.get(logStatus);
		
		String txtCurrent = FileReaderEx.readAllText(filePath);
		txtCurrent = txtCurrent.replace(MarkupFlag.get("step"), Resources.getText(packagePath + "step.txt") + MarkupFlag.get("step"));
		txtCurrent = txtCurrent.replace(MarkupFlag.get("timestamp"), new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
		txtCurrent = txtCurrent.replace(MarkupFlag.get("stepStatus"), logStatus.toString().toLowerCase());
		txtCurrent = txtCurrent.replace(MarkupFlag.get("statusIcon"), statusIcon);
		txtCurrent = txtCurrent.replace(MarkupFlag.get("stepName"), stepName);
		txtCurrent = txtCurrent.replace(MarkupFlag.get("details"), details);
		
		FileWriterEx.write(filePath, txtCurrent);
	}
	
	@Override
	protected void startTest() {		
		// this order of creating entries in markup is important
		String txtCurrent = FileReaderEx.readAllText(filePath);
		txtCurrent = txtCurrent.replace(MarkupFlag.get("testStatus"), getLastRunStatus().toString().toLowerCase());
		txtCurrent = txtCurrent.replace(MarkupFlag.get("step"), "");
		
		if (testDisplayOrder == DisplayOrder.BY_LATEST_TO_OLDEST) {
			txtCurrent = txtCurrent.replace(MarkupFlag.get("test"), MarkupFlag.get("test") + Resources.getText(packagePath + "test.txt"));
		}
		else {
			txtCurrent = txtCurrent.replace(MarkupFlag.get("test"), Resources.getText(packagePath + "test.txt") + MarkupFlag.get("test"));
		}
		
		txtCurrent = txtCurrent.replace(MarkupFlag.get("testName"), testName);
		
		FileWriterEx.write(filePath, txtCurrent);
	}
	
	@Override
	protected void endTest() {
		String txtCurrent = FileReaderEx.readAllText(filePath);
		txtCurrent = txtCurrent.replace(MarkupFlag.get("testStatus"), getLastRunStatus().toString().toLowerCase());	
		txtCurrent = txtCurrent.replace(MarkupFlag.get("timeInfo"),  new SimpleDateFormat("HH:mm:ss").format(startTime) + " - " +  new SimpleDateFormat("HH:mm:ss").format(endTime) + " (" + timeDiff + " " + timeUnit + ")");
				
		testName = "";
		
		FileWriterEx.write(filePath, txtCurrent);
	}	
	

	//region Private Methods
	
	private void writeBaseMarkup(Boolean replaceExisting) throws IOException {
		if (replaceExisting) {
			FileWriterEx.createNewFile(filePath, Resources.getText(packagePath + "base.txt"));
		}
	}
	
	
	//region Constructor
	
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
