/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/


package com.relevantcodes.extentreports.markup;

import com.relevantcodes.extentreports.support.*;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

class Header {
	private String filePath;
	
	public void setHeadline(String summary) {
		String markup = FileReaderEx.readAllText(filePath);
		String pattern = ExtentMarkup.getPlaceHolder("reportsummary") + ".*" + ExtentMarkup.getPlaceHolder("reportsummary");
		summary = pattern.replace(".*", summary); 
		
		String oldSummary = RegexMatcher.getNthMatch(markup, pattern, 0);
		markup = markup.replace(oldSummary, summary);
		
		FileWriterEx.write(filePath, markup);
	}
	
	public void setTitle(String title) {
		String markup = FileReaderEx.readAllText(filePath);
		String pattern = ExtentMarkup.getPlaceHolder("logo") + ".*" + ExtentMarkup.getPlaceHolder("logo");
		title = pattern.replace(".*", title); 
		
		String oldSummary = RegexMatcher.getNthMatch(markup, pattern, 0);
		markup = markup.replace(oldSummary, title);
		
		FileWriterEx.write(filePath, markup);
	}
	
	public void renewSystemInfo() {
		String html = FileReaderEx.readAllText(filePath);
		String startedAt = ExtentMarkup.getPlaceHolder("starttime") + ".*" + ExtentMarkup.getPlaceHolder("starttime");
		String userName = ExtentMarkup.getPlaceHolder("userName") + ".*" + ExtentMarkup.getPlaceHolder("userName");
		String hostName = ExtentMarkup.getPlaceHolder("hostName") + ".*" + ExtentMarkup.getPlaceHolder("hostName");
		String os = ExtentMarkup.getPlaceHolder("os") + ".*" + ExtentMarkup.getPlaceHolder("os");
		String osArch = ExtentMarkup.getPlaceHolder("osarch") + ".*" + ExtentMarkup.getPlaceHolder("osarch");
		String javaVersion = ExtentMarkup.getPlaceHolder("javaversion") + ".*" + ExtentMarkup.getPlaceHolder("javaversion");
		String locale = ExtentMarkup.getPlaceHolder("locale") + ".*" + ExtentMarkup.getPlaceHolder("locale");
		String totalMem = ExtentMarkup.getPlaceHolder("totalMem") + ".*" + ExtentMarkup.getPlaceHolder("totalMem");
		String availMem = ExtentMarkup.getPlaceHolder("availMem") + ".*" + ExtentMarkup.getPlaceHolder("availMem");
		String temp = "";
		int mb = 1024 * 2014;
		
		temp = RegexMatcher.getNthMatch(html, startedAt, 0);
		html = html.replace(temp, startedAt.replace(".*", new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()).toString()));
		
		temp = RegexMatcher.getNthMatch(html, userName, 0);
		html = html.replace(temp, userName.replace(".*", System.getProperty("user.name")));
		
		temp = RegexMatcher.getNthMatch(html, hostName, 0);
		try {
			html = html.replace(temp, hostName.replace(".*", InetAddress.getLocalHost().getHostName()));
		}
		catch (Exception e) {
			html = html.replace(temp, hostName.replace(".*", "NOT_AVAILABLE"));
		}
		
		temp = RegexMatcher.getNthMatch(html, os, 0);
		html = html.replace(temp, os.replace(".*", System.getProperty("os.name")));
		
		temp = RegexMatcher.getNthMatch(html, osArch, 0);
		html = html.replace(temp, osArch.replace(".*", System.getProperty("os.arch")));
		
		temp = RegexMatcher.getNthMatch(html, javaVersion, 0);
		html = html.replace(temp, javaVersion.replace(".*", System.getProperty("java.version")));
		
		temp = RegexMatcher.getNthMatch(html, locale, 0);
		html = html.replace(temp, locale.replace(".*", System.getProperty("user.language")));
		
		temp = RegexMatcher.getNthMatch(html, totalMem, 0);
		html = html.replace(temp, totalMem.replace(".*", "" + Runtime.getRuntime().totalMemory() / mb + "MB"));
		
		temp = RegexMatcher.getNthMatch(html, availMem, 0);
		html = html.replace(temp, availMem.replace(".*", "" + Runtime.getRuntime().freeMemory() / mb + "MB"));
		
		FileWriterEx.write(filePath, html);
	}
	
	public Header setFile(String filePath) {
		this.filePath = filePath;
		return this;
	}
	
	public Header() {}
	
	public Header(String filePath) {
		this.filePath = filePath;
	} 
}
