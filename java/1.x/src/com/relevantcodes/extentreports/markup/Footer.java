/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/


package com.relevantcodes.extentreports.markup;

import com.relevantcodes.extentreports.support.*;

class Footer {
	private String filePath;
	
	public void useExtentFooter(Boolean use) {
		String start = ExtentMarkup.getPlaceHolder("PROJECTFOOTER");
		String end = ExtentMarkup.getPlaceHolder("/PROJECTFOOTER");
		String markup = FileReaderEx.readAllText(filePath);
		
		if (use) {
			markup = markup.replace(start + "<!--", start).replace("-->" + end, end);
		}
		else {
			markup = markup.replace(start, start + "<!--");
			markup = markup.replace(end, "-->" + end);
		}
		
		FileWriterEx.write(filePath, markup);
	}
	
	// ToDo
	// provide ability to add custom footers
	// 	addCustomFooter("MyCompanyName", [ "<a href=''>Link1</a>", "<a href=''>Link2</a>" ]);
	//		MyCompanyName
	//			+ Link1
	//			+ Link2
	// private void addCustomFooter(String header, String[] chilren) { }
	
	@Deprecated
	public void addExtentFooter() {
		String placeHolderStart = ExtentMarkup.getPlaceHolder("PROJECTFOOTER");
		String placeHolderEnd = ExtentMarkup.getPlaceHolder("/PROJECTFOOTER");
		String markup = FileReaderEx.readAllText(filePath);
		
		if (markup.contains(placeHolderStart + "<!--")) {
			markup = markup.replace(placeHolderStart + "<!--", placeHolderStart);
			markup = markup.replace("-->" + placeHolderEnd, placeHolderEnd);
			
			FileWriterEx.write(filePath, markup);
		}
	}
	
	@Deprecated
	public void removeExtentFooter() {
		String placeHolderStart = ExtentMarkup.getPlaceHolder("PROJECTFOOTER");
		String placeHolderEnd = ExtentMarkup.getPlaceHolder("/PROJECTFOOTER");
		String markup = FileReaderEx.readAllText(filePath);
		
		if (markup.contains(placeHolderStart + "<!--")) {
			return;
		}
		
		markup = markup.replace(placeHolderStart, placeHolderStart + "<!--");
		markup = markup.replace(placeHolderEnd, "-->" + placeHolderEnd);
		
		FileWriterEx.write(filePath, markup);
	}
	
	public Footer setFile(String filePath) {
		this.filePath = filePath;
		return this;
	}
	
	public Footer() {}
	
	public Footer(String filePath) {
		this.filePath = filePath;
	}
}
