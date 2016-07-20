package com.relevantcodes.extentreports;

@SuppressWarnings("serial")
public class ExtentTestInterruptedException extends RuntimeException {
	public ExtentTestInterruptedException(Throwable t) {
		super(t);
	}

	public ExtentTestInterruptedException(String string) {
		super(string);
	}

	public ExtentTestInterruptedException(String string, Throwable t) {
		super(string, t);
	}   

}
