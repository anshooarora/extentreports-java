/*
Copyright 2015 Cube Reports committer(s)

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


package com.relevantcodes.cubereports;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.relevantcodes.cubereports.support.*;

class Logger extends AbstractLog {

	//region Private Variables
	
	private String filePath;
	private String dirPath = "com/relevantcodes/cubereports/";

	
	//region Protected Methods
	
	@Override
	protected void log() {
		String parentFolder = "src/";
		String statusIcon = logStatus.toString().toLowerCase();
		
		switch (logStatus) {
			case FAIL: statusIcon = "times"; break;
			case ERROR: statusIcon = "exclamation-circle"; break;
			case PASS: statusIcon = "check"; break;
			default: break;
		}
		
		String txtCurrent = FileOps.readAllText(filePath);
		txtCurrent = txtCurrent.replace("<!--%%STEP%%-->", Resources.getText(dirPath + parentFolder + "step.txt") + "<!--%%STEP%%-->");
		txtCurrent = txtCurrent.replace("<!--%%TIMESTAMP%%-->", new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
		txtCurrent = txtCurrent.replace("<!--%%STATUS%%-->", logStatus.toString().toLowerCase());
		txtCurrent = txtCurrent.replace("<!--%%STATUSICON%%-->", statusIcon);
		txtCurrent = txtCurrent.replace("<!--%%STEPNAME%%-->", stepName);
		txtCurrent = txtCurrent.replace("<!--%%DETAILS%%-->", details);
		
		FileOps.write(filePath, txtCurrent);
	}
	
	@Override
	protected void startTest() {
		String parentFolder = "src/";
		
		String txtCurrent = FileOps.readAllText(filePath);
		txtCurrent = txtCurrent.replace("<!--%%TESTSTATUS%%-->", getLastRunStatus().toString().toLowerCase());
		txtCurrent = txtCurrent.replace("<!--%%STEP%%-->", "");
		txtCurrent = txtCurrent.replace("<!--%%TEST%%-->", "<!--%%TEST%%-->" + Resources.getText(dirPath + parentFolder + "test.txt"));
		txtCurrent = txtCurrent.replace("<!--%%TESTNAME%%-->", testName);
		
		FileOps.write(filePath, txtCurrent);
	}
	
	@Override
	protected void endTest() {
		String txtCurrent = FileOps.readAllText(filePath);
		txtCurrent = txtCurrent.replace("<!--%%TESTSTATUS%%-->", getLastRunStatus().toString().toLowerCase());	
		txtCurrent = txtCurrent.replace("<!--%%TIMEINFO%%-->",  new SimpleDateFormat("HH:mm:ss").format(startTime) + " - " +  new SimpleDateFormat("HH:mm:ss").format(endTime) + " (" + timeDiff + " " + timeUnit + ")");
				
		testName = "";
		
		FileOps.write(filePath, txtCurrent);
	}
	
	@Override
	protected void customStylesheet(String cssFilePath) {
		Insight.customStylesheet(filePath, cssFilePath);
	}
	
	@Override
	protected void updateSummary(String summary) {
		Insight.changeIntroSummary(filePath, summary);
	}
	

	//region Private Methods
	
	private void writeBaseMarkup(Boolean replaceExisting) throws IOException {
		String parentFolder = "src/";
		
		if (replaceExisting) {
			FileOps.createNewFile(filePath, Resources.getText(dirPath + parentFolder + "base.txt"));
		}
	}
	
	
	//region Constructor
	
	public Logger(String filePath) throws IOException {
		this(filePath, false);
	}
	
	public Logger(String filePath, Boolean replaceExisting) {
		this.filePath = filePath;
		
		try {
			writeBaseMarkup(replaceExisting);
			Insight.renewSystemSpecs(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
