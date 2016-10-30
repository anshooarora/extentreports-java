package com.aventstack.extentreports.utils;

public class StringUtil {
    
    private StringUtil() { }
    
    public static String capitalize(String s) {
        if (s.isEmpty())
            return "";
        
        return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
    }
    
    public static boolean isNotNullOrEmpty (String str) {
    	return str != null && !str.isEmpty();
    }
    
}