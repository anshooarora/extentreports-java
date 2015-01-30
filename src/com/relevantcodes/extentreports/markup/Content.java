package com.relevantcodes.extentreports.markup;

import java.net.InetAddress;

import com.relevantcodes.extentreports.support.*;

class Content implements IContent {
	private String filePath;
	
	public void renewSystemInfo() {
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
	
	public Content(String filePath) {
		this.filePath = filePath;
	}
}
