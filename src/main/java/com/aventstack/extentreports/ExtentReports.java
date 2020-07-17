package com.aventstack.extentreports;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import com.aventstack.extentreports.gherkin.GherkinDialectManager;
import com.aventstack.extentreports.gherkin.model.IGherkinFormatterModel;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.model.ReportStats;
import com.aventstack.extentreports.model.SystemEnvInfo;
import com.aventstack.extentreports.model.service.TestService;
import com.aventstack.extentreports.observer.ExtentObserver;

/**
 * <p>
 * The ExtentReports report client for starting reporters and building reports.
 * For most applications, you should have one ExtentReports instance for the
 * entire JVM.
 * </p>
 * 
 * <p>
 * ExtentReports itself does not build any reports, but allows reporters to
 * access information, which in turn build the said reports. An example of
 * building an HTML report and adding information to ExtentX:
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
 * <li>It is mandatory to call the <code>flush</code> method to ensure
 * information is written to the started reporters.</li>
 * <li>You can create standard and BDD-style tests using the
 * <code>createTest</code> method</li>
 * </ul>
 * 
 * @see ExtentTest
 * @see GherkinKeyword
 * @see IGherkinFormatterModel
 * @see Status
 */

public class ExtentReports extends AbstractProcessor implements Writable, AnalysisTypeConfigurable {

    /**
     * Attach a {@link ExtentObserver} reporter, allowing it to access all
     * started tests, nodes and logs
     * 
     * <p>
     * Available reporter types are:
     * </p>
     * 
     * <ul>
     * <li>ExtentHtmlReporter provided by artifactId
     * "extent-html-formatter"</li>
     * <li>ExtentEmailReporter (pro-only) provided by artifactId
     * "extent-email-formatter"</li>
     * <li>KlovReporter provided by artifactId "extent-klov-reporter"</li>
     * <li>ConsoleLogger</li>
     * </ul>
     * 
     * @param reporter
     *            {@link ExtentObserver} reporter
     */
    @SuppressWarnings("rawtypes")
    public void attachReporter(ExtentObserver... observer) {
        attachReporter(Arrays.asList(observer));
    }

