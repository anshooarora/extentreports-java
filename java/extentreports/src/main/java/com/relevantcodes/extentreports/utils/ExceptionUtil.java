package com.relevantcodes.extentreports.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExceptionUtil {
	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        
        return sw.toString();
	}
	
	public static String getExceptionHeadline(Throwable t) {
		Pattern pattern = Pattern.compile("([\\w\\.]+)(:.*)?");
		String stackTrace = getStackTrace(t);
		Matcher matcher = pattern.matcher(stackTrace);
		
		if (matcher.find()) {
			return matcher.group(1);
		}
		
		return null;
	}
}
