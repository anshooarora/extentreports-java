package com.aventstack.extentreports.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateUtil {
	private static final Logger logger = Logger.getLogger(DateUtil.class.getName());
	
	public static Date parse(String dt, String format) {
	    DateFormat df = new SimpleDateFormat(format);
	    
	    try {
	    	return df.parse(dt);
	    } catch (Exception e) {
	    	logger.log(Level.SEVERE, "", e);
	    	return null;
	    }
	}
}
