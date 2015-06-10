package com.relevantcodes.extentreports.model;

import java.util.HashMap;
import java.util.Map;

public class SystemProperties {
	public Map<String, String> info = new HashMap<String, String>();
	
	/*
	private SystemProperties() { }
	
	private static class Instance {
        static final SystemProperties INSTANCE = new SystemProperties();
    }
	
	public static SystemProperties getInstance() {
		//if (info == null) {
			//info = new HashMap<String, String>();
		//}
		
		return Instance.INSTANCE;
	}
	*/
	
	public SystemProperties() {
		info = new HashMap<String, String>();		
	}
}
