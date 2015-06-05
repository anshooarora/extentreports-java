package com.relevantcodes.extentreports.model;

import java.util.ArrayList;

import com.relevantcodes.extentreports.LogStatus;

public class Test {
	public ArrayList<Log> log;
	public String name;
	public String startedAt;
	public String endedAt;
	public LogStatus status;
	public String statusMessage;
	public String description;
	
	public Test() {
		log = new ArrayList<Log>();
	}
}
