/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/


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
	
	public DocumentConfig displayTestHeaders(Boolean display) {
		if (!display)
			return addCustomStyles("th { display: none; }");
		else
			return addCustomStyles("th { display: table-cell; }");		
	}
	
	public DocumentConfig useExtentFooter(Boolean display) {
		if (null == footer)
			footer = new Footer();
		
		footer.setFile(filePath).useExtentFooter(display);
		
		return this;
	}

	public DocumentConfig insertJS(String script) {
		if (null == scripts)
	        scripts = new Scripts();
		
		scripts.setFile(filePath).insertJS(script);
		
		return this;
	}

	public DocumentConfig reportHeadline(String headline) {
		if (null == header)
	        header = new Header();
				
		Integer maxLength = 70;
		
		if (headline.length() > 70)
			headline = headline.substring(0, maxLength - 1);
		
		header.setFile(filePath).setHeadline(headline);
		
		return this;
	}

	public DocumentConfig addCustomStylesheet(String cssFilePath) {
		if (null == docHead)
	        docHead = new DocumentHead();
		
		docHead.setFile(filePath).addCustomStylesheet(cssFilePath);
		
		return this;
	}

	public DocumentConfig addCustomStyles(String styles) {
		if (null == docHead)
	        docHead = new DocumentHead();
		
		docHead.setFile(filePath).addCustomStyles(styles);
		
		return this;
	}

	public DocumentConfig documentTitle(String title) {
		if (null == content)
	        content = new Content();
		
		content.setFile(filePath).documentTitle(title);
		
		return this;
	}
	
	public DocumentConfig renewSystemInfo() {
		if (null == content)
	        content = new Content();
		
		content.setFile(filePath).renewSystemInfo();
		
		return this;
	}
	
	public DocumentConfig(String filePath) {
		this.filePath = filePath;
	}
}
