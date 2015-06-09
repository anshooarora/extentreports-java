/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/


package com.relevantcodes.extentreports.support;

import java.util.Scanner;

public class Stream {
	public static String toString(java.io.InputStream is) {
		Scanner scanner = null;
		
	    try {
	    	scanner = new Scanner(is).useDelimiter("\\A");
	    	return scanner.hasNext() ? scanner.next() : "";
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
	    
		return null;
	}	
}
