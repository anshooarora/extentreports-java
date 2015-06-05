package com.relevantcodes.extentreports;

import com.relevantcodes.extentreports.support.RegexMatcher;

public class SourceBuilder {
	public static String build(String source, String[] flags, String[] values) {
		for (int ix = 0; ix < flags.length; ix++) {
			
			String matcher = flags[ix] + ".*" + flags[ix];
			String match = RegexMatcher.getNthMatch(source, matcher, 0);
			
			if (match == null) {
				source = source.replace(flags[ix], values[ix]);
			}
			else {				
				source = source.replace(match, matcher.replace(".*", values[ix]));
			
			}
		}
		
		return source;
	}
}
