package com.relevantcodes.extentreports;

import java.util.Arrays;
import java.util.List;

import com.relevantcodes.extentreports.model.SystemAttribute;

public class ExtentReports extends Report {
    
    public void attachReporter(ExtentReporter... reporter) {
        Arrays.stream(reporter).forEach(this::attach);
    }

    public synchronized ExtentTest createTest(String testName, String description) {
        ExtentTest t = new ExtentTest(this, testName, description);
        super.createTest(t.getInternalTest());
        
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
