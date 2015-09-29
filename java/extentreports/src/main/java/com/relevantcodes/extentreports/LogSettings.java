/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

abstract class LogSettings {
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
