package com.relevantcodes.cubereports.support;

public class Stream {
	@SuppressWarnings("resource")
	public static String toString(java.io.InputStream is) {
	    java.util.Scanner scanner = new java.util.Scanner(is).useDelimiter("\\A");
	    return scanner.hasNext() ? scanner.next() : "";
	}	
}
