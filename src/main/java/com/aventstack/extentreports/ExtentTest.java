package com.aventstack.extentreports;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

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
import com.aventstack.extentreports.utils.StringUtil;

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

    public synchronized ExtentTest log(Status status, String details, MediaEntityModelProvider provider) {
    	Log evt = createLog(status, details);
    	
    	if (provider != null) {
    		evt.setScreenCapture((ScreenCapture) provider.getMedia());
    	}
    	
    	return addLog(evt);
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
        return log(status, details, null);
    }
    
    /**
     * Logs an event with {@link Status} and custom {@link Markup} such as:
     * 
     * <ul>
     * 	<li>Code block</li>
     * 	<li>Label</li>
     * 	<li>Table</li>
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
        Log evt = new Log(this);
        evt.setStatus(status);
        evt.setSequence(test.getLogContext().getAll().size() + 1);
        
        return evt;
    }
    
    private Log createLog(Status status, String details) {
        Log evt = createLog(status);
        evt.setDetails(details == null ? "" : details.trim());
        
        return evt;
    }

    public synchronized ExtentTest log(Status status, Throwable t, MediaEntityModelProvider provider) {
        ExceptionInfo exInfo = new ExceptionInfo();
        exInfo.setException(t);
        exInfo.setExceptionName(ExceptionUtil.getExceptionHeadline(t));
        exInfo.setStackTrace(ExceptionUtil.getStackTrace(t));
        
        getModel().setExceptionInfo(exInfo);
        if (getModel().getLevel() > 1)
            getModel().getParent().setExceptionInfo(exInfo);
        
        log(status, exInfo.getStackTrace(), provider);
        
        return this;
    }
    
    /**
     * Logs an event with {@link Status} and exception
     * 
     * @param status {@link Status}
     * @param t {@link Throwable}
     * 
     * @return {@link ExtentTest} object
     */
    public synchronized ExtentTest log(Status status, Throwable t) {
        return log(status, t, null);
    }
    
    public ExtentTest info(String details, MediaEntityModelProvider provider) {
    	log(Status.INFO, details, provider);
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
        return info(details, null);
    }
    
    public ExtentTest info(Throwable t, MediaEntityModelProvider provider) {
    	log(Status.INFO, t, provider);
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
        return info(t, null);
    }
    
    /**
     * Logs an event with <code>Status.INFO</code> and custom {@link Markup} such as:
     * 
     * <ul>
     * 	<li>Code block</li>
     * 	<li>Label</li>
     * 	<li>Table</li>
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
    
    public ExtentTest pass(String details, MediaEntityModelProvider provider) {
    	log(Status.PASS, details, provider);
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
        return pass(details, null);
    }
    
    public ExtentTest pass(Throwable t, MediaEntityModelProvider provider) {
    	log(Status.PASS, t, provider);
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
        return pass(t, null);
    }
    
    /**
     * Logs an event with <code>Status.PASS</code> and custom {@link Markup} such as:
     * 
     * <ul>
     * 	<li>Code block</li>
     * 	<li>Label</li>
     * 	<li>Table</li>
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
    
    public ExtentTest fail(String details, MediaEntityModelProvider provider) {
    	log(Status.FAIL, details, provider);
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
        return fail(details, null);
    }
    
    public ExtentTest fail(Throwable t, MediaEntityModelProvider provider) {
    	log(Status.FAIL, t, provider);
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
        return fail(t, null);
    }
    
    /**
     * Logs an event with <code>Status.FAIL</code> and custom {@link Markup} such as:
     * 
     * <ul>
     * 	<li>Code block</li>
     * 	<li>Label</li>
     * 	<li>Table</li>
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
    
    public ExtentTest fatal(String details, MediaEntityModelProvider provider) {
    	log(Status.FATAL, details, provider);
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
    
    public ExtentTest fatal(Throwable t, MediaEntityModelProvider provider) {
    	log(Status.FATAL, t, provider);
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
     * 	<li>Code block</li>
     * 	<li>Label</li>
     * 	<li>Table</li>
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
    
    public ExtentTest warning(String details, MediaEntityModelProvider provider) {
    	log(Status.WARNING, details, provider);
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
        return warning(details, null);
    }
    
    public ExtentTest warning(Throwable t, MediaEntityModelProvider provider) {
    	log(Status.WARNING, t, provider);
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
        return warning(t, null);
    }
    
    /**
     * Logs an event with <code>Status.WARNING</code> and custom {@link Markup} such as:
     * 
     * <ul>
     * 	<li>Code block</li>
     * 	<li>Label</li>
     * 	<li>Table</li>
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
    
    public ExtentTest error(String details, MediaEntityModelProvider provider) {
    	log(Status.ERROR, details, provider);
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
        return error(details, null);
    }
    
    public ExtentTest error(Throwable t, MediaEntityModelProvider provider) {
    	log(Status.ERROR, t, provider);
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
        return error(t, null);
    }
    
    /**
     * Logs an event with <code>Status.ERROR</code> and custom {@link Markup} such as:
     * 
     * <ul>
     * 	<li>Code block</li>
     * 	<li>Label</li>
     * 	<li>Table</li>
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
    
    public ExtentTest skip(String details, MediaEntityModelProvider provider) {
    	log(Status.SKIP, details, provider);
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
        return skip(details, null);
    }
    
    public ExtentTest skip(Throwable t, MediaEntityModelProvider provider) {
    	log(Status.SKIP, t, provider);
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
        return skip(t, null);
    }
    
    /**
     * Logs an event with <code>Status.SKIP</code> and custom {@link Markup} such as:
     * 
     * <ul>
     * 	<li>Code block</li>
     * 	<li>Label</li>
     * 	<li>Table</li>
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
    public ExtentTest assignCategory(String... category) {
    	Arrays.stream(category).filter(StringUtil::isNotNullOrEmpty).forEach(c -> {
    		Category objCategory = new Category(c.replace(" ", ""));
	        test.setCategory(objCategory);
	        
	        extent.assignCategory(test, objCategory);
    	});

        return this;
    }
    
    /**
     * Assigns an author
     * 
     * @param author Author name
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest assignAuthor(String... author) {
    	Arrays.stream(author).filter(StringUtil::isNotNullOrEmpty).forEach(a -> {
    		Author objAuthor = new Author(a.replace(" ", ""));
	        test.setAuthor(objAuthor);
	        
	        extent.assignAuthor(test, objAuthor);
    	});
        
        return this;
    }

    @Override
    public ExtentTest addScreenCaptureFromPath(String imagePath, String title) throws IOException {
        ScreenCapture sc = new ScreenCapture();
        sc.setPath(imagePath);
        if (title != null)
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
