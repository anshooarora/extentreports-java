package com.relevantcodes.extentreports;

import com.relevantcodes.extentreports.source.ExtentFlag;

class ReportSource {
	private static ReportSource instance = null;
	private String source;
	
	public synchronized void setSrc(String source) {
		this.source = source;
	}
	
	public synchronized String getSrc() {
		return source;
	}
	
	public synchronized void addTest(String src) {
		source = source.replace(ExtentFlag.getPlaceHolder("test"), src + ExtentFlag.getPlaceHolder("test"));
	}
	
	protected ReportSource() {
	}
 
	public static ReportSource getInstance() {
		if (instance == null) {
			synchronized (ReportSource.class) {
				if (instance == null) {
					instance = new ReportSource();
				}
			}
		}
		
		return instance;
	}
}
