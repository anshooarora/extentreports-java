package com.relevantcodes.extentreports.source;

import com.relevantcodes.extentreports.support.RegexMatcher;

public class ExtentFlag {
	public static String getPlaceHolder(String flag) {
		return "<!--%%" + flag.toUpperCase() + "%%-->";
	}
	
	public static String clearStringWithinPlaceHolder(String source, String flag) {
		String pattern = ExtentFlag.getPlaceHolder(flag) + ".*" + ExtentFlag.getPlaceHolder(flag);
		String res = RegexMatcher.getNthMatch(source, pattern, 0); 

		if (res == null) {
			return source;
		}
		
		source = source.replace(res, ExtentFlag.getPlaceHolder(flag) + ExtentFlag.getPlaceHolder(flag));
		
		return source;
	}
	
	public static String insertStringWithinPlaceHolder(String source, String flag, String newString) {
		String pattern = ExtentFlag.getPlaceHolder(flag) + ".*" + ExtentFlag.getPlaceHolder(flag);
		String res = RegexMatcher.getNthMatch(source, pattern, 0); 

		source = source.replace(res, ExtentFlag.getPlaceHolder(flag) + newString + ExtentFlag.getPlaceHolder(flag));
		
		return source;
	}
}
