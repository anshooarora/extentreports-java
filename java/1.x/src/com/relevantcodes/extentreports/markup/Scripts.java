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

class Scripts {
	private String filePath;
	
	public void insertJS(String script) {
		script = "<script type='text/javascript'>" + script + "</script>";
		String markup = FileReaderEx.readAllText(filePath)
						.replace(ExtentMarkup.getPlaceHolder("customscript"), script + ExtentMarkup.getPlaceHolder("customscript"));
		
		FileWriterEx.write(filePath, markup);
	}
	
	public Scripts setFile(String filePath) {
		this.filePath = filePath;
		return this;
	}
	
	public Scripts() {}
	
	public Scripts(String filePath) {
		this.filePath = filePath;
	}
}
