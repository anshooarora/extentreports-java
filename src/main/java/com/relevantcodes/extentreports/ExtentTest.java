package com.relevantcodes.extentreports;

import java.io.IOException;
import java.io.Serializable;

import com.relevantcodes.extentreports.gherkin.model.IGherkinFormatterModel;
import com.relevantcodes.extentreports.markuputils.Markup;
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

    ExtentTest(ExtentReports extent, Class<? extends IGherkinFormatterModel> type, String testName, String description) {
        if (testName == null || testName.isEmpty())
            throw new IllegalArgumentException("testName cannot be null or empty");
        
        this.extent = extent;
        
        test = new Test();
        test.setName(testName.trim()); 
        test.setDescription(description == null ? "" : description.trim());
        
        if (type != null)
            test.setBehaviorDrivenType(type);
    }
    
    ExtentTest(ExtentReports extent, String testName, String description) {
        this(extent, null, testName, description);
    }
    
    public synchronized ExtentTest createNode(String name, String description) {
        ExtentTest t = new ExtentTest(extent, name, description);
        t.getModel().setLevel(test.getLevel() + 1);
        t.getModel().setParent(getModel());
        test.getNodeContext().add(t.getModel());
        
        extent.addNode(t.getModel());
        
        return t;
    }
    
    public synchronized ExtentTest createNode(Class<? extends IGherkinFormatterModel> type, String name, String description) {
        ExtentTest t = createNode(name, description);
        t.getModel().setBehaviorDrivenType(type);
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

    public synchronized ExtentTest log(Status status, String details) {       
        Log evt = createLog(status, details);
        return addLog(evt);
    }
    
    public synchronized ExtentTest log(Status status, Markup markup) {
        String details = markup.getMarkup();
        return log(status, details);
    }
    
    private synchronized ExtentTest addLog(Log evt) {
        test.getLogContext().add(evt);
        test.end();
        
        extent.addLog(test, evt);
        
        return this;
    }
    
    private Log createLog(Status status) {
        Log evt = new Log();
        evt.setStatus(status);
        evt.setSequence(test.getLogContext().getAll().size() + 1);
        
        return evt;
    }
    
    private Log createLog(Status status, String details) {
        Log evt = createLog(status);
        evt.setDetails(details == null ? "" : details.trim());
        
        return evt;
    }

    public synchronized ExtentTest log(Status logStatus, Throwable t) {
        ExceptionInfo exInfo = new ExceptionInfo();
        exInfo.setException(t);
        exInfo.setExceptionName(ExceptionUtil.getExceptionHeadline(t));
        exInfo.setStackTrace(ExceptionUtil.getStackTrace(t));
        
        getModel().setExceptionInfo(exInfo);
        if (getModel().getLevel() > 1)
            getModel().getParent().setExceptionInfo(exInfo);
        
        log(logStatus, exInfo.getStackTrace());
        
        return this;
    }
    
    public ExtentTest info(String details) {
        log(Status.INFO, details);
        return this;
    }
    
    public ExtentTest info(Throwable t) {
        log(Status.INFO, t);
        return this;
    }
    
    public ExtentTest info(Markup m) {
        log(Status.INFO, m);
        return this;
    }
    
    public ExtentTest pass(String details) {
        log(Status.PASS, details);
        return this;
    }
    
    public ExtentTest pass(Throwable t) {
        log(Status.PASS, t);
        return this;
    }
    
    public ExtentTest pass(Markup m) {
        log(Status.PASS, m);
        return this;
    }
    
    public ExtentTest fail(String details) {
        log(Status.FAIL, details);
        return this;
    }
    
    public ExtentTest fail(Throwable t) {
        log(Status.FAIL, t);
        return this;
    }
    
    public ExtentTest fail(Markup m) {
        log(Status.FAIL, m);
        return this;
    }
    
    public ExtentTest fatal(String details) {
        log(Status.FATAL, details);
        return this;
    }
    
    public ExtentTest fatal(Throwable t) {
        log(Status.FATAL, t);
        return this;
    }
    
    public ExtentTest fatal(Markup m) {
        log(Status.FATAL, m);
        return this;
    }
    
    public ExtentTest warning(String details) {
        log(Status.WARNING, details);
        return this;
    }
    
    public ExtentTest warning(Throwable t) {
        log(Status.WARNING, t);
        return this;
    }
    
    public ExtentTest warning(Markup m) {
        log(Status.WARNING, m);
        return this;
    }
    
    public ExtentTest error(String details) {
        log(Status.ERROR, details);
        return this;
    }
    
    public ExtentTest error(Throwable t) {
        log(Status.ERROR, t);
        return this;
    }
    
    public ExtentTest error(Markup m) {
        log(Status.ERROR, m);
        return this;
    }
    
    public ExtentTest skip(String details) {
        log(Status.SKIP, details);
        return this;
    }
    
    public ExtentTest skip(Throwable t) {
        log(Status.SKIP, t);
        return this;
    }
    
    public ExtentTest skip(Markup m) {
        log(Status.SKIP, m);
        return this;
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

    public Status getStatus() {
        return getModel().getStatus();
    }

    public Test getModel() {        
        return test;
    }
}
