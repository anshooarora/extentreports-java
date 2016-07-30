package com.relevantcodes.extentreports;


public class TestExtentReports extends ExtentReports {

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
