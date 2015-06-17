package com.relevantcodes.extentreports.model;

import java.util.ArrayList;

import com.relevantcodes.extentreports.LogStatus;

public class Test {
	public ArrayList<Log> log;
	public ArrayList<ScreenCapture> screenCapture;
	public ArrayList<Screencast> screencast;
	public String name;
	public String startedAt;
	public String endedAt;
	public LogStatus status;
	public String statusMessage;
	public String description;
	
	public Test() {
		log = new ArrayList<Log>();
		screenCapture = new ArrayList<ScreenCapture>();
		screencast = new ArrayList<Screencast>();
	}
}
