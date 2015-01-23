package com.relevantcodes.cubereports;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.relevantcodes.cubereports.support.*;

class Logger extends BaseLogger {

	//region Private Variables
	
	private String filePath;
	private String dirPath = "com/relevantcodes/cubereports/";

	//region Protected Methods
	
	protected void log() {
		String statusIcon = logStatus.toString().toLowerCase();
		
		switch (logStatus) {
			case FAIL: statusIcon = "times"; break;
			case ERROR: statusIcon = "exclamation-circle"; break;
			case PASS: statusIcon = "check"; break;
			default: break;
		}
		
		String txtCurrent = FileOps.readAllText(filePath);
		txtCurrent = txtCurrent.replace("<!--%%STEP%%-->", Resources.getText(dirPath + "src/step.txt") + "<!--%%STEP%%-->");
		txtCurrent = txtCurrent.replace("<!--%%TIMESTAMP%%-->", new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
		txtCurrent = txtCurrent.replace("<!--%%STATUS%%-->", logStatus.toString().toLowerCase());
		txtCurrent = txtCurrent.replace("<!--%%STATUSICON%%-->", statusIcon);
		txtCurrent = txtCurrent.replace("<!--%%STEPNAME%%-->", stepName);
		txtCurrent = txtCurrent.replace("<!--%%DETAILS%%-->", details);
		
		FileOps.write(filePath, txtCurrent);
	}
	
	protected void startTest() {	
		String txtCurrent = FileOps.readAllText(filePath);
		txtCurrent = txtCurrent.replace("<!--%%TESTSTATUS%%-->", getLastRunStatus().toString().toLowerCase());
		txtCurrent = txtCurrent.replace("<!--%%STEP%%-->", "");
		txtCurrent = txtCurrent.replace("<!--%%TEST%%-->", "<!--%%TEST%%-->" + Resources.getText(dirPath + "src/test.txt"));
		txtCurrent = txtCurrent.replace("<!--%%TESTNAME%%-->", testName);
		
		FileOps.write(filePath, txtCurrent);
	}
	
	protected void endTest() {
		String txtCurrent = FileOps.readAllText(filePath);
		txtCurrent = txtCurrent.replace("<!--%%TESTSTATUS%%-->", getLastRunStatus().toString().toLowerCase());	
		txtCurrent = txtCurrent.replace("<!--%%TIMEINFO%%-->",  new SimpleDateFormat("HH:mm:ss").format(startTime) + " - " +  new SimpleDateFormat("HH:mm:ss").format(endTime) + " (" + timeDiff + " " + timeUnit + ")");
				
		testName = "";
		
		FileOps.write(filePath, txtCurrent);
	}
	
	@Override
	protected void customCSS(String cssFilePath) {
		LogInsight.useCustomStylesheet(filePath, cssFilePath);
	}
	
	protected void updateSummary(String summary) {
		LogInsight.updateSummary(filePath, summary);
	}
	

	//region Private Methods
	
	private void writeBaseMarkup(Boolean replaceExisting) throws IOException {
		if (replaceExisting) {
			FileOps.createNewFile(filePath, Resources.getText(dirPath + "src/base.txt"));
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
