package com.relevantcodes.extentreports.markup;

import com.relevantcodes.extentreports.support.*;

class Header implements IHeader {
	private String filePath;
	
	public void introSummary(String newSummary) {
		String txtCurrent = FileReaderEx.readAllText(filePath);
		String pattern = MarkupFlag.get("reportsummary") + ".*" + MarkupFlag.get("reportsummary");
		newSummary = pattern.replace(".*", newSummary); 
		
		String oldSummary = RegexMatcher.getNthMatch(txtCurrent, pattern, 0);
		txtCurrent = txtCurrent.replace(oldSummary, newSummary);
		
		FileWriterEx.write(filePath, txtCurrent);		
	}
	
	public Header(String filePath) {
		this.filePath = filePath;
	}
}
