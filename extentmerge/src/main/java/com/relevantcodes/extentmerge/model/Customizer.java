package com.relevantcodes.extentmerge.model;

public class Customizer {
	private String css;
	private String stylesheetPath;
	private String js;
	private String scriptPath;
	
	public String getInlineCss() {	
		return css;
	}
	
	public void setInlineCss(String css) {
		this.css = css;
	}
	
	public String getStylesheet() {
		return stylesheetPath;
	}
	
	public void setStylesheet(String stylesheetPath) {
		this.stylesheetPath = stylesheetPath;
	}
	
	public String getInlineScript() {
		return js;
	}
	
	public void setInlineScript(String js) {
		this.js = js;
	}
	
	public String getScriptFile() {
		return scriptPath;
	}
	
	public void setScriptFile(String scriptPath) {
		this.scriptPath = scriptPath;
	}
}
