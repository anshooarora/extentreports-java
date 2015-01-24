/*
Copyright 2015 Cube Reports committer(s)

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

package com.relevantcodes.cubereports.markup;

import java.util.HashMap;

import com.relevantcodes.cubereports.LogStatus;
import com.relevantcodes.cubereports.support.FileReaderEx;
import com.relevantcodes.cubereports.support.FileWriterEx;
import com.relevantcodes.cubereports.support.RegexMatcher;

public class Configuration {
	public static Configuration instance = new Configuration();
	private HashMap<String, String> map = new HashMap<String, String>();
	
	public void statusIcon(LogStatus status, String newIcon) {
		FontAwesomeIco.override(status, newIcon);
	}
	
	public void introSummary(String newSummary) {
		String filePath = map.get("filePath");
		String txtCurrent = FileReaderEx.readAllText(filePath);
		String pattern = MarkupFlag.get("reportsummary") + ".*" + MarkupFlag.get("reportsummary");
		newSummary = pattern.replace(".*", newSummary); 
		
		String oldSummary = RegexMatcher.getNthMatch(txtCurrent, pattern, 0);
		txtCurrent = txtCurrent.replace(oldSummary, newSummary);
		
		FileWriterEx.write(filePath, txtCurrent);		
	}
	
	public void addCustomStylesheet(String cssFilePath) {
		String filePath = map.get("filePath");
		String txtCurrent = FileReaderEx.readAllText(filePath);
		String placeHolder = MarkupFlag.get("customCss") + ".*" + MarkupFlag.get("customCss");
		String link = "<link href='file:///" + cssFilePath + "' rel='stylesheet' type='text/css' />";
		
		String match = RegexMatcher.getNthMatch(txtCurrent, placeHolder, 0);
		txtCurrent = txtCurrent.replace(match, placeHolder.replace(".*", link));
		
		FileWriterEx.write(filePath, txtCurrent);
	}
	
	public void params(String varName, String varValue) {
		map.put(varName, varValue);
	}
	
	private Configuration() {}
}
