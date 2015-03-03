package com.relevantcodes.extentreports.markup;

import com.relevantcodes.extentreports.LogStatus;

// proxy
public class DocumentConfig {
	private Content content;
	private DocumentHead docHead;
	private Footer footer;
	private Header header;
	private Scripts scripts;
	private String filePath;
	
	public DocumentConfig statusIcon(LogStatus status, String newIcon) {
		FontAwesomeIco.override(status, newIcon);
		
		return this;
	}
	
	public DocumentConfig useExtentFooter(Boolean display) {
		if (null == footer)
			footer = new Footer(filePath);
		
		footer.useExtentFooter(display);
		
		return this;
	}

	public DocumentConfig insertJS(String script) {
		if (null == scripts)
	        scripts = new Scripts(filePath);
		
		scripts.insertJS(script);
		
		return this;
	}

	public DocumentConfig reportHeadline(String headline) {
		if (null == header)
	        header = new Header(filePath);
		
		Integer maxLength = 70;
		
		header.introSummary(headline.substring(0, maxLength - 1));
		
		return this;
	}

	public DocumentConfig addCustomStylesheet(String cssFilePath) {
		if (null == docHead)
	        docHead = new DocumentHead(filePath);
		
		docHead.addCustomStylesheet(cssFilePath);
		
		return this;
	}

	public DocumentConfig addCustomStyles(String styles) {
		if (null == docHead)
	        docHead = new DocumentHead(filePath);
		
		docHead.addCustomStyles(styles);
		
		return this;
	}

	public DocumentConfig renewSystemInfo() {
		if (null == content)
	        content = new Content(filePath);
		
		content.renewSystemInfo();
		
		return this;
	}
	
	public DocumentConfig(String filePath) {
		this.filePath = filePath;
	}
}
