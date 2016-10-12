package com.aventstack.extentreports;

/**
 * Primary interface implemented by each reporter. This interface implements {@link TestListener} and 
 * {@link ReportAggregatesListener} interface to provide additional functionality to each reporter.
 */
public interface ExtentReporter extends TestListener, ReportAggregatesListener {
    
    /**
     * Starts passing run information to the reporter
     */
    void start();
    
    /**
     * Stops the reporter. Ensures no information is passed to the reporter.
     */
    void stop();
    
    /*
     * Write/update the target (file, database etc.)
     */
    void flush();
    
}