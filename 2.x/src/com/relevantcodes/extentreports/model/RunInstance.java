package com.relevantcodes.extentreports.model;

import java.util.ArrayList;

public class RunInstance {
	public RunInfo runInfo;
	public ArrayList<Test> tests;
	public SystemProperties systemInfo;
	public ArrayList<ScreenCapture> screenCapture;
	
	public void init() {
		tests = new ArrayList<Test>();
		screenCapture = new ArrayList<ScreenCapture>();
	}
	
	private RunInstance() { }
	
	private static class Instance {
        static final RunInstance INSTANCE = new RunInstance();
    }
	
	public static RunInstance getInstance() {
		return Instance.INSTANCE;
	}
}
