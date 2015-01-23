package com.relevantcodes.cubereports.support;

import java.io.InputStream;

public class Resources {
	public static String getText(String resourcePath) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	  	InputStream input = classLoader.getResourceAsStream(resourcePath);
		
	  	return Stream.toString(input);
	}
}
