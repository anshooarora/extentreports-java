/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.relevantcodes.extentreports.converters.TestConverter;
import com.relevantcodes.extentreports.model.Test;

public class ExtentReports extends Report {
	private static final Logger logger = Logger.getLogger(ExtentReports.class.getName());
	
	/**
	 * <p>
     * Initializes a localized version of Extent HTML report
     * 
     * @param filePath 
     * 		Path of the file, in .htm or .html format
     * 
     * @param replaceExisting 
     * 		Setting to overwrite (TRUE) the existing file or append (FALSE) to it
     * 		<ul>
     * 			<li>true - the file will be replaced with brand new markup, and all existing data
     * 			will be lost. Use this option to create a brand new report</li>
     * 			<li>false - existing data will remain, new tests will be appended to the existing report.
     * 			If the the supplied path does not exist, a new file will be created.</li>
     * 		</ul>
     * 
     * @param displayOrder 
     * 		Determines the order in which your tests will be displayed
     *		<ul>
     *			<li>OLDEST_FIRST (default) - oldest test at the top, newest at the end</li>
     * 			<li>NEWEST_FIRST - newest test at the top, oldest at the end</li>
     * 		</ul>
     * 
     * @param networkMode 
     * 		<ul>
     * 			<li>ONLINE - creates a single report file with all artifacts</li>
     * 			<li>OFFLINE - all report artifacts will be stored locally in <code>%reportFolder%/extentreports</code>
     *				with the following structure:
     *				<br>
     *				- extentreports/css
     *				<br>
     *				- extentreports/js
     * 			</li>
     * 		</ul>
     * 
     * @param locale
     * 		Locale to adapt for the report. All standard text for the report will be displayed
     * 		in the selected locale. 
     */
    public ExtentReports(String filePath, Boolean replaceExisting, DisplayOrder displayOrder, NetworkMode networkMode, Locale locale) {
    	replaceExisting = replaceExisting == null ? true : replaceExisting;
    	displayOrder = displayOrder == null ? DisplayOrder.OLDEST_FIRST : displayOrder;
    	networkMode = networkMode == null ? NetworkMode.ONLINE : networkMode;
    	locale = locale == null ? Locale.ENGLISH : locale;
    	
        setFilePath(filePath);
        setReplaceExisting(replaceExisting);
        setDisplayOrder(displayOrder);
        setNetworkMode(networkMode);
        setDocumentLocale(locale);

        attach(new HTMLReporter(filePath));
        
        if (!replaceExisting) {
        	File file = new File(filePath);
        	
        	if (file.exists()) {
        		TestConverter converter = new TestConverter(this, file);
        		converter.createTestList();
        	}
        }
    }
    
	/**
	 * <p>
     * Initializes Extent HTML report
     * 
     * @param filePath 
     * 		Path of the file, in .htm or .html format
     * 
     * @param replaceExisting 
     * 		Setting to overwrite (TRUE) the existing file or append (FALSE) to it
     * 		<ul>
     * 			<li>true - the file will be replaced with brand new markup, and all existing data
     * 			will be lost. Use this option to create a brand new report</li>
     * 			<li>false - existing data will remain, new tests will be appended to the existing report.
     * 			If the the supplied path does not exist, a new file will be created.</li>
     * 		</ul>
     * 
     * @param displayOrder 
     * 		Determines the order in which your tests will be displayed
     *		<ul>
     *			<li>OLDEST_FIRST (default) - oldest test at the top, newest at the end</li>
     * 			<li>NEWEST_FIRST - newest test at the top, oldest at the end</li>
     * 		</ul>
     * 
     * @param networkMode 
     * 		<ul>
     * 			<li>ONLINE - creates a single report file with all artifacts</li>
     * 			<li>OFFLINE - all report artifacts will be stored locally in <code>%reportFolder%/extentreports</code>
     *				with the following structure:
     *				<br>
     *				- extentreports/css
     *				<br>
     *				- extentreports/js
     * 			</li>
     * 		</ul>
     */
    public ExtentReports(String filePath, Boolean replaceExisting, DisplayOrder displayOrder, NetworkMode networkMode) {
        this(filePath, replaceExisting, displayOrder, networkMode, null);
    }
    
