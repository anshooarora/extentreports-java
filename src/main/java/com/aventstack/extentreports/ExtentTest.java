package com.aventstack.extentreports;

import java.io.IOException;
import java.io.Serializable;

import com.aventstack.extentreports.gherkin.model.IGherkinFormatterModel;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.model.Author;
import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.ExceptionInfo;
import com.aventstack.extentreports.model.IAddsMedia;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.MediaType;
import com.aventstack.extentreports.model.ScreenCapture;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.utils.ExceptionUtil;

/**
 * Defines a test. You can add logs, snapshots, assign author and categories to a test and its children.
 * 
 * <p>
 * The below log types will all be logged with <code>Status.PASS</code>:
 * </p>
 * 
 * <pre>
 * test.log(Status.PASS, "details");
 * test.pass("details");
 * test.pass(MarkupHelper.createCodeBlock(code));
 * </pre>
 * 
 * <p>
 * A few notes:
 * </p>
 * 
 * <ul>
 *  <li>Tests started with the <code>createTest</code> method are parent-level, always level 0</li>
 *  <li>Tests started with the <code>createNode</code> method are children, always level 1 and greater</li>
 * </ul>
 */
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
    
    /**
     * Creates a node with description
     * 
     * @param name Name of node
     * @param description A short description
     * 
     * @return {@link ExtentTest} object
     */
    public synchronized ExtentTest createNode(String name, String description) {
        ExtentTest t = new ExtentTest(extent, name, description);
        t.getModel().setLevel(test.getLevel() + 1);
        t.getModel().setParent(getModel());
        test.getNodeContext().add(t.getModel());
        
        extent.addNode(t.getModel());
        
        return t;
    }
    
    /**
     * Creates a BDD-style node with description representing one of the {@link IGherkinFormatterModel}
     * classes such as:
     * 
     * <ul>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.Feature}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.Background}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.Scenario}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.Given}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.When}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.Then}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.And}</li>
     * </ul>
     * 
     * <p>
     * Example:
     * </p>
     * 
     * <pre>
     * test.createNode(Scenario.class, "bddNode", "description");
     * </pre>
     * 
     * @param type A {@link IGherkinFormatterModel} type
     * @param name Name of node
     * @param description A short description
     * 
     * @return {@link ExtentTest} object
     */
    public synchronized ExtentTest createNode(Class<? extends IGherkinFormatterModel> type, String name, String description) {
        ExtentTest t = createNode(name, description);
        t.getModel().setBehaviorDrivenType(type);
        return t;
    }
    
    /**
     * Creates a BDD-style node representing one of the {@link IGherkinFormatterModel} classes such as:
     * 
     * <ul>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.Feature}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.Background}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.Scenario}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.Given}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.When}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.Then}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.And}</li>
     * </ul>
     * 
     * <p>
     * Example:
     * </p>
     * 
     * <pre>
     * test.createNode(Scenario.class, "bddNode");
     * </pre>
     * 
     * @param type A {@link IGherkinFormatterModel} type
     * @param name Name of node
     * 
     * @return {@link ExtentTest} object
     */
    public synchronized ExtentTest createNode(Class<? extends IGherkinFormatterModel> type, String name) {
        return createNode(type, name, null);
    }
    
    /**
     * Creates a BDD-style node with description using name of the Gherkin model such as:
     * 
     * <ul>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.Feature}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.Background}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.Scenario}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.Given}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.When}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.Then}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.And}</li>
     * </ul>
     * 
     * <p>
     * Example:
     * </p>
     * 
     * <pre>
     * test.createNode(new GherkinKeyword("Scenario"), "bddTest", "description");
     * </pre>
     * 
     * @param gherkinKeyword Name of the {@link GherkinKeyword}
     * @param name Name of node
     * @param description A short description
     * 
     * @return {@link ExtentTest}
     */
    public synchronized ExtentTest createNode(GherkinKeyword gherkinKeyword, String name, String description) {       
        return createNode(gherkinKeyword.getKeyword().getClass(), name, description);
    }
    
    /**
     * Creates a BDD-style node using name of the Gherkin model such as:
     * 
     * <ul>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.Feature}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.Background}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.Scenario}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.Given}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.When}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.Then}</li>
     *  <li>{@link com.aventstack.extentreports.gherkin.model.And}</li>
     * </ul>
     * 
     * <p>
     * Example:
     * </p>
     * 
     * <pre>
     * test.createNode(new GherkinKeyword("Scenario"), "bddTest");
     * </pre>
     * 
     * @param gherkinKeyword Name of the {@link GherkinKeyword}
     * @param name Name of node
     * 
     * @return {@link ExtentTest} object
     */
    public synchronized ExtentTest createNode(GherkinKeyword gherkinKeyword, String name) {       
        return createNode(gherkinKeyword.getKeyword().getClass(), name, null);
    }
    
    /**
     * Creates a node
     * 
     * @param name Name of node
     * 
     * @return {@link ExtentTest} object
     */
    public synchronized ExtentTest createNode(String name) {
        return createNode(name, null);
    }

    /**
     * Logs an event with {@link Status} and details
     * 
     * @param status {@link Status}
     * @param details Details
     * 
     * @return {@link ExtentTest} object
     */
    public synchronized ExtentTest log(Status status, String details) {       
        Log evt = createLog(status, details);
        return addLog(evt);
    }
    
    /**
     * Logs an event with {@link Status} and custom {@link Markup} such as:
     * 
     * <ul>
     *  <li>Code block</li>
     *  <li>Label</li>
     *  <li>Table</li>
     * </ul>
     * 
     * @param status {@link Status}
     * @param markup {@link Markup}
     * 
     * @return  {@link ExtentTest} object
     */
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

    /**
     * Logs an event with {@link Status} and exception
     * 
     * @param logStatus {@link Status}
     * @param t {@link Throwable}
     * 
     * @return {@link ExtentTest} object
     */
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
    
    /**
     * Logs an event <code>Status.INFO</code> with details
     * 
     * @param details Details
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest info(String details) {
        log(Status.INFO, details);
        return this;
    }
    
    /**
     * Logs an event with <code>Status.INFO</code> and exception
     * 
     * @param t {@link Throwable}
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest info(Throwable t) {
        log(Status.INFO, t);
        return this;
    }
    
    /**
     * Logs an event with <code>Status.INFO</code> and custom {@link Markup} such as:
     * 
     * <ul>
     *  <li>Code block</li>
     *  <li>Label</li>
     *  <li>Table</li>
     * </ul>
     * 
     * @param m {@link Markup}
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest info(Markup m) {
        log(Status.INFO, m);
        return this;
    }
    
    /**
     * Logs an event <code>Status.PASS</code> with details
     * 
     * @param details Details
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest pass(String details) {
        log(Status.PASS, details);
        return this;
    }
    
    /**
     * Logs an event with <code>Status.PASS</code> and exception
     * 
     * @param t {@link Throwable}
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest pass(Throwable t) {
        log(Status.PASS, t);
        return this;
    }
    
    /**
     * Logs an event with <code>Status.PASS</code> and custom {@link Markup} such as:
     * 
     * <ul>
     *  <li>Code block</li>
     *  <li>Label</li>
     *  <li>Table</li>
     * </ul>
     * 
     * @param m {@link Markup}
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest pass(Markup m) {
        log(Status.PASS, m);
        return this;
    }
    
    /**
     * Logs an event <code>Status.FAIL</code> with details
     * 
     * @param details Details
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest fail(String details) {
        log(Status.FAIL, details);
        return this;
    }
    
    /**
     * Logs an event with <code>Status.FAIL</code> and exception
     * 
     * @param t {@link Throwable}
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest fail(Throwable t) {
        log(Status.FAIL, t);
        return this;
    }
    
    /**
     * Logs an event with <code>Status.FAIL</code> and custom {@link Markup} such as:
     * 
     * <ul>
     *  <li>Code block</li>
     *  <li>Label</li>
     *  <li>Table</li>
     * </ul>
     * 
     * @param m {@link Markup}
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest fail(Markup m) {
        log(Status.FAIL, m);
        return this;
    }
    
    /**
     * Logs an event <code>Status.FATAL</code> with details
     * 
     * @param details Details
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest fatal(String details) {
        log(Status.FATAL, details);
        return this;
    }
    
    /**
     * Logs an event with <code>Status.FATAL</code> and exception
     * 
     * @param t {@link Throwable}
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest fatal(Throwable t) {
        log(Status.FATAL, t);
        return this;
    }
    
    /**
     * Logs an event with <code>Status.FATAL</code> and custom {@link Markup} such as:
     * 
     * <ul>
     *  <li>Code block</li>
     *  <li>Label</li>
     *  <li>Table</li>
     * </ul>
     * 
     * @param m {@link Markup}
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest fatal(Markup m) {
        log(Status.FATAL, m);
        return this;
    }
    
    /**
     * Logs an event <code>Status.WARNING</code> with details
     * 
     * @param details Details
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest warning(String details) {
        log(Status.WARNING, details);
        return this;
    }
    
    /**
     * Logs an event with <code>Status.WARNING</code> and exception
     * 
     * @param t {@link Throwable}
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest warning(Throwable t) {
        log(Status.WARNING, t);
        return this;
    }
    
    /**
     * Logs an event with <code>Status.WARNING</code> and custom {@link Markup} such as:
     * 
     * <ul>
     *  <li>Code block</li>
     *  <li>Label</li>
     *  <li>Table</li>
     * </ul>
     * 
     * @param m {@link Markup}
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest warning(Markup m) {
        log(Status.WARNING, m);
        return this;
    }
    
    /**
     * Logs an event <code>Status.ERROR</code> with details
     * 
     * @param details Details
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest error(String details) {
        log(Status.ERROR, details);
        return this;
    }
    
    /**
     * Logs an event with <code>Status.ERROR</code> and exception
     * 
     * @param t {@link Throwable}
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest error(Throwable t) {
        log(Status.ERROR, t);
        return this;
    }
    
    /**
     * Logs an event with <code>Status.ERROR</code> and custom {@link Markup} such as:
     * 
     * <ul>
     *  <li>Code block</li>
     *  <li>Label</li>
     *  <li>Table</li>
     * </ul>
     * 
     * @param m {@link Markup}
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest error(Markup m) {
        log(Status.ERROR, m);
        return this;
    }
    
    /**
     * Logs an event <code>Status.SKIP</code> with details
     * 
     * @param details Details
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest skip(String details) {
        log(Status.SKIP, details);
        return this;
    }
    
    /**
     * Logs an event with <code>Status.SKIP</code> and exception
     * 
     * @param t {@link Throwable}
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest skip(Throwable t) {
        log(Status.SKIP, t);
        return this;
    }
    
    /**
     * Logs an event with <code>Status.SKIP</code> and custom {@link Markup} such as:
     * 
     * <ul>
     *  <li>Code block</li>
     *  <li>Label</li>
     *  <li>Table</li>
     * </ul>
     * 
     * @param m {@link Markup}
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest skip(Markup m) {
        log(Status.SKIP, m);
        return this;
    }

    /**
     * Assigns a category or group
     * 
     * @param category Category name
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest assignCategory(String category) {
        String cat = category.replace(" ", "");
        
        Category c = new Category();
        c.setName(cat);
        test.setCategory(c);
        
        extent.assignCategory(test, c);
        
        return this;
    }
    
    /**
     * Assigns an author
     * 
     * @param author Author name
     * 
     * @return {@link ExtentTest} object
     */
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

    /**
     * Provides the current run status of the test or node
     * 
     * @return {@link Status}
     */
    public Status getStatus() {
        return getModel().getStatus();
    }

    /**
     * Returns the underlying test which controls the internal model
     * 
     * @return {@link Test} object
     */
    public Test getModel() {        
        return test;
    }
}
