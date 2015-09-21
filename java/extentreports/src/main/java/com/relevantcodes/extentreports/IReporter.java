package com.relevantcodes.extentreports;


public interface IReporter {
	void start(Report report);
	void stop();
	void flush();
	void addTest();
	void setTestRunnerLogs();
}
