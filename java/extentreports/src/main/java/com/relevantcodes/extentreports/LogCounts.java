package com.relevantcodes.extentreports;

import java.util.HashMap;

import com.relevantcodes.extentreports.model.Test;

public class LogCounts {
    private int pass = 0;
    private int fail = 0;
    private int fatal = 0;
    private int error = 0;
    private int warning = 0;
    private int info = 0;
    private int skip = 0;
    private int unknown = 0;
    
    // counts each type of log for the test
    public HashMap<LogStatus, Integer> getLogCounts(Test test) {
    	for (int ix = 0; ix < test.getLog().size(); ix++) {
            if (test.getLog().get(ix).getLogStatus() == LogStatus.PASS)
                pass++; 
            else if (test.getLog().get(ix).getLogStatus() == LogStatus.FAIL)
            	fail++;
            else if (test.getLog().get(ix).getLogStatus() == LogStatus.FATAL)
            	fatal++;
            else if (test.getLog().get(ix).getLogStatus() == LogStatus.ERROR)
            	error++;
            else if (test.getLog().get(ix).getLogStatus() == LogStatus.WARNING)
            	warning++;
            else if (test.getLog().get(ix).getLogStatus() == LogStatus.INFO)
            	info++;
            else if (test.getLog().get(ix).getLogStatus() == LogStatus.SKIP)
            	skip++;
            else if (test.getLog().get(ix).getLogStatus() == LogStatus.UNKNOWN)
            	unknown++;
        }

        // recursively count status events
        for (Test node : test.getNodeList()) {
            getLogCounts(node);
        }
        
        HashMap<LogStatus, Integer> logCounts = new HashMap<LogStatus, Integer>();
    	
        logCounts.put(LogStatus.PASS, pass);
    	logCounts.put(LogStatus.FAIL, fail);
    	logCounts.put(LogStatus.FATAL, fatal);
    	logCounts.put(LogStatus.ERROR, error);
    	logCounts.put(LogStatus.WARNING, warning);
    	logCounts.put(LogStatus.INFO, info);
    	logCounts.put(LogStatus.SKIP, skip);
    	logCounts.put(LogStatus.UNKNOWN, unknown);
    	
    	return logCounts;
    }

    public LogCounts() { }
}