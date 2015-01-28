/*
Copyright 2015 ExtentReports committer(s)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0
	
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
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
