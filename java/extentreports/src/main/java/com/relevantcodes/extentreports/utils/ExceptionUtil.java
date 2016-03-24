package com.relevantcodes.extentreports.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.relevantcodes.extentreports.model.ExceptionInfo;
import com.relevantcodes.extentreports.model.Test;

public class ExceptionUtil {
    private static String getStackTrace(Throwable t) {
	if (t == null) {
	    return null;
	}
	StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);
	t.printStackTrace(pw);
	return sw.toString();
    }

    private static String getExceptionHeadline(Throwable t) {
	Pattern pattern = Pattern.compile("([\\w\\.]+)(:.*)?");
	String stackTrace = getStackTrace(t);
	Matcher matcher = pattern.matcher(stackTrace);
	if (matcher.find()) {
	    return matcher.group(1);
	}
	return null;
    }

    public static ExceptionInfo createExceptionInfo(Throwable t, Test test) {
	ExceptionInfo exceptionInfo = new ExceptionInfo();
	exceptionInfo.setExceptionName(ExceptionUtil.getExceptionHeadline(t));
	exceptionInfo.setStackTrace(ExceptionUtil.getStackTrace(t));
	exceptionInfo.setTest(test);
	return exceptionInfo;
    }
}
