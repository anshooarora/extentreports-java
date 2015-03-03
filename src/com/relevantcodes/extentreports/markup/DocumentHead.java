/*
Copyright 2015 ExtentReports committer(s)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0
	
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/


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
