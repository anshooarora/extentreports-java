/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.relevantcodes.extentreports.model.SuiteTimeInfo;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.model.TestAttribute;
import com.relevantcodes.extentreports.utils.DateTimeUtil;

// Report abstract
abstract class Report extends LogSettings {
	private static final Logger LOGGER = Logger.getLogger(Report.class.getName());
	private static final String INTERNAL_WARNING = "Close was called before test could end safely using EndTest.";
	private static final String DEFAULT_PROTOCOL = "https";
	
	/**
	 * Default configuration file for HTML report. This file is loaded by default
	 * when the {@link ExtentReports} initializes, and overridden when user provides
	 * their own configuration using <code>loadConfig(args)</code>. 
	 */
	private final String extentConfigFile = "extent-config.xml";
	
    private String filePath;
    private DisplayOrder displayOrder;
    private NetworkMode networkMode;
    private Boolean replaceExisting;
    private LogStatus reportStatus = LogStatus.UNKNOWN;    
    private Date startedTime;
    private List<String> testRunnerLogList;
    private List<LogStatus> logStatusList;
    private List<IReporter> reporters;
    private Test test;
    private UUID reportId;
    private Boolean terminated = false;
    private Map<String, List<Test>> categoryTestMap;
    private Map<String, String> configurationMap;
    private Map<String, String> defaultConfiguration;
    private Locale locale = Locale.ENGLISH;
    
    protected SuiteTimeInfo suiteTimeInfo;
    protected SystemInfo systemInfo;
    protected List<ExtentTest> testList;
    protected File configFile = null;
    
    protected List<ExtentTest> getTestList() {
        return testList;
    }
    
    protected List<LogStatus> getLogStatusList() {
    	return logStatusList;
    }
    
    protected Date getStartedTime() {
        return startedTime;
    }
    
    protected String getRunDuration() {
    	return DateTimeUtil.getDiff(Calendar.getInstance().getTime(), startedTime);
    }
    
    protected List<String> getTestRunnerLogList() {
        return testRunnerLogList;
    }

    protected Map<String, String> getConfigurationMap() {
    	return configurationMap;
    }
    
    protected Map<String, List<Test>> getCategoryTestMap() {
    	return categoryTestMap;
    }
    
    protected SystemInfo getSystemInfo() {
        return systemInfo;
    }
    
    protected Map<String, String> getSystemInfoMap() {
    	return getSystemInfo().getInfo();
    }
    
    protected void attach(IReporter reporter) {
        if (reporters == null) {
            reporters = new ArrayList<IReporter>();
        }

        reporters.add(reporter);
        reporter.start(this);
    }
    
    protected void detach(IReporter reporter) {
        reporter.stop();
        reporters.remove(reporter);
    }
    
    protected void addTest(Test test) {
        if (test.getEndedTime() == null) {
            test.setEndedTime(Calendar.getInstance().getTime());
        }

        Iterator<TestAttribute> catIter = test.categoryIterator();
        
        // add each category and associated test
        while (catIter.hasNext()) {
        	TestAttribute category = catIter.next();
        	
        	if (!categoryTestMap.containsKey(category.getName())) {
        		List<Test> testList = new ArrayList<Test>();
        		testList.add(test);
        		
        		categoryTestMap.put(category.getName(), testList);
        	}
        	else {
        		categoryTestMap.get(category.getName()).add(test);
        	}
        }
        
        test.prepareFinalize();
        
        this.test = test;
        
        for (IReporter reporter : reporters) {
            reporter.addTest();
        }
        
        updateReportStatus(test.getStatus());
        
        updateReportStartedTime(test);
    }
    
    protected void updateTestStatusList(Test test) {
        Boolean toAdd = false;

    	toAdd = test.getStatus() == LogStatus.FATAL || test.getStatus() == LogStatus.ERROR || test.getStatus() == LogStatus.WARNING || test.getStatus() == LogStatus.UNKNOWN
    			? true
    			: false;
    	
    	if (toAdd) {
    		if (!logStatusList.contains(test.getStatus())) {
    			logStatusList.add(test.getStatus());
    		}
    	}
        
        if (test.hasChildNodes) {
        	List<Test> nodeList = test.getNodeList();
        
        	for (Test node : nodeList) {
        		updateTestStatusList(node);
        	}
        }
    }
    
    private void updateReportStartedTime(Test test) {
        long testStartedTime = test.getStartedTime().getTime();
        
        if (suiteTimeInfo.getSuiteStartTimestamp() > testStartedTime) {
            suiteTimeInfo.setSuiteStartTimestamp(testStartedTime);
        }
    }
    
    protected void terminate() {
    	for (ExtentTest extentTest : testList) {
    		Test test = extentTest.getTest();
    		
    		if (!test.hasEnded) {
    			ExtentTestInterruptedException e = new ExtentTestInterruptedException(INTERNAL_WARNING);
    			
    			test.setInternalWarning(INTERNAL_WARNING);
    			extentTest.log(LogStatus.FAIL, e);
    			test.hasEnded = true;
    			
    			addTest(test);
    		}
    	}
    	
    	flush();
    	
        Iterator<IReporter> iter = reporters.iterator();
        
        while (iter.hasNext()) {
            iter.next().stop();
            iter.remove();
        }
        
        terminated = true;
    }
    
    protected void flush() {
    	if (terminated) {
            try {
                throw new IOException("Unable to write source: Stream closed.");
            } 
            catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Stream closed", e);
            }
            
            return;
        }
    	
