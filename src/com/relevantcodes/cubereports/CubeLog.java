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
import com.relevantcodes.cubereports.markup.*;

class CubeLog extends AbstractLog {

	//region Private Variables
	
	private String filePath;
	private String dirPath = "com/relevantcodes/cubereports/markup/";

	
	//region Protected Methods
	
	@Override
	protected void log() {
		String statusIcon = FontAwesomeIco.get(logStatus);
		
		String txtCurrent = FileReaderEx.readAllText(filePath);
		txtCurrent = txtCurrent.replace(MarkupFlag.get("step"), Resources.getText(dirPath + "step.txt") + MarkupFlag.get("step"));
		txtCurrent = txtCurrent.replace(MarkupFlag.get("timestamp"), new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
		txtCurrent = txtCurrent.replace(MarkupFlag.get("stepStatus"), logStatus.toString().toLowerCase());
		txtCurrent = txtCurrent.replace(MarkupFlag.get("statusIcon"), statusIcon);
		txtCurrent = txtCurrent.replace(MarkupFlag.get("stepName"), stepName);
		txtCurrent = txtCurrent.replace(MarkupFlag.get("stepDetails"), details);
		
		FileWriterEx.write(filePath, txtCurrent);
	}
	
	@Override
	protected void startTest() {		
		String txtCurrent = FileReaderEx.readAllText(filePath);
		txtCurrent = txtCurrent.replace(MarkupFlag.get("testStatus"), getLastRunStatus().toString().toLowerCase());
		txtCurrent = txtCurrent.replace(MarkupFlag.get("step"), "");
		txtCurrent = txtCurrent.replace(MarkupFlag.get("test"), MarkupFlag.get("test") + Resources.getText(dirPath + "test.txt"));
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
			FileWriterEx.createNewFile(filePath, Resources.getText(dirPath + "base.txt"));
		}
	}
	
	
	//region Constructor
	
	public CubeLog(String filePath) throws IOException {
		this(filePath, false);
	}
	
	public CubeLog(String filePath, Boolean replaceExisting) {
		this.filePath = filePath;
		
		try {
			writeBaseMarkup(replaceExisting);
			CubeSource.renewSystemSpecs(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
