package com.relevantcodes.extentreports;

import java.io.IOException;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

import com.relevantcodes.extentreports.model.Author;
import com.relevantcodes.extentreports.model.Category;
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.ScreenCapture;
import com.relevantcodes.extentreports.model.SystemAttribute;
import com.relevantcodes.extentreports.model.Test;

abstract class Report implements IReport, Serializable {

    private static final long serialVersionUID = -6302628790953442220L;

    private Status reportStatus = Status.UNKNOWN;
    
    private TestAttributeTestContextProvider<Category> testAttrCategoryContext;
    private TestAttributeTestContextProvider<Author> testAttrAuthorContext;
    private ExceptionTestContextImpl exContextBuilder;
    private SessionStatusStats stats;
    private SystemAttributeContext saContext;
    
    private List<ExtentReporter> reporterList;
    private List<String> testRunnerLogs;
    private List<Test> testList;

    protected Report() {
        saContext = new SystemAttributeContext();
    }
    
    protected void attach(ExtentReporter reporter) {
        if (reporterList == null)
            reporterList = new ArrayList<>();
        
        reporterList.add(reporter);
        reporter.start();
    }
    
    protected void detach(ExtentReporter reporter) {
        reporter.stop();
        reporterList.remove(reporter);
    }
    
    protected synchronized void createTest(Test test) {
    	if (testList == null)
            testList = new ArrayList<>();
        
        testList.add(test);
        
        reporterList.forEach(x -> x.onTestStarted(test));
    }
    
    synchronized void addNode(Test node) {
        reporterList.forEach(x -> x.onNodeStarted(node));
    }
    
    synchronized void addLog(Test test, Log log) {
        collectRunInfo();
        
        reporterList.forEach(x -> x.onLogAdded(test, log));
    }
    
    synchronized void assignCategory(Test test, Category category) {
        reporterList.forEach(x -> x.onCategoryAssigned(test, category));
    }
    
    synchronized void assignAuthor(Test test, Author author) {
        reporterList.forEach(x -> x.onAuthorAssigned(test, author));
    }
    
    synchronized void addScreenCapture(Test test, ScreenCapture sc) throws IOException {
        reporterList.forEach(x -> {
            try {
                x.onScreenCaptureAdded(test, sc);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }
    
    void setNodeAttributes(Test node) {
        if (node.hasCategory())
            node.getCategoryList().forEach(x -> testAttrCategoryContext.setAttributeContext((Category) x, node));
        
        if (node.hasAuthor())
            node.getAuthorList().forEach(x -> testAttrAuthorContext.setAttributeContext((Author) x, node));
        
        if (node.hasChildren())
            node.getNodeContext().getAll().forEach(this::setNodeAttributes);
    }
    
    void setNodeExceptionInfo(Test node) {
        if (node.hasException())
            node.getExceptionInfoList().forEach(x -> exContextBuilder.setExceptionContext(x, node));
    }

    protected TestAttributeTestContextProvider<Author> getAuthorContextInfo() { 
    	return testAttrAuthorContext; 
	}
    
    private void updateReportStatus(Status logStatus) {
        int logStatusIndex = Status.getStatusHierarchy().indexOf(logStatus);        
        int reportStatusIndex = Status.getStatusHierarchy().indexOf(reportStatus);
        
        reportStatus = logStatusIndex < reportStatusIndex 
                ? logStatus 
                : reportStatus;
    }

    private void endTest(Test test) {
        test.end();
        updateReportStatus(test.getStatus());
    }

    protected synchronized void flush() {
        collectRunInfo();
        notifyReporters();
    }
    
    private synchronized void collectRunInfo() {
        if (testList == null || testList.isEmpty())
            return;
        
        testList.forEach(this::endTest);

        stats = new SessionStatusStats(testList);
        testAttrCategoryContext = new TestAttributeTestContextProvider<>();
        testAttrAuthorContext = new TestAttributeTestContextProvider<>();
        exContextBuilder = new ExceptionTestContextImpl();
        
        testList.forEach(test -> {
            if (test.hasCategory())
                test.getCategoryList().forEach(x -> testAttrCategoryContext.setAttributeContext((Category) x, test));
                        
            if (test.hasAuthor()) 
                test.getAuthorList().forEach(x -> testAttrAuthorContext.setAttributeContext((Author) x, test));
            
            if (test.hasException())
                test.getExceptionInfoList().forEach(x -> exContextBuilder.setExceptionContext(x, test));
            
            if (test.hasChildren())
                test.getNodeContext().getAll().forEach(x -> {
                    setNodeAttributes(x);
                    setNodeExceptionInfo(x);
                });
        });
    }
    
    private synchronized void notifyReporters() {
        reporterList.forEach(x -> {
            x.setTestList(testList);
            
            x.setCategoryContextInfo(testAttrCategoryContext);
            x.setExceptionContextInfo(exContextBuilder);
            x.setSystemAttributeContext(saContext);
            x.setTestRunnerLogs(testRunnerLogs);
            x.setStatusCount(stats);
        });
        
        reporterList.forEach(ExtentReporter::flush);
    }
    
    protected void end() {
        flush();
        
        reporterList.forEach(ExtentReporter::stop);
        reporterList.clear();
    }
    
    protected void setSystemInfo(SystemAttribute sa) {
        saContext.setSystemAttribute(sa);
    }

    protected void setTestRunnerLogs(String log) {
        if (testRunnerLogs == null)
            testRunnerLogs = new ArrayList<>();
        
        testRunnerLogs.add(log);
    }
    
}
