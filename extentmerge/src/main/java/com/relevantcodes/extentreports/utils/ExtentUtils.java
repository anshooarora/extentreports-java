package com.relevantcodes.extentreports.utils;

import com.relevantcodes.extentreports.LogStatus;

public class ExtentUtils {
    public static LogStatus toLogStatus(String logStatus) {
        logStatus = logStatus.toLowerCase();
        
        if (logStatus.equals(LogStatus.PASS.toString())) {
            return LogStatus.PASS;
        }
        
        if (logStatus.equals(LogStatus.FAIL.toString())) {
            return LogStatus.FAIL;
        }
        
        if (logStatus.equals(LogStatus.FATAL.toString())) {
            return LogStatus.FATAL;
        }
        
        if (logStatus.equals(LogStatus.ERROR.toString())) {
            return LogStatus.ERROR;
        }
        
        if (logStatus.equals(LogStatus.WARNING.toString())) {
            return LogStatus.WARNING;
        }
        
        if (logStatus.equals(LogStatus.SKIP.toString())) {
            return LogStatus.SKIP;
        }
        
        if (logStatus.equals(LogStatus.INFO.toString())) {
            return LogStatus.INFO;
        }
        
        return LogStatus.UNKNOWN;
    }
}
