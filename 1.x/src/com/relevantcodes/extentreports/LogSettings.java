/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/


package com.relevantcodes.extentreports;

public abstract class LogSettings {
	protected static Boolean displayCallerClass = true;
	protected static String logTimeFormat = "hh:mm:ss";
	protected static String logDateFormat = "mm-dd-yyyy";
	protected static String logDateTimeFormat = logDateFormat + " " + logTimeFormat;
}
