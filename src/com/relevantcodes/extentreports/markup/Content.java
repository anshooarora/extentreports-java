package com.relevantcodes.extentreports.markup;

import java.net.InetAddress;

import com.relevantcodes.extentreports.support.*;

class Content implements IContent {
	private String filePath;
	
	public void renewSystemInfo() {
		String markup = FileReaderEx.readAllText(filePath);
		String userName = MarkupFlag.get("userName") + ".*" + MarkupFlag.get("userName");
		String hostName = MarkupFlag.get("hostName") + ".*" + MarkupFlag.get("hostName");
		String ip = MarkupFlag.get("ip") + ".*" + MarkupFlag.get("ip");
		String os = MarkupFlag.get("os") + ".*" + MarkupFlag.get("os");
		String osArch = MarkupFlag.get("osarch") + ".*" + MarkupFlag.get("osarch");
		String javaVersion = MarkupFlag.get("javaversion") + ".*" + MarkupFlag.get("javaversion");
		String locale = MarkupFlag.get("locale") + ".*" + MarkupFlag.get("locale");
		String totalMem = MarkupFlag.get("totalMem") + ".*" + MarkupFlag.get("totalMem");
		String availMem = MarkupFlag.get("availMem") + ".*" + MarkupFlag.get("availMem");
		String temp = "";
		int mb = 1024 * 2014;
		
		temp = RegexMatcher.getNthMatch(markup, userName, 0);
		markup = markup.replace(temp, userName.replace(".*", System.getProperty("user.name")));
		
		temp = RegexMatcher.getNthMatch(markup, hostName, 0);
		try {
			markup = markup.replace(temp, hostName.replace(".*", InetAddress.getLocalHost().getHostName()));
		}
		catch (Exception e) {
			markup = markup.replace(temp, hostName.replace(".*", "NOT_AVAILABLE"));
		}
		
		temp = RegexMatcher.getNthMatch(markup, ip, 0);
		try {
			markup = markup.replace(temp, ip.replace(".*", InetAddress.getLocalHost().getHostAddress()));
		}
		catch (Exception e) {
			markup = markup.replace(temp, ip.replace(".*", "NOT_AVAILABLE"));
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
	
	public Content(String filePath) {
		this.filePath = filePath;
	}
}
