package com.relevantcodes.extentreports.markup;

import com.relevantcodes.extentreports.support.FileReaderEx;
import com.relevantcodes.extentreports.support.FileWriterEx;

class DocumentHead implements IDocumentHead {
	private String filePath;
	
	public void addCustomStylesheet(String cssFilePath) {
		String link = "<link href='file:///" + cssFilePath + "' rel='stylesheet' type='text/css' />";
		String markup = FileReaderEx.readAllText(filePath)
						.replace(MarkupFlag.get("customcss"), link + MarkupFlag.get("customcss"));
		
		FileWriterEx.write(filePath, markup);
	}
	
	public void addCustomStyles(String styles) {
		styles = "<style type='text/css'>" + styles + "</style>";
		String markup = FileReaderEx.readAllText(filePath)
						.replace(MarkupFlag.get("customcss"), styles + MarkupFlag.get("customcss"));
		
		FileWriterEx.write(filePath, markup);
	}
	
	public DocumentHead(String filePath) {
		this.filePath = filePath;
	} 
}
