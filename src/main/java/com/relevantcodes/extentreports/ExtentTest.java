package com.relevantcodes.extentreports;

import java.io.IOException;
import java.io.Serializable;

import com.relevantcodes.extentreports.gherkin.model.IGherkinFormatterModel;
import com.relevantcodes.extentreports.model.Author;
import com.relevantcodes.extentreports.model.Category;
import com.relevantcodes.extentreports.model.ExceptionInfo;
import com.relevantcodes.extentreports.model.IAddsMedia;
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.model.MediaType;
import com.relevantcodes.extentreports.model.ScreenCapture;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.utils.ExceptionUtil;

public class ExtentTest implements IAddsMedia, Serializable {
    
    private static final long serialVersionUID = 9199820968410788862L;

    private ExtentReports extent;
    private Test test;

    public ExtentTest(ExtentReports extent, String testName, String description) {
        this.extent = extent;
        
        test = new Test();
        test.setName(testName == null ? "" : testName.trim()); 
        test.setDescription(description == null ? "" : description.trim());
    }
    
    public synchronized ExtentTest createNode(String name, String description) {
        ExtentTest t = new ExtentTest(extent, name, description);
        t.getInternalTest().setLevel(test.getLevel() + 1);
        t.getInternalTest().setParent(getInternalTest());
        test.getNodeContext().add(t.getInternalTest());
        
        extent.addNode(t.getInternalTest());
        
        return t;
    }
    
    public synchronized ExtentTest createNode(Class<? extends IGherkinFormatterModel> type, String name, String description) {
        ExtentTest t = createNode(name, description);
        t.getInternalTest().setBehaviorDrivenType(type);
        return t;
    }
    
    public synchronized ExtentTest createNode(Class<? extends IGherkinFormatterModel> type, String name) {
        return createNode(type, name, null);
    }
    
    public synchronized ExtentTest createNode(GherkinKeyword gherkinKeyword, String name, String description) {       
        return createNode(gherkinKeyword.getKeyword().getClass(), name, description);
    }
    
    public synchronized ExtentTest createNode(GherkinKeyword gherkinKeyword, String name) {       
        return createNode(gherkinKeyword.getKeyword().getClass(), name, null);
    }
    
    public synchronized ExtentTest createNode(String name) {
        return createNode(name, null);
    }
    
    public synchronized void log(Status logStatus, String details) {
        Log evt = new Log();
        evt.setStatus(logStatus);
        evt.setDetails(details == null ? "" : details.trim());
        evt.setSequence(test.getLogContext().getAll().size() + 1);
        
        test.getLogContext().add(evt);
        test.end();
        
        extent.addLog(test, evt);
    }

    public synchronized void log(Status logStatus, Throwable t) {
        ExceptionInfo exInfo = new ExceptionInfo();
        exInfo.setException(t);
        exInfo.setExceptionName(ExceptionUtil.getExceptionHeadline(t));
        exInfo.setStackTrace(ExceptionUtil.getStackTrace(t));
        
        getInternalTest().setExceptionInfo(exInfo);
        if (getInternalTest().getLevel() > 1)
            getInternalTest().getParent().setExceptionInfo(exInfo);
        
        log(logStatus, "<pre>" + exInfo.getStackTrace() + "</pre>");
    }
    
    public void info(String details) {
        log(Status.INFO, details);
    }
    
    public void info(Throwable t) {
        log(Status.INFO, t);
    }
    
    public void pass(String details) {
        log(Status.PASS, details);
    }
    
    public void pass(Throwable t) {
        log(Status.PASS, t);
    }
    
    public void fail(String details) {
        log(Status.FAIL, details);
    }
    
    public void fail(Throwable t) {
        log(Status.FAIL, t);
    }
    
    public void fatal(String details) {
        log(Status.FATAL, details);
    }
    
    public void fatal(Throwable t) {
        log(Status.FATAL, t);
    }
    
    public void warning(String details) {
        log(Status.WARNING, details);
    }
    
    public void warning(Throwable t) {
        log(Status.WARNING, t);
    }
    
    public void error(String details) {
        log(Status.ERROR, details);
    }
    
    public void error(Throwable t) {
        log(Status.ERROR, t);
    }
    
    public void skip(String details) {
        log(Status.SKIP, details);
    }
    
    public void skip(Throwable t) {
        log(Status.SKIP, t);
    }

    public ExtentTest assignCategory(String category) {
        String cat = category.replace(" ", "");
        
        Category c = new Category();
        c.setName(cat);
        test.setCategory(c);
        
        extent.assignCategory(test, c);
        
        return this;
    }
    
    public ExtentTest assignAuthor(String author) {
        Author a = new Author();
        a.setName(author);        
        test.setAuthor(a);

        extent.assignAuthor(test, a);
        
        return this;
    }

    @Override
    public ExtentTest addScreenCaptureFromPath(String imagePath, String title) throws IOException {
        ScreenCapture sc = new ScreenCapture();
        sc.setPath(imagePath);
        sc.setName(title);
        sc.setMediaType(MediaType.IMG);
        
        test.setScreenCapture(sc);

        if (test.getObjectId() != null) {
            int sequence = test.getScreenCaptureList().size();
            sc.setTestObjectId(test.getObjectId());
            sc.setSequence(sequence);
        }
        
        extent.addScreenCapture(test, sc);
        return this;
    }
    
    @Override
    public ExtentTest addScreenCaptureFromPath(String imagePath) throws IOException {
        return addScreenCaptureFromPath(imagePath, null);
    }

    public Status getRunStatus() {
        return getInternalTest().getStatus();
    }

    Test getInternalTest() {        
        return test;
    }
}