    /**
     * <p>
     * Initializes a localized version of Extent HTML report. To see a list of supported locales,
     * 		visit: http://extentreports.relevantcodes.com
     * 
     * <ul>
     * 	<li>Default setting <code>NetworkMode.ONLINE</code> for {@link NetworkMode} is used</li>
     * </ul> 
     * 
     * @param filePath 
     * 		Path of the file, in .htm or .html format
     * 
     * @param replaceExisting 
     * 		Setting to overwrite (TRUE) the existing file or append (FALSE) to it
     * 		<ul>
     * 			<li>true - the file will be replaced with brand new markup, and all existing data
     * 			will be lost. Use this option to create a brand new report</li>
     * 			<li>false - existing data will remain, new tests will be appended to the existing report.
     * 			If the the supplied path does not exist, a new file will be created.</li>
     * 		</ul>
     * 
     * @param displayOrder 
     * 		Determines the order in which your tests will be displayed
     *		<ul>
     *			<li>OLDEST_FIRST (default) - oldest test at the top, newest at the end</li>
     * 			<li>NEWEST_FIRST - newest test at the top, oldest at the end</li>
     * 		</ul>
     * 
     * @param locale
     * 		Locale to adapt for the report. All standard text for the report will be displayed
     * 		in the selected locale. 
     */
    public ExtentReports(String filePath, Boolean replaceExisting, DisplayOrder displayOrder, Locale locale) {        
        this(filePath, replaceExisting, displayOrder, null, locale);
    }
    
    /**
     * <p>
     * Initializes Extent HTML report
     * 
     * <ul>
     * 	<li>Default setting <code>NetworkMode.ONLINE</code> for {@link NetworkMode} is used</li>
     * </ul> 
     * 
     * @param filePath 
     * 		Path of the file, in .htm or .html format
     * 
     * @param replaceExisting 
     * 		Setting to overwrite (TRUE) the existing file or append (FALSE) to it
     * 		<ul>
     * 			<li>true - the file will be replaced with brand new markup, and all existing data
     * 			will be lost. Use this option to create a brand new report</li>
     * 			<li>false - existing data will remain, new tests will be appended to the existing report.
     * 			If the the supplied path does not exist, a new file will be created.</li>
     * 		</ul>
     * 
     * @param displayOrder 
     * 		Determines the order in which your tests will be displayed
     *		<ul>
     *			<li>OLDEST_FIRST (default) - oldest test at the top, newest at the end</li>
     * 			<li>NEWEST_FIRST - newest test at the top, oldest at the end</li>
     * 		</ul>
     */
    public ExtentReports(String filePath, Boolean replaceExisting, DisplayOrder displayOrder) {        
        this(filePath, replaceExisting, displayOrder, null, null);
    }
    
    /**
     * <p>
     * Initializes a localized version of Extent HTML report. To see a list of supported locales,
     * 		visit: http://extentreports.relevantcodes.com
     * 
     * <ul>
     * 	<li>Default setting <code>DisplayOrder.OLDEST_FIRST</code> is used for {@link DisplayOrder}</li>
     * </ul>
     * 
     * @param filePath 
     * 		Path of the file, in .htm or .html format
     * 
     * @param replaceExisting 
     * 		Setting to overwrite (TRUE) the existing file or append (FALSE) to it
     * 		<ul>
     * 			<li>true - the file will be replaced with brand new markup, and all existing data
     * 			will be lost. Use this option to create a brand new report</li>
     * 			<li>false - existing data will remain, new tests will be appended to the existing report.
     * 			If the the supplied path does not exist, a new file will be created.</li>
     * 		</ul>
     * 
     * @param networkMode 
     * 		<ul>
     * 			<li>ONLINE - creates a single report file with all artifacts</li>
     * 			<li>OFFLINE - all report artifacts will be stored locally in <code>%reportFolder%/extentreports</code>
     *				with the following structure:
     *				<br>
     *				- extentreports/css
     *				<br>
     *				- extentreports/js
     * 			</li>
     *         </ul>
     *         
     * @param locale
     * 		Locale to adapt for the report. All standard text for the report will be displayed
     * 		in the selected locale. 
     */
    public ExtentReports(String filePath, Boolean replaceExisting, NetworkMode networkMode, Locale locale) {
        this(filePath, replaceExisting, null, networkMode, locale);
    }
    
