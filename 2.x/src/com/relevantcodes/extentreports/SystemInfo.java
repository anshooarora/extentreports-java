package com.relevantcodes.extentreports;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import com.relevantcodes.extentreports.model.SystemProperties;

public class SystemInfo {
	private SystemProperties systemProperties;
	
	public Map<String, String> getInfo() {
		if (systemProperties == null) {
			return null;			
		}
		
		return systemProperties.info;
	}
	
	public void setInfo(HashMap<String, String> info) {
		for (Map.Entry<String, String> entry : info.entrySet()) {
		    systemProperties.info.put(entry.getKey(), entry.getValue());
		}
	}
	
	public void setInfo(String param, String value) {
		systemProperties.info.put(param, value);
	}
	
	public void setInfo() {
		if (systemProperties == null) {
			systemProperties = new SystemProperties();
		}
		
		systemProperties.info.put("User Name", System.getProperty("user.name"));
		systemProperties.info.put("OS", System.getProperty("os.name"));
		systemProperties.info.put("Java Version", System.getProperty("java.version"));
		
		try {
			systemProperties.info.put("Host Name", InetAddress.getLocalHost().getHostName());
		} 
		catch(Exception e) { }
	}
	
	private SystemInfo() { }
	
	private static class Instance {
        static final SystemInfo INSTANCE = new SystemInfo();
    }
	
	public static SystemInfo getInstance() {
		//if (map == null) {
			//map = new HashMap<String, String>();
			//setInfo();
		//}
		
		return Instance.INSTANCE;
	}
	/*
	public SystemInfo() { 
		map = new HashMap<String, String>();
		
		setInfo();
	}*/
}
