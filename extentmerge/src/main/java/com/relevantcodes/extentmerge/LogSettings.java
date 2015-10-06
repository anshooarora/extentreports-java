/*
* The MIT License (MIT)
* 
* Copyright (c) 2015, Anshoo Arora (Relevant Codes)
*/

package com.relevantcodes.extentmerge;

public abstract class LogSettings {
	protected static String getLogTimeFormat() {
    	return "HH:mm:ss";
    }
    
	protected static String getLogDateFormat() {
    	return "yyyy-MM-dd";
    }
    
	protected static String getLogDateTimeFormat() {
    	return getLogDateFormat() + " " + getLogTimeFormat();
    }
}
