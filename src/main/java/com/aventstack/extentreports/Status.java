package com.aventstack.extentreports;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * List of allowed status for {@link com.aventstack.extentreports.model.Log}
 */
public enum Status implements Serializable {
    PASS,
    FAIL,
    FATAL,
    ERROR,
    WARNING,
    INFO,
    SKIP,
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
    
    /**
     * Returns the hierarchical list of status, in the below order:
     * 
     * <ul>
     *  <li>FATAL</li>
     *  <li>FAIL</li>
     *  <li>ERROR</li>
     *  <li>WARNING</li>
     *  <li>SKIP</li>
     *  <li>PASS</li>
     *  <li>INFO</li>
     * </ul>
     * 
     * @return Hierarchical list of status
     */
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
            default: return "unknown";
        }
    }
}