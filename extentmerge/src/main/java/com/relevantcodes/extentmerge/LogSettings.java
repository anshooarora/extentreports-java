/*
* The MIT License (MIT)
* 
* Copyright (c) 2015, Anshoo Arora (Relevant Codes)
*/

package com.relevantcodes.extentmerge;

public abstract class LogSettings {
    public static String getLogTimeFormat() {
        return "HH:mm:ss";
    }
    
    public static String getLogDateFormat() {
        return "yyyy-MM-dd";
    }
    
    public static String getLogDateTimeFormat() {
        return getLogDateFormat() + " " + getLogTimeFormat();
    }
}