    /**
     * <p>
     * Initializes Extent HTML report
     * 
     * <ul>
     * 	<li>Default setting <code>DisplayOrder.OLDEST_FIRST</code> is used for {@link DisplayOrder}</li>
     * </ul>
     * 
     * @param filePath 
     * 		Path of the file, in .htm or .html format
     * 
     * @param replaceExisting 
     * 		Setting to overwrite (TRUE) the existing file or append (FALSE) to it
     * 		<ul>
     * 			<li>true - the file will be replaced with brand new markup, and all existing data
     * 			will be lost. Use this option to create a brand new report</li>
     * 			<li>false - existing data will remain, new tests will be appended to the existing report.
     * 			If the the supplied path does not exist, a new file will be created.</li>
     * 		</ul>
     * 
     * @param networkMode 
     * 		<ul>
     * 			<li>ONLINE - creates a single report file with all artifacts</li>
     * 			<li>OFFLINE - all report artifacts will be stored locally in <code>%reportFolder%/extentreports</code>
     *				with the following structure:
     *				<br>
     *				- extentreports/css
     *				<br>
     *				- extentreports/js
     * 			</li>
     *         </ul>
     */
    public ExtentReports(String filePath, Boolean replaceExisting, NetworkMode networkMode) {
        this(filePath, replaceExisting, null, networkMode, null);
    }
    
    /**
     * <p>
     * Initializes Extent HTML report
     * 
     * <ul>
     * 	<li>Default setting (true) is used for replaceExisting</li>
     * 	<li>Default setting <code>DisplayOrder.OLDEST_FIRST</code> is used for {@link DisplayOrder}</li>
     * </ul>
     * 
     * @param filePath 
     * 		Path of the file, in .htm or .html format
     * 
     * @param networkMode 
     * 		<ul>
     * 			<li>ONLINE - creates a single report file with all artifacts</li>
     * 			<li>OFFLINE - all report artifacts will be stored locally in <code>%reportFolder%/extentreports</code>
     *				with the following structure:
     *				<br>
     *				- extentreports/css
     *				<br>
     *				- extentreports/js
     * 			</li>
     * 		</ul>
     */
    public ExtentReports(String filePath, NetworkMode networkMode) {
        this(filePath, null, null, networkMode, null);
    }
    
    /**
     * <p>
     * Initializes a localized version of Extent HTML report. To see a list of supported locales,
     * 		visit: http://extentreports.relevantcodes.com
     * 
     * <p>
     * Note: a new report will be created by default since <code>replaceExisting</code> is 
     * <code>true</code> by default
     * 
     * <p>
     * Examples:
     * 
     * <ul>
     * 	<li>English (default): <code>new ExtentReports("filePath", Locale.ENGLISH);</code></li>
     * 	<li>Spanish locale: <code>new ExtentReports("filePath", new Locale("es"));</code></li>
     * </ul>
     * 
     * @param filePath 
     * 		Path of the file, in .htm or .html format
     * 
     * @param replaceExisting 
     * 		Setting to overwrite (TRUE) the existing file or append (FALSE) to it
     * 		<ul>
     * 			<li>true - the file will be replaced with brand new markup, and all existing data
     * 			will be lost. Use this option to create a brand new report</li>
     * 			<li>false - existing data will remain, new tests will be appended to the existing report.
     * 			If the the supplied path does not exist, a new file will be created.</li>
     * 		</ul>
     * 
     * @param locale
     * 		Locale to adapt for the report. All standard text for the report will be displayed
     * 		in the selected locale. 
     */
    public ExtentReports(String filePath, Boolean replaceExisting, Locale locale) {
    	this(filePath, replaceExisting, null, null, locale);
    }
    
