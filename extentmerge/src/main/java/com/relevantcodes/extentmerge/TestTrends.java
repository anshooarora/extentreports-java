/*
* The MIT License (MIT)
* 
* Copyright (c) 2015, Anshoo Arora (Relevant Codes)
*/

package com.relevantcodes.extentmerge;

import java.util.HashMap;
import java.util.Map;

import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.utils.MapUtil;

class TestTrends {
	private static Map<String, Integer> testsMapPassed;
	private static Map<String, Integer> testsMapFailed;
	
	public static void setTest(String testName, LogStatus logStatus) {
		if (testsMapPassed == null || testsMapFailed == null) {
			testsMapPassed = new HashMap<String, Integer>();
			testsMapFailed = new HashMap<String, Integer>();
		}
		
		Integer value;
		
		if (logStatus == LogStatus.PASS) {
			if (testsMapPassed.containsKey(testName)) {
				value = testsMapPassed.get(testName) + 1;
				testsMapPassed.put(testName, value);
			}
			else {
				testsMapPassed.put(testName, 1);
			}
		}
		
		if (logStatus == LogStatus.FAIL) {
			if (testsMapFailed.containsKey(testName)) {
				value = testsMapFailed.get(testName) + 1;
				testsMapFailed.put(testName, value);
			}
			else {
				testsMapFailed.put(testName, 1);
			}
		}
	}
	
	public static Map<String, Integer> getTopPassed() {
		return MapUtil.getSortedMap(testsMapPassed);
	}
	
	public static Map<String, Integer> getTopFailed() {
		return MapUtil.getSortedMap(testsMapFailed);
	}
}
