package com.relevantcodes.extentreports;

import com.relevantcodes.extentreports.source.ExtentFlag;
import com.relevantcodes.extentreports.support.RegexMatcher;

public class ReportConfig {
	private String configSource;
	
	public ReportConfig insertJs(String js) {
		js = "<script type='text/javascript'>" + js + "</script>";
		configSource = configSource.replace(ExtentFlag.getPlaceHolder("customscript"), js + ExtentFlag.getPlaceHolder("customscript"));
		
		return this;
	}
	
	public ReportConfig insertCustomStyles(String styles) {
		styles = "<style type='text/css'>" + styles + "</style>";
		configSource = configSource.replace(ExtentFlag.getPlaceHolder("customcss"), styles + ExtentFlag.getPlaceHolder("customcss"));
		
		return this;
	}
	
	public ReportConfig reportHeadline(String headline) {
		String html = configSource;
		String pattern = ExtentFlag.getPlaceHolder("headline") + ".*" + ExtentFlag.getPlaceHolder("headline");
		headline = pattern.replace(".*", headline); 
		
		String oldHeadline = RegexMatcher.getNthMatch(html, pattern, 0);
		configSource = html.replace(oldHeadline, headline);
		
		return this;
	}
	
	public ReportConfig reportName(String name) {
		String html = configSource;
		String pattern = ExtentFlag.getPlaceHolder("logo") + ".*" + ExtentFlag.getPlaceHolder("logo");
		name = pattern.replace(".*", name); 
		
		String oldName = RegexMatcher.getNthMatch(html, pattern, 0);
		configSource = html.replace(oldName, name);
		
		return this;
	}
	
	public ReportConfig documentTitle(String title) {
		String docTitle = "<title>.*</title>";
		String html = configSource;
		
		configSource = html.replace(RegexMatcher.getNthMatch(html, docTitle, 0), docTitle.replace(".*", title));
		
		return this;
	}
	
	public void setSource(String source) {
		configSource = source;
	}
	
	public void update() {
		ReportSource.getInstance().setSource(configSource);
	}
	
	public ReportConfig() { }
}
