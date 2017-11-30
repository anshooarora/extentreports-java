package com.aventstack.extentreports.model;

import java.io.Serializable;

public class ExceptionInfo implements Serializable {

    private static final long serialVersionUID = 2672123037706464734L;

    private String exceptionName;
    private String stackTrace;
    private Throwable t;
    
    // exception-name
    public String getExceptionName() { return exceptionName; }
    
    public void setExceptionName(String exceptionName) { this.exceptionName = exceptionName; }

    // stack-trace
    public String getStackTrace() { return stackTrace; }
    
    public void setStackTrace(String stackTrace) {
        this.stackTrace = "<pre>" + stackTrace + "</pre>";
        if (stackTrace.contains(">") || stackTrace.contains("<"))
            this.stackTrace = "<textarea>" + stackTrace + "</textarea>";
    }
    
    // exception
    public void setException(Throwable t) { this.t = t; }
    
    public Throwable getException() { return t; }
    
}