    /**
     * Creates a BDD-style test with description representing one of the
     * {@link IGherkinFormatterModel} classes such as:
     * 
     * <ul>
     * <li>{@link com.aventstack.extentreports.gherkin.model.Feature}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.Background}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.Scenario}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.Given}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.When}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.Then}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.And}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.But}</li>
     * </ul>
     * 
     * <p>
     * Example:
     * </p>
     * 
     * <pre>
     * extent.createTest(Feature.class, "feature", "description");
     * extent.createTest(Scenario.class, "scenario", "description");
     * extent.createTest(Given.class, "given", "description");
     * </pre>
     * 
     * @param type
     *            A {@link IGherkinFormatterModel} type
     * @param name
     *            Name of test
     * @param description
     *            A short description of the test
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest createTest(Class<? extends IGherkinFormatterModel> type, String name,
            String description) {
        ExtentTest t = new ExtentTest(this, type, name, description);
        onTestCreated(t.getModel());
        return t;
    }

    /**
     * Creates a BDD-style test representing one of the
     * {@link IGherkinFormatterModel} classes such as:
     * 
     * <ul>
     * <li>{@link com.aventstack.extentreports.gherkin.model.Feature}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.Background}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.Scenario}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.Given}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.When}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.Then}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.And}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.But}</li>
     * </ul>
     * 
     * <p>
     * Example:
     * </p>
     * 
     * <pre>
     * extent.createTest(Feature.class, "feature");
     * extent.createTest(Scenario.class, "scenario");
     * extent.createTest(Given.class, "given");
     * </pre>
     * 
     * @param type
     *            A {@link IGherkinFormatterModel} type
     * @param name
     *            Name of test
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest createTest(Class<? extends IGherkinFormatterModel> type, String name) {
        return createTest(type, name, null);
    }

    /**
     * Creates a BDD-style test with description using name of the Gherkin model
     * such as:
     * 
     * <ul>
     * <li>{@link com.aventstack.extentreports.gherkin.model.Feature}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.Background}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.Scenario}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.Given}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.When}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.Then}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.And}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.But}</li>
     * </ul>
     * 
     * <p>
     * Example:
     * </p>
     * 
     * <pre>
     * extent.createTest(new GherkinKeyword("Feature"), "feature", "description");
     * extent.createTest(new GherkinKeyword("Scenario"), "scenario", "description");
     * extent.createTest(new GherkinKeyword("Given"), "given", "description");
     * </pre>
     * 
     * @param gherkinKeyword
     *            Name of the {@link GherkinKeyword}
     * @param name
     *            Name of test
     * @param description
     *            A short description of the test
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest createTest(GherkinKeyword gherkinKeyword, String name, String description) {
        Class<? extends IGherkinFormatterModel> clazz = gherkinKeyword.getKeyword().getClass();
        return createTest(clazz, name, description);
    }

    /**
     * Creates a BDD-style test using name of the Gherkin model such as:
     * 
     * <ul>
     * <li>{@link com.aventstack.extentreports.gherkin.model.Feature}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.Background}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.Scenario}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.Given}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.When}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.Then}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.And}</li>
     * <li>{@link com.aventstack.extentreports.gherkin.model.But}</li>
     * </ul>
     * 
     * <p>
     * Example:
     * </p>
     * 
     * <pre>
     * extent.createTest(new GherkinKeyword("Feature"), "feature");
     * extent.createTest(new GherkinKeyword("Scenario"), "scenario");
     * extent.createTest(new GherkinKeyword("Given"), "given");
     * </pre>
     * 
     * @param gherkinKeyword
     *            Name of the {@link GherkinKeyword}
     * @param testName
     *            Name of test
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest createTest(GherkinKeyword gherkinKeyword, String testName) {
        return createTest(gherkinKeyword, testName, null);
    }

    /**
     * Creates a test with description
     * 
     * @param name
     *            Name of test
     * @param description
     *            A short test description
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest createTest(String name, String description) {
        ExtentTest t = new ExtentTest(this, name, description);
        onTestCreated(t.getModel());
        return t;
    }

    /**
     * Creates a test
     * 
     * @param name
     *            Name of test
     * 
     * @return {@link ExtentTest} object
     */
    public ExtentTest createTest(String name) {
        return createTest(name, null);
    }

    /**
     * Removes a test
     * 
     * @param test
     *            {@link ExtentTest} object
     */
    public void removeTest(ExtentTest test) {
        onTestRemoved(test.getModel());
    }

    /**
     * Removes a test by name
     * 
     * @param name
     *            The test name
     */
    public void removeTest(String name) {
        TestService.findTest(getTestList(), name).ifPresent(this::onTestRemoved);
    }

    /**
     * Writes test information from the started reporters to their output view
     * 
     * <ul>
     * <li>extent-html-formatter: flush output to HTML file</li>
     * <li>extent-klov-reporter: updates MongoDB collections</li>
     * <li>extent-email-formatter (pro-only): creates or appends to an HTML
     * file</li>
     * <li>ConsoleLogger: no action taken</li>
     * </ul>
     */
    @Override
    public void flush() {
        onFlush();
    }

    /**
     * Adds any applicable system information to all started reporters
     * 
     * <p>
     * Example:
     * </p>
     * 
     * <pre>
     * extent.setSystemInfo("HostName", "AventStack");
     * </pre>
     * 
     * @param k
     *            Name of system variable
     * @param v
     *            Value of system variable
     */
    public void setSystemInfo(String k, String v) {
        onSystemInfoAdded(new SystemEnvInfo(k, v));
    }

    /**
     * Adds logs from test framework tools to the test-runner logs view (if
     * available in the reporter)
     * 
     * <p>
     * TestNG usage example:
     * </p>
     * 
     * <pre>
     * extent.setTestRunnerOutput(Reporter.getOutput());
     * </pre>
     *
     * @param log
     *            Log string from the test runner frameworks such as TestNG or
     *            JUnit
     */
    public void addTestRunnerOutput(List<String> log) {
        log.forEach(this::addTestRunnerOutput);
    }

