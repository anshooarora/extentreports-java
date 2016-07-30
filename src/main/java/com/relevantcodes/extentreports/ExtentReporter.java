package com.relevantcodes.extentreports;

public interface ExtentReporter extends TestListener, ReportAggregatesListener {
    void start();
    void stop();
    void flush();
}