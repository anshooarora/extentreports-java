/*
* The MIT License (MIT)
* 
* Copyright (c) 2015, Anshoo Arora (Relevant Codes)
*/

package com.relevantcodes.extentmerge;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.relevantcodes.extentreports.model.Test;

public class Report {
	private List<Test> testList;
	private SourceType sourceType;
	private Date startedTime;
	private Date endedTime;
	private UUID id;
	
	public void setTest(Test test) {
		testList.add(test);
	}
	
	public void setTestList(List<Test> testList) {
		this.testList = testList;
	}
	
	public List<Test> getTestList() {
		return testList;
	}
	
	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}
	
	public SourceType getSourceType() {
		return sourceType;
	}
	
	public Date getStartedTime() {
		return startedTime;
	}
	
	public void setStartedTime(String startedTime) {
		SimpleDateFormat sdf = new SimpleDateFormat(LogSettings.getLogDateTimeFormat());
		
		try {
			this.startedTime = sdf.parse(startedTime);
		} 
		catch (ParseException e) {			
			e.printStackTrace();
		}
	}
	
	public Date getEndedTime() {
		return endedTime;
	}
	
	public void setEndedTime(String startedTime) {
		SimpleDateFormat sdf = new SimpleDateFormat(LogSettings.getLogDateTimeFormat());
		
		try {
			this.endedTime = sdf.parse(startedTime);
		} 
		catch (ParseException e) {			
			e.printStackTrace();
		}
	}
	
	public UUID getId() {
		return id;
	}
	
	public Report() {
		id = UUID.randomUUID();
		testList = new ArrayList<Test>();
	}
}
