/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/


package com.relevantcodes.extentreports.markup;

import com.relevantcodes.extentreports.support.*;

class Header {
	private String filePath;
	
	public void setHeadline(String summary) {
		String markup = FileReaderEx.readAllText(filePath);
		String pattern = MarkupFlag.get("reportsummary") + ".*" + MarkupFlag.get("reportsummary");
		summary = pattern.replace(".*", summary); 
		
		String oldSummary = RegexMatcher.getNthMatch(markup, pattern, 0);
		markup = markup.replace(oldSummary, summary);
		
		FileWriterEx.write(filePath, markup);
	}
	
	public void setTitle(String title) {
		String markup = FileReaderEx.readAllText(filePath);
		String pattern = MarkupFlag.get("logo") + ".*" + MarkupFlag.get("logo");
		title = pattern.replace(".*", title); 
		
		String oldSummary = RegexMatcher.getNthMatch(markup, pattern, 0);
		markup = markup.replace(oldSummary, title);
		
		FileWriterEx.write(filePath, markup);
	}
	
	public Header setFile(String filePath) {
		this.filePath = filePath;
		return this;
	}
	
	public Header() {}
	
	public Header(String filePath) {
		this.filePath = filePath;
	} 
}
