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

import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.utils.DateTimeUtil;

public class Test {
    private List<TestAttribute> categoryList;
    private List<TestAttribute> authorsList;
    private List<Log> logList;
    private List<Test> nodeList;
    
    private HashMap<LogStatus, Integer> logCounts;
    
    public boolean isChildNode = false;
    public boolean hasEnded = false;
    public boolean hasChildNodes = false;
    
    private Date startedTime;
    private Date endedTime;
    
    private LogStatus status = LogStatus.UNKNOWN;
    
    private String description;
    private String name;
    
    private UUID id;
    
    private String nodeLevel;
    
    public void setLevelClass(String nodeLevel) {
    	this.nodeLevel = nodeLevel;
    }
    
    public String getLevelClass() {
    	return nodeLevel;
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
    
    public String getFormattedStartedTime() {
    	return DateTimeUtil.getFormattedDateTime(startedTime, "yyyy-MM-dd HH:mm:ss");
    }
    
    // ended time
    public void setEndedTime(Date endedTime) {
        this.endedTime = endedTime;
    }
    
    public Date getEndedTime() {
        return endedTime;
    }
    
    public String getFormattedEndedTime() {
    	return DateTimeUtil.getFormattedDateTime(endedTime, "yyyy-MM-dd HH:mm:ss");
    }
    
    public String getFormattedTimeDiff() {
    	return DateTimeUtil.getDiff(endedTime, startedTime);
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
    
    public List<String> getCategoriesAsTags() {
    	List<String> list = new ArrayList<String>();

    	for (TestAttribute c : categoryList) {
    		list.add(c.getName().toLowerCase());
    	}
    	
    	return list;
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
        id = UUID.randomUUID();
        
        logList = new ArrayList<Log>();
        categoryList = new ArrayList<TestAttribute>();
        authorsList = new ArrayList<TestAttribute>();
        nodeList = new ArrayList<Test>();
    }
}
