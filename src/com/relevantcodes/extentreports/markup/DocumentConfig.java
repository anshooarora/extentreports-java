/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/


package com.relevantcodes.extentreports.markup;

import com.relevantcodes.extentreports.Chart;
import com.relevantcodes.extentreports.LogSettings;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.support.FileReaderEx;
import com.relevantcodes.extentreports.support.FileWriterEx;
import com.relevantcodes.extentreports.support.RegexMatcher;

/**
 * DocumentConfig class for customizing the report layout
 * 
 * @author Anshoo Arora
 */
public class DocumentConfig extends LogSettings {
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
	 * @return DocumentConfig
	 */
	public DocumentConfig statusIcon(LogStatus status, String newIcon) {
		FontAwesomeIco.override(status, newIcon);
		
		return this;
	}
	
	/**
	 * Allows showing of TH headers for the test
	 * 
	 * @param display Setting FALSE will remove the TH headers from the test table
	 * @return DocumentConfig
	 */
	@Deprecated
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
	 * @return DocumentConfig
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
	 * @return DocumentConfig
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
	 * @return DocumentConfig
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
	 * Add, remove or modify the report summary
	 * 
	 * @param title Title of the report
	 * @return DocumentConfig
	 */
	public DocumentConfig reportTitle(String title) {
		if (null == header)
	        header = new Header();

		header.setFile(filePath).setTitle(title);
		
		return addCustomStyles(".topbar-items-left { display: inline-block; }");
	}

	/**
	 * Use a custom stylesheet file with CSS
	 * 
	 * @param cssFilePath Path of the CSS stylesheet file
	 * @return DocumentConfig
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
	 * @return DocumentConfig
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
	 * @return DocumentConfig
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
	 * Setting to append the caller class to the log event. By default, the setting is enabled 
	 * to allow the caller class to be displayed
	 * 
	 * @param display Shows the caller class in the report step
	 * @return DocumentConfig
	 */
	public DocumentConfig displayCallerClass(Boolean display) {
		displayCallerClass = display;
		
		return this;
	}
	
	/**
	 * Sets the size of the attached snapshots by percentage. Default = 50%.
	 * 
	 * <p>
	 * <b>Usage: </b>
	 * 		extent.setImageSize("40%");
	 * 
	 * @param percent Size of the image in percentage
	 * @return DocumentConfig
	 */
	public DocumentConfig setImageSize(String percent) {
		if (percent.substring(percent.length() - 1).equals("%"))
			return addCustomStyles(".report-img, .report-img-large { width:" + percent + ";}");
			
		return this;
	}
	
	/**
	 * Allows changing the title of the dashboard charts
	 * 
	 * @param chart TEST_SET, TEST
	 * @param title New title of the chart
	 * @return DocumentConfig
	 */
	public DocumentConfig chartTitle(Chart chart, String title) {
		String html = FileReaderEx.readAllText(filePath);
		String currentTitle = MarkupFlag.get(chart.toString() + "chartTitle", "js", true) + ".*" + MarkupFlag.get(chart.toString() + "chartTitle", "js", true);
		String match = RegexMatcher.getNthMatch(html, currentTitle, 0);
		
		html = html.replace(match, MarkupFlag.get(chart.toString() + "chartTitle", "js", false) + "'" + title + "'" + MarkupFlag.get(chart.toString() + "chartTitle", "js", false));
		
		FileWriterEx.write(filePath, html);
		
		return this;
	}
		
	/**
	 * @param filePath Path of the HTML file with .htm or .html extension
	 */
	public DocumentConfig(String filePath) {
		this.filePath = filePath;
	}
}
