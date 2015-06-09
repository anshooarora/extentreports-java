/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/


package com.relevantcodes.extentreports.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatcher {
	
	public static Matcher getMatch(String line, String pattern) {
	      Pattern p = Pattern.compile(pattern);
	      Matcher m = p.matcher(line);
	      
	      if (m.find()) {
	    	  return m;
	      }
	      
	      return null;
	}
	
	public static String getNthMatch(String line, String pattern, Integer n) {
		try {
			return getMatch(line, pattern).group(n);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
