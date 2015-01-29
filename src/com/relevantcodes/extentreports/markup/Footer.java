package com.relevantcodes.extentreports.markup;

import com.relevantcodes.extentreports.support.*;

class Footer implements IFooter {
	private String filePath;
	
	public void addExtentFooter() {
		String placeHolderStart = MarkupFlag.get("PROJECTFOOTER");
		String placeHolderEnd = MarkupFlag.get("/PROJECTFOOTER");
		String txtCurrent = FileReaderEx.readAllText(filePath);
		
		if (txtCurrent.contains(placeHolderStart + "<!--")) {
			txtCurrent = txtCurrent.replace(placeHolderStart + "<!--", placeHolderStart);
			txtCurrent = txtCurrent.replace("-->" + placeHolderEnd, placeHolderEnd);
			
			FileWriterEx.write(filePath, txtCurrent);
		}
	}
	
	public void removeExtentFooter() {
		String placeHolderStart = MarkupFlag.get("PROJECTFOOTER");
		String placeHolderEnd = MarkupFlag.get("/PROJECTFOOTER");
		String txtCurrent = FileReaderEx.readAllText(filePath);
		
		if (txtCurrent.contains(placeHolderStart + "<!--")) {
			return;
		}
		
		txtCurrent = txtCurrent.replace(placeHolderStart, placeHolderStart + "<!--");
		txtCurrent = txtCurrent.replace(placeHolderEnd, "-->" + placeHolderEnd);
		
		FileWriterEx.write(filePath, txtCurrent);
	}
	
	public Footer(String filePath) {
		this.filePath = filePath;
	}
}
