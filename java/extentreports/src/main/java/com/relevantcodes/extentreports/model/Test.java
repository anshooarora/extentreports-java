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
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.relevantcodes.extentreports.LogCounts;
import com.relevantcodes.extentreports.LogStatus;

public class Test {
    private List<TestAttribute> categoryList;
    private List<TestAttribute> authorsList;
    private List<Log> logList;
    private List<ScreenCapture> screenCaptureList;
    private List<Screencast> screencastList;
    private List<Test> nodeList;
    
    private HashMap<LogStatus, Integer> logCounts;
    
    public boolean isChildNode = false;
    public boolean hasEnded = false;
    public boolean hasChildNodes = false;
    
    private Date startedTime;
    private Date endedTime;
    
    private LogStatus status = LogStatus.UNKNOWN;
    
    private String description;
    private String internalWarning;
    private String name;
    
    private UUID id;
    
    private void setLogCounts() {
    	this.logCounts = new LogCounts().getLogCounts(this);
    }
    
    public HashMap<LogStatus, Integer> getLogCounts() {
    	return logCounts;
    }
    
    // started time
    public void setStartedTime(Date startedTime) {
        this.startedTime = startedTime;
    }
    
    public Date getStartedTime() {
        return startedTime;
    }
    
    // ended time
    public void setEndedTime(Date endedTime) {
        this.endedTime = endedTime;
    }
    
    public Date getEndedTime() {
        return endedTime;
    }
    
    // status
    public void setStatus(LogStatus status) {
        this.status = status;
    }
    
    public LogStatus getStatus() {
        return status;
    }
    
    // description
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    // internal warning
    public void setInternalWarning(String warning) {
        this.internalWarning = warning;
    }
    
    public String getInternalWarning() {
        return internalWarning;
    }
    
    // name
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    // id
    public UUID getId() {
        return id;
    }
    
    // categories
    public void setCategoryList(List<TestAttribute> categoryList) {
        this.categoryList = categoryList;
    }
    
    public void setCategory(TestAttribute category) {
        categoryList.add(category);
    }
    
    public List<TestAttribute> getCategoryList() {
        return categoryList;
    }
    
    // authors
    public void setAuthorsList(List<TestAttribute> authorsList) {
        this.authorsList = authorsList;
    }
    
    public void setAuthor(TestAttribute author) {
        authorsList.add(author);
    }
    
    public List<TestAttribute> getAuthorsList() {
        return authorsList;
    }
    
    // logs
    public void setLog(List<Log> logList) {
        this.logList = logList;
    }
    
    public void setLog(Log log) {
        logList.add(log);
    }
    
    public List<Log> getLog() {
        return logList;
    }
    
    // screencapture
    public void setScreenCaptureList(List<ScreenCapture> screenCaptureList) {
        this.screenCaptureList = screenCaptureList;
    }
    
    public void setScreenCapture(ScreenCapture screenCapture) {
        screenCaptureList.add(screenCapture);
    }
    
    public List<ScreenCapture> getScreenCaptureList() {
        return screenCaptureList;
    }
    
    // screencast
    public void setScreencastList(List<Screencast> screencastList) {
        this.screencastList = screencastList;
    }
    
    public void setScreencast(Screencast screencast) {
        screencastList.add(screencast);
    }
    
    public List<Screencast> getScreencastList() {
        return screencastList;
    }
    
    // nodes
    public void setNodeList(List<Test> nodeList) {
        this.nodeList = nodeList;
    }
    
    public void setNode(Test node) {
        nodeList.add(node);
    }
    
    public List<Test> getNodeList() {
        return nodeList;
    }  
    
    public void prepareFinalize() {
    	setLogCounts();
        updateTestStatusRecursively(this);
        
        if (status == LogStatus.INFO) {
            status = LogStatus.PASS;
        }
    }
    
    public void trackLastRunStatus() {
        for (Log l : logList) {
            findStatus(l.getLogStatus());
        }
        
        if (status == LogStatus.INFO) {
            status = LogStatus.PASS;
        }
    }
    
    private void updateTestStatusRecursively(Test test) {
        for (Log log : test.logList) {
            findStatus(log.getLogStatus());
        }
        
        if (test.hasChildNodes) {
            for (Test node : test.nodeList) {
                updateTestStatusRecursively(node);
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
        
        if (status == LogStatus.PASS) return;
        
        if (logStatus == LogStatus.PASS) {
            status = LogStatus.PASS;
            return;
        }
        
        if (status == LogStatus.SKIP) return;
        
        if (logStatus == LogStatus.SKIP) {
            status = LogStatus.SKIP;
            return;
        }
        
        if (status == LogStatus.INFO) return;
        
        if (logStatus == LogStatus.INFO) {
            status = LogStatus.INFO;
            return;
        }
        
        status = LogStatus.UNKNOWN;
    }
    
    public Test() {
        internalWarning = "";
        
        id = UUID.randomUUID();
        
        logList = new ArrayList<Log>();
        categoryList = new ArrayList<TestAttribute>();
        authorsList = new ArrayList<TestAttribute>();
        screenCaptureList = new ArrayList<ScreenCapture>();
        screencastList = new ArrayList<Screencast>();
        nodeList = new ArrayList<Test>();
    }
}
