package com.relevantcodes.extentreports;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class SystemInfo {
	Map<String, String> map;
	
	public Map<String, String> getInfo() {
		return map;
	}
	
	public void setInfo(HashMap<String, String> map) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
		    this.map.put(entry.getKey(), entry.getValue());
		}
	}
	
	public void setInfo(String param, String value) {
		map.put(param, value);
	}
	
	private Map<String, String> setInfo() {
		map.put("User Name", System.getProperty("user.name"));
		map.put("OS", System.getProperty("os.name"));
		map.put("Java Version", System.getProperty("java.version"));
		
		try {
			map.put("Host Name", InetAddress.getLocalHost().getHostName());
		} catch(Exception e) { }
		
		return map;
	}
	
	public SystemInfo() { 
		map = new HashMap<String, String>();
		
		setInfo();
	}
}
