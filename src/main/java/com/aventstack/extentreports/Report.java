package com.aventstack.extentreports;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.aventstack.extentreports.model.Author;
import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.ScreenCapture;
import com.aventstack.extentreports.model.Screencast;
import com.aventstack.extentreports.model.SystemAttribute;
import com.aventstack.extentreports.model.Test;

abstract class Report implements IReport {

    protected boolean usesManualConfiguration = false;
    protected AnalysisStrategy strategy = AnalysisStrategy.TEST;
    
    private Date reportStartDate;
    private Date reportEndDate;
    
    private Status reportStatus = Status.PASS;
    
    private TestAttributeTestContextProvider<Category> categoryContext;
    private TestAttributeTestContextProvider<Author> authorContext;
    private ExceptionTestContextImpl exceptionContextBuilder;
    private SessionStatusStats stats;
    private SystemAttributeContext systemAttributeContext;
    
    private List<ExtentReporter> reporterCollection;
    private List<String> testRunnerLogs;
    private List<Test> testCollection;    
    
    protected Report() {
        systemAttributeContext = new SystemAttributeContext();
        stats = new SessionStatusStats(strategy);
        categoryContext = new TestAttributeTestContextProvider<>();
        authorContext = new TestAttributeTestContextProvider<>();
        exceptionContextBuilder = new ExceptionTestContextImpl();
        
        reportStartDate = Calendar.getInstance().getTime();
    }
    
    protected void attach(ExtentReporter reporter) {
        if (reporterCollection == null)
            reporterCollection = new ArrayList<>();
        
        reporterCollection.add(reporter);
        reporter.start();
    }
    
    protected void detach(ExtentReporter reporter) {
        reporter.stop();
        reporterCollection.remove(reporter);
    }
    
    protected synchronized void createTest(Test test) {
        if (reporterCollection == null || reporterCollection.isEmpty())
            throw new IllegalStateException("No reporters were started. Atleast 1 reporter must be started to create tests.");
        
    	if (testCollection == null)
            testCollection = new ArrayList<>();
        
        testCollection.add(test);
        
        reporterCollection.forEach(x -> x.onTestStarted(test));
    }
    
    protected synchronized void removeTest(Test test) {
        List<Test> testList = testCollection
            .stream()
            .filter(x -> x.getID() == test.getID())
            .collect(Collectors.toList());
        
        if (testList.size() == 1) {
            testCollection.remove(testList.get(0));
            return;
        }
        
        for (Test t : testCollection) {
            testList = t.getNodeContext().getAll()
                .stream()
                .filter(n -> n.getID() == test.getID())
                .collect(Collectors.toList());
            
            if (testList.size() == 1) {
                t.getNodeContext().getAll().remove(testList.get(0));
                return;
            }
        }
    }
        
    synchronized void addNode(Test node) {
        reporterCollection.forEach(x -> x.onNodeStarted(node));
    }
    
    synchronized void addLog(Test test, Log log) {
        collectRunInfo();
        
        reporterCollection.forEach(x -> x.onLogAdded(test, log));
    }
    
    synchronized void assignCategory(Test test, Category category) {
        reporterCollection.forEach(x -> x.onCategoryAssigned(test, category));
    }
    
    synchronized void assignAuthor(Test test, Author author) {
        reporterCollection.forEach(x -> x.onAuthorAssigned(test, author));
    }
    
