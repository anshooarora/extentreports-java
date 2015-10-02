/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

/**
 * Specifies the log status of the log-event
 * 
 * @author Anshoo Arora
 *
 */
public enum LogStatus {
    PASS, 
    FAIL, 
    FATAL, 
    ERROR, 
    WARNING, 
    INFO, 
    SKIP,
    UNKNOWN;
    
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
