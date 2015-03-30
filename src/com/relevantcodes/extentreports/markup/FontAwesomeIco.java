/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/


package com.relevantcodes.extentreports.markup;

import java.util.HashMap;

import com.relevantcodes.extentreports.LogStatus;

public class FontAwesomeIco {
	private static HashMap<LogStatus, String> map = new HashMap<LogStatus, String>();
	
	public static void override(LogStatus status, String icon) {
		map.put(status, icon);
	}
	
	public static String get(LogStatus status) {
		if (map.containsKey(status))
	        return map.get(status);
	
		if(status.toString().toLowerCase().equals(new String("fail"))) {
			return "times";
		}
		else if (status.toString().toLowerCase().equals(new String("error"))) {
			return "exclamation-circle";
		}
		else if (status.toString().toLowerCase().equals(new String("fatal"))) {
			return "exclamation-circle";
		}
		else if (status.toString().toLowerCase().equals(new String("pass"))) {
			return "check";
		}
		else if (status.toString().toLowerCase().equals(new String("info"))) {
			return "info";
		}
		else if (status.toString().toLowerCase().equals(new String("warning"))) {
			return "warning";
		}
		else if (status.toString().toLowerCase().equals(new String("skip"))) {
			return "angle-double-right";
		}
		else {
	        return "question";
		}
	}
}
