package com.relevantcodes.extentreports.model;

public class RunInfo {
	public String startedAt;
	public String endedAt;
	
	private RunInfo() { }
	
	private static class Instance {
        static final RunInfo INSTANCE = new RunInfo();
    }
	
	public static RunInfo getInstance() {
		return Instance.INSTANCE;
	}
}
