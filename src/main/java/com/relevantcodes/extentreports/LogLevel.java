/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

/**
 * Sets the maximum logging level for the log events
 * <br>
 * ALLOW_ALL: default setting which passes all log events to the report
 * <br>
 * FAIL:  Allows FAIL and FATAL events sent to the report
 * <br>
 * WARNING:  Allows WARNING and FAIL events to be sent to the report
 * <br>
 * ERROR:  Allows ERROR and FAIL events to be sent to the report
 * <br>
 * ERRORS_AND_WARNING:  Allows Errors, Warnings and FAIL/FATAL events
 * <br> 
 * OFF:  No events are allowed to be written to the report  
 * 
 * @author Anshoo Arora
 *
 */
public enum LogLevel {
    ALLOW_ALL, 
    FAIL, 
    WARNING, 
    ERROR, 
    ERRORS_AND_WARNINGS, 
    OFF
}

