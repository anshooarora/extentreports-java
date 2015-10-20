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
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.utils.DateTimeUtil;

public class Test {
	/**
	 * Attribute to mark the test as a child node<br>
	 * Top-most test will always have this attribute as false<br>
	 * eg:<br>
	 *     Parent             - false<br>
	 *         Child          - true<br>
	 *             GrandChild - true<br>
	 */
	public boolean isChildNode = false;
	
	/**
	 * Attribute to mark if the test ended safely<br>
	 * It is marked TRUE when extent.endTest(test) is called
	 */
    public boolean hasEnded = false;
    
    /**
     *  Attribute to denote if the current test has child tests<br>
     *  Default = false<br>
     *  When test.appendChild(child) is called, the flag becomes true
     */
    public boolean hasChildNodes = false;
    
	// test categories
	// parent test contains all categories from child tests
    private List<TestAttribute> categoryList;
    
    // not yet implemented
    // assign author(s) of the test
    private List<TestAttribute> authorsList;
    
    // logs
    private List<Log> logList;

    // child test list
    private List<Test> nodeList;
    
    // detailed log counts for each status type
    // eg:  LogStatus.PASS, 2 -> denotes 2 steps passed in the test
    // eg:  LogStatus.FAIL, 0 -> denotes 0 steps failed in the test
    private HashMap<LogStatus, Integer> logCounts;
    
    // test started time
    private Date startedTime;
    
    // test ended time
    private Date endedTime;
    
    // default status when the test starts
    private LogStatus status = LogStatus.UNKNOWN;

    // test description string
    private String description;

    // test name
    private String name;
    
    // unique id assigned when the test starts
    private UUID id;
    
    // level of the node relative to the top-most parent
    // Parent            - 0
    //    Child          - 1
    //        GrandChild - 2
    private String nodeLevel = "";
    
    // log iterator instance
    private LogIterator iter;
    
    // child node iterator instance
    private NodeIterator nodeIter;

    
    // node iterator
    private class NodeIterator implements Iterator<Test> {
    	private int nodeIterIndex = 0;
    	
    	public NodeIterator() {
    		nodeIterIndex = 0;  		
    	}
    	
		public boolean hasNext() {
			if (nodeList != null && nodeList.size() >= nodeIterIndex + 1) {
				return true;
			}
			
			return false;
		}

		public Test next() {
			if (hasNext()) {
				return nodeList.get(nodeIterIndex++);
			}
			
			return null; 
		}
    	
    }
    
    /**
     * Returns a NodeIterator instance
     * 
     * @return {@link NodeIterator}
     */
    public NodeIterator nodeIterator() {
		nodeIter = new NodeIterator();
		
		return nodeIter;
    }
    
    // log iterator
    private class LogIterator implements Iterator<Log> {
    	private int logIterIndex = 0;
    	
    	public LogIterator() {
    		logIterIndex = 0;
    	}
    	
		public boolean hasNext() {
			if (logList != null && logList.size() >= logIterIndex + 1) {
				return true;
			}
			
			return false;
		}

		public Log next() {
			if (hasNext()) {
				return logList.get(logIterIndex++);
			}
			
			return null; 
		}
    	
    }
    
    /**
     * Returns a LogIterator instance
     * 
     * @return {@link LogIterator}
     */
    public LogIterator logIterator() {
		iter = new LogIterator();
		
		return iter;
    }
	
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
    
    public List<Log> getLog() {
    	return logList;
    }
    
    // nodes
    public void setNodeList(List<Test> nodeList) {
        this.nodeList = nodeList;
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
