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

class Content implements IContent {
	private String filePath;

	public void renewSystemInfo() {
		String markup = FileReaderEx.readAllText(filePath);
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
		
		temp = RegexMatcher.getNthMatch(markup, startedAt, 0);
		markup = markup.replace(temp, startedAt.replace(".*", new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date()).toString()));
		
		temp = RegexMatcher.getNthMatch(markup, userName, 0);
		markup = markup.replace(temp, userName.replace(".*", System.getProperty("user.name")));
		
		temp = RegexMatcher.getNthMatch(markup, hostName, 0);
		try {
			markup = markup.replace(temp, hostName.replace(".*", InetAddress.getLocalHost().getHostName()));
		}
		catch (Exception e) {
			markup = markup.replace(temp, hostName.replace(".*", "NOT_AVAILABLE"));
		}
		
		temp = RegexMatcher.getNthMatch(markup, os, 0);
		markup = markup.replace(temp, os.replace(".*", System.getProperty("os.name")));
		
		temp = RegexMatcher.getNthMatch(markup, osArch, 0);
		markup = markup.replace(temp, osArch.replace(".*", System.getProperty("os.arch")));
		
		temp = RegexMatcher.getNthMatch(markup, javaVersion, 0);
		markup = markup.replace(temp, javaVersion.replace(".*", System.getProperty("java.version")));
		
		temp = RegexMatcher.getNthMatch(markup, locale, 0);
		markup = markup.replace(temp, locale.replace(".*", System.getProperty("user.language")));
		
		temp = RegexMatcher.getNthMatch(markup, totalMem, 0);
		markup = markup.replace(temp, totalMem.replace(".*", "" + Runtime.getRuntime().totalMemory() / mb + "MB"));
		
		temp = RegexMatcher.getNthMatch(markup, availMem, 0);
		markup = markup.replace(temp, availMem.replace(".*", "" + Runtime.getRuntime().freeMemory() / mb + "MB"));
		
		FileWriterEx.write(filePath, markup);
	}
	
	public Content setFile(String filePath) {
		this.filePath = filePath;
		return this;
	}
	
	public Content(String filePath) {
		this.filePath = filePath;
	}
}
