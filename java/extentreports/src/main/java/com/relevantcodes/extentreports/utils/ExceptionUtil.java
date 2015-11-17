package com.relevantcodes.extentreports.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {
	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
	}
}
