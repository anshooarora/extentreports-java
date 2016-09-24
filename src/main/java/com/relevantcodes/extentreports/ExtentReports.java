package com.relevantcodes.extentreports;

import java.util.Arrays;
import java.util.List;

import com.relevantcodes.extentreports.gherkin.model.IGherkinFormatterModel;
import com.relevantcodes.extentreports.model.SystemAttribute;

public class ExtentReports extends Report {

    private static final long serialVersionUID = 2722419612318167707L;

    public void attachReporter(ExtentReporter... reporter) {
        Arrays.stream(reporter).forEach(this::attach);
    }

    public synchronized ExtentTest createTest(Class<? extends IGherkinFormatterModel> type, String testName, String description) {
        ExtentTest t = new ExtentTest(this, type, testName, description);
        
        createTest(t.getModel());
        
        return t;
    }
    
    public synchronized ExtentTest createTest(Class<? extends IGherkinFormatterModel> type, String testName) {
        return createTest(type, testName, null);
    }
    
    public synchronized ExtentTest createTest(GherkinKeyword gherkinKeyword, String testName, String description) {
        Class<? extends IGherkinFormatterModel> clazz = gherkinKeyword.getKeyword().getClass();
        return createTest(clazz, testName, description);
    }
    
    public synchronized ExtentTest createTest(GherkinKeyword gherkinKeyword, String testName) {
        return createTest(gherkinKeyword, testName, null);
    }
    
    public synchronized ExtentTest createTest(String testName, String description) {
        ExtentTest t = new ExtentTest(this, testName, description);
        
        createTest(t.getModel());
        
        return t;
    }

    public synchronized ExtentTest createTest(String testName) {
        return createTest(testName, null);
    }
    
    public synchronized void flush() {
        super.flush();
    }
    
    public void close() {
    	end();
    }
    
    public void setSystemInfo(String k, String v) {
        SystemAttribute sa = new SystemAttribute();
        sa.setName(k);
        sa.setValue(v);
        
        super.setSystemInfo(sa);
    }
    
    public void setTestRunnerOutput(List<String> log) {
        log.forEach(this::setTestRunnerLogs);
    }
    
    public void setTestRunnerOutput(String log) {
        setTestRunnerLogs(log);
    }

}
