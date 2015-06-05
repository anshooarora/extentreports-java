package com.relevantcodes.extentreports.model;

import java.util.ArrayList;

public class ReportInstance {
	public RunInfo runInfo;
	public ArrayList<Test> tests;
	public SystemProperties systemInfo;
	public ArrayList<ScreenCapture> screenCapture;
	
	public ReportInstance() {
		tests = new ArrayList<Test>();
		screenCapture = new ArrayList<ScreenCapture>();
	}	
}
