/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/


package com.relevantcodes.extentreports.markup;

import com.relevantcodes.extentreports.LogStatus;

/**
 * DocumentConfig class for customizing the report layout
 * 
 * @author Anshoo Arora
 */
public class DocumentConfig {
	private Content content;
	private DocumentHead docHead;
	private Footer footer;
	private Header header;
	private Scripts scripts;
	private String filePath;
	
	/**
	 *  Choose to use your own icons for log status (PASS, FAIL, WARNING etc.) 
	 *  by choosing one of the icons from Fontawesome website: 
	 *  http://fortawesome.github.io/Font-Awesome/icons/.
	 *  
	 * @param status LogStatus
	 * @param newIcon Icon string from font-awesome
	 * @return DocumentConfig object
	 */
	public DocumentConfig statusIcon(LogStatus status, String newIcon) {
		FontAwesomeIco.override(status, newIcon);
		
		return this;
	}
	
	/**
	 * Allows showing of TH headers for the test
	 * 
	 * @param display Setting FALSE will remove the TH headers from the test table
	 * @return DocumentConfig object
	 */
	public DocumentConfig displayTestHeaders(Boolean display) {
		if (!display)
			return addCustomStyles("th { display: none; }");
		else
			return addCustomStyles("th { display: table-cell; }");		
	}
	
	/**
	 * Setting to show or hide the default Extent footer
	 * By default, this is FALSE
	 * 
	 * @param display Setting FALSE will hide the default Extent footer
	 * @return DocumentConfig object
	 */
	public DocumentConfig useExtentFooter(Boolean display) {
		if (null == footer)
			footer = new Footer();
		
		footer.setFile(filePath).useExtentFooter(display);
		
		return this;
	}

	/**
	 * Adds custom scripts to the report
	 * 
	 * @param script JavaScript code to be used for the report
	 * @return DocumentConfig object
	 */
	public DocumentConfig insertJS(String script) {
		if (null == scripts)
	        scripts = new Scripts();
		
		scripts.setFile(filePath).insertJS(script);
		
		return this;
	}

	/**
	 * Add, remove or modify the report summary
	 * 
	 * @param headline Report summary
	 * @return DocumentConfig object
	 */
	public DocumentConfig reportHeadline(String headline) {
		if (null == header)
	        header = new Header();
				
		Integer maxLength = 70;
		
		if (headline.length() > 70)
			headline = headline.substring(0, maxLength - 1);
		
		header.setFile(filePath).setHeadline(headline);
		
		return this;
	}

	/**
	 * Use a custom stylesheet file with CSS
	 * 
	 * @param cssFilePath Path of the CSS stylesheet file
	 * @return DocumentConfig object
	 */
	public DocumentConfig addCustomStylesheet(String cssFilePath) {
		if (null == docHead)
	        docHead = new DocumentHead();
		
		docHead.setFile(filePath).addCustomStylesheet(cssFilePath);
		
		return this;
	}

	/**
	 * Use custom CSS directly for the document to change layout
	 * 
	 * @param styles CSS style string
	 * @return DocumentConfig object
	 */ 
	public DocumentConfig addCustomStyles(String styles) {
		if (null == docHead)
	        docHead = new DocumentHead();
		
		docHead.setFile(filePath).addCustomStyles(styles);
		
		return this;
	}

	/**
	 * Change the title of the report file
	 * 
	 * @param title Document title
	 * @return DocumentConfig object
	 */
	public DocumentConfig documentTitle(String title) {
		if (null == content)
	        content = new Content();
		
		content.setFile(filePath).documentTitle(title);
		
		return this;
	}
	
	/**
	 * @return DocumentConfig object
	 */
	public DocumentConfig renewSystemInfo() {
		if (null == content)
	        content = new Content();
		
		content.setFile(filePath).renewSystemInfo();
		
		return this;
	}
	
	/**
	 * @param filePath Path of the HTML file with .htm or .html extension
	 */
	public DocumentConfig(String filePath) {
		this.filePath = filePath;
	}
}