    synchronized void addScreenCapture(Test test, ScreenCapture sc) throws IOException {
        reporterCollection.forEach(x -> {
            try {
                x.onScreenCaptureAdded(test, sc);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }
    
    synchronized void addScreenCapture(Log log, ScreenCapture sc) throws IOException {
        reporterCollection.forEach(x -> {
            try {
                x.onScreenCaptureAdded(log, sc);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }
    
    synchronized void addScreencast(Test test, Screencast screencast) throws IOException {
        reporterCollection.forEach(x -> {
            try {
                x.onScreencastAdded(test, screencast);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }

    protected TestAttributeTestContextProvider<Author> getAuthorContextInfo() {
    	return authorContext; 
	}
    
    private void updateReportStatus(Status status) {
        int statusIndex = Status.getStatusHierarchy().indexOf(status);        
        int reportStatusIndex = Status.getStatusHierarchy().indexOf(reportStatus);
        
        reportStatus = statusIndex < reportStatusIndex 
                ? status 
                : reportStatus;
    }

    private void endTest(Test test) {
        test.end();
        updateReportStatus(test.getStatus());
    }

    protected synchronized void flush() {
    	if (reporterCollection == null || reporterCollection.isEmpty())
            throw new IllegalStateException("No reporters were started. Atleast 1 reporter must be started to flush results.");
    	
        collectRunInfo();
        notifyReporters();
    }
    
    private synchronized void collectRunInfo() {
        if (testCollection == null || testCollection.isEmpty())
            return;
        
        reportEndDate = Calendar.getInstance().getTime();
        
        testCollection.forEach(this::endTest);
        
        stats.refresh(testCollection);
        
        testCollection.forEach(test -> {
            if (test.hasCategory())
                test.getCategoryContext().getAll().forEach(x -> categoryContext.setAttributeContext((Category) x, test));
                        
            if (test.hasAuthor()) 
                test.getAuthorContext().getAll().forEach(x -> authorContext.setAttributeContext((Author) x, test));
            
            if (test.hasException())
                test.getExceptionInfoList().forEach(x -> exceptionContextBuilder.setExceptionContext(x, test));
            
            if (test.hasChildren())
                test.getNodeContext().getAll().forEach(this::copyNodeAttributeAndRunTimeInfoToAttributeContexts);
        });
        
        updateReportStartTimeForManualConfigurationSetting();
    }
    
    private void updateReportStartTimeForManualConfigurationSetting() {
        if (usesManualConfiguration) {
            testCollection.forEach(test -> {
                Date testStartDate = test.getStartTime();
                Date testEndDate = test.getEndTime();
                long testStartTime = testStartDate.getTime();
                long testEndTime = testEndDate.getTime();
                
                if (reportStartDate.getTime() > testStartTime) {
                    reportStartDate = testStartDate;
                }

                if (reportEndDate.getTime() < testEndTime) {
                    reportEndDate = testEndDate;
                }
            });
        }
    }
    
    private void copyNodeAttributeAndRunTimeInfoToAttributeContexts(Test node) {
        if (node.hasCategory())
            node.getCategoryContext().getAll().forEach(x -> categoryContext.setAttributeContext((Category) x, node));
        
        if (node.hasAuthor())
            node.getAuthorContext().getAll().forEach(x -> authorContext.setAttributeContext((Author) x, node));

        if (node.hasException())
            node.getExceptionInfoList().forEach(x -> exceptionContextBuilder.setExceptionContext(x, node));
        
        if (node.hasChildren())
            node.getNodeContext().getAll().forEach(this::copyNodeAttributeAndRunTimeInfoToAttributeContexts);
    }
        
    private synchronized void notifyReporters() {
        reporterCollection.forEach(x -> {
            x.setTestList(testCollection);
            
            x.setCategoryContextInfo(categoryContext);
            x.setAuthorContextInfo(authorContext);
            x.setExceptionContextInfo(exceptionContextBuilder);
            x.setSystemAttributeContext(systemAttributeContext);
            x.setTestRunnerLogs(testRunnerLogs);
            x.setAnalysisStrategy(strategy);
            x.setStatusCount(stats);
            x.setStartTime(reportStartDate);
            x.setEndTime(reportEndDate);
        });
        
        reporterCollection.forEach(ExtentReporter::flush);
    }
    
    protected void end() {
        flush();
        
        reporterCollection.forEach(ExtentReporter::stop);
        reporterCollection.clear();
    }
    
    protected void setSystemInfo(SystemAttribute sa) {
        systemAttributeContext.setSystemAttribute(sa);
    }

    protected void setTestRunnerLogs(String log) {
        if (testRunnerLogs == null)
            testRunnerLogs = new ArrayList<>();
        
        testRunnerLogs.add(log);
    }
    
    protected void setAnalysisStrategy(AnalysisStrategy strategy) {
        this.strategy = strategy;
        stats = new SessionStatusStats(strategy);
    }
    
}