    /**
     * <p>
     * Initializes Extent HTML report
     * 
     * <ul>
     * 	<li>Default setting <code>DisplayOrder.OLDEST_FIRST</code> is used for {@link DisplayOrder}</li>
     * 	<li>Default setting <code>NetworkMode.ONLINE</code> for {@link NetworkMode} is used</li> 
     * </ul>
     * 
     * @param filePath 
     * 		Path of the file, in .htm or .html format
     * 
     * @param replaceExisting 
     * 		Setting to overwrite (TRUE) the existing file or append (FALSE) to it
     * 		<ul>
     * 			<li>true - the file will be replaced with brand new markup, and all existing data
     * 			will be lost. Use this option to create a brand new report</li>
     * 			<li>false - existing data will remain, new tests will be appended to the existing report.
     * 			If the the supplied path does not exist, a new file will be created.</li>
     * 		</ul>
     */
    public ExtentReports(String filePath, Boolean replaceExisting) {
        this(filePath, replaceExisting, null, null, null);
    }
    
    /**
     * <p>
     * Initializes a localized version of Extent HTML report. To see a list of supported locales,
     * 		visit: http://extentreports.relevantcodes.com
     * 
     * <p>
     * Note: a new report will be created by default since <code>replaceExisting</code> is 
     * <code>true</code> by default
     * 
     * <p>
     * Examples:
     * 
     * <ul>
     * 	<li>English (default): <code>new ExtentReports("filePath", Locale.ENGLISH);</code></li>
     * 	<li>Spanish locale: <code>new ExtentReports("filePath", new Locale("es"));</code></li>
     * </ul>
     * 
     * @param filePath 
     * 		Path of the file, in .htm or .html format
     * 
     * @param locale
     * 		Locale to adapt for the report. All standard text for the report will be displayed
     * 		in the selected locale. 
     */
    public ExtentReports(String filePath, Locale locale) {
    	this(filePath, null, null, null, locale);
    }
    
    /**
     * <p>
     * Initializes Extent HTML report
     * 
     * <ul>
     * 	<li>Default setting (true) is used for replaceExisting</li>
     * 	<li>Default setting <code>DisplayOrder.OLDEST_FIRST</code> is used for {@link DisplayOrder}</li>
     * 	<li>Default setting <code>NetworkMode.ONLINE</code> for {@link NetworkMode} is used</li>
     * </ul>
     * 
     * @param filePath 
     * 		Path of the file, in .htm or .html format
     */
    public ExtentReports(String filePath) {
        this(filePath, null, null, null, null);
    }
    
    /**
     * <p>
     * Allows performing configuration and customization to the HTML report from 
     * configuration external file
     * 
     * @param configFile 
     * 		Config file (extent-config.xml)
     */
    public void loadConfig(File configFile) {
    	if (!configFile.exists()) {
    		logger.log(
    				Level.WARNING,
    				"Unable to perform report configuration. The file " + configFile.getAbsolutePath() + " was not found."
    		);
    		
    		return;
    	}
    	
    	if (!configFile.getName().endsWith(".xml")) {
    		logger.log(
    				Level.WARNING, 
    				"Unable to perform report configuration. The file " + configFile.getAbsolutePath()  + " must be an XML file."
    		);
    		
    		return;
    	}

    	loadConfig(new Configuration(configFile));
    }

