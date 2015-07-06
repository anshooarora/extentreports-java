/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.model;

import java.util.ArrayList;
import java.util.Date;

import com.relevantcodes.extentreports.LogStatus;

public class Test {
	public ArrayList<TestAttribute> categoryList;
	public ArrayList<TestAttribute> authorList;
    public ArrayList<Log> log;
    public ArrayList<ScreenCapture> screenCapture;
    public ArrayList<Screencast> screencast;
    public Boolean hasEnded = false;
    public Date startedTime;
    public Date endedTime;
    public LogStatus status;
    public String description;
    public String internalWarning;
    public String name;
    public String statusMessage;
        
    public Test() {
    	internalWarning = "";
    	
        log = new ArrayList<Log>();
        categoryList = new ArrayList<TestAttribute>();
        authorList = new ArrayList<TestAttribute>();
        screenCapture = new ArrayList<ScreenCapture>();
        screencast = new ArrayList<Screencast>();
    }
}
