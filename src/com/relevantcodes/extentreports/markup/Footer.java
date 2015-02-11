package com.relevantcodes.extentreports.markup;

import com.relevantcodes.extentreports.support.*;

class Footer implements IFooter {
	private String filePath;
	
	public void useExtentFooter(Boolean use) {
		if (use) {
			addExtentFooter();
			return;
		}
			
		removeExtentFooter();
	}
	
	public void addExtentFooter() {
		String placeHolderStart = MarkupFlag.get("PROJECTFOOTER");
		String placeHolderEnd = MarkupFlag.get("/PROJECTFOOTER");
		String markup = FileReaderEx.readAllText(filePath);
		
		if (markup.contains(placeHolderStart + "<!--")) {
			markup = markup.replace(placeHolderStart + "<!--", placeHolderStart);
			markup = markup.replace("-->" + placeHolderEnd, placeHolderEnd);
			
			FileWriterEx.write(filePath, markup);
		}
	}
	
	public void removeExtentFooter() {
		String placeHolderStart = MarkupFlag.get("PROJECTFOOTER");
		String placeHolderEnd = MarkupFlag.get("/PROJECTFOOTER");
		String markup = FileReaderEx.readAllText(filePath);
		
		if (markup.contains(placeHolderStart + "<!--")) {
			return;
		}
		
		markup = markup.replace(placeHolderStart, placeHolderStart + "<!--");
		markup = markup.replace(placeHolderEnd, "-->" + placeHolderEnd);
		
		FileWriterEx.write(filePath, markup);
	}
	
	public Footer(String filePath) {
		this.filePath = filePath;
	}
}