    /**
     * Adds logs from test framework tools to the test-runner logs view (if
     * available in the reporter)
     * 
     * <p>
     * TestNG usage example:
     * </p>
     * 
     * <pre>
     * for (String s : Reporter.getOutput()) {
     *     extent.setTestRunnerOutput(s);
     * }
     * </pre>
     *
     * @param log
     *            Log string from the test runner frameworks such as TestNG or
     *            JUnit
     */
    public void addTestRunnerOutput(String log) {
        onReportLogAdded(log);
    }

    /**
     * Tries to resolve a {@link Media} location if the supplied path is not
     * found using supplied locations. This can resolve cases where the default
     * path was supplied to be relative for a FileReporter. If the absolute path
     * is not determined, the supplied will be used.
     * 
     * @param path
     *            Dirs used to create absolute path of the {@link Media} object
     * 
     * @return {@link ExtentReports}
     */
    public ExtentReports tryResolveMediaPath(String[] path) {
        setMediaResolverPath(path);
        return this;
    }

    /**
     * Creates the internal models by consuming a JSON archive from a previous
     * run session. This provides the same functionality as available in earlier
     * versions via <code>appendExisting</code>, with the exception of being
     * accessible by all reporters instead of just one.
     * 
     * @param jsonFile
     *            The JSON archive file
     * @throws IOException
     *             Exception thrown if the jsonFile is not found
     */
    public void createDomainFromJsonArchive(File jsonFile) throws IOException {
        convertRawEntities(this, jsonFile);
    }

    /**
     * Creates the internal models by consuming a JSON archive from a previous
     * run session. This provides the same functionality as available in earlier
     * versions via <code>appendExisting</code>, with the exception of being
     * accessible by all reporters instead of just one.
     * 
     * @param jsonFilePath
     *            The JSON archive file
     * @throws IOException
     *             Exception thrown if the jsonFilePath is not found
     */
    public void createDomainFromJsonArchive(String jsonFilePath) throws IOException {
        createDomainFromJsonArchive(new File(jsonFilePath));
    }

    /**
     * Use this setting when building post-execution reports, such as from
     * TestNG IReporter. This setting allows setting test with your own
     * time-stamps. With this enabled, Extent does not use time-stamps for tests
     * at the time they were created.
     * 
     * <p>
     * If this setting is enabled and time-stamps are not specified explicitly,
     * the time-stamps of test creation are used.
     * 
     * @param useManualConfig
     *            Set to true if building reports at the end of execution with
     *            manual configuration
     */
    public void setReportUsesManualConfiguration(boolean useManualConfig) {
        setUsingNaturalConf(!useManualConfig);
    }

    /**
     * Type of AnalysisStrategy for the reporter. Not all reporters support this
     * setting.
     * 
     * <p>
     * There are 2 types of strategies available:
     * 
     * <ul>
     * <li>TEST: Shows analysis at the test and step level</li>
     * <li>SUITE: Shows analysis at the suite, test and step level</li>
     * </ul>
     * 
     * @param strategy
     *            {@link AnalysisStrategy} determines the type of analysis
     *            (dashboard) created for the reporter. Not all reporters will
     *            support this setting.
     */
    @Override
    public void setAnalysisStrategy(AnalysisStrategy strategy) {
        getReport().getStats().setAnalysisStrategy(strategy);
    }

    /**
     * Allows setting the target language for Gherkin keywords.
     * 
     * <p>
     * Default setting is "en"
     * 
     * @param language
     *            A valid dialect from <a href=
     *            "https://github.com/cucumber/cucumber/blob/master/gherkin/gherkin-languages.json">gherkin-languages.json</a>
     * 
     * @throws UnsupportedEncodingException
     *             Thrown if the language is one of the supported language from
     *             <a href=
     *             "https://github.com/cucumber/cucumber/blob/master/gherkin/gherkin-languages.json">gherkin-languages.json</a>
     */
    public void setGherkinDialect(String language) throws UnsupportedEncodingException {
        GherkinDialectManager.setLanguage(language);
    }

    /**
     * Returns an instance of {@link ReportStatusStats} with counts of tests
     * executed by their status (pass, fail, skip etc)
     * 
     * @return an instance of {@link ReportStatusStats}
     */
    public ReportStats getStats() {
        return getReport().getStats();
    }
}