    /**
     * <p>
     * Allows performing configuration and customization to the HTML report from URL resource
     * 
     * @param url
     * 		URL pointer to the resource file
     */
	public void loadConfig(URL url) {
    	loadConfig(new Configuration(url));
    }
    
    /**
     * <p>
     * Allows performing configuration and customization to the HTML report from local resource
     * 
     * @param clazz
     * 		The class relative to which the configuration file will be loaded
     * 
     * @param fileName
     * 		Name of the file from the <code>clazz</code> package
     */
    @SuppressWarnings("rawtypes")
	public void loadConfig(Class clazz, String fileName) {
    	loadConfig(clazz, null, fileName);
    }

    /**
     * <p>
     * Allows performing configuration and customization to the HTML report from local resource.
     * 
     * <p>
     * Example: Sonsider the following <code>clazz</code>, <code>basePackagePath</code> 
     * and <code>fileName</code>:
     * 
     * <ul>
     * 	<li><code>clazz</code> : "com/relevantcodes/extentreports/ExtentReports.class"</li>
     * 	<li><code>basePackagePath</code> : "resources"</li>
     * 	<li><code>fileName</code> : "extent-config.xml"</li>
     * </ul>
     * 
     * <p>
     * The above inputs will build the final path as: "com/relevantcodes/extentreports/resources/extent-config.xml"
     * 
     * @param clazz 
     * 		The class relative to which the configuration file will be loaded
     * 
     * @param basePackagePath 
     * 		The package that contains the configuration file. The basePackagePath is relative
     * 		to the <code>clazz</code>
     * 
     * @param fileName
     * 		Name of the file from the <code>clazz</code> package
     */
    @SuppressWarnings("rawtypes")
	public void loadConfig(Class clazz, String basePackagePath, String fileName) {
    	String fullPackagePath = 
    			clazz.getPackage().getName().replace(".", File.separator) 
    			+ File.separator
    			+ fileName;
    	
    	if (basePackagePath != null) {
	    	fullPackagePath = 
	    			clazz.getPackage().getName().replace(".", File.separator) 
	    			+ File.separator 
	    			+ basePackagePath
	    			+ File.separator
	    			+ fileName;
    	}
    	
    	URL url = getClass().getClassLoader().getResource(fullPackagePath);

    	if (url == null) {
    		logger.log(
    				Level.WARNING,
    				"Unable to perform report configuration. The package or file " + fullPackagePath + " was not found."
    		);
    		
    		return;
    	}
    	
    	loadConfig(new Configuration(url));
    }
    
    /**
     * <p>
     * Starts a custom reporter based upon {@link ReporterType}. The file extension determines if 
     * the reporter-type will be started.
     * 
     * <p>
     * For <code>ReporterType</code> the extension must be <code>.db</code>
     * 
     * @param reporterType {@link ReporterType} 
     * 		Type of the reporter to be initialized
     * 
     * @param filePath 
     * 		Path of the report source, with the correct extension for the reporter
     * 
     * @return
     * 		An {@link ExtentReports} object
     */
    public synchronized ExtentReports startReporter(ReporterType reporterType, String filePath) {
        if (reporterType == ReporterType.DB) {
            attach(new DBReporter(filePath));
        }
        
        return this;
    }
    
    /**
     * <p>
     * Calling startTest() generates a toggle for the test in the HTML file and adds all
     * log events under this level. This is a required step and without calling this method
     * the toggle will not be created for the test and log will not be added.
     * 
     * @param testName 
     * 		Name of the test
     * 
     * @return
     * 		An {@link ExtentTest} object
     */
    public synchronized ExtentTest startTest(String testName) {
        return startTest(testName, "");
    }
    
