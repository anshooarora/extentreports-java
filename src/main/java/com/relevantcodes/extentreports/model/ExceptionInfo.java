package com.relevantcodes.extentreports.model;

import java.io.Serializable;

public class ExceptionInfo implements Serializable {

    private static final long serialVersionUID = 2672123037706464734L;
    
    String exceptionName;
    String stackTrace;
    Throwable t;
    
    // exception-name
    public String getExceptionName() { return exceptionName; }
    
    public void setExceptionName(String exceptionName) { this.exceptionName = exceptionName; }

    // stack-trace
    public String getStackTrace() { return stackTrace; }
    
    public void setStackTrace(String stackTrace) { this.stackTrace = stackTrace; }
    
    // exception
    public void setException(Throwable t) { this.t = t; }
    
    public Throwable getException() { return t; }
    
}