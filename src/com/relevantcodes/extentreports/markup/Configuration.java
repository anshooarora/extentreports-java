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

import java.net.InetAddress;
import java.util.HashMap;

import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.support.FileReaderEx;
import com.relevantcodes.extentreports.support.FileWriterEx;
import com.relevantcodes.extentreports.support.RegexMatcher;

public class Configuration {
	public static Configuration instance = new Configuration();
	private HashMap<String, String> map = new HashMap<String, String>();
	
	public void statusIcon(LogStatus status, String newIcon) {
		FontAwesomeIco.override(status, newIcon);
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
	
	public void renewSystemSpecs(String filePath) {
		String txtCurrent = FileReaderEx.readAllText(filePath);
		String hostName = MarkupFlag.get("hostName") + ".*" + MarkupFlag.get("hostName");
		String ip = MarkupFlag.get("ip") + ".*" + MarkupFlag.get("ip");
		String os = MarkupFlag.get("os") + ".*" + MarkupFlag.get("os");
		String locale = MarkupFlag.get("locale") + ".*" + MarkupFlag.get("locale");
		String totalMem = MarkupFlag.get("totalMem") + ".*" + MarkupFlag.get("totalMem");
		String availMem = MarkupFlag.get("availMem") + ".*" + MarkupFlag.get("availMem");
		String oldValue = "";
		
		oldValue = RegexMatcher.getNthMatch(txtCurrent, hostName, 0);
		try {
			txtCurrent = txtCurrent.replace(oldValue, hostName.replace(".*", InetAddress.getLocalHost().getHostName()));
		}
		catch (Exception e) {
			txtCurrent = txtCurrent.replace(oldValue, hostName.replace(".*", "NOT_AVAILABLE"));
		}
		
		oldValue = RegexMatcher.getNthMatch(txtCurrent, ip, 0);
		try {
			txtCurrent = txtCurrent.replace(oldValue, ip.replace(".*", InetAddress.getLocalHost().getHostAddress()));
		}
		catch (Exception e) {
			txtCurrent = txtCurrent.replace(oldValue, ip.replace(".*", "NOT_AVAILABLE"));
		}
		
		oldValue = RegexMatcher.getNthMatch(txtCurrent, os, 0);
		txtCurrent = txtCurrent.replace(oldValue, os.replace(".*", System.getProperty("os.name")));
		
		oldValue = RegexMatcher.getNthMatch(txtCurrent, locale, 0);
		txtCurrent = txtCurrent.replace(oldValue, locale.replace(".*", System.getProperty("user.language")));
		
		oldValue = RegexMatcher.getNthMatch(txtCurrent, totalMem, 0);
		txtCurrent = txtCurrent.replace(oldValue, totalMem.replace(".*", "" + Runtime.getRuntime().totalMemory() + ""));
		
		oldValue = RegexMatcher.getNthMatch(txtCurrent, availMem, 0);
		txtCurrent = txtCurrent.replace(oldValue, availMem.replace(".*", "" + Runtime.getRuntime().freeMemory() + ""));
		
		FileWriterEx.write(filePath, txtCurrent);
	}
	
	public void params(String varName, String varValue) {
		map.put(varName, varValue);
	}
	
	public IHeader header() {
		return new Header(map.get("filePath"));
	}
	
	public IFooter footer() {
		return new Footer(map.get("filePath"));
	}
	
	private Configuration() {}
}
