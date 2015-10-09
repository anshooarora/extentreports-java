/*
* The MIT License (MIT)
* 
* Copyright (c) 2015, Anshoo Arora (Relevant Codes)
*/

package com.relevantcodes.extentmerge;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.Log;
import com.relevantcodes.extentreports.utils.DateTimeUtil;

class Logger extends LogSettings {
    private static Queue<Log> logList = new LinkedList<Log>();
    private static Log log;
    
    public static void info(String message) {
        log(LogStatus.INFO, message);
    }
    
    public static void error(String message) {
        log(LogStatus.ERROR, message);
    }
    
    public static void warn(String message) {
        log(LogStatus.WARNING, message);
    }
    
    public static void log(String message) {
        log(LogStatus.UNKNOWN, message);
    }
    
    public static Queue<Log> getLogs() {
        return logList;
    }
    
    private static void log(LogStatus logStatus, String message) {
        log = new Log();
        log.setDetails(message);
        log.setTimestamp(Calendar.getInstance().getTime());
        log.setLogStatus(logStatus);
        
        if (logStatus != LogStatus.UNKNOWN) {
            System.out.println("[" + DateTimeUtil.getFormattedDateTime(log.getTimestamp().getTime(), getLogDateTimeFormat()) + "] [" + logStatus.toString().toUpperCase() + "] "  + message);
        }
        else {
            System.out.println(message);
        }
        
        logList.add(log);
    }
}