    	for (ExtentTest test : getTestList()) { 
    		updateTestStatusList(test.getTest());
    	}
    	
        for (IReporter reporter : reporters) {
            reporter.flush();            
        }
    }
    
    protected Map<String, String> loadConfig(Configuration config) {
  		configurationMap = config.getConfigurationMap();
  		
  		// if user is using an out-dated version of the configuration file, 
  		// use default configuration for keys that are not available
  		if (defaultConfiguration != null) {
	  		for (Map.Entry<String, String> entry : defaultConfiguration.entrySet()) {
	  			if (!configurationMap.containsKey(entry.getKey())) {
	  				configurationMap.put(entry.getKey(), entry.getValue());
	  			}  		    
	  		}
  		}
  		
  		updateBaseDefaultSettings(configurationMap);
  		
  		return configurationMap;
    }
    
    /**
     * Updates LogSettings with custom values
     * 
     * @param configurationMap
     */
    private void updateBaseDefaultSettings(Map<String, String> configurationMap) {
    	if (configurationMap.get("dateFormat") != null && !configurationMap.get("dateFormat").isEmpty()) {
    		setLogDateFormat(configurationMap.get("dateFormat"));
    	}
    	else {
    		configurationMap.put("dateFormat", getLogDateFormat());
    	}
    		
    	if (configurationMap.get("timeFormat") != null && !configurationMap.get("timeFormat").isEmpty()) {
    		setLogTimeFormat(configurationMap.get("timeFormat"));
    	}
    	else {
    		configurationMap.put("timeFormat", getLogTimeFormat());
    	}
    	
    	if (configurationMap.get("protocol") == null || configurationMap.get("protocol").isEmpty()) {
    		configurationMap.put("protocol", DEFAULT_PROTOCOL);
    	}
    }
    
    protected void setTestRunnerLogs(String logs) {
        testRunnerLogList.add(logs);
    }
    
    protected Test getTest() {
        return test;
    }
    
    protected void setFilePath(String filePath) {
        this.filePath = filePath;
        
        File reportFile = new File(filePath);
        
        if (!reportFile.getParentFile().exists()) {
            reportFile.getParentFile().mkdirs();
        }
    }
    
    protected String getFilePath() {
        return filePath;
    }
    
    protected void setReplaceExisting(Boolean replaceExisting) {
        this.replaceExisting = replaceExisting;
    }
    
    protected Boolean getReplaceExisting() {
        return replaceExisting;
    }
    
    protected void setDisplayOrder(DisplayOrder displayOrder) {
        this.displayOrder = displayOrder;
    }
    
    protected DisplayOrder getDisplayOrder() {
        return displayOrder;
    }
    
    protected void setNetworkMode(NetworkMode networkMode) {
        this.networkMode = networkMode;
    }
    
    protected NetworkMode getNetworkMode() {
        return networkMode;
    }
        
    protected UUID getId() {
        return reportId;
    }

    protected LogStatus getStatus() {
        return reportStatus;
    }
    
    protected SuiteTimeInfo getSuiteTimeInfo() {
        return suiteTimeInfo;
    }
    
    protected void setStartedTime(long startTime) {
        suiteTimeInfo.setSuiteStartTimestamp(startTime);
    }
    
    protected void setDocumentLocale(Locale locale) {
    	this.locale = locale;
    }
    
    protected Locale getDocumentLocale() {
    	return locale;
    }
    
    protected Report() {
    	String resourceFile = Report.class
        		.getPackage()
        		.getName()
        		.replace(".", "/")
        			+ "/resources/"
        			+ extentConfigFile;

    	URL url = getClass().getClassLoader().getResource(resourceFile);
    	defaultConfiguration = loadConfig(new Configuration(url));
    	
    	categoryTestMap = new TreeMap<String, List<Test>>();
    	systemInfo = new SystemInfo();
        suiteTimeInfo = new SuiteTimeInfo();
        testRunnerLogList = new ArrayList<String>();
        logStatusList = new ArrayList<LogStatus>();
        
        reportId = UUID.randomUUID();
        startedTime = new Date(suiteTimeInfo.getSuiteStartTimestamp());
    }
    
    private void updateReportStatus(LogStatus logStatus) {
        if (reportStatus == LogStatus.FATAL) return;
        
        if (logStatus == LogStatus.FATAL) {
            reportStatus = logStatus;
            return;
        }
        
        if (reportStatus == LogStatus.FAIL) return;
        
        if (logStatus == LogStatus.FAIL) {
            reportStatus = logStatus;
            return;
        }
        
        if (reportStatus == LogStatus.ERROR) return;
        
        if (logStatus == LogStatus.ERROR) {
            reportStatus = logStatus;
            return;
        }
        
        if (reportStatus == LogStatus.WARNING) return;
        
        if (logStatus == LogStatus.WARNING) {
            reportStatus = logStatus;
            return;
        }
        
        if (reportStatus == LogStatus.PASS) return;
        
        if (logStatus == LogStatus.PASS) {
            reportStatus = LogStatus.PASS;
            return;
        }
        
        if (reportStatus == LogStatus.SKIP) return;
        
        if (logStatus == LogStatus.SKIP) {
            reportStatus = LogStatus.SKIP;
            return;
        }
        
        if (reportStatus == LogStatus.INFO) return;
        
        if (logStatus == LogStatus.INFO) {
            reportStatus = LogStatus.INFO;
            return;
        }
        
        reportStatus = LogStatus.UNKNOWN;
    }
}
