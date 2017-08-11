package com.aventstack.extentreports.reporter.converters;

import java.util.List;

import com.aventstack.extentreports.model.Test;

public class ExtentHtmlReporterConverter {
	
	private String filePath;
	private String charset;
	
	public ExtentHtmlReporterConverter(String filePath) {
		this.filePath = filePath;
	}
		
	public List<Test> parseAndGetModelCollection() {
		ExtentHtmlTestConverter converter = new ExtentHtmlTestConverter(filePath);
		setCharset(converter.getCharset());
		List<Test> testList = converter.parseAndGetTests();
		return testList;
	}
	
	private void setCharset(String charset) {
	    this.charset = charset;
	}
	public String getCharset() {
	    return charset;
	}
	
}
