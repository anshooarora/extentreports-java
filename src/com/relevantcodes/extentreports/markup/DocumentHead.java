package com.relevantcodes.extentreports.markup;

import com.relevantcodes.extentreports.support.*;

class DocumentHead implements IDocumentHead {
	private String filePath;
	
	public void addCustomStylesheet(String cssFilePath) {
		String markup = FileReaderEx.readAllText(filePath);
		String placeHolder = MarkupFlag.get("customCss") + ".*" + MarkupFlag.get("customCss");
		String link = "<link href='file:///" + cssFilePath + "' rel='stylesheet' type='text/css' />";
		
		String match = RegexMatcher.getNthMatch(markup, placeHolder, 0);
		markup = markup.replace(match, placeHolder.replace(".*", link));
		
		FileWriterEx.write(filePath, markup);
	}
	
	public DocumentHead(String filePath) {
		this.filePath = filePath;
	} 
}
