/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports.markup;

import com.relevantcodes.extentreports.support.FileReaderEx;
import com.relevantcodes.extentreports.support.FileWriterEx;

class DocumentHead {
	private String filePath;
	
	public void addCustomStylesheet(String cssFilePath) {
		String link = "<link href='file:///" + cssFilePath + "' rel='stylesheet' type='text/css' />";
		
		if (cssFilePath.substring(0, 1).equals(new String(".")) || cssFilePath.substring(0, 1).equals(new String("/")))
			link = "<link href='" + cssFilePath + "' rel='stylesheet' type='text/css' />";		
			
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
	
	public DocumentHead setFile(String filePath) {
		this.filePath = filePath;
		return this;
	}
	
	public DocumentHead() {}
	
	public DocumentHead(String filePath) {
		this.filePath = filePath;
	} 
}
