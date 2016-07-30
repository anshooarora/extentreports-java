package com.relevantcodes.extentreports;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Anshoo on 4/21/2016.
 */
public enum Status implements Serializable {
    PASS,
    FAIL,
    FATAL,
    ERROR,
    WARNING,
    INFO,
    SKIP,
    DEBUG,
    UNKNOWN;

    private static List<Status> statusHierarchy = Arrays.asList(
            Status.FATAL,
            Status.FAIL,
            Status.ERROR,
            Status.WARNING,
            Status.SKIP,
            Status.PASS,
            Status.INFO,
            Status.UNKNOWN
    );
    
    public static List<Status> getStatusHierarchy() {
        return statusHierarchy;
    }
    
    @Override
    public String toString() {
        switch (this) {
            case PASS: return "pass";
            case FAIL: return "fail";
            case FATAL: return "fatal";
            case ERROR: return "error";
            case WARNING: return "warning";
            case INFO: return "info";
            case SKIP: return "skip";
            case DEBUG: return "debug";
            default: return "unknown";
        }
    }
}