    /**
     * <p>
     * Calling startTest() generates a toggle for the test in the HTML file and adds all
     * log events under this level. This is a required step and without calling this method
     * the toggle will not be created for the test and log will not be added.
     * 
     * @param testName 
     * 		Name of the test
     * 
     * @param description 
     * 		A short description of the test
     * 
     * @return 
     * 		An {@link ExtentTest} object
     */
    public synchronized ExtentTest startTest(String testName, String description) {
        if (testList == null) {
            testList = new ArrayList<ExtentTest>();
        }
        
        ExtentTest test = new ExtentTest(testName, description);
        testList.add(test);
        
        return test;
    }

    /**
     * <p>
     * Ends and prepares the test to be added to the report on <code>flush()</code>
     * 
     * <p>
     * If <code>ReporterType.DB</code> is used, a row in the TEST table is created
     * 
     * @param extentTest 
     * 		An {@link ExtentTest} object
     */
    public synchronized void endTest(ExtentTest extentTest) {
    	Test test = extentTest.getInternalTest();    	
        test.hasEnded = true;

        addTest(test);
    }
    
    /**
     * <p>
     * Add system information to the SystemInfo view
     * 
     * @param info 
     * 		SystemInfo values as Key-Value pairs
     * @return 
     * 		An {@link ExtentReports} object
     */
    public ExtentReports addSystemInfo(Map<String, String> info) {
        systemInfo.setInfo(info);
        
        return this;
    }

    /**
     * <p>
     * Add system information to the SystemInfo view
     * 
     * @param param 
     * 		Name of system parameter
     * 
     * @param value 
     * 		Value of system parameter
     * 
     * @return 
     * 		An {@link ExtentReports} object
     */
    public ExtentReports addSystemInfo(String param, String value) {
        systemInfo.setInfo(param, value);
        
        return this;
    }
    
    /**
     * <p>
     * Adds logs from test framework tools such as TestNG
     *     
     * @param log 
     * 		Log string from the TestRunner
     */
    public void setTestRunnerOutput(String log) {
        setTestRunnerLogs(log);
    }
    
    /**
     * <p>
     * Appends the HTML file with all the ended tests. There must be at least 1 ended test
     * for anything to be appended to the report.
     * 
     * <p>
     * Note: If <code>flush()</code> is called before any of the ended tests, 
     * no information will be appended.
     * 
     * <p>
     * Note: If <code>flush()</code> is called while the test is running (not yet ended),
     * it will not be appended to the report.
     */
    public synchronized void flush() {
        removeChildTests();
        
        super.flush();
    }
    
    /**
     * <p>
     * Closes the underlying stream and clears all resources
     * 
     * <p>
     * Once close is called, additional information will not be sent to the report. Any
     * attempt to <code>flush()</code> will result in a <code>IOException</code>.
     * 
     * <p>
     * If any of your test ended abruptly causing any side-affects  (not all logs sent 
     * to ExtentReports, information missing), this method will ensure that the test is 
     * still appended to the report with a warning message.
     */
    public synchronized void close() {
        terminate();
        
        if (testList != null) {
            testList.clear();
        }
    }
    
    /**
     * <p>
     * This method is deprecated and replace with an external configuration file. For more
     * information, visit this link: 
     * extentreports.relevantcodes.com/java/version2/docs.html#configuration
     * 
     * @return 
     * 		HTMLReporter.Config object
     */
    @Deprecated
    public HTMLReporter.Config config() {
        HTMLReporter hr = new HTMLReporter(null);
        return hr.new Config();
    }
    
    /**
     * Removes all child nodes in <code>testList</code> - a container for parent tests only. 
     * When <code>flush()</code> is called, it adds all parent tests to the report and child 
     * tests as nodes. This method makes sure no child tests are added as top-level nodes. 
     */
    private synchronized void removeChildTests() {
        if (testList == null) {
            return;
        }
        
        Iterator<ExtentTest> iterator = testList.iterator();
        Test t;
        
        while (iterator.hasNext()) {
            t = iterator.next().getInternalTest();
            
            if (t.isChildNode) {
                iterator.remove();
            }
        }
    }
}
