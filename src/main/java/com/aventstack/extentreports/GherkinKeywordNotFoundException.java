package com.aventstack.extentreports;

public class GherkinKeywordNotFoundException extends ClassNotFoundException {
	private static final long serialVersionUID = 3140022717738862603L;

	public GherkinKeywordNotFoundException(String message) {
		super(message);
	}
}