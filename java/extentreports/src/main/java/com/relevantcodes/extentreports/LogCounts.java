package com.relevantcodes.extentreports;

import java.util.HashMap;
import java.util.Iterator;

import com.relevantcodes.extentreports.model.Log;
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
    	Iterator<Log> iter = test.logIterator();
    	Log log;
    	
    	while (iter.hasNext()) {
    		log = iter.next();
    		
            if (log.getLogStatus() == LogStatus.PASS)
                pass++; 
            else if (log.getLogStatus() == LogStatus.FAIL)
            	fail++;
            else if (log.getLogStatus() == LogStatus.FATAL)
            	fatal++;
            else if (log.getLogStatus() == LogStatus.ERROR)
            	error++;
            else if (log.getLogStatus() == LogStatus.WARNING)
            	warning++;
            else if (log.getLogStatus() == LogStatus.INFO)
            	info++;
            else if (log.getLogStatus() == LogStatus.SKIP)
            	skip++;
            else if (log.getLogStatus() == LogStatus.UNKNOWN)
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