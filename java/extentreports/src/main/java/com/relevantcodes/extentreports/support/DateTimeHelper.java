/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.support;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeHelper {
	public static String getFormattedDateTime(Date date, String pattern) {
		SimpleDateFormat sdfDate = new SimpleDateFormat(pattern);

	    return sdfDate.format(date);
	}
	
	public static String getFormattedDateTime(String dateTime, String pattern) {
		SimpleDateFormat sdfDate = new SimpleDateFormat(pattern);
		DateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);
		Date date;
		
		try {
			date = format.parse(dateTime);
			return sdfDate.format(date);
		} catch (ParseException e) {
			return dateTime;
		}
	}
}
