package com.relevantcodes.extentreports;

public class TestExtentReports extends ExtentReports {

    private static final long serialVersionUID = 2657381564600381509L;
    private final TestReporter testReporter;

    public TestExtentReports(String filePath) {
        super(filePath);
        testReporter = new TestReporter();
        attach(testReporter);
    }

    public TestReporter getTestReporter() {
        return testReporter;
    }
}
