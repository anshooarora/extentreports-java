package com.relevantcodes.extentreports.markup;

import com.relevantcodes.extentreports.support.*;

class DocumentHead implements IDocumentHead {
	private String filePath;
	
	public void addCustomStylesheet(String cssFilePath) {
		String txtCurrent = FileReaderEx.readAllText(filePath);
		String placeHolder = MarkupFlag.get("customCss") + ".*" + MarkupFlag.get("customCss");
		String link = "<link href='file:///" + cssFilePath + "' rel='stylesheet' type='text/css' />";
		
		String match = RegexMatcher.getNthMatch(txtCurrent, placeHolder, 0);
		txtCurrent = txtCurrent.replace(match, placeHolder.replace(".*", link));
		
		FileWriterEx.write(filePath, txtCurrent);
	}
	
	public DocumentHead(String filePath) {
		this.filePath = filePath;
	} 
}
