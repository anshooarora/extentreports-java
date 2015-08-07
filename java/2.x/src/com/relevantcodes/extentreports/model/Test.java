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
import java.util.UUID;

import com.relevantcodes.extentreports.LogStatus;

public class Test {
	public ArrayList<TestAttribute> categoryList;
	public ArrayList<TestAttribute> authorList;
    public ArrayList<Log> log;
    public ArrayList<ScreenCapture> screenCapture;
    public ArrayList<Screencast> screencast;
    public ArrayList<Test> nodeList;
    public boolean isChildNode = false;
    public boolean hasEnded = false;
    public boolean hasChildNodes = false;
    public Date startedTime;
    public Date endedTime;
    public LogStatus status;
    public String description;
    public String internalWarning;
    public String name;
    public String statusMessage;
    public UUID id;
    
    public void trackLastRunStatusRecursively() {
    	getTestStatus(this);
    }
    
    public void trackLastRunStatus() {
    	for (Log l : log) {
    		findStatus(l.logStatus);
    	}
    }
    
    private void getTestStatus(Test test) {
    	for (Log log : test.log) {
    		findStatus(log.logStatus);
    	}
    	
    	if (test.hasChildNodes) {
    		for (Test node : test.nodeList) {
    			getTestStatus(node);
    		}
    	}
    }
    
    private void findStatus(LogStatus logStatus) {
        if (status == LogStatus.FATAL) return;
        
        if (logStatus == LogStatus.FATAL) {
            status = logStatus;
            return;
        }
        
        if (status == LogStatus.FAIL) return;
        
        if (logStatus == LogStatus.FAIL) {
            status = logStatus;
            return;
        }
        
        if (status == LogStatus.ERROR) return;
        
        if (logStatus == LogStatus.ERROR) {
            status = logStatus;
            return;
        }
        
        if (status == LogStatus.WARNING) return;
        
        if (logStatus == LogStatus.WARNING) {
            status = logStatus;
            return;
        }
        
        if (status == LogStatus.PASS || status == LogStatus.INFO) {
            status = LogStatus.PASS;
            return;
        }
        
        if (logStatus == LogStatus.PASS || logStatus == LogStatus.INFO) {
            status = LogStatus.PASS;
            return;
        }
        
        status = LogStatus.SKIP;     
    }
    
    public Test() {
    	internalWarning = "";
    	
    	id = UUID.randomUUID();
    	
        log = new ArrayList<Log>();
        categoryList = new ArrayList<TestAttribute>();
        authorList = new ArrayList<TestAttribute>();
        screenCapture = new ArrayList<ScreenCapture>();
        screencast = new ArrayList<Screencast>();
        nodeList = new ArrayList<Test>();
    }
}
