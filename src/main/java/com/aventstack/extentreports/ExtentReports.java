package com.aventstack.extentreports;

import java.util.Arrays;
import java.util.List;

import com.aventstack.extentreports.gherkin.model.IGherkinFormatterModel;
import com.aventstack.extentreports.model.SystemAttribute;

/**
 * <p>
 * The ExtentReports report client for starting reporters and building reports. For most applications,
 * you should have one ExtentReports instance for the entire JVM. 
 * </p>
 * 
 * <p>
 * ExtentReports itself does not build any reports, but allows reporters to access information, which in
 * turn build the said reports. An example of building an HTML report and adding information to ExtentX:
 * </p>
 * 
 * <pre>
 * ExtentHtmlReporter html = new ExtentHtmlReporter("Extent.html");
 * ExtentXReporter extentx = new ExtentXReporter("localhost");
 * 
 * ExtentReports extent = new ExtentReports();
 * extent.attachReporter(html, extentx);
 * 
 * extent.createTest("TestName").pass("Test Passed");
 * 
 * extent.flush();
 * </pre>
 * 
 * <p>
 * A few notes: 
 * </p>
 * 
 * <ul>
 *  <li>It is mandatory to call the <code>flush</code> method to ensure information is written to the started
 * reporters.</li>
 *  <li>You can create standard and BDD-style tests using the <code>createTest</code> method</li>
 * </ul>
 * 
 * @see ExtentTest
 * @see GherkinKeyword
 * @see IGherkinFormatterModel
 * @see Status
 */
public class ExtentReports extends Report {

    private static final long serialVersionUID = 2722419612318167707L;

    /**
     * Attach a {@link ExtentReporter} reporter, allowing it to access all started tests, nodes and logs 
     * 
     * <p>
     * Available reporter types are:
     * </p>
     * 
     * <ul>
     *  <li>ExtentHtmlReporter</li>
     *  <li>ExtentEmailReporter</li>
     *  <li>ExtentXReporter</li>
     *  <li>ExtentLogger</li>
     * </ul>
     * 
     * @param reporter {@link ExtentReporter} reporter
     */
    public void attachReporter(ExtentReporter... reporter) {
        Arrays.stream(reporter).forEach(this::attach);
    }

    /**
     * Creates a BDD-style test with description representing one of the {@link IGherkinFormatterModel}
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
     * extent.createTest(Feature.class, "bddTest", "description");
     * </pre>
     * 
     * <p>
     * Note: BDD styling is not available on ExtentX server. Your tests will show as regular nodes 2 
     * levels deep.
     * </p>
     * 
     * @param type A {@link IGherkinFormatterModel} type
     * @param testName Name of test
     * @param description A short description of the test
     * 
     * @return {@link ExtentTest} object
     */
    public synchronized ExtentTest createTest(Class<? extends IGherkinFormatterModel> type, String testName, String description) {
        ExtentTest t = new ExtentTest(this, type, testName, description);
        
        createTest(t.getModel());
        
        return t;
    }
    
    /**
     * Creates a BDD-style test representing one of the {@link IGherkinFormatterModel} classes such as:
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
     * extent.createTest(Feature.class, "bddTest");
     * </pre>
     * 
     * <p>
     * Note: BDD styling is not available on ExtentX server. Your tests will show as regular nodes 2 
     * levels deep.
     * </p>
     * 
     * @param type A {@link IGherkinFormatterModel} type
     * @param testName Name of test
     * 
     * @return {@link ExtentTest} object
     */
    public synchronized ExtentTest createTest(Class<? extends IGherkinFormatterModel> type, String testName) {
        return createTest(type, testName, null);
    }
    
    /**
     * Creates a BDD-style test with description using name of the Gherkin model such as:
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
     * extent.createTest(new GherkinKeyword("Feature"), "bddTest", "description");
     * </pre>
     * 
     * <p>
     * Note: BDD styling is not available on ExtentX server. Your tests will show as regular nodes 2 
     * levels deep.
     * </p>
     * 
     * @param gherkinKeyword Name of the {@link GherkinKeyword} 
     * @param testName Name of test
     * @param description A short description of the test
     * 
     * @return {@link ExtentTest} object
     */
    public synchronized ExtentTest createTest(GherkinKeyword gherkinKeyword, String testName, String description) {
        Class<? extends IGherkinFormatterModel> clazz = gherkinKeyword.getKeyword().getClass();
        return createTest(clazz, testName, description);
    }
    
    /**
     * Creates a BDD-style test using name of the Gherkin model such as:
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
     * extent.createTest(new GherkinKeyword("Feature"), "bddTest", "description");
     * </pre>
     *   
     * <p>
     * Note: BDD styling is not available on ExtentX server. Your tests will show as regular nodes 2 
     * levels deep.
     * </p>
     * 
     * @param gherkinKeyword Name of the {@link GherkinKeyword}
     * @param testName Name of test
     * 
     * @return {@link ExtentTest} object
     */
    public synchronized ExtentTest createTest(GherkinKeyword gherkinKeyword, String testName) {
        return createTest(gherkinKeyword, testName, null);
    }
    
    /**
     * Creates a test with description
     * 
     * @param testName Name of test
     * @param description A short test description
     * 
     * @return {@link ExtentTest} object
     */
    public synchronized ExtentTest createTest(String testName, String description) {
        ExtentTest t = new ExtentTest(this, testName, description);
        
        createTest(t.getModel());
        
        return t;
    }

    /**
     * Creates a test
     * 
     * @param testName Name of test
     * 
     * @return {@link ExtentTest} object
     */
    public synchronized ExtentTest createTest(String testName) {
        return createTest(testName, null);
    }
    
    /**
     * Writes test information from the started reporters to their output view
     * 
     * <ul>
     *  <li>ExtentHtmlReporter - creates or appends to an HTML file</li>
     *  <li>ExtentXReporter - updates database</li>
     *  <li>ExtentEmailReporter - creates or appends to an HTML file</li>
     *  <li>ExtentLogger - no action taken</li>
     * </ul>
     */
    public synchronized void flush() {
        super.flush();
    }
    
    public void close() {
    	end();
    }
    
    /**
     * Adds any applicable system information to all started reporters
     * 
     * <p>
     * Example:
     * </p>
     * 
     * <pre>
     * extent.setSystemInfo("HostName", "AventStack-PC");
     * </pre>
     * 
     * @param k Name of system variable
     * @param v Value of system variable
     */
    public void setSystemInfo(String k, String v) {
        SystemAttribute sa = new SystemAttribute(k, v);        
        super.setSystemInfo(sa);
    }
    
    /**
     * Adds logs from test framework tools to the test-runner logs view (if available in the reporter)
     * 
     * @param log Log string from the test runner frameworks such as TestNG or JUnit
     */
    public void setTestRunnerOutput(List<String> log) {
        log.forEach(this::setTestRunnerLogs);
    }
    
    /**
     * Adds logs from test framework tools to the test-runner logs view (if available in the reporter)
     * 
     * @param log Log string from the test runner frameworks such as TestNG or JUnit
     */
    public void setTestRunnerOutput(String log) {
        setTestRunnerLogs(log);
    }

}
