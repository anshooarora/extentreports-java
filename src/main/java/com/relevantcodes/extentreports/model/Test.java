/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.relevantcodes.extentreports.LogCounts;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.utils.DateTimeUtil;

public class Test implements ITest, Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2685089412285339081L;

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
    
    // assign author(s) of the test
    private List<TestAttribute> authorsList;
    
    // list of exceptions occurred for the test
    private List<ExceptionInfo> exceptionList;
    
    // logs
    private List<Log> logList;
    
    // screencapture list <img />
    private List<ScreenCapture> screenCaptureList;
    
    // screencast / video <video />
    private List<Screencast> screencastList;
    
    // child test list
    private List<Test> nodeList;
    
    // detailed log counts for each status type
    // eg:  LogStatus.PASS, 2 -> denotes 2 steps passed in the test
    // eg:  LogStatus.FAIL, 0 -> denotes 0 steps failed in the test
    private HashMap<LogStatus, Integer> logCounts;
    
    // test started time
    private Date startTime;
    
    // test ended time
    private Date endTime;
    
    // default status when the test starts
    private LogStatus status = LogStatus.UNKNOWN;

    // test description string
    private String description;
    
    // internal warning - only enabled if the test is not ended safely
    private String internalWarning = null;
    
    // test name
    private String name;

    // this test's parent, if this is a child node
    private Test parentTest;
    
    // unique id assigned when the test starts
    private UUID id;
    
    // log iterator
    private class LogIterator implements Iterator<Log> {
        private int logIterIndex;
        
        public LogIterator() {
            logIterIndex = 0;
        }
        
        public boolean hasNext() {
            if (logList != null && logList.size() >= logIterIndex + 1) {
                return true;
            }
            
            return false;
        }
		
		public void remove(){}

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
        return new LogIterator();
    }
    
    // TestAttribute (category, author) iterator
    private class TestAttributeIterator<T extends TestAttribute> implements Iterator<TestAttribute> {
        private int attrIterIndex;
        private List<TestAttribute> list;
        
        public TestAttributeIterator(Class<T> type) {
            attrIterIndex = 0;
            
            if (type == Category.class) {
                list = categoryList;
            }
            else {
                list = authorsList;
            }
        }
		
		public void remove(){}
        
        public boolean hasNext() {
            if (list != null && list.size() >= attrIterIndex + 1) {
                return true;
            }
            
            return false;
        }
        
        public TestAttribute next() {
            if (hasNext()) {
                return list.get(attrIterIndex++);
            }
            
            return null;
        }
    }
    
    /**
     * Returns a TestAttributeIterator instance
     * 
     * @return {@link TestAttributeIterator}
     */
    public TestAttributeIterator<Author> authorIterator() {
        return new TestAttributeIterator<Author>(Author.class);
    }
    
    /**
     * Returns a TestAttributeIterator instance
     * 
     * @return {@link TestAttributeIterator}
     */
    public TestAttributeIterator<Category> categoryIterator() {
        return new TestAttributeIterator<Category>(Category.class);
    }
    
    private void setLogCounts() {
        this.logCounts = new LogCounts().getLogCounts(this);
    }
    
    public HashMap<LogStatus, Integer> getLogCounts() {
        return logCounts;
    }
    
    // started time
    public void setStartedTime(Date startedTime) {
        this.startTime = startedTime;
    }
    
    public Date getStartedTime() {
        return startTime;
    }
    
    public String getRunDuration() {
        return DateTimeUtil.getDiff(endTime, startTime);
    }
    
    // ended time
    public void setEndedTime(Date endedTime) {
        this.endTime = endedTime;
    }
    
    public Date getEndedTime() {
        return endTime;
    }

    /**
     * Set the status of the test
     * @param status
     *          The status ({@link LogStatus}) to be set
     */
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
    
    @Override
    public void setUUID(UUID id) {
    this.id = id;
    }

    // categories
    public void setCategory(TestAttribute category) {
        categoryList.add(category);
    }
    
    public List<TestAttribute> getCategoryList() {
        return categoryList;
    }
    
    // authors
    public void setAuthor(TestAttribute author) {
        authorsList.add(author);
    }
    
    public List<TestAttribute> getAuthorsList() {
        return authorsList;
    }
    
    // exceptions
    public void setException(ExceptionInfo exceptionInfo) {
    if (exceptionList == null) {
        exceptionList = new ArrayList<ExceptionInfo>();
    }
    exceptionList.add(exceptionInfo);
    }
    
    public List<ExceptionInfo> getExceptionList() {
        return exceptionList;
    }
    
    // logs
    public void setLog(List<Log> logList) {
        this.logList = logList;
    }
    
    public void setLog(Log log) {
        logList.add(log);
    }
    
    public List<Log> getLogList() {
        return logList;
    }
    
    public int getLogColumnSize() {
        int logSize = 3;
        
        if (logList.size() > 0 && logList.get(0).getStepName() != "") {
            logSize = 4;
        }
        
        return logSize;
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
    @Override
    public void hasChildNodes(boolean val) {
        hasChildNodes = val;
    }
    
    public void setNodeList(List<Test> nodeList) {
        this.nodeList = nodeList;
        updateTestStatusRecursively(this); 
    }
    
    public void setNode(Test node) {
        nodeList.add(node);
    }
    
    public List<Test> getNodeList() {
        return nodeList;
    }
    
    public void setParentTest(Test test) {
        parentTest = test;
    }
    
    public Test getParentTest() {
        return parentTest;
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
        
        if (status == LogStatus.SKIP) return;
        
        if (logStatus == LogStatus.SKIP) {
            status = LogStatus.SKIP;
            return;
        }
        
        if (status == LogStatus.PASS) return;
        
        if (logStatus == LogStatus.PASS) {
            status = LogStatus.PASS;
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
        setStartedTime(Calendar.getInstance().getTime());
        
        logList = new ArrayList<Log>();
        categoryList = new ArrayList<TestAttribute>();
        authorsList = new ArrayList<TestAttribute>();
        screenCaptureList = new ArrayList<ScreenCapture>();
        screencastList = new ArrayList<Screencast>();
        nodeList = new ArrayList<Test>();
    }
}
