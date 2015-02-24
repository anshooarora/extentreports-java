package com.relevantcodes.extentreports.markup;

import com.relevantcodes.extentreports.support.FileReaderEx;
import com.relevantcodes.extentreports.support.FileWriterEx;

class Scripts implements IScripts {
	private String filePath;
	
	public void insertJS(String script) {
		script = "<script type='text/javascript'>" + script + "</script>";
		String markup = FileReaderEx.readAllText(filePath)
						.replace(MarkupFlag.get("customscript"), script + MarkupFlag.get("customscript"));
		
		FileWriterEx.write(filePath, markup);
	}
	
	public Scripts(String filePath) {
		this.filePath = filePath;
	}
}
