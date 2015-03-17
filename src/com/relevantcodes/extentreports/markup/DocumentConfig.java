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
