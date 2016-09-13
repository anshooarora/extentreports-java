package com.relevantcodes.extentreports;

import com.relevantcodes.extentreports.model.ExceptionInfo;
import com.relevantcodes.extentreports.model.Test;

import java.util.List;
import java.util.Map;

public class TestReporter implements IReporter {
    private Report report;
    @SuppressWarnings("unused")
    private TestReporter testReporter;

    @Override
    public void start(Report report) {
        this.report = report;
        getExceptionTestMap();
    }

    public Map<String, List<ExceptionInfo>> getExceptionTestMap() {
        return report.getExceptionTestMap();
    }

    @Override
    public void stop() {

    }

    @Override
    public void flush() {

    }

    @Override
    public void addTest(Test test) {

    }

    @Override
    public void setTestRunnerLogs() {

    }


}
