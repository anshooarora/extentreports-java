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
import java.text.SimpleDateFormat;
import java.util.Date;

import com.relevantcodes.extentreports.support.*;

class Content {
	private String filePath;

	public void documentTitle(String title) {
		String docTitle = "<title>.*</title>";
		String html = FileReaderEx.readAllText(filePath);
		
		html = html.replace(RegexMatcher.getNthMatch(html, docTitle, 0), docTitle.replace(".*", title));
		
		FileWriterEx.write(filePath, html);
	}
	
	public void renewSystemInfo() {
		String html = FileReaderEx.readAllText(filePath);
		String startedAt = MarkupFlag.get("starttime") + ".*" + MarkupFlag.get("starttime");
		String userName = MarkupFlag.get("userName") + ".*" + MarkupFlag.get("userName");
		String hostName = MarkupFlag.get("hostName") + ".*" + MarkupFlag.get("hostName");
		String os = MarkupFlag.get("os") + ".*" + MarkupFlag.get("os");
		String osArch = MarkupFlag.get("osarch") + ".*" + MarkupFlag.get("osarch");
		String javaVersion = MarkupFlag.get("javaversion") + ".*" + MarkupFlag.get("javaversion");
		String locale = MarkupFlag.get("locale") + ".*" + MarkupFlag.get("locale");
		String totalMem = MarkupFlag.get("totalMem") + ".*" + MarkupFlag.get("totalMem");
		String availMem = MarkupFlag.get("availMem") + ".*" + MarkupFlag.get("availMem");
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
	
	public Content setFile(String filePath) {
		this.filePath = filePath;
		return this;
	}
	
	public Content() {}
	
	public Content(String filePath) {
		this.filePath = filePath;
	}
}
