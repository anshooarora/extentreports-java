/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

public abstract class LogSettings {
	private static String logDateFormat = "yyyy-MM-dd";
	private static String logTimeFormat = "HH:mm:ss";
	
    protected static String getLogTimeFormat() {
    	return logTimeFormat;
    }
    
    protected static void setLogTimeFormat(String format) {
    	logTimeFormat = format;
    }
    
    protected static String getLogDateFormat() {
    	return logDateFormat;
    }
    
    protected static void setLogDateFormat(String format) {
    	logDateFormat = format;
    }
    
    protected static String getLogDateTimeFormat() {
    	return getLogDateFormat() + " " + getLogTimeFormat();
    }
    
    protected static String getLongDateTimeFormat() {
    	return "MMM dd, yyyy hh:mm:ss a";
    }
    
    
}
